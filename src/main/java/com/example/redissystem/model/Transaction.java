package com.example.redissystem.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Transaction {
    private List<Runnable> commands = new ArrayList<>();

    public void addCommand(Runnable command) {
        commands.add(command);
    }

    public void execute() {
        commands.forEach(Runnable::run);
        commands.clear();
    }

    public void rollback() {
        commands.clear();
    }
}
