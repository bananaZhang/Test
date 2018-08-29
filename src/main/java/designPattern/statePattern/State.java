package designPattern.statePattern;

/**
 * zhangjunyang 2018/8/29 21:10
 */
public interface State {

    /**
     * 投入零钱
     */
    void insertQuarter();

    /**
     * 退回零钱
     */
    void ejectQuarter();

    /**
     * 转动曲柄
     */
    void turnCrank();

    /**
     * 发放糖果
     */
    void dispense();
}
