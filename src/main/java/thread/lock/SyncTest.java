package thread.lock;

import bean.JustBean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ZJY
 * @ClassName: SyncTest
 * @Description: SyncTest
 * @date 2019/5/8 10:55
 */
public class SyncTest {

    private static Map<String, JustBean> numMap = new ConcurrentHashMap<>();

    static {
        JustBean bean = new JustBean();
        numMap.put("test", bean);
    }

    public static void getLock(String key) {
        JustBean bean = numMap.get(key);
        /**
         * 对map中的同一个对象加锁，所以是互斥的，可以忽视下面的idea警告
         */
        synchronized (bean) {
            System.out.println(Thread.currentThread().getId() + " get in...");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Thread(() -> SyncTest.getLock("test")).start();
        new Thread(() -> SyncTest.getLock("test")).start();
        new Thread(() -> SyncTest.getLock("test")).start();
    }
}
