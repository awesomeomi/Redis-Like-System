package com.example.redissystem.controller;

import com.example.redissystem.model.HashData;
import com.example.redissystem.service.HashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/hashes")
public class HashController {
    @Autowired
    private HashService hashService;

    @PostMapping("/{key}/put")
    public ResponseEntity<String> put(@PathVariable String key, @RequestParam String field, @RequestParam String value) {
        hashService.put(key, field, value);
        return ResponseEntity.ok("Field added: " + field + " -> " + value);
    }

    @GetMapping("/{key}/get")
    public ResponseEntity<String> get(@PathVariable String key, @RequestParam String field) {
        String value = hashService.get(key, field);
        return value != null ? ResponseEntity.ok(value) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{key}/delete")
    public ResponseEntity<String> delete(@PathVariable String key, @RequestParam String field) {
        hashService.delete(key, field);
        return ResponseEntity.ok("Field deleted: " + field);
    }

    @GetMapping("/{key}")
    public ResponseEntity<Map<String, String>> getAllFields(@PathVariable String key) {
        HashData hashData = hashService.getAll(key);
        return hashData != null ? ResponseEntity.ok(hashData.getValues()) : ResponseEntity.notFound().build();
    }
}
