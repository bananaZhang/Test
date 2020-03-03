package generic;

import bean.Fruit;

/**
 * Class<T>，Class<?>区别
 */
public class TwoOfClass {
    /**
     * 获取Class的三种方式
     */
    private void getClassMethod() throws ClassNotFoundException {
        Fruit f = new Fruit();
        Class clazz1 = f.getClass();
        Class clazz2 = Class.forName("bean.Fruit");
        Class clazz3 = Fruit.class;
    }

    /**
     * Class<T>在实例化的时候，T要替换成具体类
     * Class<?>它是个通配泛型，?可以代表任何类型，主要用于声明时的限制情况
     * 例如可以声明一个：Class<?> clazz;
     * 但是不能声明一个：Class<T> clazz;
     * 因为T需要指定类型，所以当，不知道定声明什么类型的Class的时候可以定义
     * 一个Class<?>,Class<?>可以用于参数类型定义，方法返回值定义等。
     */
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        // 使用Class<T>和Class<?>多发生在反射场景下，不用范型反射创建一个类如下，可见需要强制转换
        Fruit fruit1 = (Fruit) Class.forName("bean.Fruit").newInstance();
        // 使用范型后，不需要强制转换了
        Fruit fruit2 = createInstance(Fruit.class);

    }

    private static <T> T createInstance(Class<T> clazz) throws IllegalAccessException, InstantiationException {
        return clazz.newInstance();
    }
}
