package thread.observer;

import java.util.Observable;

/**
 * @author ZJY
 * @ClassName: MyThread
 * @Description: MyThread
 * @date 2018/6/14 11:21
 */
public class MyThread extends Observable implements Runnable {

    private void exceptionHandle() {
        super.setChanged();
        notifyObservers();
    }

    @Override
    public void run() {
        int count = 0;
        try {
            for (int i = 0; i < 100; i++) {
                count += i;
                if (i == 20)
                    throw new RuntimeException("我挂了...");
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.exceptionHandle();
        }
    }
}
