package com.example.redissystem.service;

import com.example.redissystem.model.SetData;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SetService {
    private final Map<String, SetData> setStore = new ConcurrentHashMap<>();

    public void add(String key, String value) {
        setStore.computeIfAbsent(key, k -> new SetData(key)).getValues().add(value);
    }

    public void remove(String key, String value) {
        SetData setData = setStore.get(key);

        if(setData != null){
            setData.getValues().remove(value);
        }
    }

    public Set<String> getSet(String key) {
        SetData setData = setStore.get(key);
        return setData != null ? setData.getValues() : null;
    }
}
