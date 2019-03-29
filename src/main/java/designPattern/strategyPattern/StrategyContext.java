package designPattern.strategyPattern;

/**
 * @author ZJY
 * @ClassName: StrategyContext
 * @Description: 策略上下文
 * @date 2019/3/29 20:38
 */
public class StrategyContext {

    private DiscountStrategy discountStrategy;

    public StrategyContext(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }

    public void contextMethod() {
        discountStrategy.strategy();
    }
}
