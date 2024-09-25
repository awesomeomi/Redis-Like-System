package com.example.redissystem.controller;

import com.example.redissystem.service.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lists")
public class ListController {
    @Autowired
    private ListService listService;

    @PostMapping("/{key}/push")
    public ResponseEntity<String> push(@PathVariable String key, @RequestParam String value) {
        listService.push(key, value);
        return ResponseEntity.ok("pushed:{}"+value);
    }

    @GetMapping("/{key}/pop")
    public ResponseEntity<String> pop(@PathVariable String key) {
        String value = listService.pop(key);
        return value != null ? ResponseEntity.ok(value) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{key}")
    public ResponseEntity<List<String>> getList(@PathVariable String key) {
        List<String> values = listService.getList(key);
        return values != null ? ResponseEntity.ok(values) : ResponseEntity.notFound().build();
    }
}
