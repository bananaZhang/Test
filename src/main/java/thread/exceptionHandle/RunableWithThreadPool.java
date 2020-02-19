package thread.exceptionHandle;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class RunableWithThreadPool {

    private static final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public static void main(String[] args) {
        Future<?> future = executorService.submit(new MyTask());
        try {
            System.out.println("myTask的执行结果为: " + future.get());
        } catch (InterruptedException e) {
            System.out.println("任务被中断");
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }

    /**
     * 从程序运行的结果来看，Runnable结合Executor的情况下，仍然是和Callable的异常处理机制一样。
     * 我们可以这样总结：当使用Executor线程池相关框架来执行线程任务时，UncaughtExceptionHandler线程异常处理机制就是不生效的，
     * 这种情况下，线程内部的异常由线程池框架统一管理。当程序调用Future类的get()方法时，将感知到线程内部的运行时异常。
     * 当程序不使用Future类获取结果时，运行时异常将被线程池框架隐藏。
     */
    private static final class MyTask implements Runnable {
        @Override
        public void run() {
            Thread.currentThread().setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(Thread t, Throwable e) {
                    System.out.println("unchecked exception happened:");
                    System.out.println(t.getId());
                    System.out.println(t.getName());
                    e.printStackTrace(System.out);
                }
            });
            int sum = 0;
            for (int i = 4; i >= 0; i--) {
                sum = sum + (12 / i);
            }
        }
    }
}
