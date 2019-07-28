package designPattern.commandPattern;

/**
 * 相当于Invoker发送了一个command给Receiver去执行
 */
public class Client {
    public static void main(String[] args) {
        Receiver receiver = new Receiver();
        Command command = new ConcreteCommand(receiver);
        Invoker invoker = new Invoker();
        invoker.setCommand(command);
        invoker.action(); // 客户端通过调用者来执行命令
    }
}
