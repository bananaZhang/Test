package jdkTest.interrupt;

/**
 * @author ZJY
 * @ClassName: InterruptTest
 * @Description: InterruptTest
 * @date 2019/3/22 15:43
 * void Thread.interrupt();//中断线程
 * boolean Thread.isInterrupted()//判断是否中断
 * static boolean Thread.interrupted()//判断是否中断，并清除当前中断状态
 * Thread.interrupt()方法是一个实例方法，它通知目标线程中断，也就是设置中断标志位。中断标志位表示当前线程已经被中断了。
 * Thread.isInterrupted()方法也是实例方法，它判断当前线程是否有被中断（通过检查中断标志位）。
 */
public class InterruptTest {

    public static void main(String[] args) throws Exception {
        test03();
    }

    /**
     * 运行中的线程不会因为interrupt()而中断，因为它仅仅是一个信号（status）
     * 这个程序虽然对t进程了中断，但是在t中并没有中断处理的逻辑，因此即使t线程被置上了中断状态，但是这个中断不会发生任何作用。
     */
    public static void test01() throws InterruptedException {
        Thread t = new Thread(() -> {
            while (true) {}
        });
        t.start();
        Thread.sleep(1000);

        t.interrupt();
    }

    /**
     * 在循环中增加处理中断的逻辑
     */
    public static void test02() throws InterruptedException {
        Thread t = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("Thread is interrupted");
                    break;
                }
            }
        });
        t.start();
        Thread.sleep(1000);

        t.interrupt();
    }

    /**
     * 等待中的线程（wait(long), sleep(long), join(long)）收到中断信号会抛出InterruptedException
     * 如果线程在等待锁，对线程对象调用interrupt()只是会设置线程的中断标志位，线程依然会处于BLOCKED状态，
     * 也就是说，interrupt()并不能使一个在等待锁的线程真正”中断”。通过前面的代码可以看到，中断是通过循环不判进行Thread.currentThread().isInterrupted()判断的。
     * 在使用synchronized关键字获取锁的过程中不响应中断请求，这是synchronized的局限性。
     * 如果想在等待获取锁的过程中能响应中断，应该使用显式锁，Lock接口，它支持以响应中断的方式获取锁。
     */
    public static void test03() throws InterruptedException {
        Thread t = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("intsmaze Interrupt");
                    break;
                }
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    // 当线程在休眠sleep时，如果被中断就会产生该异常，此时它会清楚中断标志，如果不加处理，那么在下一次循环开始时，就无法捕获这个中断。
                    System.out.println("Interrupt when intsmaze sleep");
                    Thread.currentThread().interrupt();// 设置中断状态，如果注释掉这一行线程会一直运行
                }
            }
        });
        t.start();
        Thread.sleep(2000);
        t.interrupt();
    }
}
