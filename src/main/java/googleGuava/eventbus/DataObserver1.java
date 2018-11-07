package googleGuava.eventbus;

import com.google.common.eventbus.Subscribe;

/**
 * @author ZJY
 * @ClassName: DataObserver1
 * @Description: DataObserver1
 * @date 2018/11/7 11:00
 */
public class DataObserver1 {

	@Subscribe
	public void handle(String msg) {
		System.out.println("DataObserver1 get msg: " + msg);
	}
}
