package designPattern.commandPattern;

/**
 * 具体命令实现
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
