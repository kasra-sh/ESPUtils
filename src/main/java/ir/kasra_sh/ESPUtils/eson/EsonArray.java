package ir.kasra_sh.ESPUtils.eson;

import ir.kasra_sh.ESPUtils.estore.EKey;
import ir.kasra_sh.ESPUtils.estore.EValue;

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
        return toString(0, indent);
    }

    protected String toString(int layer, int indent) {
        StringBuilder r = new StringBuilder();

        String ind = getIndent(indent);
        String layerInd = getIndent(layer * indent);

        if (layer == 0)
            r.append("[");
        if (list.size() == 0) return r.append("]").toString();
        for (int i = 0; i < list.size(); i++) {
            if (indent > 0) r.append("\n");
            EsonElement element = (EsonElement) list.get(i);
            if (element.getType() == EsonType.OBJECT) {
                if (indent>0) {
                    r.append(layerInd).append(ind);
                }
                r.append("{");
                r.append(element.getObject().toString(layer + 1, indent));
            } else if (element.getType() == EsonType.ARRAY) {
                r.append("[");
                r.append(ind).append(layerInd).append(element.getArray().toString(layer + 1, indent));
            } else {
                r.append(ind).append(layerInd).append(ind).append(element.getValue());
            }
            if ((i + 1) < list.size()) {
                r.append(',');
            }
        }
        if (indent > 0) r.append("\n");
        r.append(layerInd).append("]");

        return r.toString();

    }

    private String getIndent(int i) {
        if (i == 0) return "";
        StringBuilder ind = new StringBuilder();
        for (int j = 0; j < i; j++) {
            ind.append(' ');
        }
        return ind.toString();
    }
}
