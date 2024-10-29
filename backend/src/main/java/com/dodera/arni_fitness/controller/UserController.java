package com.dodera.arni_fitness.controller;

import com.dodera.arni_fitness.dto.AvailableSession;
import com.dodera.arni_fitness.dto.details.MembershipDetails;
import com.dodera.arni_fitness.dto.response.PurchaseResponse;
import com.dodera.arni_fitness.dto.response.UserDetailsResponse;
import com.dodera.arni_fitness.service.RecoverPasswordService;
import com.dodera.arni_fitness.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final RecoverPasswordService recoverPasswordService;

    public UserController(UserService userService, RecoverPasswordService recoverPasswordService) {
        this.userService = userService;
        this.recoverPasswordService = recoverPasswordService;
    }

    @GetMapping("/details")
    public UserDetailsResponse getUserDetails() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.getUserDetails(email);
    }

    @PostMapping("/reserveSession/{sessionId}")
    public UserDetailsResponse reserveSession(@PathVariable Long sessionId) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userService.reserveSession(email, sessionId);
        return userService.getUserDetails(email);
    }

    @DeleteMapping("/cancelReservation/{reservationId}")
    public UserDetailsResponse cancelReservation(@PathVariable Long reservationId) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userService.cancelReservation(email, reservationId);
        return userService.getUserDetails(email);
    }

    @PostMapping("/purchase/{subscriptionId}")
    public PurchaseResponse purchaseSubscription(@PathVariable Long subscriptionId) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.purchaseSubscription(email, subscriptionId);
    }

    @GetMapping("/memberships")
    public List<MembershipDetails> getUserMemberships() {
        return userService.getMemberships();
    }

    @GetMapping("/sessions")
    public List<AvailableSession> getAvailableSessions() {
        return userService.getAvailableSessions();
    }

    @PostMapping("/reset-password")
    public String forgetPassword() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return recoverPasswordService.forgetPasswordFromApp(email);
    }
}
