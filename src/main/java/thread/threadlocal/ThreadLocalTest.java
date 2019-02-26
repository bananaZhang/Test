package thread.threadlocal;

/**
 * @author ZJY
 * @ClassName: ThreadLocalTest
 * @Description: ThreadLocalTest
 * @date 2019/2/25 20:07
 */
public class ThreadLocalTest {

    public static void main(String[] args) {
        new Thread(new TaskExecutor("aaa")).start();
//        new Thread(new TaskExecutor("bbb")).start();
//        new Thread(new TaskExecutor("ccc")).start();
//        new Thread(new TaskExecutor("ddd")).start();
//        new Thread(new TaskExecutor("eee")).start();
    }
}



