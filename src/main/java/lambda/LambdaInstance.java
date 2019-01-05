package lambda;

import com.google.common.collect.Lists;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author ZJY
 * @ClassName: LambdaInstance
 * @Description: LambdaInstance
 * @date 2018/4/20 10:03
 */
public class LambdaInstance {
    public static void main(String[] args) {
        LambdaInstance instance = new LambdaInstance();
//        instance.test1();
//        instance.test2();
//        instance.test3();
//        instance.test4();
//        instance.test5();
//        instance.test6();
//        instance.test7();
//        instance.test8();
        instance.test9();
    }

    /* 匿名内部类 */
    public void test1() {
        System.out.println("-------------->test1");
        new Thread(() -> System.out.println("hehe")).start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /* 迭代列表 */
    public void test2() {
        System.out.println("-------------->test2");
        List<String> list = Arrays.asList("java", "cafe", "lambda", "hik");
        list.forEach(e -> System.out.println(e));
        list.forEach(System.out::println);// 使用方法引用

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /* map的用法 */
    public void test3() {
        System.out.println("-------------->test3");
        List<Integer> list = Arrays.asList(10, 20, 30);
        list.stream().map((e) -> {
            e += 1;
            e *= 2;
            return e;
        }).forEach(System.out::println);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /* peek的用法 */
    /**
     * peek接受的是Consumer入参，没有返回值
     * map接受的是Function，有入参和返回值
     */
    public void test9() {
        System.out.println("-------------->test9");
        List<Integer> nums = Lists.newArrayList(1, 1, null, 2, 3, 4, null, 5, 6, 7, 8, 9, 10);
        System.out.println("sum is:"+nums.stream().filter(num -> num != null)
                .distinct()// 1,1,2,3,4,5,6,7,8,9,10
                .mapToInt(num -> num * 2)// 1,2,3,4,5,6,7,8,9,10
                .skip(2)// 2,4,6,8,10,12
                .limit(4)// 6,8,10,12,14,16,18,20
                .peek(System.out::println)// 每个元素被消费(sum)时打印自身
                .sum());
    }

    /* reduce的用法 */
    public void test4() {
        System.out.println("-------------->test4");
        List<Double> list = Arrays.asList(10d, 20d, 30d);
        double all = list.stream().reduce((sum, x) -> sum + x).get();
        System.out.println(all);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /* filter的用法 */
    public void test5() {
        System.out.println("-------------->test5");
        List<Integer> list = Arrays.asList(1, 2, 3, 5, 6, 7);
        List<Integer> resList = list.stream().filter(e -> e > 5).collect(Collectors.toList());
        resList.forEach(System.out::println);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /* Predicate用法 */
    public void test6() {
        System.out.println("-------------->test6");
        List<String> languages = Arrays.asList("Java", "Python", "scala", "Shell", "R");

        System.out.println("Language starts with J: ");
        filterLang(languages, x -> x.startsWith("J"));

        System.out.println("\nLanguage ends with a: ");
        filterLang(languages, x -> x.endsWith("a"));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void filterLang(List<String> languages, Predicate<String> condition) {
        languages.stream().filter(e -> condition.test(e)).forEach(System.out::println);
    }

    public void testFilter() {
        List<String> list = Arrays.asList("java", "cafe", "lambda", "hik");
        list.stream().filter(new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s.length() > 5;
            }
        }).forEach(x -> System.out.print(x + " "));
    }

    /* distinct方法, String、Integer均适用 */
    public void test7() {
        System.out.println("-------------->test7");
        List<Integer> list = Arrays.asList(1, 2, 4, 5, 6, 3, 2, 1);
        List<Integer> resList = list.parallelStream().distinct().collect(Collectors.toList());

        resList.forEach(System.out::println);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /* summaryStatistics用法 */
    public void test8() {
        System.out.println("-------------->test8");
        List<Integer> primes = Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19, 23, 29);
        IntSummaryStatistics stats = primes.stream().mapToInt((x) -> x).summaryStatistics();
        System.out.println("Highest prime number in List : " + stats.getMax());
        System.out.println("Lowest prime number in List : " + stats.getMin());
        System.out.println("Sum of all prime numbers : " + stats.getSum());
        System.out.println("Average of all prime numbers : " + stats.getAverage());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
