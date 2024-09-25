package com.example.redissystem.service;

import java.util.HashSet;
import java.util.Set;

public class ClusterManager {

    private final Set<String> nodes = new HashSet<>();
    private final ConsistentHashing hashing;

    public ClusterManager(int replicas) {
        this.hashing = new ConsistentHashing(replicas);
    }

    public void addNode( String node) {
        nodes.add(node);
        hashing.addNode(node);
    }

    public void removeNode( String node) {
        nodes.remove(node);
        hashing.removeNode(node);
    }

    public String getNode(String key) {
        return hashing.getNode(key);
    }
}
