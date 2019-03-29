package designPattern.strategyPattern;

/**
 * @author ZJY
 * @ClassName: StrategyContext
 * @Description: 策略上下文
 * @date 2019/3/29 20:38
 */
/**
 * 在策略模式中，一般情况下都是上下文持有策略的引用，以进行对具体策略的调用。但具体的策略对象也可以从上下文中获取所需数据，
 * 可以将上下文当做参数传入到具体策略中，具体策略通过回调上下文中的方法来获取其所需要的数据。
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
