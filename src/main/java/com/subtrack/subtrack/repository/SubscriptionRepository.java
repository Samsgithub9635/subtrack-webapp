package com.subtrack.subtrack.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.subtrack.subtrack.model.Subscription;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class SubscriptionRepository {

    private final ObjectMapper objectMapper;
    private final List<Subscription> subscriptions;
    private final File databaseFile;
    // New: The ID counter is now managed here
    private final AtomicLong idCounter;

    public SubscriptionRepository() {
        this.objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        File tempDbFile = null;
        List<Subscription> loadedSubscriptions = new ArrayList<>();
        long maxId = 0;

        try {
            tempDbFile = ResourceUtils.getFile("classpath:subscriptions.json");
            loadedSubscriptions = objectMapper.readValue(tempDbFile, new TypeReference<List<Subscription>>() {});
            maxId = loadedSubscriptions.stream()
                        .mapToLong(Subscription::getId)
                        .max()
                        .orElse(0L);
        } catch (IOException e) {
            System.out.println("Could not read subscriptions.json, starting with an empty list.");
        }
        
        this.databaseFile = tempDbFile;
        this.subscriptions = loadedSubscriptions;
        // New: Initialize the counter with the max ID found
        this.idCounter = new AtomicLong(maxId);
    }
    
    public List<Subscription> findAll() {
        return subscriptions;
    }
    
    public void save(Subscription subscription) {
        // New: Assign a new ID before saving
        subscription.setId(idCounter.incrementAndGet());
        subscriptions.add(subscription);
        saveToFile();
    }
    
    private void saveToFile() {
        if (databaseFile == null) {
            System.err.println("Database file not found, cannot save.");
            return;
        }
        try {
            objectMapper.writeValue(databaseFile, subscriptions);
        } catch (IOException e) {
            System.err.println("Error saving subscriptions to file: " + e.getMessage());
        }
    }

    // Updated to return an Optional, which is a modern and safer practice
    public Optional<Subscription> findById(Long id) {
        return subscriptions.stream()
                .filter(subscription -> subscription.getId().equals(id))
                .findFirst();
    }
    
    public void updateById(Subscription updatedSubscription) {
        // ... (existing update logic is fine, no changes needed here)
        for (int i = 0; i < subscriptions.size(); i++) {
            if (subscriptions.get(i).getId().equals(updatedSubscription.getId())) {
                subscriptions.set(i, updatedSubscription);
                break;
            }
        }
        saveToFile();
    }
    
    public void deleteById(Long id) {
        subscriptions.removeIf(sub -> sub.getId().equals(id));
        saveToFile();
    }
}