package designPattern.strategyPattern;

/**
 * @author ZJY
 * @ClassName: Client
 * @Description: 策略模式在现实中的运用：线程池的拒绝策略
 * @date 2019/3/29 20:41
 */
public class Client {

    public static void main(String[] args) {
        DiscountStrategy strategy = new OldCustomerStrategy();
        StrategyContext context = new StrategyContext(strategy);

        context.contextMethod();
    }
}
