package designPattern.holderdesign;

import lombok.Data;
import org.omg.CORBA.StringHolder;

/**
 * refer: https://www.jianshu.com/p/9ab6c6eb6653
 *
 * @author: ZJY
 * @date: 2020/8/2 8:30 下午
 */
public class HolderDesign {

    public static void main(String[] args) {

        UserInfo userInfo = new UserInfo();
        userInfo.setUserAddress("浙江省杭州市");
        userInfo.setUserAge(18);
        userInfo.setUserEmail("xiaoxie@163.com");
        userInfo.setUserName("小谢");
        System.out.println(userInfo);

        Holder<UserInfo> holder = new Holder<>(userInfo);
        testHolder(holder);
        System.out.println("邮箱改变了: " + holder.value + "\n");

        // 这种方法是不会输出修改后的值的
        String str = "not changed string";
        System.out.println("原字符串:" + str);
        changeString(str);
        System.out.println("changeString后的字符串:" + str + "\n");

        StringHolder stringHolder = new StringHolder("test");
        System.out.println("原字符串:" + stringHolder.value);
        changeStringHolder(stringHolder);
        // testnew String
        System.out.println("changeStringHolder后的字符串:" + stringHolder.value);

    }


    /**
     * 不通过返回值在一个方法中改变一个对象
     *
     * @param holder
     */
    public static void testHolder(Holder<UserInfo> holder) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserAddress(holder.value.getUserAddress());
        userInfo.setUserAge(holder.value.getUserAge());
        userInfo.setUserEmail("xxxxxx@2qq.com");
        userInfo.setUserName(holder.value.getUserName());
        holder.value = userInfo;
    }


    public static void changeString(String str) {
        str = "changes";
    }


    public static void changeStringHolder(StringHolder stringHolder) {
        stringHolder.value = "test new String";
    }
}

@Data
class UserInfo {

    private String userName;

    private Integer userAge;

    private String userEmail;

    private String userAddress;

}
