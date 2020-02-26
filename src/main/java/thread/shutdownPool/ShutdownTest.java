package thread.shutdownPool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ShutdownTest {

    public static void main(String[] args) {
//        canExecuteAfterShutdown();
//        queueTaskWillExecute();
        interruptWhenRunning();
    }

    /**
     * 当线程池关闭后，继续提交新任务会执行拒绝策略。默认的拒绝策略就是抛出异常
     * @author: ZJY
     * @date: 2020/2/26 下午9:40
     */
    private static void canExecuteAfterShutdown() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 4, 10,
                TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        executor.execute(() -> System.out.println("before shutdown"));
        executor.shutdown();
        executor.execute(() -> System.out.println("after shutdown"));
    }

    /**
     * 使用shutdown关闭线程池等待队列中的任务会继续执行，使用shutdownNow则会终止队列中执行的任务
     * @author: ZJY
     * @date: 2020/2/26 下午9:47
     */
    private static void queueTaskWillExecute() {
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
        for (int i = 1; i <= 10; i++){
            workQueue.add(new QueueTask(String.valueOf(i)));
        }
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 10,
                TimeUnit.SECONDS, workQueue);
        executor.execute(new QueueTask("0"));
//        executor.shutdown();
        executor.shutdownNow();
        System.out.println("workQueue size = " + workQueue.size() + " after shutdown");
    }

    /**
     * shutdown方法不会interrupt线程，而shutdownNow会
     * @author: ZJY
     * @date: 2020/2/26 下午9:54
     */
    public static void interruptWhenRunning() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        executor.execute(new InterruptTask("0"));
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        executor.shutdown();
        executor.shutdownNow();
        System.out.println("executor has been shutdown");
    }

    static class QueueTask implements Runnable{
        String name;

        public QueueTask(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            for(int i = 1; i <= 10; i++){
                System.out.println("task " + name + " is running");
            }
            System.out.println("task " + name + " is over");
        }
    }

    static class InterruptTask implements Runnable {
        String name;

        public InterruptTask(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            for (int i = 1; i <= 100 && !Thread.interrupted(); i++) {
                Thread.yield();// 放弃当前时间片，让其他线程先运行
                System.out.println("task " + name + " is running, round " + i);
            }
        }
    }
}
