package googleGuava.threadcallback;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author ZJY
 * @ClassName: Test
 * @Description: 传统JDK中的Future通过异步的方式计算返回结果:在多线程运算中可能或者可能在没有结束返回结果，
 * Future是运行中的多线程的一个引用句柄，确保在服务执行返回一个Result。ListenableFuture可以允许你注册回调方法(callbacks)，
 * 在运算（多线程执行）完成的时候进行调用,  或者在运算（多线程执行）完成后立即执行。这样简单的改进，
 * 使得可以明显的支持更多的操作，这样的功能在JDK concurrent中的Future是不支持的。
 * @date 2018/11/9 10:57
 */
public class Test {
    public static void main(String[] args) {

    }

    private void singleThread() {
        // ListenableFutureTask通过静态create方法返回实例，还有一个重载方法，不太常用
        ListenableFutureTask<String> task = ListenableFutureTask.create(() -> "");

        new Thread(task).start();
        /**
         * 增加回调方法，MoreExecutors.directExecutor()返回guava默认的Executor，执行回调方法不会新开线程，
         * 所有回调方法都在当前线程做(可能是主线程或者执行ListenableFutureTask的线程，具体可以看最后面的代码)。
         * guava异步模块中参数有Executor的方法，一般还会有一个没有Executor参数的重载方法，使用的就是MoreExecutors.directExecutor()
         */

        // listener是任务结束后的回调方法，executor是执行回调方法的执行器
        task.addListener(() -> System.out.println("done"), MoreExecutors.directExecutor());
    }

    private void multiThread() {
        // 创建线程池
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(
                5,
                5,
                0,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100),
                new CustomizableThreadFactory("demo"),
                new ThreadPoolExecutor.DiscardPolicy());
        // guava的接口ListeningExecutorService继承了jdk原生ExecutorService接口，重写了submit方法，修改返回值类型为ListenableFuture
        ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(poolExecutor);

        // 获得一个随着jvm关闭而关闭的线程池，通过Runtime.getRuntime().addShutdownHook(hook)实现
        // 修改ThreadFactory为创建守护线程，默认jvm关闭时最多等待120秒关闭线程池，重载方法可以设置时间
        ExecutorService newPoolExecutor = MoreExecutors.getExitingExecutorService(poolExecutor);

        // 只增加关闭线程池的钩子，不改变ThreadFactory
        MoreExecutors.addDelayedShutdownHook(poolExecutor, 120, TimeUnit.SECONDS);

        // 像线程池提交任务，并得到ListenableFuture
        ListenableFuture<String> listenableFuture = listeningExecutorService.submit(() -> "");
        // 可以通过addListener对listenableFuture注册回调，但是通常使用Futures中的工具方法
    }
}
