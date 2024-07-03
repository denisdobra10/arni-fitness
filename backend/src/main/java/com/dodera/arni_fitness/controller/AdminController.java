package com.dodera.arni_fitness.controller;

import com.dodera.arni_fitness.dto.MembershipRequest;
import com.dodera.arni_fitness.model.Membership;
import com.dodera.arni_fitness.service.AdminService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/create_membership")
    public Membership createMemberShip(@RequestBody  MembershipRequest membershipRequest) {
        return adminService.createMembership(membershipRequest);
    }
}
