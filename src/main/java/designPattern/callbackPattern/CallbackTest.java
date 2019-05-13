package designPattern.callbackPattern;

/**
 * 回调函数比较通用的解释是，它是一个通过函数指针调用的函数。
 * 如果你把函数的指针（地址）作为参数传递给另一个函数，当这个指针被用为调用它所指向的函数时，我们就说这是回调函数。
 * 回调函数不是由该函数的实现方直接调用，而是在特定的事件或条件发生时由另外一方调用的，用于对该事件或条件进行响应。
 * @author ZJY
 * @date 2019/5/13 10:43
 */
public class CallbackTest {
    public static void main(String[] args) {
        Dog dog = new Dog();
        dog.wangwang(d -> {
            System.out.println("wangwang");
            return true;
        });
    }
}
