package thread.timeoutstop;

import java.util.concurrent.*;

/**
 * 如何实现线程超时关闭
 * // TODO 未完成
 * @author ZJY
 * @date 2019/5/16 16:28
 */
public class FutureTimeout {

    public static void main(String[] args) {
        ExecutorService pool = Executors.newCachedThreadPool();

        Task task1 = new Task("task1");
        Future<String> resultOfTask1 = pool.submit(task1);

        Task task2 = new Task("task2");
        Future<String> resultOfTask2 = pool.submit(task2);
        try {
//            System.out.println(resultOfTask1.get(2, TimeUnit.SECONDS));
            System.out.println(resultOfTask2.get(4, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            System.out.println("future在睡着时被打断");
            pool.shutdown();
        } catch (ExecutionException e) {
            System.out.println("future在尝试取得任务结果时出错");
            pool.shutdown();
        } catch (TimeoutException e) {
            System.out.println("future时间超时");
        } finally {
            pool.shutdown();
        }
    }

    static class Task implements Callable<String> {

        private String name;

        public Task(String name) {
            this.name = name;
        }

        @Override
        public String call() throws Exception {
            System.out.println(String.format("task:%s is started", name));
            try {
                Thread.sleep(3000);
            } catch (Exception e) {
                System.out.println(String.format("task:%s execute failed", name));
                return "fail";
            }
            return "success";
        }
    }
}
