package generic;

public class AboutT<T> {

    private T ele;

    public T getEle() {
        return ele;
    }

    public void setEle(T ele) {
        this.ele = ele;
    }
    /**
     * 总结下泛型的好处就是
     * 省去了强制转换，可以在编译时候检查类型安全，可以用在类，方法，接口上
     */
    public static void main(String[] args) {
        // 创建一个对象，不带泛型参数，获取对象的时候需要强制转换
        AboutT t1 = new AboutT();
        t1.setEle(5);
        Integer num1 = (Integer) t1.getEle();
        // 创建一个对象，带泛型参数，获取对象的时候就不需要强制转换
        AboutT<Integer> t2 = new AboutT<>();
        t2.setEle(5);
        Integer num2 = t2.getEle();
    }
}
