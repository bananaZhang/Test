package jdkTest.strTest;

import java.util.HashSet;
import java.util.Set;

/**
 * zhangjunyang 2018/3/13 21:44
 */
public class StringImmutable {
    
    /**
     * [aaa, aaabbb]
     * [aaa, aaabbb]
     * [aaabbb, aaa]
     * [aaabbb, aaabbb]
     * @Author: zjy
     * @Date: 2018/3/13 21:49
     */
    public static void main(String[] args) {
        Set<String> set1 = new HashSet<>();
        String s1 = new String("aaa");
        String s2 = new String("aaabbb");
        set1.add(s1);
        set1.add(s2);
        System.out.println(set1);
        String s3 = s1;// 虽然s3修改了，但指向的是内存中其他地址，是s1仍指向原来的地方
        s3 += "bbb";
        System.out.println(set1);
        // 证明String不可变的必要性，sb类指向同一个修改内容后会在set中出现两个相同的值
        Set<StringBuilder> set2 = new HashSet<>();
        StringBuilder sb1 = new StringBuilder("aaa");
        StringBuilder sb2 = new StringBuilder("aaabbb");
        set2.add(sb1);
        set2.add(sb2);
        System.out.println(set2);
        StringBuilder sb3 = sb1;
        sb3.append("bbb");
        System.out.println(set2);
    }
}
