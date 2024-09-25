package com.example.redissystem.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Data
public class PubSubChannel {
    private  String channelName;
    private List<Consumer<String>> subscribers = new ArrayList<>();

    public PubSubChannel(String channelName) {
        this.channelName = channelName;
    }

    public void addSubscriber(Consumer<String> subscriber) {
        subscribers.add(subscriber);
    }

    public void publish(String message) {
        subscribers.forEach(subscriber -> subscriber.accept(message));
    }
}
