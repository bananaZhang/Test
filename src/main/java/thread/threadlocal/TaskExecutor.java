package thread.threadlocal;

/**
 * @author ZJY
 * @ClassName: TaskExecutor
 * @Description: TaskExecutor
 * @date 2019/2/25 19:56
 */
public class TaskExecutor implements Runnable {
    private static ThreadLocal<String> taskInfo = new ThreadLocal<>();

    TaskExecutor(String info) {
        taskInfo.set(info);
        System.out.println("ThreadId is ------> " + Thread.currentThread().getId());
    }

    @Override
    public void run() {
        System.out.println("info is ----------------> " + taskInfo.get());
        System.out.println("ThreadId is ------> " + Thread.currentThread().getId());
    }
}


