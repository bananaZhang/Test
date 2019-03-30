package designPattern.responsibilityChainPattern;

/**
 * @author ZJY
 * @ClassName: ChainMain
 * @Description: ChainMain
 * @date 2019/3/30 11:08
 */
public class ChainMain {
    public static void main(String[] args) {
        CharHandler charHandler = new CharHandler("CharHandler");
        DigitalHandler digitalHandler = new DigitalHandler("DigitalHandler");
        SpecialCharHandler specialCharHandler = new SpecialCharHandler("SpecialCharHandler");
        // 设置责任链
        charHandler.setNext(digitalHandler).setNext(specialCharHandler);

        charHandler.handle("111_aaa");
    }
}
