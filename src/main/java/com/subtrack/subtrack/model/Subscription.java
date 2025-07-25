package com.subtrack.subtrack.model;

import lombok.Data;

@Data
public class Subscription {
    private String serviceName;
    private LocalDate startDate;
    private int trialPeriodValue;
    private ValidityUnit trialPeriodUnit;
    private BigDecimal amount;
}
