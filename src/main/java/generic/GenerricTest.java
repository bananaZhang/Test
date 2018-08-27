package generic;

import java.lang.reflect.ParameterizedType;

/**
 * @author ZJY
 * @ClassName: GenerricTest
 * @Description: GenerricTest
 * @date 2018/4/19 10:22
 */
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

class Param<T1, T2> {

    class A {}
    class B extends A {}

    private Class<T1> entityClass;

    /* Param<MyClass, MyInvoke> */
    public Param (){
        Type type = getClass().getGenericSuperclass();
        System.out.println("getClass() == " + getClass());
        System.out.println("type = " + type);

        Type trueType = ((ParameterizedType) type).getActualTypeArguments()[0];
        System.out.println("trueType1 = " + trueType);
        trueType = ((ParameterizedType) type).getActualTypeArguments()[1];
        System.out.println("trueType2 = " + trueType);

        this.entityClass = (Class<T1>) trueType;
        System.out.println("entityClass = " + entityClass);

        B t = new B();
        type = t.getClass().getGenericSuperclass();
        System.out.println("B's type = " + type);

        System.out.println("B is A's super class : " + ((ParameterizedType) type).getActualTypeArguments().length);
    }

}

class MyClass{}
class MyInvoke{}

public class GenerricTest extends Param<MyClass, MyInvoke> {
    public static void main(String[] args) {
        GenerricTest classDemo = new GenerricTest();
    }
}