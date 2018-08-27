package collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author ZJY
 * @ClassName: IterateRemove
 * @Description: IterateRemove
 * @date 2018/4/21 14:46
 */
public class IterateRemove {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        //List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);// 此时迭代remove一定会报错
        int i = 0;
        for (Iterator<Integer> it = list.iterator(); it.hasNext(); i++) {
            Integer obj = it.next();
            System.out.println(obj);
            if (i % 2 == 0)
                it.remove();
        }
        list.forEach(System.out::print);
    }
}
