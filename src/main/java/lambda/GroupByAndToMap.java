package lambda;

import bean.Person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GroupByAndToMap {

    public static void main(String[] args) {
        List<Person> people = initData();
        // 根据年龄分组(toMap可以生成1:1的关系)
        Map<Integer, Person> ageMap = people.stream().collect(Collectors.toMap(Person::getAge, e -> e));
        for (Map.Entry<Integer, Person> entry : ageMap.entrySet()) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }
        // 根据国家分成map(groupBy是把相同key的放到一个list)
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
