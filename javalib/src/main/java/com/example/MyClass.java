package com.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import sun.rmi.runtime.Log;

public class MyClass {

    public static void main(String[] args) {



        testList();





        a:
        for (int i = 0; i < 5; i++) {
            b:
            for (int j = 0; j < 3; j++) {

                if (j > 2 && i > 3) {
                    continue a;
                }

                if (j > 1) {
                    break b;
                }
                System.out.println("循环 b:" + j);
            }
            System.out.println("循环 a:" + i);
        }
        System.out.println("循环 结束");

        HashMap map;

        int str11 = 0;
        String str22 = String.valueOf(null);

        System.out.println("22222222222:" + str22);

        String ss = "http://fx-t-m.cp1h.com/user/funds/source/update?t=1570788894737&deviceId=&sourceId=10&device=1&v=2.4.4.63&language=zh-CN&market=debug&exchangeId=7&tradeToken=2fd18110-7f8b-4141-b682-2faf10182f93&timeZoneOffset=28800&remoteLoginTips=1&userId=208129&removeFundsSourceFile=&fundsSourceFile=%5B%7B%22fundsSource%22%3A2%2C%22id%22%3A1%2C%22picList%22%3A%22%5B%7B%5C%22fundsSourceFile%5C%22%3A%5C%22https%3A%2F%2Fxtrend-test.oss-eu-central-1.aliyuncs.com%2Fimages%2Ffunds%2Fsource%2F208129%2F20191011101442426.jpg%5C%22%2C%5C%22id%5C%22%3A1%7D%5D%22%7D%5D&otherAddress=&addressType=1&auth=e6f2c761173d23bc5b02e16b2c048e77";


        System.out.println(java.net.URLDecoder.decode(ss));


        List<String> list = new ArrayList<>();
        list.add("a");
        List<String> unmodifiableList = Collections.unmodifiableList(list);

        System.out.println("-1-:" + list.toString());
        System.out.println("-2-:" + unmodifiableList.toString());
        list.add("b");
        list.add("c");
        System.out.println("-3-:" + list.toString());
        System.out.println("-4-:" + unmodifiableList.toString());

        unmodifiableList.add("d");


    }

    private static void testList() {

        List<String> ss = new ArrayList<>();
        ss.add("1");
        ss.add("10");
        ss.add("2");
        ss.add("4");
        ss.add("434");
        ss.add("dsdfgh");
        System.out.println("循环ss:" + ss);
        CopyOnWriteArrayList<String> csl = new CopyOnWriteArrayList<>(ss);
        System.out.println("循环csl:" + csl);

        List<String> al = new ArrayList<>(csl) ;
        System.out.println("循环 al:" + al);
    }
}
