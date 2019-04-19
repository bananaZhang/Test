package thread.threadorder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池保证线程顺序执行
 * @author ZJY
 * @ClassName: SingleThreadPoolExecution
 * @Description: SingleThreadPoolExecution
 * @date 2019/4/19 19:49
 */
public class SingleThreadPoolExecution {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> System.out.println("t1"));
        Thread t2 = new Thread(() -> System.out.println("t2"));
        Thread t3 = new Thread(() -> System.out.println("t3"));

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(t1);
        executorService.submit(t2);
        executorService.submit(t3);

        executorService.shutdown();
    }
}
