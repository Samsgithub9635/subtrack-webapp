package com.subtrack.subtrack.controller;

import com.subtrack.subtrack.model.Subscription;
import com.subtrack.subtrack.model.SubscriptionForm;
import com.subtrack.subtrack.model.ValidityUnit;
import com.subtrack.subtrack.repository.SubscriptionRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Controller
public class SubscriptionController {

    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionController(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @GetMapping("/home")
    public String showHomePage(Model model) {
        List<Subscription> allSubscriptions = subscriptionRepository.findAll();
        model.addAttribute("subscriptions", allSubscriptions);
        model.addAttribute("subscriptionForm", new SubscriptionForm());
        return "home";
    }

    @GetMapping("/subscription/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        // Find the subscription by ID.
        // We use orElseThrow to return a 404 if the ID is not found.
        Subscription subscriptionToEdit = subscriptionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Subscription not found with ID: " + id));

        // Add the found subscription to the model. The 'th:object' will bind to this.
        model.addAttribute("subscription", subscriptionToEdit);

        // Return the name of the new HTML template.
        return "edit-form";
    }

    @PostMapping("/subscription/add")
    public String addSubscription(@Valid @ModelAttribute("subscriptionForm") SubscriptionForm form,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.err.println("Form has validation errors: " + bindingResult.getAllErrors());
            return "redirect:/home";
        }

        // Initialize nextBillDate with the start date, as this is the base case
        LocalDate nextBillDate = form.getStartDate();

        // Then apply the trial period logic
        if (form.getTrialPeriodUnit() == ValidityUnit.DAYS) {
            nextBillDate = nextBillDate.plusDays(form.getTrialPeriodValue());
        } else if (form.getTrialPeriodUnit() == ValidityUnit.MONTHS) {
            nextBillDate = nextBillDate.plusMonths(form.getTrialPeriodValue());
        } else if (form.getTrialPeriodUnit() == ValidityUnit.YEARS) {
            nextBillDate = nextBillDate.plusYears(form.getTrialPeriodValue());
        }

        Subscription newSubscription = new Subscription(
                null,
                form.getServiceName(),
                nextBillDate,
                form.getAmount());

        subscriptionRepository.save(newSubscription);
        return "redirect:/home";
    }

    @PostMapping("/subscription/edit/{id}")
    public String editSubscription(@PathVariable Long id, @ModelAttribute Subscription subscription) {
        subscription.setId(id); // Ensure the ID is set from the path variable
        subscriptionRepository.updateById(subscription);
        return "redirect:/home";
    }

    // ... (your existing controller code)

    @PostMapping("/subscription/delete/{id}")
    public String deleteSubscription(@PathVariable Long id) {
        // Check if the subscription exists before attempting to delete it
        if (subscriptionRepository.findById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Subscription not found with ID: " + id);
        }

        // Tell the repository to delete the subscription with this ID
        subscriptionRepository.deleteById(id);

        // Redirect back to the home page to see the updated list
        return "redirect:/home";
    }
}