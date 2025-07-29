package com.subtrack.subtrack.controller;

import com.subtrack.subtrack.model.Subscription;
import com.subtrack.subtrack.model.SubscriptionForm;
import com.subtrack.subtrack.model.ValidityUnit;
import com.subtrack.subtrack.repository.SubscriptionRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class SubscriptionController {

    // 1. Declare the repository as a final variable.
    private final SubscriptionRepository subscriptionRepository;

    // 2. Use the constructor to receive the repository from Spring.
    // This is called "Constructor Dependency Injection".
    public SubscriptionController(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @GetMapping("/home")
    public String showHomePage(Model model) {
        // 1. Ask the repository for the list of all subscriptions.
        List<Subscription> allSubscriptions = subscriptionRepository.findAll();

        // 2. Add that list to the model to send to the HTML page.
        model.addAttribute("subscriptions", allSubscriptions);

        // 3. Return the name of the HTML file.
        return "home";
    }

    // We need a temporary ID counter here until we move it to the repository later.
    private final AtomicLong idCounter = new AtomicLong();

    @PostMapping("/subscription/add")
    public String addSubscription(@ModelAttribute SubscriptionForm form) {
        // 1. Calculate the next bill date from the form data.
        LocalDate nextBillDate = form.getStartDate();
        if (form.getTrialPeriodUnit() == ValidityUnit.DAYS) {
            nextBillDate = nextBillDate.plusDays(form.getTrialPeriodValue());
        } else if (form.getTrialPeriodUnit() == ValidityUnit.MONTHS) {
            nextBillDate = nextBillDate.plusMonths(form.getTrialPeriodValue());
        } else if (form.getTrialPeriodUnit() == ValidityUnit.YEARS) {
            nextBillDate = nextBillDate.plusYears(form.getTrialPeriodValue());
        }

        // 2. Create a new Subscription object.
        Subscription newSubscription = new Subscription(
                idCounter.incrementAndGet(),
                form.getServiceName(),
                nextBillDate,
                form.getAmount());

        // 3. Use the repository to SAVE the new subscription.
        // This will add it to the list AND write it to the JSON file.
        subscriptionRepository.save(newSubscription);

        // 4. Redirect back to the home page.
        return "redirect:/home";
    }

    @PostMapping("/subscription/delete/{id}")
    public String deleteSubscription(@PathVariable Long id) {
        // Tell the repository to delete the subscription with this ID
        subscriptionRepository.deleteById(id);

        // Redirect back to the home page to see the updated list
        return "redirect:/home";
    }
}