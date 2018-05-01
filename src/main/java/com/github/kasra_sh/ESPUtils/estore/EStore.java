package com.github.kasra_sh.ESPUtils.estore;

import java.util.HashMap;
import java.util.Set;

public class EStore {
    private static HashMap<String, EStoreDB> dbs = new HashMap<>(10);

    public static synchronized void initDB(String dbname) {
        if (!dbs.containsKey(dbname)) {
            dbs.put(dbname, new EStoreDB());
        }
    }

    public static synchronized EStoreDB getDB(String dbname) {
        if (!dbs.containsKey(dbname)) {
            initDB(dbname);
        }

        return dbs.get(dbname);
    }

    public static synchronized Set<String> getDBs() {
        return dbs.keySet();
    }
}
