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

        /**
         * 注意：在这里如果是用executorService.submit方法执行的线程，这里的t为null
         * （因为submit会将Runnable包装成FutureTask，在FutureTask的run方法中把异常吃掉了）；
         * 如果是用executorService.execute方法执行的线程，t则为线程执行时抛出的异常
         * 参考：https://segmentfault.com/a/1190000000669942
         */
        private void printException(Runnable r, Throwable t) {
            /**
             * t == null的情况使用future.get()来获取线程执行的异常
             */
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
        /**
         * submit方法都会返回一个Future对象
         */
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
