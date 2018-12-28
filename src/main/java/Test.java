import java.util.Arrays;
import java.util.List;

/**
 * @author ZJY
 * @ClassName: Test
 * @Description: Test
 * @date 2018/4/21 11:41
 */
public class Test {
    public static void main(String[] args) throws Exception {
        List<String> list = Arrays.asList("abc", " edf", "qqq ", " 12345 ");
        for (String s : list) {
            s = s.trim();
        }
        list.forEach(System.out::println);
    }
}
