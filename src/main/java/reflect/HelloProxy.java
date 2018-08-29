package reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * zhangjunyang 2018/1/29 22:02
 */
public class HelloProxy implements InvocationHandler {

    private Object target;

    public Object bind(Object target) {
        this.target = target;
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("jdk动态代理开始");
        Object result = method.invoke(target, args);
        System.out.println("jdk动态代理结束");
        return result;
    }
}
