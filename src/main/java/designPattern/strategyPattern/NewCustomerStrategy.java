package designPattern.strategyPattern;

/**
 * @author ZJY
 * @ClassName: NewCustomerStrategy
 * @Description: 新客户策略类
 * @date 2019/3/29 20:36
 */
public class NewCustomerStrategy implements DiscountStrategy {
    @Override
    public void strategy() {
        System.out.println("New Customer don't have any discount");
    }
}
