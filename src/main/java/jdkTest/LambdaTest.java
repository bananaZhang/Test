package jdkTest;

import java.util.Arrays;

/**
 * 函数接口指的是只有一个函数的接口，这样的接口可以隐式转换为Lambda表达式。java.lang.Runnable和java.util.concurrent.Callable是函数式接口的最佳例子
 * zhangjunyang 2018/1/7 14:48
 */
public class LambdaTest {
    public static void main(String[] args) {
        // 参数e的类型是由编译器推理得出的
        Arrays.asList("a", "b", "c").forEach(e -> System.out.println(e));
        // 可以显式指定该参数的类型
        Arrays.asList("a", "b", "c").forEach((String e) -> System.out.println(e));
        // 可以使用花括号接更复杂的语句
        Arrays.asList("a", "b", "c").forEach(e -> {
            System.out.println(e + "..");
            System.out.println(e + "....");
        });

        Arrays.asList( "a", "b", "d" ).sort( ( e1, e2 ) -> {
            int result = e1.compareTo( e2 );
            return result;
        } );
    }
}
