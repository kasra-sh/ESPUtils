package ir.kasra_sh.ESPUtils.ereqt;

import java.util.concurrent.Executor;

public class EReqt {

    private Executor executor;
    private static EReqt def;
    private int retries;
    private int timeout;

    private EReqt(int i, int timeout, int retries){
        executor = new ThreadPoolCompatExecutor(i);
        this.timeout = timeout;
        this.retries = retries;
    }

    public static EReqt make(int poolSize, int timeout_ms, int retries) {
        return new EReqt(poolSize, timeout_ms, retries);
    }

    public synchronized static EReqt getDefault() {
        if (def==null) {
            def = new EReqt(2, 10000, 5);
        }
        return def;
    }

    public EReqt enqueue(ERequest request, ResponseListener listener) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                listener.onResponse(new RequestResult(request));
            }
        });
        return this;
    }

}
