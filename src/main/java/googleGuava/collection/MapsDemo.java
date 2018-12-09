package googleGuava.collection;

import com.google.common.collect.*;

import java.util.Collection;
import java.util.Map;

/**
 * @author ZJY
 * @ClassName: MapsDemo
 * @Description: MapsDemo
 * @date 2018/12/6 11:24
 */
public class MapsDemo {

    public static void main(String[] args) {
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
        System.out.println(immutableMap);

        Map<Integer, String> map = ImmutableMap.of(1, "A", 2, "B", 3, "C");
        System.out.println(map);
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

    /**
     * 一键多值的map，类似于Map<String, List<String>>结构，可实现对list分组
     */
    public static void multiMap() {
        // 还有SetMultiMap
        Multimap<String, String> multimap = ArrayListMultimap.create();
        multimap.put("fruit", "apple");
        multimap.put("fruit", "banana");
        multimap.put("fruit", "apple");
        multimap.put("pet", "dog");
        multimap.put("pet", "cat");

        System.out.println(multimap);
        System.out.println(multimap.get("fruit"));

        multimap.asMap();// 返回Map<String, Collection<String>>字符串

        System.out.println("entry size = " + multimap.size());// 返回的是entry的数量 5
        System.out.println("keySet size = " + multimap.keySet().size());// 返回不重复键的数量 2

        Collection<String> fruits = multimap.get("fruit");
        System.out.println(fruits);// 取出集合
        System.out.println(ImmutableSet.copyOf(fruits));// 取出集合并去重
        // 取出所有的value
        System.out.println("----- all value -----");
        for (String value : multimap.values()) {
            System.out.println(value);
        }

        multimap.remove("fruit", "apple");
        System.out.println(multimap.get("fruit"));

        multimap.removeAll("fruit");
        System.out.println(multimap.get("fruit"));
    }

}
