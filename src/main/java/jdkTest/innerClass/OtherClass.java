package jdkTest.innerClass;

/**
 * zhangjunyang 2018/3/1 22:21
 */
public class OtherClass {

    public static void main(String[] args) {
        new InnerClass().new Inner().innerMethod();// Inner的构造方法为public可以在外部创建内部类对象
        new InnerClass().show();


        /**
         * 匿名内部类，顾名思义就是一个没有名字的类，下面new Thread方法中的new Runnable从表面看很显然的有个错误，就是不能new一个接口，
         * 但是，这里使用的就是匿名内部类，一个实现了Runnble接口的没有名字的类，使用者只需实现接口中的方法就行
         * a·匿名内部类不能有构造方法。
         * b·匿名内部类不能定义任何静态成员、方法和类。
         * c·匿名内部类不能是public,protected,private,static。
         * d·只能创建匿名内部类的一个实例。
         * e·一个匿名内部类一定是在new的后面，用其隐含实现一个接口或实现一个类。
         * f·因匿名内部类为局部内部类，所以局部内部类的所有限制都对其生效。
         * g.匿名内部类来自外部闭包环境的自由变量必须是final的
         * */
        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });

        new NoNameClassImpl(new NoNameInterface() {
            @Override
            public void methodA() {

            }

            @Override
            public void methodB() {

            }
        });
    }
}

class NoNameClassImpl {
    public NoNameClassImpl(NoNameInterface noNameInterface) {}
}
