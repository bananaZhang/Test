package thread.threadorder;

public class JoinExecution {

    public static void main(String[] args) throws InterruptedException {
        ExecutionThread t1 = new ExecutionThread("t1");
        ExecutionThread t2 = new ExecutionThread("t2");
        ExecutionThread t3 = new ExecutionThread("t3");

        t1.start();
        t1.join();// 等到t1完成之后再执行主线程，主线程调用t1.join()时，相当于获取了对象t1的锁
        t2.start();
        t2.join();// 等到t2完成之后再执行主线程
        t3.start();
    }

}

class ExecutionThread extends Thread {
    private String msg;

    public ExecutionThread(String msg) {
        this.msg = msg;
    }

    @Override
    public void run() {
        System.out.println(msg);
    }
}
