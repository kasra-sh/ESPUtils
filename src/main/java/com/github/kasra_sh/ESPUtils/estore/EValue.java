package com.github.kasra_sh.ESPUtils.estore;

public class EValue<T> {
    private T value;
    private Long id;

    public EValue() {
    }

    public EValue(Long id, T value) {
        this.value = value;
        this.id = id;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
