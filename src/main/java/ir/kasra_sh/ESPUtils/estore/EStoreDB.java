package ir.kasra_sh.ESPUtils.estore;

import java.util.ArrayList;
import java.util.List;

public class EStoreDB {
    private ArrayList<EKey> keys = new ArrayList<>();
    private ArrayList<EValue> values = new ArrayList<>();
    private long lastid = 0;

    public synchronized EStoreDB put(String key, Object value) {
        keys.add(new EKey(lastid, key));
        values.add(new EValue<Object>(lastid, value));
        lastid++;
        return this;
    }

    public synchronized EStoreDB del(String key) {
        int hsh = key.hashCode();
        for (int i = 0; i < keys.size(); i++) {
            if (hsh == keys.get(i).hashCode()) {
                keys.remove(i);
                values.remove(i);
            }
        }
        return this;
    }

    public synchronized <T> T get(String key, T defValue) {
        int hsh = key.hashCode();
        for (int i = 0; i < keys.size(); i++) {
            if (hsh == keys.get(i).getKey().hashCode()) {
                if (values.get(i).getValue().getClass().isAssignableFrom(defValue.getClass()))
                    return (T) values.get(i).getValue();
                else break;
            }
        }
        return defValue;
    }

    public synchronized Object get(String key) {
        int hsh = key.hashCode();
        for (int i = 0; i < keys.size(); i++) {
            if (hsh == keys.get(i).getKey().hashCode()) {
                return values.get(i).getValue();
            }
        }
        return null;
    }

    public List<EKey> getKeys() {
        return keys;
    }

    public List<EValue> getValues() {
        return values;
    }
}
