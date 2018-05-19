package ir.kasra_sh.ESPUtils.ereqt;

public class KeyValue {
    public String key;
    public String value;

    public KeyValue(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static KeyValue make(String k, String v) {
        return new KeyValue(k, v);
    }
}
