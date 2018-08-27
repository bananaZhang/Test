package serialize;

import java.io.*;

/**
 * @author ZJY
 * @ClassName: SerializeTest
 * @Description: SerializeTest
 * @date 2018/4/19 19:26
 */
public class SerializeTest {
    public static void main(String[] args) {
        try {
            serialize();
            Demo demo = deSerialize();
            System.out.println(demo.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void serialize() throws Exception {
        Demo demo = new Demo();
        demo.setName("kk");
        demo.setSex("F");
        demo.setAge(21);
        demo.setWeight(100);

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("d:/ttt.txt")));
        oos.writeObject(demo);
        System.out.println("序列化成功");
        oos.close();
    }

    private static Demo deSerialize() throws Exception {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("d:/ttt.txt")));
        Demo demo = (Demo) ois.readObject();
        System.out.println("反序列化成功");
        ois.close();

        return demo;
    }
}

class Demo implements Serializable {
    // 如果序列化后吧UID改掉，反序列化会报错，版本号要保持一致
    private static final long serialVersionUID = 4431013442491729713L;

    private String name;
    private String sex;
    transient private Integer age;
    private Integer weight;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Demo{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", weight=" + weight +
                '}';
    }
}
