package com.example.redissystem.controller;

import com.example.redissystem.model.RedisData;
import com.example.redissystem.service.InMemoryStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/redis-like")
public class RedisController {
    @Autowired
    private InMemoryStoreService inMemoryStoreService;

    @PostMapping("/set")
    public ResponseEntity<String> set(@RequestParam String key, @RequestParam String value, @RequestParam(required = false) Long ttl) {
        Instant lastAccessTime = Instant.now();
        Integer accessCount = 0;
        inMemoryStoreService.set(key, value, ttl, lastAccessTime, accessCount);
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/get")
    public ResponseEntity<String> get(@RequestParam String key) {
        RedisData redisData = inMemoryStoreService.get(key);
        return redisData != null ?
                ResponseEntity.ok(redisData.getValue()) :
                ResponseEntity.notFound().build();
    }

    @PostMapping("/setEvictionPolicy")
    public String setEvictionPolicy(@RequestParam String policy) {
        inMemoryStoreService.setEvictionPolicy(policy);
        return "Eviction policy set to " + policy;
    }

    @GetMapping("/memorySize")
    public int getMemorySize() {
        return inMemoryStoreService.getMemorySize();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam String key) {
        inMemoryStoreService.delete(key);

        return ResponseEntity.ok("Ok");
    }

}
