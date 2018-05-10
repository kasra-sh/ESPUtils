package ir.kasra_sh.ESPUtils.eson;

import java.util.List;

public class EsonElement {

    private Object value;

    private EsonType type;

    public EsonElement(Object value, EsonType type){
        this.value = value;
        this.type = type;
    }

    public static EsonElement make(Integer v){
        return new EsonElement(v, EsonType.INTEGER);
    }

    public static EsonElement make(Long v){
        return new EsonElement(v, EsonType.LONG);
    }

    public static EsonElement make(Boolean v){
        return new EsonElement(v, EsonType.BOOLEAN);
    }

    public static EsonElement make(Double v){
        return new EsonElement(v, EsonType.DOUBLE);
    }

    public static EsonElement make(String v){
        return new EsonElement(v, EsonType.STRING);
    }

    public static EsonElement make(EsonObject v){
        return new EsonElement(v, EsonType.OBJECT);
    }

    public static EsonElement make(EsonArray v){
        return new EsonElement(v, EsonType.ARRAY);
    }

    public static EsonElement makeNull() {
        return new EsonElement(null, EsonType.NULL);
    }

    public Integer getInt() {
        Integer integer = null;
        try {
            integer = getDoubleValue().intValue();
        } catch (Exception e){
            //e.printStackTrace();
        }
        return integer;
    }

    public Long getLong() {
        Long lng = null;
        try {
            lng = getDoubleValue().longValue();
        } catch (Exception e){
//            e.printStackTrace();
        }
        return lng;
    }

    public Boolean getBool() {
        Boolean val = null;
        try {
            val = Boolean.class.cast(value);
        } catch (Exception e){}
        return val;
    }

    public Double getDouble() {
        return getDoubleValue();
    }

    public String getString() {
        return String.valueOf(value);
    }

    public EsonObject getObject() {
        return EsonObject.class.cast(value);
    }

    public EsonArray getArray() {
        return EsonArray.class.cast(value);
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public EsonType getType() {
        return type;
    }

    public void setType(EsonType type) {
        this.type = type;
    }

    public static EsonElement makeFromObject(Object src) {
        if (src == null) return EsonElement.makeNull();
        Class<?> sType = src.getClass();

        if (sType == Short.TYPE || Short.class.isAssignableFrom(sType)) {
            return EsonElement.make(Integer.valueOf(Short.class.cast(src)));
        } else if (sType == Integer.TYPE || Integer.class.isAssignableFrom(sType)) {
            return EsonElement.make(Integer.class.cast(src));
        } else if (sType == Long.TYPE || Long.class.isAssignableFrom(sType)) {
            return EsonElement.make(Long.class.cast(src));
        } else if (sType == Float.TYPE || Float.class.isAssignableFrom(sType)) {
            return EsonElement.make(Double.valueOf(Float.class.cast(src)));
        } else if (sType == Double.TYPE || Double.class.isAssignableFrom(sType)) {
            return EsonElement.make(Double.class.cast(src));
        } else if (sType == Character.TYPE || Character.class.isAssignableFrom(sType)) {
            return EsonElement.make(String.valueOf(Character.class.cast(src)));
        } else if (sType == Byte.TYPE || Byte.class.isAssignableFrom(sType)) {
            return EsonElement.make(Integer.valueOf(Byte.class.cast(src)));
        } else if (sType == Boolean.TYPE || Boolean.class.isAssignableFrom(sType)) {
            return EsonElement.make(Boolean.class.cast(src));
        } else if (String.class.isAssignableFrom(sType)) {
            return EsonElement.make(String.valueOf(src));
        } else {
            // SKIPPED !
//            System.out.println("Obj "+sType.getName());
            return EsonElement.make(new Eson().objectFrom(src));
        }
    }

    public String toString(int indent) {
        if (type == EsonType.OBJECT) {
            return getObject().toString(indent);
        } else if (type == EsonType.ARRAY){
            return getArray().toString(indent);
        } else
            return String.valueOf(value);
    }

    @Override
    public String toString() {
        if (type == EsonType.OBJECT) {
            return getObject().toString();
        } else if (type == EsonType.ARRAY) {
            return getArray().toString();
        } else {
            return String.valueOf(value);
        }
    }

    public Object mapTo(Class<?> tClass) {
        Object ret = null;
        if (type == EsonType.ARRAY || tClass.isAssignableFrom(List.class)) {
            System.out.println("isArray");
            ret = tClass.cast(getArray().mapTo(tClass));
        } else if (type == EsonType.OBJECT) {
            ret = getObject().mapTo(tClass);
        }
        try {
//            tClass.cast(ret);
            return ret;
        } catch (Exception e){
            e.printStackTrace();
            try {
                return tClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e1) {
                return null;
            }
        }
    }

    public boolean isArray() {
        return type == EsonType.ARRAY;
    }

    public boolean isObject() {
        return type == EsonType.OBJECT;
    }

    private Double getDoubleValue() {
        if (type == EsonType.INTEGER) {
            return Double.valueOf(Integer.class.cast(value));
        } else if (type == EsonType.LONG) {
            return Double.valueOf(Long.class.cast(value));
        } else if (type == EsonType.DOUBLE) {
            return Double.class.cast(value);
        } else {
            return null;
        }
    }

}
