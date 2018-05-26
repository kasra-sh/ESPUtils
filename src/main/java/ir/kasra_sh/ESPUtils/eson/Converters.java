package ir.kasra_sh.ESPUtils.eson;

import java.util.ArrayList;
import java.util.Arrays;

public final class Converters {

    static class SPair {
        public EsonSerializer serializer;
        public ArrayList<Class<?>> types = new ArrayList<>();

        public SPair() {
        }
    }

    static class DTrio {
        public EsonDeserializer deserializer;
        public Class<?> type;
        public EsonType esonType;
    }

    private static ArrayList<SPair> serializers = new ArrayList<>();
    private static ArrayList<DTrio> deserializers = new ArrayList<>();

    public static EsonSerializer getSerializer(Class<?> type) {
        for (int i = 0; i < serializers.size(); i++) {
            SPair ser = serializers.get(i);
            for (int j = 0; j < ser.types.size(); j++) {
                if (type == ser.types.get(j)) {
                    return ser.serializer;
                }
            }
        }
        return Defaults.primitiveSerializer;
    }

    public synchronized static void addSerializer(EsonSerializer serializer, Class<?>... types) throws Exception {
        for (int i = 0; i < types.length; i++) {
            if (Eson.isPrimitive(types[i])) {
                throw new Exception("Cannot override default serializers");
            }
        }
        for (int i = 0; i < serializers.size(); i++) {

            if (getSerializer(types[i]) != null) {

            }
        }
        SPair sp = new SPair();
        sp.serializer = serializer;
        sp.types.addAll(Arrays.asList(types));
        serializers.add(sp);
    }

    public static <T> EsonDeserializer getDeserializer(Class<T> type, EsonType esonType) {
        for (int i = 0; i < deserializers.size(); i++) {
            DTrio dser = deserializers.get(i);
            if (type == dser.type && esonType == dser.esonType) {
                return dser.deserializer;
            }
        }
        return Defaults.defaultDeserializer;
    }

    public static synchronized <T> void addDeserializer(EsonDeserializer deserializer, EsonType esonType ,Class<?>... types) {

        for (int i = 0; i < types.length; i++) {
            DTrio dTrio = new DTrio();
            dTrio.deserializer = deserializer;
            dTrio.esonType = esonType;
            dTrio.type = types[i];
            deserializers.add(dTrio);
        }
    }
}
