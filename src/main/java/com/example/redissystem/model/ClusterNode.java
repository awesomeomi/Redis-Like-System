package com.example.redissystem.model;

import lombok.Data;

@Data
public class ClusterNode {

    private String nodeId;
    private String host;
    private Integer port;

    public ClusterNode(String nodeId, String host, int port) {
        this.nodeId = nodeId;
        this.host = host;
        this.port = port;
    }
}
