package com.github.kasra_sh.ESPUtils.eventman;

public class DefMessage<T> {
    private Long id=0L;
    private String tag="";
    private T data=null;

    public DefMessage() {
    }

    public DefMessage(Long id, String tag, T data) {
        this.id = id;
        this.tag = tag;
        this.data = data;
    }

    public DefMessage(String tag, T data) {
        this.tag = tag;
        this.data = data;
    }

    public DefMessage(T data) {
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
