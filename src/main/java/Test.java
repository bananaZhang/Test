import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

/**
 * @author ZJY
 * @ClassName: Test
 * @Description: Test
 * @date 2018/4/21 11:41
 */
public class Test {
    public static void main(String[] args) throws Exception {
        Integer taskId = 10605;
        String s = new BASE64Encoder().encode(taskId.toString().getBytes());
        System.out.println(s);

        String sss = new String(org.apache.commons.codec.binary.Base64.encodeBase64(taskId.toString().getBytes()));
        System.out.println(sss);

        String re = new String(org.apache.commons.codec.binary.Base64.decodeBase64(sss.getBytes()));
        System.out.println(re);
//        for (; taskId < 10500; taskId ++) {
//            String temp = new BASE64Encoder().encode(taskId.toString().getBytes());
//            System.out.println(temp);
//        }
//
//        String result = new String(new BASE64Decoder().decodeBuffer(s));
//        System.out.println(result);
    }

}
