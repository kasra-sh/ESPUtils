package ir.kasra_sh.ESPUtils.eson;

import ir.kasra_sh.ESPUtils.eson.internal.EField;

public interface EsonDeserializer {
    void deserialize(Object origin, EField field, EsonElement element);
}
