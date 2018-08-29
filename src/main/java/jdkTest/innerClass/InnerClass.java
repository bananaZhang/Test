package jdkTest.innerClass;

/**
 * zhangjunyang 2018/3/1 22:15
 */
public class InnerClass {

    private int outVar = 100;

    class Inner {
        public Inner() {}

        public int getOutVar() {
            return outVar;
        }

        public void innerMethod() {
            System.out.println("this is inner class's method");
        }
    }

    public void show() {
        this.new Inner().innerMethod();
    }

}
