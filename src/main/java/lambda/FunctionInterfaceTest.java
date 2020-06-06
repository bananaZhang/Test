package lambda;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class FunctionInterfaceTest {

    public static void main(String[] args) {
        consumerTest();
        supplierTest();
        functionTest();
        predicateTest();
    }

    /**
     * Consumer<T>：消费型接口
     * 抽象方法：void accept(T t)，接收一个参数进行消费，但无需返回结果。
     * 默认方法：andThen(Consumer<? super T> after)，先消费然后在消费，先执行调用andThen接口的accept方法，
     * 然后在执行andThen方法参数after中的accept方法。
     * @author: ZJY
     * @date: 2020/6/6 下午1:31
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void consumerTest() {
        Consumer consumer = System.out::println;
        consumer.accept("hello function");

        Consumer<String> consumer1 = s -> System.out.print("车名："+s.split(",")[0]);
        Consumer<String> consumer2 = s -> System.out.println("-->颜色："+s.split(",")[1]);

        String[] strings = {"保时捷,白色", "法拉利,红色"};
        for (String string : strings) {
            consumer1.andThen(consumer2).accept(string);
        }
    }

    /**
     * Supplier<T>: 供给型接口
     * 抽象方法：T get()，无参数，有返回值。
     * @author: ZJY
     * @date: 2020/6/6 下午1:35
     */
    public static void supplierTest() {
        Supplier<String> supplier = () -> "xixi";
        System.out.println(supplier.get());
    }

    /**
     * Function<T,R>: 函数型接口
     * 抽象方法：R apply(T t)，传入一个参数，返回想要的结果。
     * 默认方法：compose(Function<? super V, ? extends T> before)，先执行compose方法参数before中的apply方法，
     * 然后将执行结果传递给调用compose函数中的apply方法在执行。
     * 默认方法：andThen(Function<? super R, ? extends V> after)，先执行调用andThen函数的apply方法，
     * 然后在将执行结果传递给andThen方法after参数中的apply方法在执行。它和compose方法整好是相反的执行顺序。
     * 静态方法：identity()，获取一个输入参数和返回结果相同的Function实例。
     * @author: ZJY
     * @date: 2020/6/6 下午1:36
     */
    public static void functionTest() {
        Function<Integer, Integer> function = e -> e * 6;
        System.out.println(function.apply(2));

        Function<Integer, Integer> function1 = e -> e * 2;
        Function<Integer, Integer> function2 = e -> e * e;
        // compose方法执行流程是先执行function2的表达式也就是33=9，然后在将执行结果传给function1的表达式也就是92=18，
        // 所以最终的结果是18。
        Integer apply2 = function1.compose(function2).apply(3);
        System.out.println(apply2);

        // 这里我们和compose方法使用一个例子，所以是一模一样的例子，由于方法的不同，执行顺序也就不相同，
        // 那么结果是大大不同的。andThen方法是先执行function1表达式，也就是32=6，然后在执行function2表达式也就是66=36。结果就是36。
        Integer apply3 = function1.andThen(function2).apply(3);
        System.out.println(apply3);

        // 平常没有遇到过使用这个方法的场景，总之这个方法的作用就是输入什么返回结果就是什么。
        Function<Integer, Integer> identity = Function.identity();
        Integer apply = identity.apply(3);
        System.out.println(apply);
    }

    /**
     * Predicate<T> ：断言型接口
     * 抽象方法：boolean test(T t),传入一个参数，返回一个布尔值。
     * 默认方法：and(Predicate<? super T> other)，相当于逻辑运算符中的&&，当两个Predicate函数的返回结果都为true时才返回true。
     * 默认方法：or(Predicate<? super T> other) ,相当于逻辑运算符中的||，当两个Predicate函数的返回结果有一个为true则返回true，
     * 否则返回false。
     * 默认方法：negate()，这个方法的意思就是取反。
     * 静态方法：isEqual(Object targetRef)，对当前操作进行"="操作,即取等操作,可以理解为 A == B。
     * @author: ZJY
     * @date: 2020/6/6 下午1:43
     */
    public static void predicateTest() {
        Predicate<Integer> predicate = t -> t > 0;
        boolean test = predicate.test(1);
        System.out.println(test);

        Predicate<String> predicate1 = s -> s.length() > 0;
        Predicate<String> predicate2 = Objects::nonNull;
        boolean test1 = predicate1.and(predicate2).test("&&测试");
        System.out.println(test1);

        Predicate<String> predicate3 = s -> false;
        Predicate<String> predicate4 = Objects::nonNull;
        boolean test2 = predicate3.and(predicate4).test("||测试");
        System.out.println(test2);

        Predicate<String> predicate5 = s -> s.length() > 0;
        boolean result = predicate5.negate().test("取反");
        System.out.println(result);

        boolean test3 = Predicate.isEqual("test").test("test");
        boolean test4 = Predicate.isEqual("test").test("equal");
        System.out.println(test3);   //true
        System.out.println(test4);   //false
    }
}
