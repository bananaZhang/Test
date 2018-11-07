package googleGuava.eventbus;

import com.google.common.eventbus.Subscribe;

/**
 * @author ZJY
 * @ClassName: DataObserver2
 * @Description: DataObserver2
 * @date 2018/11/7 11:01
 */
public class DataObserver2 {

	@Subscribe
	public void handle(String msg) {
		System.out.println("DataObserver2 get msg: " + msg);
	}
}
