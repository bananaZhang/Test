package designPattern.commandPattern;

/**
 * 客户端调用者，持有一个命令对象
 */
public class Invoker {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void action() {
        System.out.println("I will send a msg");
        this.command.execute();
    }
}
