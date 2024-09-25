package com.example.redissystem.model;

import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

@Data
public class RedisData implements Serializable {
    private static final long serialVersionUID = 1L;
    private String key;
    private String value;
    private Instant expirationTime;
    private Instant lastAccessTime;
    private Integer accessCount;

    public RedisData() {}

    public RedisData(String key, String value, Instant expirationTime, Instant lastAccessTime, Integer accessCount ) {
        this.key = key;
        this.value = value;
        this.expirationTime = expirationTime;
        this.lastAccessTime = lastAccessTime != null ? lastAccessTime : Instant.now();
        this.accessCount = accessCount;
    }

    public boolean isExpired() {
        return expirationTime != null && Instant.now().isAfter(expirationTime);
    }

    public void updateAccessMetaData() {
        this.lastAccessTime = Instant.now();
        this.accessCount++;
    }

}
