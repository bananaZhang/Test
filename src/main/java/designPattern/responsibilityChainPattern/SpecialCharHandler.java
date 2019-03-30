package designPattern.responsibilityChainPattern;

/**
 * @author ZJY
 * @ClassName: SpecialCharHandler
 * @Description: SpecialCharHandler
 * @date 2019/3/30 11:05
 */
public class SpecialCharHandler extends Handler {
    @Override
    protected void handleRequest(String value) {
        if (value.contains("_")) {
            System.out.println("handle special char...");
            return;
        }
        System.out.println("cannot handle special char");
    }
}
