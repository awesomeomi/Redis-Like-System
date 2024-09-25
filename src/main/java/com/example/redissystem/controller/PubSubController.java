package com.example.redissystem.controller;

import com.example.redissystem.service.PubSubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pubsub")
public class PubSubController {

    @Autowired
    private PubSubService pubSubService;

    @PostMapping("/publish/{channel}")
    public ResponseEntity<String> publish(@PathVariable String channel, @RequestParam String message) {
        pubSubService.publish(channel, message);
        return ResponseEntity.ok("Message published to channel: " + channel);
    }

    @PostMapping("/subscribe/{channel}")
    public ResponseEntity<String> subscribe(@PathVariable String channel, @RequestParam String clientName) {
        pubSubService.subscribe(channel, message -> System.out.println("[" + clientName + "] Received: " + message));
        return ResponseEntity.ok(clientName + " subscribed to channel: " + channel);
    }
}
