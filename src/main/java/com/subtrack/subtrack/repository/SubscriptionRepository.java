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

@Repository // Tells Spring this class is for data storage and retrieval
public class SubscriptionRepository {

    // The tool to read/write JSON
    private final ObjectMapper objectMapper;
    // The in-memory copy of our data
    private List<Subscription> subscriptions;
    // The location of our database file
    private final File databaseFile;

    // The constructor is updated to safely initialize the final databaseFile field.
    public SubscriptionRepository() {
        this.objectMapper = new ObjectMapper();
        // We need to tell Jackson how to handle Java's new date/time objects
        objectMapper.findAndRegisterModules();
        // Make the JSON output pretty and readable
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        
        File tempDbFile; // Use a temporary local variable to hold the file reference
        try {
            // Find the subscriptions.json file in our resources folder
            tempDbFile = ResourceUtils.getFile("classpath:subscriptions.json");
            // Read the file and load the data into our list
            this.subscriptions = objectMapper.readValue(tempDbFile, new TypeReference<List<Subscription>>() {});
        } catch (IOException e) {
            // If the file is empty or doesn't exist, start with an empty list
            System.out.println("Could not read subscriptions.json, starting with an empty list.");
            tempDbFile = null; // Mark the file as not found
            this.subscriptions = new ArrayList<>();
        }
        // Assign the result to the final field once at the end. This removes the error.
        this.databaseFile = tempDbFile;
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
        saveToFile();
    }
    
    /**
     * A private helper method to handle writing to the file to avoid repeating code.
     */
    private void saveToFile() {
        if (databaseFile == null) {
            System.err.println("Database file not found, cannot save.");
            return;
        }
        try {
            objectMapper.writeValue(databaseFile, subscriptions);
        } catch (IOException e) {
            // If we can't save, print an error. In a real app, we'd handle this better.
            System.err.println("Error saving subscriptions to file: " + e.getMessage());
        }
    }


    /**
     * Finds and returns a single subscription by its unique ID.
     * @param id The ID of the subscription to find.
     * @return The Subscription object if found, otherwise null.
     */
    public Subscription findById(Long id) {
        // Use a modern Java Stream to find the first subscription that matches the ID.
        return subscriptions.stream()
                .filter(subscription -> subscription.getId().equals(id))
                .findFirst()
                .orElse(null); // Return null if no subscription with that ID is found.
    }


    /**
     * Updates an existing subscription in the list and saves the changes to the file.
     * @param updatedSubscription The subscription object containing the new details.
     */
    public void updateById(Subscription updatedSubscription) {
        // Find the index of the old subscription in the list.
        for (int i = 0; i < subscriptions.size(); i++) {
            if (subscriptions.get(i).getId().equals(updatedSubscription.getId())) {
                // Once found, replace the old object with the updated one.
                subscriptions.set(i, updatedSubscription);
                break; // Exit the loop once we've found and replaced it.
            }
        }
        // Save the entire updated list back to the file.
        saveToFile();
    }


    /**
     * Finds a subscription by its ID, removes it from the list,
     * and saves the updated list back to the JSON file.
     * @param id The ID of the subscription to delete.
     */
    public void deleteById(Long id) {
        // Use the removeIf method to find the subscription with the matching ID and remove it.
        subscriptions.removeIf(sub -> sub.getId().equals(id));
        
        // After removing, save the changes back to the file.
        saveToFile();
    }

}
