package com.example.redissystem.model;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class SetData {
    private String key;
    private Set<String> values;

    public SetData( String key){
        this.key = key;
        this.values = new HashSet<>();
    }

}
