import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author ZJY
 * @ClassName: Test
 * @Description: Test
 * @date 2018/4/21 11:41
 */
@ClientEndpoint
public class Test {


    private String deviceId;

    private Session session;

    public Test () {

    }

    public Test (String deviceId) {
        this.deviceId = deviceId;
    }

    protected boolean start() {
        WebSocketContainer Container = ContainerProvider.getWebSocketContainer();
        String uri = "ws://172.16.30.21:8181/demo/socket/1";
        System.out.println("Connecting to " + uri);
        try {
            session = Container.connectToServer(Test.class, URI.create(uri));
            System.out.println("count: " + deviceId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        try {
            for (int i = 1; i < 50; i++) {
                Test wSocketTest = new Test(String.valueOf(i));
                if (!wSocketTest.start()) {
                    System.out.println("测试结束！");
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.in.read();
    }
}
