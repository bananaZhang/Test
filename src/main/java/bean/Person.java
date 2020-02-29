package bean;

import java.util.Objects;

public class Person {
    private String id;
    private Integer age;
    private String sex;
    private String country;

    public Person() {
    }

    public Person(String id, Integer age, String sex, String country) {
        this.id = id;
        this.age = age;
        this.sex = sex;
        this.country = country;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) &&
                Objects.equals(age, person.age) &&
                sex.equals(person.sex) &&
                country.equals(person.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, age, sex, country);
    }
}
