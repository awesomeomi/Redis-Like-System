package com.example.redissystem.model;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class HashData {
    private String key;
    private Map<String, String > values;

    public HashData(String key){
        this.key = key;
        this.values = new HashMap<>();
    }
}
