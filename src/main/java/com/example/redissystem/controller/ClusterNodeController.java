package com.example.redissystem.controller;

import com.example.redissystem.model.ClusterNode;
import com.example.redissystem.service.ClusterManager;
import com.example.redissystem.service.ClusterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cluster/nodes")
public class ClusterNodeController {

    @Autowired
    private ClusterService clusterService;

    @PostMapping("/add")
    public ResponseEntity<String> addNode(@RequestBody ClusterNode clusterNode) {
        clusterService.addNode(clusterNode.getNodeId(), clusterNode.getHost(), clusterNode.getPort());
        return ResponseEntity.ok("Node added: " + clusterNode.getNodeId());
    }

    @DeleteMapping("/remove/{nodeId}")
    public ResponseEntity<String> removeNode(@PathVariable String nodeId) {
        clusterService.removeNode(nodeId);
        return ResponseEntity.ok("Node removed: " + nodeId);
    }

    @GetMapping("/list")
    public ResponseEntity<List<ClusterNode>> listNodes() {
        List<ClusterNode> nodes = clusterService.getNodes();
        return ResponseEntity.ok(nodes);
    }

    @GetMapping("/{nodeId}")
    public ResponseEntity<ClusterNode> getNode(@PathVariable String nodeId) {
        ClusterNode node = clusterService.getNode(nodeId);
        return node != null ? ResponseEntity.ok(node) : ResponseEntity.notFound().build();
    }


}
