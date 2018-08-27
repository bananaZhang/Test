package thread;

import java.util.Observer;

/**
 * @author ZJY
 * @ClassName: ThreadObserver
 * @Description: ThreadObserver
 * @date 2018/6/14 11:19
 */
public class ThreadObserver implements Observer {
    @Override
    public void update(java.util.Observable o, Object arg) {
        System.out.println("线程挂了...");
    }
}
