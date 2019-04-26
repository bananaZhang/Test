package reflect;

import bean.Student;

import java.lang.reflect.Method;

/**
 * @author ZJY
 * @ClassName: Reflect4GetFatherClassMethod
 * @Description: Reflect4GetFatherClassMethod
 * @date 2019/4/26 13:52
 */
public class Reflect4GetFatherClassMethod {

    public static void main(String[] args) {
        Method[] methods = Student.class.getMethods();
        System.out.println("get methods for subclass return:");
        for (Method method : methods) {
            System.out.println(method.getName());
        }

        System.out.println("get declared methods for subclass return:");
        methods = Student.class.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(method.getName());
        }

        methods = Student.class.getSuperclass().getDeclaredMethods();
        System.out.println("get declared methods for father class return:");
        for (Method method : methods) {
            System.out.println(method.getName());
        }
    }
}
