package googleGuava.collection;

import com.google.common.collect.*;

import java.util.Map;

/**
 * @author ZJY
 * @ClassName: MapsDemo
 * @Description: MapsDemo
 * @date 2018/12/6 11:24
 */
public class MapsDemo {

    public static void main(String[] args) {
        biMap();
    }

    /**
     * 不可变且线程安全
     */
    public static void immutableMap() {
        Map<String, String> immutableMap = ImmutableMap.<String, String>builder()
                .put("1", "A")
                .put("2", "B")
                .put("3", "C")
                .build();

        Map<Integer, String> map = ImmutableMap.of(1, "A", 2, "B", 3, "C");
    }

    /**
     * 有序map
     */
    public static void soredMap() {
        ImmutableSortedMap<String, String> sortedMap = new ImmutableSortedMap.Builder<String, String>(Ordering.natural())
                .put("4", "D")
                .put("1", "A")
                .put("3", "C")
                .put("2", "B")
                .build();
        System.out.println(sortedMap);
    }

    /**
     * 可以反向映射的map，要确保值-键唯一
     */
    public static void biMap() {
        BiMap<Integer, String> biMap = HashBiMap.create();
        biMap.put(1, "A");
        biMap.put(2, "B");
        biMap.put(3, "C");

        System.out.println(biMap);
        System.out.println(biMap.inverse().get("B"));
    }
}
