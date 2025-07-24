package com.subtrack.subtrack.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data // Generates all getters, setters, toString(), equals(), and hashCode() methods.
@NoArgsConstructor // Generates a no-argument constructor (e.g., new Subscription()).
@AllArgsConstructor // Generates a constructor with all arguments (e.g., new Subscription(id, name, ...)).
public class Subscription {

    private Long id;
    private String serviceName;
    private LocalDate nextBillDate;
    private BigDecimal amount;

}