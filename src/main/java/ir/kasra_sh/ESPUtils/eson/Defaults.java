package ir.kasra_sh.ESPUtils.eson;


import ir.kasra_sh.ESPUtils.eson.internal.EField;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Defaults {

    public static final EsonSerializer primitiveSerializer = new EsonSerializer() {
        @Override
        public EsonElement serialize(Object field) {
            Class<?> sType = field.getClass();
//            System.out.println(field.getClass());
            if (sType == Short.TYPE || Short.class.isAssignableFrom(sType)) {
                return EsonElement.make(Integer.valueOf(Short.class.cast(field)));
            } else if (sType == Integer.TYPE || Integer.class.isAssignableFrom(sType)) {
                return EsonElement.make(Integer.class.cast(field));
            } else if (sType == Long.TYPE || Long.class.isAssignableFrom(sType)) {
                return EsonElement.make(Long.class.cast(field));
            } else if (sType == Float.TYPE || Float.class.isAssignableFrom(sType)) {
                return EsonElement.make(Double.valueOf(Float.class.cast(field)));
            } else if (sType == Double.TYPE || Double.class.isAssignableFrom(sType)) {
                return EsonElement.make(Double.class.cast(field));
            } else if (sType == Character.TYPE || Character.class.isAssignableFrom(sType)) {
                return EsonElement.make(String.valueOf(Character.class.cast(field)));
            } else if (sType == Byte.TYPE || Byte.class.isAssignableFrom(sType)) {
                return EsonElement.make(Integer.valueOf(Byte.class.cast(field)));
            } else if (sType == Boolean.TYPE || Boolean.class.isAssignableFrom(sType)) {
                return EsonElement.make(Boolean.class.cast(field));
            } else if (String.class.isAssignableFrom(sType)) {
                return EsonElement.make(String.valueOf(field));
            } else {
//                System.out.println("NUlllle keee");
                return EsonElement.make(new Eson().objectFrom(field));
//                if (EsonElement.makeFromObject(field).getValue() == null) {
//                    return EsonElement.makeNull();
//                } else {
//                    return EsonElement.makeFromObject(field);
//                }
            }
        }
    };

    public static final EsonDeserializer defaultDeserializer = new EsonDeserializer() {
        @Override
        public void deserialize(Object origin, EField field, EsonElement element) {
            try {
                if (field.getType() == Short.TYPE || Short.class.isAssignableFrom(field.getType())) {
                    field.getField().set(origin, element.getInt());
                } else if (field.getType() == Integer.TYPE || Integer.class.isAssignableFrom(field.getType())) {
                    field.getField().set(origin, element.getInt());
                } else if (field.getType() == Long.TYPE || Long.class.isAssignableFrom(field.getType())) {
                    field.getField().set(origin, element.getLong());
                } else if (field.getType() == Float.TYPE || Float.class.isAssignableFrom(field.getType())) {
                    field.getField().set(origin, element.getDouble());
                } else if (field.getType() == Double.TYPE || Double.class.isAssignableFrom(field.getType())) {
                    field.getField().set(origin, element.getDouble());
                } else if (field.getType() == Character.TYPE || Character.class.isAssignableFrom(field.getType())) {
                    field.getField().set(origin, element.getString());
                } else if (field.getType() == Byte.TYPE || Byte.class.isAssignableFrom(field.getType())) {
                    field.getField().set(origin, element.getInt());
                } else if (field.getType() == Boolean.TYPE || Boolean.class.isAssignableFrom(field.getType())) {
                    field.getField().set(origin, element.getBool());
                } else if (String.class.isAssignableFrom(field.getType())) {
                    field.getField().set(origin, element.getString());
                } else if (element.getType() == EsonType.OBJECT) {
                    field.getField().set(origin, element.getObject().mapTo(field.getType()));
                } else if (element.getType() == EsonType.ARRAY) {
                    field.getField().set(origin, element.getArray().mapTo(field.getArrayGenericType()));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };

    private static final EsonSerializer dateSerializer = new EsonSerializer() {
        @Override
        public EsonElement serialize(Object field) {
            try {
                return EsonElement.make(SimpleDateFormat.getDateTimeInstance().format(field));
            } catch (Exception e) {
                System.out.println(field);
                e.printStackTrace();
            }
            return EsonElement.makeNull();
        }
    };

    private static final EsonDeserializer dateDeserializer = new EsonDeserializer() {
        @Override
        public void deserialize(Object origin,EField field, EsonElement type) {
            try {
                Date date = DateFormat.getDateInstance().parse(type.toString());
//                return date;
                field.getField().set(origin, date);
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                field.getField().set(origin, null);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    };

    private static boolean init = false;

    protected synchronized static void init() {
        if (init) return;
        try {
            Converters.addSerializer(dateSerializer,
                    Date.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Converters.addSerializer(primitiveSerializer,
//                Byte.class, Byte.TYPE,
//                Character.class, Character.TYPE,
//                Short.class, Short.TYPE,
//                Integer.class, Integer.TYPE,
//                Long.class, Long.TYPE,
//                Float.class, Float.TYPE,
//                Double.class, Double.TYPE,
//                String.class,
//                Boolean.class, Boolean.TYPE
//        );

        Converters.addDeserializer(dateDeserializer, EsonType.STRING, Date.class);

        init = true;
    }
}
