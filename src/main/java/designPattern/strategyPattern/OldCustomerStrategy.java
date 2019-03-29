package designPattern.strategyPattern;

/**
 * @author ZJY
 * @ClassName: OldCustomerStrategy
 * @Description: 老客户策略类
 * @date 2019/3/29 20:36
 */
public class OldCustomerStrategy implements DiscountStrategy {
    @Override
    public void strategy() {
        System.out.println("Old Customer have 80% discount");
    }
}
