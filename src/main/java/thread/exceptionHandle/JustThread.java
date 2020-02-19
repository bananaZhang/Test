package thread.exceptionHandle;

public class JustThread {

    public static void main(String[] args) {
        new Thread(new MyTask()).start();
    }

    private static final class MyTask implements Runnable {

        @Override
        public void run() {
            /**
             * 没有线程池的情况，线程内部的异常会走下面的逻辑
             */
            Thread.currentThread().setUncaughtExceptionHandler((Thread t, Throwable e) -> {
                System.out.println("unchecked exception happened:");
                System.out.println(t.getId());
                System.out.println(t.getName());
                e.printStackTrace(System.out);
            });

            int sum = 0;
            for (int i = 4; i >= 0; i--) {
                sum = sum + (12 / i);
            }
            System.out.println(sum);
        }
    }
}
