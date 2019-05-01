package CodeGeneration.Execution;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

class ThreadPoolExecutorSimple extends ThreadPoolExecutor {
    ThreadPoolExecutorSimple(int poolSize) {
        super(
                poolSize, //Minimal amount of threads
                poolSize, //Fixed pool size, so same as minimal amount of threads.
                1, //This will normally decide how long a non-core thread can exist being idle before termination. Irrelevant due to fixed pool size.
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(), // Causes new tasks to wait if all threads are busy.
                runnable -> { //Thread Factory
                    Thread thread = new Thread(runnable);
                    thread.setDaemon(true); //Does not prevent the program from exiting.
                    return thread;
                });
        this.allowCoreThreadTimeOut(true);
    }
}
