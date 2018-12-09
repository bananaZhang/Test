package bean;

public class Person {
    private String id;
    private Integer age;
    private String sex;
    private String country;

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
}
