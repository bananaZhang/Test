package reflect;

import bean.Organization;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ZJY
 * @ClassName: ReflectListSetter
 * @Description: ReflectListSetter
 * @date 2018/5/14 15:51
 */
public class ReflectListSetter {

    public static void main(String[] args) {
        Organization o1 = new Organization(1, "1");
        Organization o2 = new Organization(2, "2");
        Organization o3 = new Organization(3, "3");
        Organization o4 = new Organization(4, "2");
        Organization o5 = new Organization(5, "1");
        List<Organization> list = new ArrayList<>();
        list.add(o1);
        list.add(o2);
        list.add(o3);
        list.add(o4);
        list.add(o5);

        try {
            setterName(list);
        } catch (Exception e) {
            e.printStackTrace();
        }

        list.forEach(System.out::println);
    }


    public static void setterName(List list) throws Exception {
        Object obj = list.get(0);
        Class clazz = obj.getClass();
        Field[] fds = clazz.getDeclaredFields();
        Method getMethod = null, setMethod = null;
        for (Field field : fds) {
            if ("orgName".equals(field.getName())) {
                setMethod = clazz.getMethod("setOrgName", field.getType());
                getMethod = clazz.getMethod("getOrgNo");
            }
        }
        Map<String, String> map = new HashMap<>();
        map.put("1", "one");
        map.put("2", "two");
        map.put("3", "three");
        if (null != setMethod && null != getMethod) {
            for (Object o : list) {
                String name = (String) getMethod.invoke(o);
                setMethod.invoke(o, map.get(name));
            }
        }
    }

}
