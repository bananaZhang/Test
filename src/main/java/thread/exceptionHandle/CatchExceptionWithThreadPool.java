package thread.exceptionHandle;

import java.util.concurrent.*;

public class CatchExceptionWithThreadPool {
    // 自定义一个线程池
    private static final ExecutorService executorService = new ThreadPoolExecutor(1, 1, 1, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10)) {
        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            System.out.println("afterExecute...");
            super.afterExecute(r, t);
            printException(r, t);
        }

        private void printException(Runnable r, Throwable t) {

            if (t == null && r instanceof Future<?>) {
                try {
                    Object result = ((Future<?>) r).get();
                } catch (CancellationException ce) {
                    t = ce;
                } catch (ExecutionException ee) {
                    t = ee.getCause();
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt(); // ignore/reset
                }
            }
            if (t != null) {
                t.printStackTrace(System.out);
                executeTask();
            }
        }
    };

    public static void main(String[] args) throws InterruptedException {
        executeTask();
        executorService.awaitTermination(5, TimeUnit.SECONDS);
        executorService.shutdownNow();
    }

    private static void executeTask() {
        executorService.submit(new MyTask());
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static final class MyTask implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            int sum = 0;
            for (int i = 4; i >= 0; i--) {
                sum = sum + (12 / i);
            }
            return sum;
        }
    }
}
