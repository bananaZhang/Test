package thread.threadorder;

public class LockExecution {

    private volatile int orderNum = 1;

    public synchronized void methodA() {
        try {
            while (orderNum != 1) {
                wait();
            }
            System.out.println("AAA");
            orderNum = 2;
            notifyAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void methodB() {
        try {
            while (orderNum != 2) {
                wait();
            }
            System.out.println("BBB");
            orderNum = 3;
            notifyAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void methodC() {
        try {
            while (orderNum != 3) {
                wait();
            }
            System.out.println("CCC");
            orderNum = 1;
            notifyAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        LockExecution execution = new LockExecution();
        new Thread(execution::methodC).start();
        new Thread(execution::methodA).start();
        new Thread(execution::methodB).start();
    }
}
