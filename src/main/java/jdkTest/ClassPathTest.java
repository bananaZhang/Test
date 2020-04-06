package jdkTest;

public class ClassPathTest {

    public static void main(String[] args) {
        /*
         * path不以"/"开头时，默认是从此类所在的包下获取资源
         * 以"/"开头时，则是从ClassPath根下获取
         */
        System.out.println(ClassPathTest.class.getResource(""));
        System.out.println(ClassPathTest.class.getResource("/"));
        /*
         * path不能以’/'开头时
         * path是从ClassPath根下获取
         * 从结果来看：ClassPathTest.class.getResource("/") == test.getClass().getClassLoader().getResource("")
         */
        ClassPathTest test = new ClassPathTest();
        System.out.println(test.getClass());
        System.out.println(test.getClass().getClassLoader());
        System.out.println(test.getClass().getClassLoader().getResource(""));
        System.out.println(test.getClass().getClassLoader().getResource("/"));// null
    }
}
