package ir.kasra_sh.ESPUtils.eson.internal;

import java.lang.reflect.Field;

public class EField {
    private Field field;
    private String jsonName;
    private Class<?> type;
    private Class<?> arrayGenericType;

    public EField(Field field, String jsonName, Class<?> type, Class<?> arrayGenType) {
        this.field = field;
        this.jsonName = jsonName;
        this.type = type;
        arrayGenericType = arrayGenType;
    }

    public EField() {
    }

    public Field getField() {
        return field;
    }

    public void setField(Field fields) {
        this.field = fields;
    }

    public String getJsonName() {
        return jsonName;
    }

    public void setJsonName(String jsonName) {
        this.jsonName = jsonName;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public Class<?> getArrayGenericType() {
        return arrayGenericType;
    }

    public void setArrayGenericType(Class<?> arrayGenericType) {
        this.arrayGenericType = arrayGenericType;
    }
}
