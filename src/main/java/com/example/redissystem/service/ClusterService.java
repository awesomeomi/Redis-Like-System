package com.example.redissystem.service;

import com.example.redissystem.model.ClusterNode;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClusterService {
    private final ConsistentHashing consistentHashing;
    private final Map<String, ClusterNode> nodes;

    public ClusterService() {
        this.consistentHashing = new ConsistentHashing(3);
        this.nodes = new HashMap<>();
    }

    public void addNode(String nodeId, String host, int port) {
        ClusterNode node = new ClusterNode(nodeId, host, port);
        nodes.put(nodeId, node);
        consistentHashing.addNode(nodeId);
    }

    public void removeNode(String nodeId) {
        nodes.remove(nodeId);
        consistentHashing.removeNode(nodeId);
    }

    public List<ClusterNode> getNodes() {
        return new ArrayList<>(nodes.values());
    }

    public ClusterNode getNode(String nodeId) {
        return nodes.get(nodeId);
    }

    public String getNodeForKey(String key) {
        return consistentHashing.getNode(key);
    }
}
