package jdkTest.strTest;

/**
 * String的intern方法
 * 返回字符串对象的规范表示形式。
 * 字符串常量池，初始为空，由String类来维护
 * 当intern方法被调用时，如果池中已经有一个字符串和传入的字符串相等(equals)，
 * 返回池中的字符串，否则，将这个String对象添加到池中并返回这个String对象的引用。
 * 因此，对于任意两个字符串s和t，如果str1.equals(str2)则有str1.intern() == str2.intern()。
 * zhangjunyang 2018/2/4 21:48
 */
public class Intern {



    public static void main(String[] args) {
        /**
         * JDK1.6 下输出 false false（1.6以前字符串常量池在Perm Gen方法区中）
         * JDK1.7 及以后输出 true false（1.7以后字符串常量池被移到了堆中）
         */
        String s1 = new StringBuilder().append("String").append("Test").toString();
        System.out.println(s1.intern() == s1);

        String s2 = new StringBuilder().append("ja").append("va").toString();
        System.out.println(s2.intern() == s2);

        pushPool1();
        pushPool2();
    }

    /**
     * Java技术驿站公众号9.4日的“浅谈String的intern”
     */
    public static void pushPool1() {
        String a = "a";
        String param = "b" + a;
        // true
        System.out.println(param.intern() == "ba");
        // true
        System.out.println(param == "ba");
    }

    public static void pushPool2() {
        String a = "a";
        String param = "b" + a;
        // true
        System.out.println("ba" == param.intern());
        // false
        System.out.println(param == "ba");
    }
}
