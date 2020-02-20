package thread.exceptionHandle;

public class CatchMethodWithSubmit {
    /**
     * 用线程组的方式统一捕获组内的所有异常
     */
    public static class MyThreadGroup extends ThreadGroup {
        public MyThreadGroup(String name) {
            super(name);
        }

        @Override
        public void uncaughtException(Thread t, Throwable e) {
            System.out.println("thread:" + t.getName() + " exception is:");
            e.printStackTrace(System.err);
        }
    }

    public static ThreadGroup workerThreads = new MyThreadGroup("Worker Threads");

    private static class WorkerThread extends Thread {
        public WorkerThread(String name) {
            super(workerThreads, name);
        }

        @Override
        public void run() {
            int sum = 0;
            for (int i = 4; i >= 0; i--) {
                sum = sum + (12 / i);
            }
        }
    }

    public static void main(String[] args) {
        Thread t = new WorkerThread("myThread");
        t.start();
    }

}
