package com.subtrack.subtrack.controller;

import com.subtrack.subtrack.model.Subscription;
import com.subtrack.subtrack.model.SubscriptionForm;
import com.subtrack.subtrack.model.ValidityUnit;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class SubscriptionController {

    private final List<Subscription> subscriptionList = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong();

    public SubscriptionController() {
        // This constructor needs the arguments to create real data
        subscriptionList.add(new Subscription(idCounter.incrementAndGet(), "Netflix", LocalDate.now().plusDays(10), new BigDecimal("15.99")));
        subscriptionList.add(new Subscription(idCounter.incrementAndGet(), "Spotify", LocalDate.now().plusDays(2), new BigDecimal("9.99")));
    }

    @GetMapping("/home")
    public String showHomePage(Model model) {
        model.addAttribute("subscriptions", subscriptionList);
        return "home";
    }

    @PostMapping("/subscriptions/add")
    public String addSubscription(@ModelAttribute SubscriptionForm form) {
        LocalDate nextBillDate = form.getStartDate();

        // ** THIS IS THE CORRECTED LOGIC **
        // We compare the UNIT from the form, not the value.
        if (form.getTrialPeriodUnit() == ValidityUnit.DAYS) {
            nextBillDate = nextBillDate.plusDays(form.getTrialPeriodValue());
        } else if (form.getTrialPeriodUnit() == ValidityUnit.MONTHS) {
            nextBillDate = nextBillDate.plusMonths(form.getTrialPeriodValue());
        } else if (form.getTrialPeriodUnit() == ValidityUnit.YEARS) {
            nextBillDate = nextBillDate.plusYears(form.getTrialPeriodValue());
        }

        // Now this will work because the logic above is correct
        Subscription newSubscription = new Subscription(
                idCounter.incrementAndGet(),
                form.getServiceName(),
                nextBillDate,
                form.getAmount()
        );

        subscriptionList.add(newSubscription);

        return "redirect:/home";
    }
}