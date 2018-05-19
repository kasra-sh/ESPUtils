package ir.kasra_sh.ESPUtils.ereqt;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EReqt {

    private Executor executor;
    private static EReqt def;
    private int retries;
    private int timeout;

    static {
        def = new EReqt(3, 7000, 3);
    }

    private EReqt(int i, int timeout, int retries){
        executor = Executors.newFixedThreadPool(i);
        this.timeout = timeout;
        this.retries = retries;
    }

    public EReqt make(int poolSize, int timeout_ms, int retries) {
        return new EReqt(poolSize, timeout_ms, retries);
    }

    public static EReqt getDefault() {
        return def;
    }
}
