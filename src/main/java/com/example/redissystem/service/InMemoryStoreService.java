package com.example.redissystem.service;

import com.example.redissystem.model.RedisData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import  java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class InMemoryStoreService implements Serializable {

    private final Map<String, RedisData> store = new ConcurrentHashMap<>();
    private final String filePath = "redis_store.ser";
    private final Integer memoryLimit = 100;
    private String evictionPolicy = "LRU";
    private final String snapshotFilePath = "redis_snapshot.rdb";
    private final String AOFFilePath = "redis.aof";



    public void set( String key, String value, Long ttl, Instant lastAccessTime, Integer accessCount) {
        Instant expirationTime = (ttl != null) ? Instant.now().plusMillis(ttl) : null;
        store.put(key, new RedisData(key, value, expirationTime, lastAccessTime, accessCount));
    }

    public RedisData get(String key) {
        RedisData data = store.get(key);

        if(data != null && data.isExpired()) {
            store.remove(key);
            return null;
        }

        if(data != null){
            data.updateAccessMetaData();
        }
        return data;

    }

    private void evict() {
        switch (evictionPolicy) {
            case "LRU":

        }
    }

    private void evictLRU() {
        store.entrySet().stream().min(Comparator.comparing(entry -> entry.getValue().getLastAccessTime()))
                .ifPresent(entry -> store.remove(entry.getKey()));
        System.out.println("Evicted based on LRU");
    }

    private void evictLFU() {
        store.entrySet().stream().min(Comparator.comparing(entry -> entry.getValue().getAccessCount()))
                .ifPresent(entry -> store.remove(entry.getKey()));
        System.out.println("Evicted based on LFU");
    }

    private void evictRandom() {
        String randomKey = store.keySet().stream().skip((int) (store.size() * Math.random())).findFirst().orElse(null);
        if( randomKey != null) {
            store.remove(randomKey);
        }
        System.out.println("Evicted randomly");
    }

    public void setEvictionPolicy(String policy) {
        this.evictionPolicy = policy;
    }

    public int getMemorySize() {
        return store.size();
    }

    public List<RedisData> mget( String... keys) {
        List<RedisData> results = new ArrayList<>();
        for(String key: keys) {
            RedisData data = get(key);
            results.add(data);
        }
        return results;
    }

    public void delete( String key) {
        store.remove(key);
    }

    public void saveToFile() {
        try(ObjectOutputStream oos  = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(store);
            System.out.println("data saved to file");
            System.out.println("stored keys: " + store.keySet());
        } catch ( Exception e) {
            e.printStackTrace();
        }
    }

    public void saveToSnapshot() {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(snapshotFilePath))) {
            oos.writeObject(store);
            System.out.println("Snapshot saved");
        } catch ( Exception e) {
            e.printStackTrace();
        }
    }

    public void loadSnapshot() {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(snapshotFilePath))) {
            Map<String, RedisData> loadedStore = (Map<String, RedisData>) ois.readObject();
            store.putAll(loadedStore);
            System.out.println("Loaded Snapshot");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logCommand(String command) {
        try (FileWriter fw = new FileWriter(AOFFilePath, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadAOF() {
        try (BufferedReader br = new BufferedReader(new FileReader(AOFFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                String command = parts[0].toUpperCase();
                switch (command) {
                    case "SET":
                        set(parts[1], parts[2], null, Instant.now(), 0);
                        break;
                }
            }
            System.out.println("AOF loaded.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Scheduled(fixedRate = 60000)
    public void scheduledSaveSnapshot() {
        saveToSnapshot();
    }

    @SuppressWarnings("unchecked")
    public void loadFromfile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            Map<String, RedisData> loadedStore = (Map<String, RedisData>) ois.readObject();
            store.putAll(loadedStore);
            System.out.println("Loaded data from the file");
            System.out.println("Loaded keys: " + store.keySet());
        } catch ( Exception e) {
            e.printStackTrace();
        }
    }

    @Scheduled(initialDelay = 60000,fixedRate = 60000)
    public void saveStoredPeriodically(){
        saveToFile();
    }

    @Scheduled(initialDelay = 5000, fixedRate = 60000)
    public void loadStorePeriodically(){
        loadFromfile();
    }

    @Scheduled(fixedRate = 60000)
    public void removeExpiredKeys() {
        store.entrySet().removeIf(entry -> entry.getValue().isExpired());
    }

}
