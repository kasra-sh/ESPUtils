package ir.kasra_sh.ESPUtils.estore;

import ir.kasra_sh.ESPUtils.ULog;

import java.util.ArrayList;
import java.util.List;

public class EStoreDB {

    private static final String TAG = "EStoreDB";
    private ArrayList<EKey> keys = new ArrayList<>();
    private ArrayList<EValue> values = new ArrayList<>();
    private long lastid = 0;

    public synchronized EStoreDB put(String key, Object value) {
        int fnd = find(key);
        if (fnd < 0) {
            keys.add(new EKey(lastid, key));
            values.add(new EValue<>(lastid, value));
            lastid++;
        } else {
            del(fnd);
            keys.add(new EKey(lastid, key));
            values.add(new EValue<>(lastid, value));
            lastid++;
        }
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

    private synchronized EStoreDB del(int i) {
        keys.remove(i);
        values.remove(i);
        return this;
    }


    public synchronized <T> T get(String key, T defValue) {
        int hsh = key.hashCode();
        for (int i = 0; i < keys.size(); i++) {
            if (hsh == keys.get(i).getKey().hashCode()) {
                if (values.get(i).getValue().getClass().isAssignableFrom(defValue.getClass()))
                    return (T) values.get(i).getValue();
                else {
                    ULog.w(TAG, values.get(i).getValue().getClass().getName()
                            + " is not assignable from " + defValue.getClass().getName());
                    break;
                }
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

    public synchronized int find(String key) {
        int hsh = key.hashCode();
        for (int i = 0; i < keys.size(); i++) {
            if (hsh == keys.get(i).getKey().hashCode()) {
                return i;
            }
        }
        return -1;
    }

    public synchronized Object getById(long id) {
        for (EValue ev : values) {
            if (ev.getId() == id) {
                return ev.getValue();
            }
        }
        return null;
    }

    public ArrayList<EKey> getKeys() {
        return keys;
    }

    public ArrayList<EValue> getValues() {
        return values;
    }
}
