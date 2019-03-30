package designPattern.responsibilityChainPattern;

/**
 * @author ZJY
 * @ClassName: CharHandler
 * @Description: CharHandler
 * @date 2019/3/30 10:51
 */
public class CharHandler extends Handler {

    public CharHandler(String name) {
        super(name);
    }

    @Override
    protected void handleRequest(String value) {
        if (value.contains("a")) {
            System.out.println("handle char...");
            return;
        }
        System.out.println("cannot handle char");
    }
}
