package designPattern.callbackPattern;

import java.util.function.Function;

/**
 * @author ZJY
 * @ClassName: Dog
 * @Description: Dog
 * @date 2019/5/13 10:41
 */
public class Dog {

    public void wangwang(Function<Dog, Boolean> function) {
        boolean result = function.apply(this);
        // do sth with result...
    }
}
