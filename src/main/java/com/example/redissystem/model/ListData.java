package com.example.redissystem.model;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ListData {

    private String key;
    private List<String> values;

    public ListData(String key) {
        this.key = key;
        this.values = new ArrayList<>();
    }
}
