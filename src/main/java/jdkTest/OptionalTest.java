package jdkTest;

import bean.JustBean;

import java.util.Optional;

/**
 * java8 中的optional类
 * 如果Optional实例持有一个非空值，则isPresent()方法返回true，否则返回false；orElseGet()方法，Optional实例持有null，则可以接受一个lambda表达式生成的默认值；
 * map()方法可以将现有的Opetional实例的值转换成新的值；orElse()方法与orElseGet()方法类似，但是在持有null的时候返回传入的默认值。
 * @author zhangjunyang 2017年12月3日 下午3:27:28
 */
public class OptionalTest {

	/*
	 * Optional的三种构造方式:Optional.of(obj), Optional.ofNullable(obj), Optional.empty()
	 *
	 * */

    public static void main(String[] args) {
        JustBean beanA = new JustBean("JD1001", "zjy", null, "110", 2);
        JustBean beanB = new JustBean("JD1002", "xxn", "浙江杭州", "233", 4);

        Optional<String> orderNum = Optional.of(beanA.getOrderNum());
        System.out.println(orderNum);// Optional[JD1001]
        String orderNumStr = orderNum.toString();
        System.out.println(orderNumStr);
        // 加上orElse就可以用String作为返回值了，不然就是Optional<String>
        String orderNumString = Optional.of(beanA.getOrderNum()).orElse("xxx");
//		Optional<String> addr = Optional.of(beanA.getReceiveAddr());// 抛出空指针异常

        Optional<String> addr1 = Optional.ofNullable(beanA.getReceiveAddr()).map(s -> beanA.getReceiveAddr());
        Optional<String> addr2 = Optional.ofNullable(beanB.getReceiveAddr()).map(s -> beanB.getReceiveAddr());
        System.out.println(addr1);// Optional.empty
        System.out.println(addr2);// Optional[浙江杭州]

        // map方法可以无限连级
        Optional<JustBean> aBean = Optional.of(beanB);
        String result = aBean.map(bean -> bean.getUser()).map(u -> u.toUpperCase()).orElse(null);
        System.out.println(result);
    }
}
