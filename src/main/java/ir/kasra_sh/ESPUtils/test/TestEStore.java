package ir.kasra_sh.ESPUtils.test;

import ir.kasra_sh.ESPUtils.estore.EKey;
import ir.kasra_sh.ESPUtils.estore.EStore;
import ir.kasra_sh.ESPUtils.estore.EValue;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TestEStore {

    public TestEStore() {
        Executor executor = Executors.newFixedThreadPool(2);
        executor.execute(putter);
        executor.execute(getter);
    }

    private Runnable putter = new Runnable() {
        @Override
        public void run() {
            int i=0;
            while (true) {
                EStore.getDB("test1").put(UUID.randomUUID().toString(), "XXXXX"+UUID.randomUUID().toString());
                i++;
                if (i%2 == 0) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };

    private Runnable getter = new Runnable() {
        @Override
        public void run() {
            boolean sh = false;
            int i=0;
            while (true) {
                List<EKey> keys = EStore.getDB("test1").getKeys();
                List<EValue> values = EStore.getDB("test1").getValues();
                int nk = keys.size();
                int nv = values.size();
                i++;
                if (i%2000 == 0) {
                    System.out.println("2000 reads");
                    System.out.println("Number of keys : "+nk);
                    System.out.println("Number of Values : "+nv);
                    if (keys.size()>0){
                        System.out.println("Last Key : "+keys.get(keys.size()-1).getKey());
                        System.out.println("Last Value : "+EStore.getDB("test1").get(keys.get(keys.size()-1).getKey()));

                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };




}
