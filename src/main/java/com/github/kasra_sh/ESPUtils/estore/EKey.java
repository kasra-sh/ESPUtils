package com.github.kasra_sh.ESPUtils.estore;

import java.lang.reflect.Type;

public class EKey {
    private Long id;
    private String key;
    private Type type;


    public EKey() {
    }

    public EKey(Long id, String key) {
        this.id = id;
        this.key = key;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
