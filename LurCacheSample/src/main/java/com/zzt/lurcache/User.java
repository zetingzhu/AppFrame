package com.zzt.lurcache;

import java.io.Serializable;

/**
 * @author: zeting
 * @date: 2020/10/21
 */
public class User implements Serializable {
    public String name;
    public String age;


    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}
