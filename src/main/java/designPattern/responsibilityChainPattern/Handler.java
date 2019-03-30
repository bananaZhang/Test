package designPattern.responsibilityChainPattern;

/**
 * @author ZJY
 * @ClassName: Handler
 * @Description: Handler
 * @date 2019/3/30 10:48
 */
public abstract class Handler {

    private Handler next;

    public Handler setNext(Handler next) {
        this.next = next;
        return next;
    }

    public final void handle(String value) {
        handleRequest(value);
        if (next != null) {
            next.handleRequest(value);
        }
        done(value);
    }

    protected abstract void handleRequest(String value);

    private void done(String value) {
        System.out.println(String.format("value:%s is handle over", value));
    }
}
