package ir.kasra_sh.ESPUtils.eson;

public enum EsonType {
    INTEGER,
    LONG,
    DOUBLE,
    STRING,
    OBJECT,
    ARRAY,
    BOOLEAN,
    NULL
    ;
    public Class<?> getClassType(EsonType type) {
        switch (type) {
            case INTEGER:
                return Integer.class;
            case LONG:
                return Long.class;
            case DOUBLE:
                return Double.class;
            case STRING:
                return String.class;
            case OBJECT:
                return EsonObject.class;
            case ARRAY:
                return EsonArray.class;
            case BOOLEAN:
                return Boolean.class;
            default:
                return EsonObject.NULL.getClass();
        }
    }
}
