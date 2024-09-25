package com.example.redissystem.service;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class ConsistentHashing {

    private final int numberOfReplicas;

    private final SortedMap<Integer, String> cricle = new TreeMap<>();

    public ConsistentHashing(int numberOfReplicas) {
        this.numberOfReplicas = numberOfReplicas;
    }

    public void addNode(String node) {
        for (int i = 0; i < numberOfReplicas; i++) {
            cricle.put((node + i).hashCode(), node);
        }
    }

    public void removeNode(String node) {
        for (int i = 0; i < numberOfReplicas; i++) {
            cricle.remove((node + i).hashCode(), node);
        }
    }

    public String getNode(String key) {
        if(cricle.isEmpty()) {
            return null;
        }
        int hash = key.hashCode();
        SortedMap<Integer, String> tailMap = cricle.tailMap(hash);
        Integer nodeHash = tailMap.isEmpty() ? cricle.firstKey() : tailMap.firstKey();
        return cricle.get(nodeHash);
    }
}
