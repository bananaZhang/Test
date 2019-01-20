package lambda;

import bean.Person;
import com.google.common.collect.Lists;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 复合lambda表达式
 * @author: ZJY
 * @date: 2019/1/20 下午9:24
 */
public class LambdaComposite {

    public static void main(String[] args) {
        Person p1 = new Person("1", 24, "F", "China");
        Person p2 = new Person("2", 19, "M", "Japan");
        Person p3 = new Person("3", 17, "M", "Korea");
        List<Person> personList = Lists.newArrayList(p1, p2);
        // 比较器链
        personList.sort(Comparator.comparing(Person::getAge)
                .reversed()
                .thenComparing(Person::getCountry));
        // 谓词复合
        Predicate<Person> adult = (Person p) -> p.getAge() > 18;
        Predicate<Person> teenager = adult.negate();// 对adult的非
        Predicate<Person> adultAndWoman = adult.and(person -> person.getSex().equals("M"));// 成年女性的筛选
        Predicate<Person> adultOrKorea = adult.or(person -> person.getCountry().equals("Korea"));// 成年女性或来自韩国
        // 函数复合
        Function<Integer, Integer> f = x -> x + 1;
        Function<Integer, Integer> g = x -> x * 2;
        Function<Integer, Integer> h1 = f.andThen(g);
        int result1 = h1.apply(1);// 等价于g(f(x))

        Function<Integer, Integer> h2 = g.compose(f);
        int reusult2 = h2.apply(1);// 等价于f(g(x))
    }

}
