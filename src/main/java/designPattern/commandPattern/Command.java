package designPattern.commandPattern;

/**
 * 抽象命令（定义一系列命令操作，比如execute()、undo()、redo()等）
 */
public interface Command {
    void execute();
}
