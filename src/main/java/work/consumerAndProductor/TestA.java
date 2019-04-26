package work.consumerAndProductor;

/**
 * 基于wait和notify实现生产者消费者
 */
public class TestA {

    private static final String LOCK = "lock";
    private static final int FULL = 10;
    private static int count = 0;

    public static void main(String[] args) {
        TestA test = new TestA();
        new Thread(test.new Productor()).start();
        new Thread(test.new Consumer()).start();
        new Thread(test.new Productor()).start();
        new Thread(test.new Consumer()).start();
        new Thread(test.new Productor()).start();
        new Thread(test.new Consumer()).start();
    }

    class Productor implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (LOCK) {
                    while (count == FULL) {
                        try {
                            LOCK.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    count++;
                    System.out.println(Thread.currentThread().getName() + "生产者生产，目前总共有" + count);
                    LOCK.notifyAll();
                }
            }
        }
    }

    class Consumer implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (LOCK) {
                    while (count == 0) {
                        try {
                            LOCK.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    count--;
                    System.out.println(Thread.currentThread().getName() + "消费者消费，目前总共有" + count);
                    LOCK.notifyAll();
                }
            }
        }
    }
}
