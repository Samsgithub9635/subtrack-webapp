package com.subtrack.subtrack.controller;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.pulsar.PulsarProperties.Consumer.Subscription;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.subtrack.subtrack.model.SubscriptionForm;

import org.springframework.ui.Model;

@Controller
@RequestMapping("/")
public class SubscriptionController {

    @GetMapping("/home")
    public String showHomePage(Model model) {
        // 1. Create a list to hold our subscriptions
        List<SubscriptionForm> subscriptionList = new ArrayList<>();

        // 2. Create some sample subscription objects
        SubscriptionForm sub1 = new SubscriptionForm("Netflix", LocalDate.now().plusDays(10), 10, ValidityUnit.DAYS, new BigDecimal("15.99"));
        SubscriptionForm sub2 = new SubscriptionForm("Spotify Premium", LocalDate.now().plusDays(2), 2, ValidityUnit.DAYS, new BigDecimal("9.99"));
        SubscriptionForm sub3 = new SubscriptionForm("Adobe Creative Cloud", LocalDate.now().plusMonths(2), 2, ValidityUnit.MONTHS, new BigDecimal("52.99"));
        
        // 3. Add them to the list
        subscriptionList.add(sub1);
        subscriptionList.add(sub2);
        subscriptionList.add(sub3);

        model.addAttribute("subscriptions", subscriptionList);

        return "home";
    }

}
