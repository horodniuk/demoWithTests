package com.example.demowithtests.util.loader;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;




@Slf4j
public class DataLoader {
    public static List<String> loadDataFromResource(String resourcePath) {
        List<String> dataList = new ArrayList<>();

        try (
                InputStream inputStream = ClassLoader.getSystemResourceAsStream(resourcePath);
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(inputStreamReader)
        ) {
            dataList = reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            log.error("Error loading list from resource: {}", resourcePath, e);
            e.printStackTrace();
        }

        return dataList;
    }
}
