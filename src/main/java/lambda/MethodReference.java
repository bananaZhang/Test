package lambda;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

/**
 * 方法引用的三种方式
 * @author: ZJY
 * @date: 2019/1/16 下午10:28
 */
public class MethodReference {

    public static void main(String[] args) {
        // 第一种：指向静态方法的方法引用：类名::静态方法名
        Comparator<Integer> comparator = Integer::compare;
        Integer diff = comparator.compare(2, 4);
        System.out.println("diff: " + diff);
        // 第二种：指向任意类型实例方法的方法引用：类名::实例方法名
        BiPredicate<String, String> predicate = String::equals;
        Boolean isEqual = predicate.test("aaa", "bbb");
        System.out.println("isEqual: " + isEqual);
        // 第三种：指向现有对象的实例方法的方法引用：对象的引用::实例方法名
        PrintStream ps = System.out;
        Consumer<String> consumer = ps::println;
        consumer.accept("333");
    }

}
