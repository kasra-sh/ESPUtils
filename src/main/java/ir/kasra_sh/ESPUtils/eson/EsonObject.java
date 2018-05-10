package ir.kasra_sh.ESPUtils.eson;

import com.google.gson.Gson;
import com.google.gson.internal.Primitives;
import com.sun.media.sound.UlawCodec;
import ir.kasra_sh.ESPUtils.ULog;
import ir.kasra_sh.ESPUtils.eson.internal.EClass;
import ir.kasra_sh.ESPUtils.estore.EKey;
import ir.kasra_sh.ESPUtils.estore.EStoreDB;
import ir.kasra_sh.ESPUtils.estore.EValue;
import ir.kasra_sh.ESPUtils.test.TestModel;

import java.util.ArrayList;

public class EsonObject {

    private static final String TAG = "EsonObject";

    private EStoreDB kv = new EStoreDB();

    private void mPut(String key, EsonElement element) {
        if (element.getValue() == null) {
            kv.put(key, EsonObject.NULL);
        } else {
            kv.put(key, element);
        }
    }

    public EsonObject put(String key, Integer num) {
        kv.put(key, EsonElement.make(num));
        return this;
    }

    public EsonObject put(String key, Long num) {
        kv.put(key, EsonElement.make(num));
        return this;
    }

    public EsonObject put(String key, Double num) {
        kv.put(key, EsonElement.make(num));
        return this;
    }

    public EsonObject put(String key, Boolean bool) {
        kv.put(key, EsonElement.make(bool));
        return this;
    }

    public EsonObject put(String key, String str) {
        kv.put(key, EsonElement.make(str));
        return this;
    }

    public EsonObject put(String key, EsonElement element) {
        kv.put(key, element);
        return this;
    }

    public EsonObject put(String key, EsonObject object) {
        kv.put(key, EsonElement.make(object));
        return this;
    }

    private EsonElement Null = EsonElement.makeNull();

    public EsonElement get(String key) {
        return kv.get(key, Null);
    }

    public String getString(String key) {
        return kv.get(key, Null).getString();
    }
//    public EsonElement get(String key) {
//        return kv.get(key, Null);
//    }
//    public EsonElement get(String key) {
//        return kv.get(key, Null);
//    }
//    public EsonElement get(String key) {
//        return kv.get(key, Null);
//    }

//    public void put(String key, Object obj){
//        kv.put(key, EsonElement.make(obj));
//    }

    public EsonObject put(String key, EsonArray array) {
        kv.put(key, EsonElement.make(array));
        return this;
    }

    @Override
    public String toString() {
        return toString(0);
    }

    public String toString(int indent) {
        return toString(0, indent);
    }

    protected String toString(int layer, int indent) {
        StringBuilder r = new StringBuilder();

        String ind = getIndent(indent);
        String layerInd = getIndent(layer * indent);

        ArrayList<EKey> keys = kv.getKeys();
        ArrayList<EValue> values = kv.getValues();
        if (layer == 0)
            r.append("{");
        if (keys.size() == 0) return r.append("}").toString();
        for (int i = 0; i < keys.size(); i++) {
            if (indent > 0) r.append("\n");
            EsonElement element = (EsonElement) values.get(i).getValue();
            r.append(layerInd)
                    .append(ind).append('"').append(keys.get(i).getKey()).append("\":");
            if (indent>0) r.append(' ');

            if (element.getType() == EsonType.OBJECT) {
                r.append("{");
                r.append(element.getObject().toString(layer + 1, indent));
            } else if (element.getType() == EsonType.ARRAY) {
                r.append("[");
                r.append(element.getArray().toString(layer + 1, indent));
            } else if (element.getType() == EsonType.STRING) {
                r.append('"').append(values.get(i).getValue()).append('"');
            } else {
                r.append(values.get(i).getValue());
            }
            if ((i + 1) < keys.size()) {
                r.append(',');
            }
        }
        if (indent > 0) r.append("\n");
        r.append(layerInd).append("}");

        return r.toString();

    }

    private String getIndent(int i) {
        if (i == 0) return "";
        StringBuilder ind = new StringBuilder();
        for (int j = 0; j < i; j++) {
            ind.append(' ');
        }
        return ind.toString();
    }

    private static final class Null {

        /**
         * There is only intended to be a single instance of the NULL object,
         * so the clone method returns itself.
         *
         * @return NULL.
         */
        @Override
        protected final Object clone() {
            return this;
        }

        /**
         * A Null object is equal to the null name and to itself.
         *
         * @param object An object to test for nullness.
         * @return true if the object parameter is the JSONObject.NULL object or
         * null.
         */
        @Override
        public boolean equals(Object object) {
            return object == null || object == this;
        }

        /**
         * A Null object is equal to the null name and to itself.
         *
         * @return always returns 0.
         */
        @Override
        public int hashCode() {
            return 0;
        }

        /**
         * Get the "null" string name.
         *
         * @return The string "null".
         */
        @Override
        public String toString() {
            return "null";
        }
    }

    public static final Object NULL = new Null();

    public EStoreDB getKeyValues() {
        return kv;
    }

    public <T> T mapTo(Class<T> tClass) {

        T obj = null;
        try {
            obj = tClass.newInstance();
            EClass eClass = queryClass(tClass);
            if (eClass.hasMapper()) {
//                ULog.i(TAG, "Using mapper!");
                obj = tClass.cast(eClass.getMapper().invoke(obj, this));
                return obj;
            }
            if (eClass == null) throw new Exception("Internal (EClass) is null!");
            for (EKey key : kv.getKeys()) {
//                System.out.println("Item : "+key.getKey());
                eClass.setValueByJSONName(
                        key.getKey(),
                        obj,
                        (EsonElement) kv.getById(key.getId())
                );

            }
        } catch (Exception e) {
            ULog.wtf(TAG, e);
        }
        return obj;
    }

    private EClass queryClass(Class clazz) {
        String className = clazz.getName();
        int index = Eson.classes.find(className);
//        if (className.contains("Object")) throw new NullPointerException();
//        ULog.i(TAG, "("+className+")index = "+index);
        if (index < 0) {
            Eson.classes.put(className, new EClass(clazz));
        }
        ULog.d(TAG, className);
        ULog.d(TAG, "class : "+Eson.classes.get(className).toString());
        return Eson.classes.get(className, new EClass(Object.class));
    }

}
