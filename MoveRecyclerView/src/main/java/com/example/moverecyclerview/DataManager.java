package com.example.moverecyclerview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class DataManager {
    private static List<String> sStringList = Arrays.asList("香蕉", "苹果", "草莓", "橙子",
            "柠檬", "梨", "樱桃", "哈密瓜", "猕猴桃", "葡萄");

    public static final List<String> getData(int number) {
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            stringList.add(sStringList.get(i % sStringList.size()) + " ?");
        }
        return stringList;
    }
}
