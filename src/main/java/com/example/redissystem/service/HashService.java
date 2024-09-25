package com.example.redissystem.service;

import com.example.redissystem.model.HashData;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class HashService {
    private final Map<String, HashData> hashStore = new ConcurrentHashMap<>();

    public void put(String key, String field, String value) {
        hashStore.computeIfAbsent(key, k -> new HashData(key)).getValues().put(field, value);
    }

    public String get(String key, String field) {
        HashData hashData = hashStore.get(key);

        return hashData != null ? hashData.getValues().get(field) : null;
    }

    public void delete(String key, String field) {
        HashData hashData = hashStore.get(key);
        if (hashData != null) {
            hashData.getValues().remove(field);
        }
    }

    public HashData getAll(String key) {
        return hashStore.get(key);
    }
}
