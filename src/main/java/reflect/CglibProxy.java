package reflect;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * zhangjunyang 2018/1/29 22:11
 */
public class CglibProxy implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("cglib动态代理开始");
        Object result = methodProxy.invokeSuper(o, objects);
        System.out.println("cglib动态代理结束");
        return result;
    }
}
