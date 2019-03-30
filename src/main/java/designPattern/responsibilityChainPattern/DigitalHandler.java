package designPattern.responsibilityChainPattern;

/**
 * @author ZJY
 * @ClassName: DigitalHandler
 * @Description: DigitalHandler
 * @date 2019/3/30 11:04
 */
public class DigitalHandler extends Handler {
    @Override
    protected void handleRequest(String value) {
        if (value.contains("1")) {
            System.out.println("handle digital...");
            return;
        }
        System.out.println("cannot handle digital");
    }
}
