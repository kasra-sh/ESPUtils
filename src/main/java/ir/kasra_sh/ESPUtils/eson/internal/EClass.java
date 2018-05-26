package ir.kasra_sh.ESPUtils.eson.internal;

import ir.kasra_sh.ESPUtils.ULog;
import ir.kasra_sh.ESPUtils.eson.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class EClass {
    private static final String TAG = "Eson";
    private String name;
    private Class type;
    private ArrayList<EField> fields = new ArrayList<>();
    private Method mapper = null;

    public boolean hasMapper() {
        return mapper != null;
    }

    public Method getMapper() {
        return mapper;
    }

    public EClass(Class clazz) {
        type = clazz;
        name = clazz.getName();
        Field[] fs = clazz.getDeclaredFields();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method m : methods) {
            if (m.getAnnotation(EsonMapper.class) == null) {
                continue;
            }
            if (m.getParameterCount() != 1) {
                ULog.w(TAG, "Mapper method must have only 1 parameter! "
                        + clazz.getSimpleName()
                        + "." + m.getName() + "()");
                continue;
            }
            if (m.getParameters()[0].getType() != EsonObject.class) {
                ULog.w(TAG, "Mapper method must have only 1 parameter! "
                        + clazz.getSimpleName()
                        + "." + m.getName() + "()");
                continue;
            }
            if (m.getReturnType() != clazz) {
                ULog.w(TAG, "Mapper method must return it's own class type! "
                        + clazz.getSimpleName()
                        + "." + m.getName() + "()");
                continue;
            }
            ULog.i(TAG, "Class (" + clazz.getSimpleName() + ") uses \"" + m.getName() + "(EsonObject)\" as mapper !");
            m.setAccessible(true);
            mapper = m;
            break;
        }
        for (int i = 0; i < fs.length; i++) {
            String fname = fs[i].getName();
            Class<?> genType = Object.class;
            if (fname.startsWith("$") || fname.equals("serialVersionUID")) continue;
            try {
                Annotation a = fs[i].getAnnotation(EsonField.class);
                fname = ((EsonField) a).name();
                genType = ((EsonField) a).arrayType();
            } catch (Exception e) {
            }

            fs[i].setAccessible(true);
            fields.add(new EField(fs[i], fname, fs[i].getType(), genType));
        }
    }

    public void setValueByJSONName(String jname, Object origin, EsonElement element) {
        for (EField field : fields) {
            if (field.getJsonName().equals(jname)) {
                field.getField().setAccessible(true);
                try {
                    Class<?> sType = field.getType();
                    Converters.getDeserializer(sType, element.getType());
//                    System.out.println("Weirdo :| -> " + element.getType().toString() + ", " + field.getType().getName());
                    EsonDeserializer des = Converters.getDeserializer(field.getType(), element.getType());
                    if (des != null)
                        des.deserialize(origin, field, element);
                    else {
                        System.out.println("Nashod !");
                    }
                } catch (Exception e) {
                    System.out.println("Field Type " + field.getType());
//                    e.printStackTrace();
                }
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public ArrayList<EField> getFields() {
        return fields;
    }

    public void setFields(ArrayList<EField> fields) {
        this.fields = fields;
    }
}