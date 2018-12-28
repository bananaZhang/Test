package nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Reactor implements Runnable {

    public int id = 1000001;

    private int bufferSize = 2 * 1024;

    @Override
    public void run() {
        init();
    }

    private void init() {
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            Selector selector = Selector.open();
            InetSocketAddress inetSocketAddress = new InetSocketAddress(InetAddress.getLocalHost(), 4700);

            serverSocketChannel.socket().bind(inetSocketAddress);
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_CONNECT).attach(id ++);

            System.out.println("Server started ...port: 4700");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listen(Selector inSelector) {
        try {
            while (true) {
                Thread.sleep(1000);
                inSelector.select();// 阻塞，直到有就绪事件为止
                Set<SelectionKey> readySelectionKey = inSelector.selectedKeys();
                Iterator<SelectionKey> it = readySelectionKey.iterator();
                while (it.hasNext()) {
                    SelectionKey selectionKey = it.next();
                    if (selectionKey.isAcceptable()) {
                        System.out.println(selectionKey.attachment() + " - 接受请求事件");
                        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
                        serverSocketChannel
                                .accept()
                                .configureBlocking(false)
                                .register(inSelector, SelectionKey.OP_READ | SelectionKey.OP_WRITE)
                                .attach(id ++);
                        System.out.println(selectionKey.attachment() + " - 已连接");
                    }
                    if (selectionKey.isReadable()) {
                        System.out.println(selectionKey.attachment() + " - 读数据事件");
                        SocketChannel clientChannel=(SocketChannel)selectionKey.channel();
                        ByteBuffer receiveBuf = ByteBuffer.allocate(bufferSize);
                        clientChannel.read(receiveBuf);
                        System.out.println(selectionKey.attachment() + " - 读取数据：" + getString(receiveBuf));
                    }
                    if (selectionKey.isWritable()) {
                        System.out.println(selectionKey.attachment() + " - 写数据事件");
                        SocketChannel clientChannel = (SocketChannel) selectionKey.channel();
                        ByteBuffer sendBuf = ByteBuffer.allocate(bufferSize);
                        String sendText = "hello\n";
                        sendBuf.put(sendText.getBytes());
                        sendBuf.flip();// 写完数据后调用此方法
                        clientChannel.write(sendBuf);
                    }
                    if (selectionKey.isConnectable()) {
                        System.out.println(selectionKey.attachment() + " - 连接事件");
                    }
                    it.remove();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getString(ByteBuffer buffer) {
        StringBuilder string = new StringBuilder();
        try {
            for (int i = 0; i < buffer.position(); i++) {
                string.append((char) buffer.get(i));
            }
            return string.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }
}
