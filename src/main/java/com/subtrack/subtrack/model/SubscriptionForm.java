package com.subtrack.subtrack.model;

import java.math.BigDecimal;
import java.time.LocalDate;

// This is the blueprint for the final, clean data we want to display.
public class Subscription {

    private Long id;
    private String serviceName;
    private LocalDate nextBillDate;
    private BigDecimal amount;

    public Subscription(Long id, String serviceName, LocalDate nextBillDate, BigDecimal amount) {
        this.id = id;
        this.serviceName = serviceName;
        this.nextBillDate = nextBillDate;
        this.amount = amount;
    }

    // Getters are needed so Thymeleaf can read the data in the HTML file
    public Long getId() { return id; }
    public String getServiceName() { return serviceName; }
    public LocalDate getNextBillDate() { return nextBillDate; }
    public BigDecimal getAmount() { return amount; }
}