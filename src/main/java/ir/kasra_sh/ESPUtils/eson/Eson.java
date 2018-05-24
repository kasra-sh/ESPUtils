package ir.kasra_sh.ESPUtils.eson;

import ir.kasra_sh.ESPUtils.ULog;
import ir.kasra_sh.ESPUtils.eson.internal.EsonParser;
import ir.kasra_sh.ESPUtils.estore.EStoreDB;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by blkr on 3/31/18.
 */

public class Eson {

    private static final String TAG = "ESON";

    protected static EStoreDB classes = new EStoreDB();

    public static EsonElement load(String json) {
        try {
            return new EsonParser(json).parse();
        } catch (Exception e) {
            e.printStackTrace();
            return EsonElement.makeNull();
        }
    }


    public static <T> T load(String json, Class<T> tClass) {
        try {
            return tClass.cast(new EsonParser(json).parse().mapTo(tClass));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> ArrayList<T> loadArray(String json, Class<T> itemType) {
        EsonElement element = EsonElement.make(new EsonArray());
        try {
            element = new EsonParser(json).parse();
        } catch (Exception e){
            ULog.wtf(TAG, e);
        }
        return element.getArray().mapTo(itemType);
    }


    public static String generate(Object src) {
        return generate(src, 0);
    }

    public static String generate(Object ... src) {
        return generate(src, 0);
    }

    public static String generate(ArrayList<?> objects) {
        return generate(objects, 0);
    }

    public static String generate(Object src, int indent) {
        return wrap(src).toString(indent);
    }


    public static EsonObject wrap(Object object) {
        return new Eson().objectFrom(object);
    }

    public static EsonArray wrap(List<?> object) {
        return new Eson().arrayFrom((ArrayList) object);
    }

    public static EsonArray wrap(Object ... objects) {
        return wrap(new ArrayList<>(Arrays.asList(objects)));
    }

    public EsonArray arrayFrom(List src) {
        EsonArray esonArray = new EsonArray();
        if (src == null) return esonArray;
        if (src.size() == 0) return esonArray;
        for (int i = 0; i < src.size(); i++) {
            esonArray.put(EsonElement.makeFromObject(src.get(i)));
        }
        return esonArray;
    }

    public EsonObject objectFrom(Object src) {
        EsonObject json = new EsonObject();
        if (src != null)
            try {
                Field[] fields = src.getClass().getDeclaredFields();
                for (Field f :
                        fields) {
                    if (f.getName().contains("$") || f.getName().equals("serialVersionUID")) {
                        continue;
                    }
                    f.setAccessible(true);
                    Annotation a;
                    String name = f.getName();
                    Type gat = null;
                    boolean required = false;
                    try {
                        a = f.getAnnotation(EsonField.class);
                        name = ((EsonField) a).name();
                        gat = ((EsonField) a).arrayType();
                    } catch (Exception e) {
                    }

                    try {
                        Class t = f.getType();
                        if (t == EsonObject.class) {
                            json.put(name, ((EsonObject) f.get(src)));
                        } else if (t == EsonArray.class) {
                            json.put(name, ((EsonArray) f.get(src)));
                        } else if (List.class.isAssignableFrom(t)) {
                            json.put(name, arrayFrom((List) f.get(src)));
                        } else if (isPrimitive(t)) {
                            json.put(name, EsonElement.makeFromObject(f.get(src)));
                        } else {
                            json.put(name, objectFrom(f.get(src)));
                        }
                    } catch (Exception e) {
                        ULog.wtf(TAG, e);
                    }
                }
            } catch (Exception e) {
                ULog.wtf(TAG, e);
                throw new NullPointerException("could not instantiate class");
            }
        return json;
    }

    //
//    public static EsonObject wrapObject(Object object) {
//            return new Eson().objectFrom(object);
//    }
//
//    public static EsonArray wrapArray(List list) {
//        return new Eson().arrayFrom(list);
//    }

    private boolean isPrimitive(Class<?> src) {
        return src.isPrimitive() ||
                src.isAssignableFrom(Byte.class) ||
                src.isAssignableFrom(Short.class) ||
                src.isAssignableFrom(Integer.class) ||
                src.isAssignableFrom(Long.class) ||
                src.isAssignableFrom(Float.class) ||
                src.isAssignableFrom(Double.class) ||
                src.isAssignableFrom(Character.class) ||
                src.isAssignableFrom(String.class) ||
                src.isAssignableFrom(Byte.class) ||
                src.isAssignableFrom(Boolean.class);
    }

}
