package reflect.service.impl;

import reflect.service.HelloService;

/**
 * @author ZJY
 * @ClassName: HelloServiceImpl
 * @Description: HelloServiceImpl
 * @date 2018/9/3 10:41
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public void say(String msg) {
        System.out.println(msg);
    }
}
