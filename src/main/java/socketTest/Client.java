package socketTest;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * zhangjunyang 2018/1/7 12:57
 */
public class Client {
    private static final String SERVER_HOST = "localhost";
    private static final int port = 12345;

    public static void main(String[] args) {
        System.out.println("客户端启动...");
        while (true) {
            Socket socket = null;
            try {
                ExecutorService service = Executors.newFixedThreadPool(10);
                socket = new Socket(SERVER_HOST, port);

                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                String str = "你好，我是客户端";
                out.writeUTF(str);

                String ret = input.readUTF();
                System.out.println("服务器端返回的数据是:" + ret);

                out.close();
                input.close();
            } catch (IOException e) {
                System.out.println("客户端异常:" + e.getMessage());
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        socket = null;
                        System.out.println("客户端finally异常:" + e.getMessage());
                    }
                }
            }
        }
    }
}
