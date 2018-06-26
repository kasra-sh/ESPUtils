package ir.kasra_sh.ESPUtils.ereqt;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolCompatExecutor implements Executor, Closeable {

    private Thread[] threads;
    private TPCRunnable[] workers;
    private int turn = 0;

    public ThreadPoolCompatExecutor(int threadCount) {
        threads = new Thread[threadCount];
        workers = new TPCRunnable[threadCount];
        for (int i = 0; i < threads.length; i++) {
            workers[i] = new TPCRunnable();
            threads[i] = new Thread(workers[i]);
            threads[i].setName("TPCWorker-" + i);
            threads[i].setDaemon(true);
            threads[i].start();
        }
    }

    @Override
    public synchronized void execute(Runnable runnable) {
        if (turn >= threads.length) {
            turn = 0;
        }
        workers[turn].addTask(runnable);
        turn++;
    }

    @Override
    public void close() throws IOException {
        for (int i = 0; i < threads.length; i++) {
            threads[i].interrupt();
        }
    }

    class TPCRunnable implements Runnable {
        private ConcurrentLinkedQueue<Runnable> tasks = new ConcurrentLinkedQueue<>();

        public void addTask(Runnable r) {
            tasks.add(r);
        }

        @Override
        public void run() {
            while (true) {
                if (!tasks.isEmpty()) {
                    tasks.remove().run();
                } else {
                    try {
                        TimeUnit.MILLISECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            }
        }
    }
}
