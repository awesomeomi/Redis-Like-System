package com.example.redissystem.service;

import com.example.redissystem.model.ListData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ListService {

    private final Map<String, ListData> listStore =  new ConcurrentHashMap<>();

    @Autowired
    public TransactionService transactionService;

    public void push(String key, String value) {
//        listStore.computeIfAbsent(key, k -> new ListData(key)).getValues().add(value);
        Runnable command = () -> listStore.computeIfAbsent(key, k -> new ListData(key)).getValues().add(value);
        transactionService.addCommand(command);
    }

    public String pop(String key) {
        ListData listData = listStore.get(key);

        if(listData != null && !listData.getValues().isEmpty()) {
            return listData.getValues().remove(listData.getValues().size() - 1);
        }
        return null;
    }

    public List<String> getList( String key) {
        ListData listData = listStore.get(key);
        return listData != null ? listData.getValues() : null;
    }
}
