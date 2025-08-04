package com.subtrack.subtrack.model;

import java.math.BigDecimal;
import java.time.LocalDate;

// This is your main Model. It represents the final, clean data your application uses.
public class Subscription {

    private Long id;
    private String serviceName;
    private LocalDate nextBillDate;
    private BigDecimal amount;

    // A default constructor is needed for Spring to be able to create an instance
    // when mapping from JSON or a form.
    public Subscription() {
    }

    // A parameterized constructor is still useful for creating new objects.
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

    // You also need setters to be able to modify the object's state.
    public void setId(Long id) {
        this.id = id;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setNextBillDate(LocalDate nextBillDate) {
        this.nextBillDate = nextBillDate;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}