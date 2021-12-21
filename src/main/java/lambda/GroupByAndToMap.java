package lambda;

import bean.Person;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GroupByAndToMap {

    public static void main(String[] args) {
        List<Person> people = initData();
        /**
         * toMap可以生成1:1的关系
         */
        Map<String, Person> idMap = people.stream().collect(Collectors.toMap(Person::getId, e -> e));
        for (Map.Entry<String, Person> entry : idMap.entrySet()) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }
        // 或者使用
        idMap = people.stream().collect(Collectors.toMap(Person::getId, Function.identity()));
        // 当toMap方法有重复的时候，上面的写法会抛出Duplicate key异常，可以通过下面的方式解决
        // (p1, p2) -> p1 表示只保留第一个值
        Map<String, Person> sexMap = people.stream().collect(Collectors.toMap(Person::getSex,
                Function.identity(), (p1, p2) -> p1));
        // 也可以通过toMap转成自己想要的map类型
        TreeMap<String, Person> idTreeMap = people.stream().collect(Collectors.toMap(Person::getId, Function.identity(),
                (p1, p2) -> p1, TreeMap::new));
        /**
         * 根据国家分成map(groupBy是把相同key的放到一个list)
         */
        Map<String, List<Person>> countryMap = people.stream().collect(Collectors.groupingBy(Person::getCountry));
        for (Map.Entry<String, List<Person>> entry : countryMap.entrySet()) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }
    }

    private static List<Person> initData() {
        Person p1 = new Person("1", 12, "M", "China");
        Person p2 = new Person("2", 22, "F", "Japan");
        Person p3 = new Person("3", 60, "M", "America");
        Person p4 = new Person("4", 20, "F", "Korea");
        Person p5 = new Person("5", 26, "M", "China");
        Person p6 = new Person("6", 23, "F", "Japan");
        Person p7 = new Person("7", 28, "F", "Korea");

        return new ArrayList<>(Arrays.asList(p1, p2, p3, p4, p5, p6, p7));
    }
}
