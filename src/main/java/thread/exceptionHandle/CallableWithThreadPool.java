package thread.exceptionHandle;

import java.util.concurrent.*;

public class CallableWithThreadPool {

    private static final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public static void main(String[] args) {
        Future<Integer> future = executorService.submit(new MyTask());
        try {
            System.out.println("myTask的执行结果为: " + future.get());
        } catch (InterruptedException e) {
            System.out.println("任务被中断");
            e.printStackTrace();
        } catch (ExecutionException e) {
            System.out.println("ExecutionException...");
            e.printStackTrace();
        }
        executorService.shutdown();
    }

    /**
     * 在线程池中，线程池Executor会Catch住所有运行时异常，当使用Future.get()获取其结果时，才会抛出这些运行时异常。
     * 从JDK对Future.get()方法的定义可见：Callable线程中抛出的非受检异常会被Executor框架捕获到，然后通过Future类的get()方法传递给调用者。
     * get()方法的ExecutionException异常就是Runnable线程或者Callable线程抛出的异常。
     * */
    private static final class MyTask implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            Thread.currentThread().setUncaughtExceptionHandler((Thread t, Throwable e) -> {
                System.out.println("unchecked exception happened:");
                System.out.println(t.getId());
                System.out.println(t.getName());
                e.printStackTrace(System.out);
            });

            int sum = 0;
            for (int i = 4; i >= 0; i--) {
                sum = sum + (12 / i);
            }
            return sum;
        }
    }
}
