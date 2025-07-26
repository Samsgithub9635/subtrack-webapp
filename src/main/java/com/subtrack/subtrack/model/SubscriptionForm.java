package com.subtrack.subtrack.model;

import java.math.BigDecimal;
import java.time.LocalDate;

// This is the DTO. Its only job is to carry data from the HTML form to the controller.
// Its fields MUST match the 'name' attributes of the <input> tags in your form.
public class SubscriptionForm {

    private String serviceName;
    private LocalDate startDate;
    private int trialPeriodValue;
    private ValidityUnit trialPeriodUnit;
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
