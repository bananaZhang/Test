package designPattern.statePattern;

/**
 * zhangjunyang 2018/8/29 21:08
 */
public class GumballMachine {

    private State soldOutState;

    private State noQuarterState;

    private State hasQuarterState;

    private State soldState;

    private State state = soldState;
    private int count = 0;

    public GumballMachine(int numGumballs) {
        soldOutState = new SoldOutState(this);
        noQuarterState = new NoQuarterState(this);
        hasQuarterState = new HasQuarterState(this);
        soldState = new SoldState(this);

        this.count = numGumballs;
        if (numGumballs > 0) {
            state = noQuarterState;
        }
    }

    public void insetQuarter() {
        state.insertQuarter();
    }

    public void ejectQuarter() {
        state.ejectQuarter();
    }

    public void turnCrank() {
        state.turnCrank();
    }

    public void setState(State state) {
        this.state = state;
    }

    public void releaseBall() {
        System.out.println("A gumball comes rolling out the slot...");
        if (count != 0) {
            count --;
        }
    }

    public State getSoldOutState() {
        return soldOutState;
    }

    public State getNoQuarterState() {
        return noQuarterState;
    }

    public State getHasQuarterState() {
        return hasQuarterState;
    }

    public State getSoldState() {
        return soldState;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
