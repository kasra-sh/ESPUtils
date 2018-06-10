package ir.kasra_sh.ESPUtils.eson;

import ir.kasra_sh.ESPUtils.eson.internal.EsonWriter;

import java.util.ArrayList;

public class EsonArray {
    private ArrayList<EsonElement> list = new ArrayList<>();

    public EsonArray put(EsonElement v) {
        list.add(v);
        return this;
    }

    public EsonArray put(Integer v) {
        list.add(EsonElement.make(v));
        return this;
    }

    public EsonArray put(Long v) {
        list.add(EsonElement.make(v));
        return this;
    }

    public EsonArray put(Double v) {
        list.add(EsonElement.make(v));
        return this;
    }

    public EsonArray put(Boolean v) {
        list.add(EsonElement.make(v));
        return this;
    }

    public EsonArray put(String v) {
        list.add(EsonElement.make(v));
        return this;
    }

    public EsonArray put(EsonObject v) {
        list.add(EsonElement.make(v));
        return this;
    }

    public EsonArray put(EsonArray v) {
        list.add(EsonElement.make(v));
        return this;
    }

    public EsonElement get(int i) {
        return list.get(i);
    }

    public int length() {
        return list.size();
    }

    public <T> ArrayList<T> mapTo(Class<T> itemType) {
        ArrayList<T> arrayList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            EsonElement item = list.get(i);
            if (item.getType() == EsonType.OBJECT) {
                arrayList.add(item.getObject().mapTo(itemType));
            }
        }
        return arrayList;
    }

    @Override
    public String toString() {
        return toString(0);
    }

    public String toString(int indent) {
        return new EsonWriter().writeArray(this, indent, 0);
    }

}
