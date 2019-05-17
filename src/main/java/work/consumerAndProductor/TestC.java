package work.consumerAndProductor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * BlockingQueue实现
 */
public class TestC {

    private final BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(10);
//    private static AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) {
        TestC test = new TestC();
        new Thread(test.new Producer()).start();
        new Thread(test.new Consumer()).start();
        new Thread(test.new Consumer()).start();
    }

    class Producer implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(3000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    blockingQueue.put("test" + i);// 向队列中插入元素，若队列已满则需要等待
//                    count.addAndGet(1);
                    System.out.println(Thread.currentThread().getName() + "生产者生产:" + "test" + i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    class Consumer implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                try {
                    Object ele = blockingQueue.take();// 获取并移除队列的头部元素，若队列为空则需要等待
//                    count.decrementAndGet();
                    System.out.println(Thread.currentThread().getName() + "消费者消费:" + ele);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
