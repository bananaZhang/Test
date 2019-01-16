package lambda;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;

/**
 * 方法引用的三种方式
 * @author: ZJY
 * @date: 2019/1/16 下午10:28
 */
public class MethodReference {

    public static void main(String[] args) {
        // 第一种：指向静态方法的方法引用
        Function<String, Integer> function1 = Integer::parseInt;
        // 第二种：指向任意类型实例方法的方法引用
        Function<String, Integer> function2 = String::length;
        // 第三种：指向现有对象的实例方法的方法引用
        BiPredicate<List<String>, String> function3 = List::contains;
    }

}
