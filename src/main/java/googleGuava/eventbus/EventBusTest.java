package googleGuava.eventbus;

/**
 * @author ZJY
 * @ClassName: EventBusTest
 * @Description: EventBusTest
 * @date 2018/11/7 11:02
 */
public class EventBusTest {

	public static void main(String[] args) {
		DataObserver1 observer1 = new DataObserver1();
		DataObserver2 observer2 = new DataObserver2();

		EventBusCenter.register(observer1);
		EventBusCenter.register(observer2);

		System.out.println("===== start =====");
		EventBusCenter.post("post a string");

		System.out.println("===== after unregister =====");
		EventBusCenter.unregister(observer2);
		EventBusCenter.post("post a string");

		System.out.println("===== end =====");
	}
}
