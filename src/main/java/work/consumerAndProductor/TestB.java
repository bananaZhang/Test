package work.consumerAndProductor;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁实现
 * // TODO 有问题
 */
public class TestB {

    private static final int FULL = 10;
    private static int count = 0;

    private Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    public static void main(String[] args) {
        TestB test = new TestB();
        new Thread(test.new Producer()).start();
        new Thread(test.new Consumer()).start();
        new Thread(test.new Producer()).start();
        new Thread(test.new Consumer()).start();
        new Thread(test.new Producer()).start();
        new Thread(test.new Consumer()).start();
    }

    class Producer implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.lock();
            try {
                while (count == FULL) {
                    try {
                        notFull.await();
                        System.out.println("生产者" + Thread.currentThread().getName() + "await");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                count ++;
                System.out.println(Thread.currentThread().getName() + "生产者生产，目前总共有" + count);
                notEmpty.signal();
            } finally {
                lock.unlock();
            }
        }
    }

    class Consumer implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                lock.lock();
                try {
                    while (count == 0) {
                        try {
                            notEmpty.await();
                            System.out.println("消费者" + Thread.currentThread().getName() + "await");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    count --;
                    System.out.println(Thread.currentThread().getName() + "消费者消费，目前总共有" + count);
                    notFull.signal();
                } finally {
                    lock.unlock();
                }
            }
        }
    }
}
