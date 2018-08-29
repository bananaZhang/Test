package socketTest;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * zhangjunyang 2018/1/7 12:40
 */
public class Server {
    private static final int port = 12345;

    public static void main(String[] args) {
        System.out.println("服务器启动...");
        Server server = new Server();
        server.init();
    }

    public void init() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket client = serverSocket.accept();
                new ServerThread(client);
            }
        } catch (IOException e) {
            System.out.println("服务器异常");
        }
    }

    private class ServerThread implements Runnable {
        private Socket socket;

        private ServerThread(Socket client) {
            this.socket = client;
            new Thread(this).start();
        }

        @Override
        public void run() {
            try {
                DataInputStream input = new DataInputStream(socket.getInputStream());
                String clientInputStr = input.readUTF();

                System.out.println("客户端发过来的内容:" + clientInputStr);

                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                String str = "你好，我是服务器端，收到了你的消息";
                out.writeUTF(str);

                out.close();
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        socket = null;
                        System.out.println("服务器端finally异常");
                    }
                }
            }
        }
    }
}
