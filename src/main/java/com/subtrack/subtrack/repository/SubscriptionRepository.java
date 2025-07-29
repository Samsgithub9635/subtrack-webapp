package com.subtrack.subtrack.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.subtrack.subtrack.model.Subscription;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository // Tells Spring this class is for data storage and retrieval
public class SubscriptionRepository {

    // The tool to read/write JSON
    private final ObjectMapper objectMapper;
    // The in-memory copy of our data
    private List<Subscription> subscriptions;
    // The location of our database file
    private File databaseFile;

    // The constructor runs ONCE when the application starts
    public SubscriptionRepository(File databaseFile) {
        this.objectMapper = new ObjectMapper();
        // We need to tell Jackson how to handle Java's new date/time objects
        objectMapper.findAndRegisterModules(); 
        
        try {
            // Find the subscriptions.json file in our resources folder
            this.databaseFile = ResourceUtils.getFile("classpath:subscriptions.json");
            // Read the file and load the data into our list
            this.subscriptions = objectMapper.readValue(databaseFile, new TypeReference<List<Subscription>>() {});
        } catch (IOException e) {
            // If the file is empty or doesn't exist, start with an empty list
            System.out.println("Could not read subscriptions.json, starting with an empty list.");
            this.subscriptions = new ArrayList<>();
        }
        this.databaseFile = databaseFile;
    }

    /**
     * Returns the full list of all subscriptions currently in memory.
     */
    public List<Subscription> findAll() {
        return subscriptions;
    }

    /**
     * Adds a new subscription to the list and saves the entire list back to the JSON file.
     * @param subscription The new subscription object to save.
     */
    public void save(Subscription subscription) {
        // Add the new subscription to our in-memory list
        subscriptions.add(subscription);
        // Now, save the updated list back to the file
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(databaseFile, subscriptions);
        } catch (IOException e) {
            // If we can't save, print an error. In a real app, we'd handle this better.
            System.err.println("Error saving subscriptions to file: " + e.getMessage());
        }
    }


    /**
     * Finds a subscription by its ID, removes it from the list,
     * and saves the updated list back to the JSON file.
     * @param id The ID of the subscription to delete.
     */
    public void deleteById(Long id) {
        // Find the subscription with the matching ID and remove it from the list.
        subscriptions.removeIf(subscription -> subscription.getId().equals(id));
        
        // After removing, save the smaller list back to the file.
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(databaseFile, subscriptions);
        } catch (IOException e) {
            System.err.println("Error saving subscriptions to file after deletion: " + e.getMessage());
        }
    }
}
