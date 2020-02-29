package collection.set;

import bean.Person;

import java.util.HashSet;
import java.util.Set;

public class ContainLijie {
    public static void main(String[] args) {
        Person p1 = new Person();
        p1.setCountry("china");
        p1.setSex("M");

        Person p2 = new Person();
        p2.setCountry("china");
        p2.setSex("F");

        System.out.println(p1.equals(p2));// false

        Set<Person> set = new HashSet<>();
        set.add(p1);
        p1.setSex("F");
        System.out.println(p1.equals(p2));// true
        // 虽然现在p1和p2是相等的，但是在p1 set进HashSet时，已经计算出hash值并放入了一个位置，后面p1的属性改变并不会重新计算hash值
        // 更换存放位置，所以根据p2计算出来的hash值位置上是空的，导致set.contains(p2)为空，即使现在p1.equals(p2) == true
        System.out.println(set.contains(p2));// false
    }
}
