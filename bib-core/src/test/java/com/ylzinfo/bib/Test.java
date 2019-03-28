package com.ylzinfo.bib;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/********************************************************************************
 *
 * Title: 
 *
 * Description:
 *
 * @author chenlm
 * create date on 2019/1/29
 *
 *******************************************************************************/
public class Test {
    @org.junit.Test
    public void test1() throws Exception {
        System.out.println(AESUtils.encrypt("123", AESUtils.KEY));
        System.out.println(AESUtils.decrypt("fcpeZgOv4VjCouxRKuPy0A==", AESUtils.KEY));

    }

    @org.junit.Test
    public void test2() throws Exception {
        String a = "111";
        System.out.println(a.hashCode());
        Map map = new HashMap();
        map.put("hah", a);
        int b = 7 & 1;
        System.out.println(b);
    }

    @org.junit.Test
    public void test3() throws Exception {
        LinkedList<String> list = new LinkedList<>();
        list.addFirst("1");
        list.addFirst("2");
        list.addFirst("3");
        System.out.println(list.element());
        System.out.println(list.peek());
        System.out.println(Arrays.toString(list.toArray()));
    }

    @org.junit.Test
    public void test4() throws Exception {
        int  b=(6-9);
        int a = -1 % 3;
        System.out.println(a==1);
    }
}
