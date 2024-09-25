package com.example.redissystem.service;

import com.example.redissystem.model.PubSubChannel;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@Service
public class PubSubService {
    private final Map<String, PubSubChannel> channels = new ConcurrentHashMap<>();

    public void publish(String channelName, String message) {
        PubSubChannel channel = channels.get(channelName);

        if(channel != null) {
            channel.publish(message);
        }else {
            System.out.println("Channel Does not Exists");
        }
    }

    public void subscribe(String channelName, Consumer<String> subscriber) {
        channels.computeIfAbsent(channelName, PubSubChannel::new).addSubscriber(subscriber);
    }
}
