package designPattern.statePattern;

/**
 * 没有投币状态
 * zhangjunyang 2018/8/29 21:12
 */
public class NoQuarterState implements State {

    private GumballMachine gumballMachine;

    public NoQuarterState(GumballMachine gumballMachine) {
        this.gumballMachine = gumballMachine;
    }

    @Override
    public void insertQuarter() {
        System.out.println("You insert a quarter");
    }

    @Override
    public void ejectQuarter() {
        System.out.println("You haven't inserted a quarter");
    }

    @Override
    public void turnCrank() {
        System.out.println("You turned, but there's no  quarter");
    }

    @Override
    public void dispense() {
        System.out.println("You need to pay first");
    }
}
