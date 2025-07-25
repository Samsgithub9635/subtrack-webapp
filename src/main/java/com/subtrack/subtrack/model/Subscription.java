package com.subtrack.subtrack.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class Subscription {
    private String serviceName;
    private LocalDate startDate;
    private int trialPeriodValue;
    private ValidityUnit trialPeriodUnit;
    private BigDecimal amount;
}
