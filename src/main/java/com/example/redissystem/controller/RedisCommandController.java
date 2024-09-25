package com.example.redissystem.controller;

import com.example.redissystem.parser.CommandParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis")
public class RedisCommandController {

    @Autowired
    private CommandParser commandParser;

    @PostMapping("/command")
    public ResponseEntity<String> executeCommand(@RequestParam String command, @RequestParam String[] args) {
        String result = commandParser.parseAndExecution(command, args);
        return ResponseEntity.ok(result);
    }
}
