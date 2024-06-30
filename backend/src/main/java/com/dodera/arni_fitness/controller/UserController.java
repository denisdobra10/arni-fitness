package com.dodera.arni_fitness.controller;

import com.dodera.arni_fitness.dto.SubscriptionDetails;
import com.dodera.arni_fitness.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}/subscription")
    public SubscriptionDetails getSubscriptionDetails(@PathVariable Long userId) {
        return userService.getSubscriptionDetails(userId);
    }


}
