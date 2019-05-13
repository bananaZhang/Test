package designPattern.callbackPattern;

/**
 * @author ZJY
 * @ClassName: CallbackTest
 * @Description: CallbackTest
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
