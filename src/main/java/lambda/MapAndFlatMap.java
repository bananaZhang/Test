package lambda;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MapAndFlatMap {
    /**
     * 目的：将两个数组转换为{x, y}形式的数据
     */
    public static void main(String[] args) {
        List<Integer> list1 = Arrays.asList(1, 2);
        List<Integer> list2 = Arrays.asList(3, 4, 5);
        /**
         * i -> list2.stream().map(j -> new int[]{i, j} 表达式返回的值是Stream<int[]>
         * 那么如果使用map的话，返回的结果就是元素为Stream<int{}>的一个流Stream<Stream<int[]>>，collect就不是我们想要的结果了
         * 如果使用了flatMap，flatMap的作用是将多个Stream<int[]>流合并成一个汇总的Stream<int[]>流，最终得到的流还是Stream<int[]>，
         * 就可以收集到我们想要的数据
         */
        List<int[]> result = list1.stream().flatMap(i -> list2.stream().map(j -> new int[]{i, j})).collect(Collectors.toList());
        for (int[] item : result) {
            for (int i : item) {
                System.out.print(i + " ");
            }
            System.out.println();
        }
    }
}
