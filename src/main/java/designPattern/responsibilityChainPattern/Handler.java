package designPattern.responsibilityChainPattern;

/**
 * @author ZJY
 * @ClassName: Handler
 * @Description: Handler
 * @date 2019/3/30 10:48
 */
public abstract class Handler {

    private Handler next;
    private String name;

    public Handler(String name) {
        this.name = name;
    }

    public Handler setNext(Handler next) {
        this.next = next;
        return next;
    }

    /**
     * 处理链的入口
     */
    public final void handle(String value) {
        System.out.println(String.format("current handler is: %s, next is: %s", this, next));
        handleRequest(value);
        if (next != null) {
            next.handle(value);
        }
        done(value);
    }

    protected abstract void handleRequest(String value);

    private void done(String value) {
        System.out.println(String.format("value:%s is handle over", value));
    }

    @Override
    public String toString() {
        return "[" + name + "]";
    }
}
