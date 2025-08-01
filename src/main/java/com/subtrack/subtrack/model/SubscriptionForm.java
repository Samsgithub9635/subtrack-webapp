package com.subtrack.subtrack.model;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class SubscriptionForm {
    @NotBlank(message = "Service name is required")
    @Size(max = 100, message = "Service name cannot exceed 100 characters")
    private String serviceName;
    
    @NotNull(message = "Start date is required")
    private LocalDate startDate;
    
    @NotNull(message = "Trial period value is required")
    @Min(value = 0, message = "Trial period value cannot be negative")
    private Integer trialPeriodValue;
    
    @NotNull(message = "Trial period unit is required")
    private ValidityUnit trialPeriodUnit;
    
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than zero")
    private BigDecimal amount;
    
    // Spring needs getters and setters to automatically fill this object with form data.
    // --- GETTERS ---
    public String getServiceName() {
        return serviceName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public int getTrialPeriodValue() {
        return trialPeriodValue;
    }

    public ValidityUnit getTrialPeriodUnit() {
        return trialPeriodUnit;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    // --- SETTERS ---
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setTrialPeriodValue(int trialPeriodValue) {
        this.trialPeriodValue = trialPeriodValue;
    }

    public void setTrialPeriodUnit(ValidityUnit trialPeriodUnit) {
        this.trialPeriodUnit = trialPeriodUnit;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
