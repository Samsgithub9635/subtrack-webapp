package com.subtrack.subtrack.model;

import java.math.BigDecimal;
import java.time.LocalDate;

// This is your main Model. It represents the final, clean data your application uses.
public class Subscription {

    private Long id;
    private String serviceName;
    private LocalDate nextBillDate;
    private BigDecimal amount;

    // THIS IS THE CONSTRUCTOR THE CONTROLLER NEEDS.
    // It must exist and have these exact parameters.
    public Subscription(Long id, String serviceName, LocalDate nextBillDate, BigDecimal amount) {
        this.id = id;
        this.serviceName = serviceName;
        this.nextBillDate = nextBillDate;
        this.amount = amount;
    }

    // Getters are needed so the HTML page (Thymeleaf) can read the data.
    public Long getId() {
        return id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public LocalDate getNextBillDate() {
        return nextBillDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
