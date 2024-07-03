package com.dodera.arni_fitness.controller;

import com.dodera.arni_fitness.dto.SubscriptionDetails;
import com.dodera.arni_fitness.dto.UserDetailsResponse;
import com.dodera.arni_fitness.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}/getDetails")
    public UserDetailsResponse getUserDetails(@PathVariable Long userId) {
        return userService.getUserDetails(userId);
    }

    @PostMapping("/{userId}/reserveSession/{sessionId}")
    public UserDetailsResponse reserveSession(@PathVariable Long userId, @PathVariable Long sessionId) {
        userService.reserveSession(userId, sessionId);
        return userService.getUserDetails(userId);
    }

    @DeleteMapping("/{userId}/cancelReservation/{reservationId}")
    public UserDetailsResponse cancelReservation(@PathVariable Long userId, @PathVariable Long reservationId) {
        userService.cancelReservation(userId, reservationId);
        return userService.getUserDetails(userId);
    }
}
