package collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * zhangjunyang 2018/1/13 14:10
 */
public class ListTest {
    public static void main(String[] args) {
        new ListTest().asListTest();
    }

    public void asListTest() {
        int[] arr = {1, 2, 3, 4, 5};
        List list1 = Arrays.asList(arr);
        System.out.println(list1.size());// 打印1，此时list中就一个元素，为数组

        List list2 = Arrays.asList(1, 2, 3, 4, 5);
        list2.forEach(System.out::println);
        list2.add(6);// 报错：Unsupport
    }

    /**
     * ArrayList的克隆是深克隆
     */
    @SuppressWarnings("unchecked")
    public void aboutClone() {
        ArrayList<String> listA = new ArrayList<>(Arrays.asList("a", "b", "d"));
        ArrayList<String> listB = (ArrayList<String>) listA.clone();
        System.out.println(listA == listB);

        listA.clear();
        System.out.println(listA.toString());
        System.out.println(listB.toString());
    }

    /**
     * 循环向数组中添加元素使用CopyOnWriteArrayList
     * */
    public void addEleToList() {
        List<String> list = new ArrayList<>(Arrays.asList("a", "b", "c", "d"));
        for (String s : list) {
            if ("b".equals(s)) {
                //list.add("B");// 抛出java.util.ConcurrentModificationException
            }
        }

        List<String> copyList = new CopyOnWriteArrayList<>(list);
        for (String s : copyList) {
            if ("b".equals(s)) {
                copyList.add("B");// 可以循环向list中添加元素
            }
        }
        System.out.println(copyList.toString());
    }
}
