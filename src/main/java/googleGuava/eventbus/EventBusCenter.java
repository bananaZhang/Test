package googleGuava.eventbus;

import com.google.common.eventbus.EventBus;

/**
 * @author ZJY
 * @ClassName: EventBusCenter
 * @Description: EventBusCenter
 * @date 2018/11/7 10:38
 */
public class EventBusCenter {

    private static EventBus eventBus = new EventBus();

    private EventBusCenter() {
    }

    public static EventBus getInstance() {
        return eventBus;
    }

    public static void register(Object obj) {
        eventBus.register(obj);
    }

    public static void unregister(Object obj) {
        eventBus.unregister(obj);
    }

    /**
     * 发布一个事件给所有的订阅者
     */
    public static void post(Object event) {
        eventBus.post(event);
    }
}
