package nio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class BioServer implements Runnable {

    @Override
    public void run() {
        System.out.println("Hello Server");
        try {
            // server在端口4700监听客户端请求
            ServerSocket server = new ServerSocket(4700);
            // 阻塞等待客户端请求
            Socket socket = server.accept();
            String line;
            // 由Socket对象得到输入流，并构造相应的BufferedReader对象
            BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Client: " + is.readLine());
            PrintWriter os = new PrintWriter(socket.getOutputStream());
            line = "Hello";
            os.println(line);
            os.flush();

            os.close();
            is.close();
            socket.close();
            server.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
