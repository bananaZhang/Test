package thread.observer;

/**
 * @author ZJY
 * @ClassName: ThreadTest
 * @Description: ThreadTest
 * @date 2018/6/14 11:24
 */
public class ThreadTest {
    public static void main(String[] args) {
        MyThread thread = new MyThread();
        ThreadObserver observer = new ThreadObserver();
        thread.addObserver(observer);
        System.out.println("线程启动...");

        new Thread(thread).start();
    }
}
