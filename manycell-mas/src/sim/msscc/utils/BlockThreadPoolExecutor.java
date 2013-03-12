package sim.msscc.utils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BlockThreadPoolExecutor extends ThreadPoolExecutor {
        
    private Semaphore semaphore;
  //  BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(100, true);
    public BlockThreadPoolExecutor(int coreSize, int maxSize, int bound, long time, TimeUnit timeUnit,  BlockingQueue<Runnable> queue) {
        super(coreSize, maxSize,time, timeUnit, queue);
        this.semaphore = new Semaphore(bound);
    }    

    @Override
    public void execute(Runnable task) {
        boolean acquired = false;
        do {
            try {
                semaphore.acquire();
                acquired = true;
            } catch (InterruptedException e) {
                // wait forever!
            }                   
        } while(!acquired);

        try {
            super.execute(task);
        } catch(RuntimeException e) {
            // specifically, handle RejectedExecutionException  
            semaphore.release();
            throw e;
        } catch(Error e) {
            semaphore.release();
            throw e;
        }
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        semaphore.release();
    }
}

