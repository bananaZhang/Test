package designPattern.singlePattern;

/**
 * zhangjunyang 2018/1/14 15:52
 */
public class SinglePatternTest {
}

/**
 * 懒汉（线程不安全）
 * */
class Singleton1 {
    private static Singleton1 instance;
    private Singleton1() {}

    public static Singleton1 getInstance() {
        if (instance == null)
            instance = new Singleton1();
        return instance;
    }
}


/**
 * 懒汉（线程安全），但是直接在getInstance方法上同步效率较低
 * */
class Singleton2 {
    private static Singleton2 instance;
    private Singleton2 () {}

    public static synchronized Singleton2 getInstance() {
        if (instance == null) {
            instance = new Singleton2();
        }
        return instance;
    }
}

/**
 * 饿汉（只要类被装载了，instance就被初始化了，可能会由其他static方法导致类被初始化了）
 * */
class Singleton3 {
    private static Singleton3 instance = new Singleton3();
    private Singleton3 () {}

    public static Singleton3 getInstance() {
        return instance;
    }
}


/**
 * 内部工厂类（和饿汉的区别：Singleton4被装载了INSTANCE也不会被实例化，只有getInstance被调用时才被实例化）
 * */
class Singleton4 {
    private static class SingletonHolder {
        private static final Singleton4 INSTANCE = new Singleton4();
    }

    private Singleton4 (){}

    public static final Singleton4 getInstance() {
        return SingletonHolder.INSTANCE;
    }
}


/**
 * DCL(Double Check Lock)写法
 * */
class Singleton {
    // 必须要被声明为volatile，否则会导致得到部分初始化对象问题（jvm创建了Singlton实例分配了空白内存并赋值给instance，但还没有初始化）
    // https://blog.csdn.net/a_lonely_dancer/article/details/52445402
    private volatile static Singleton instance;

    private Singleton(){}

    public static Singleton getInstance() {
        if (instance == null) { // 当instance不为null时，可能指向一个“被部分初始化的对象”
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();// 如果不声明为volatile会导致部分初始化的问题
                }
            }
        }
        return instance;
    }
}
