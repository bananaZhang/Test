package reflect;

import net.sf.cglib.proxy.Enhancer;
import service.HelloService;
import service.impl.HelloServiceImpl;

/**
 * zhangjunyang 2018/1/29 22:05
 */
public class ProxyTest {

    public static void main(String[] args) {
        String type = "1";
        if ("1".equals(type)) {
            HelloProxy proxy = new HelloProxy();
            HelloService service = new HelloServiceImpl();
            System.out.println(service.getClass());
            // 根据代理的接口动态生成代理类，继承自com.sun.proxy，由于继承了Proxy，所以JDK不支持实现类的代理，只能对接口进行代理
            service = (HelloService) proxy.bind(service);
            System.out.println(service.getClass());

            service.say("jdk");
        } else {
            CglibProxy proxy = new CglibProxy();

            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(HelloServiceImpl.class);
            enhancer.setCallback(proxy);
            // 动态生成被代理类的子类
            HelloService service = (HelloService) enhancer.create();
            System.out.println(service.getClass());

            service.say("cglib");
        }
    }
}
