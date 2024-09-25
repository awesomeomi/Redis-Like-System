package com.example.redissystem.controller;

import com.example.redissystem.service.SetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/sets")
public class SetController {
    @Autowired
    private SetService setService;

    @PostMapping("/{key}/add")
    public ResponseEntity<String> add(@PathVariable String key, @RequestParam String value) {
        setService.add(key, value);
        return ResponseEntity.ok("Added: " + value);
    }

    @DeleteMapping("/{key}/remove")
    public ResponseEntity<String> remove(@PathVariable String key, @RequestParam String value) {
        setService.remove(key, value);
        return ResponseEntity.ok("Removed: " + value);
    }

    @GetMapping("/{key}")
    public ResponseEntity<Set<String>> getSet(@PathVariable String key) {
        Set<String> values = setService.getSet(key);
        return values != null ? ResponseEntity.ok(values) : ResponseEntity.notFound().build();
    }
}
