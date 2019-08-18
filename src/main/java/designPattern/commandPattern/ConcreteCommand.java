package designPattern.commandPattern;

/**
 * 具体命令实现（绑定了命令操作和接收者之间的关系，execute命令的实现委托给了Receiver的action方法）
 */
public class ConcreteCommand implements Command {
    private Receiver receiver;

    public ConcreteCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        System.out.println("sending...");
        this.receiver.action();
    }
}
