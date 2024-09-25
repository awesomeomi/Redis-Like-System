package com.example.redissystem.parser;

import com.example.redissystem.model.RedisData;
import com.example.redissystem.service.InMemoryStoreService;
import com.example.redissystem.service.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public class CommandParser {

    @Autowired
    private InMemoryStoreService storeService;

    public String parseAndExecution(String command, String... args) {
        switch (command.toUpperCase()) {
            case "SET":
                if(args.length < 2){
                    return "ERROR: SET requires a key and a value";
                }
                storeService.set(args[0],args[1],null, Instant.now(), 0);
                return "ok";

            case "GET":
                if(args.length < 1) {
                    return "ERROR: GET requires a key";
                }
                RedisData data = storeService.get(args[0]);
                return data != null ? data.getValue() : "nil";

            case "MSET":
                if(args.length % 2 != 0) {
                    return "ERROR: MSET requires even number of arguments";
                }
                for(int i = 0; i < args.length; i+=2){
                    storeService.set(args[i], args[i+1], null, Instant.now(), 0);
                }
                return "ok";

            case "MGET":
                if(args.length < 1) {
                    return "ERROR: MGET requires a key";
                }
                List<RedisData> values = storeService.mget(args);
                return values.toString();

            default:
                return "ERROR: Unknown Command";
        }
    }
}
