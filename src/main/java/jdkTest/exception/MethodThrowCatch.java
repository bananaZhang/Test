package jdkTest.exception;

import java.io.IOException;

public class MethodThrowCatch {

    public static void main(String[] args) {
        try {
            // 方法体没有声明抛出ArithmeticException，仍然能被catch住
            test();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void test() throws IOException {
        int sum = 0;
        for (int i = 2; ; i--) {
            sum += sum / i;
        }
    }
}
