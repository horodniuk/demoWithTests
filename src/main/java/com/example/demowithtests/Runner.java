package com.example.demowithtests;

import com.example.demowithtests.util.loader.DataLoader;

import java.util.List;

public class Runner {
    public static void main(String[] args) {
        String  path = "validation_list/russian_domains_second_level.txt";
        List<String> list = DataLoader.loadDataFromResource(path);
        System.out.println(list);
    }
}
