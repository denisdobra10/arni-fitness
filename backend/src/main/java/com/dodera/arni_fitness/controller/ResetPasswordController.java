package com.dodera.arni_fitness.controller;

import com.dodera.arni_fitness.dto.request.ForgotPassRequest;
import com.dodera.arni_fitness.dto.request.ResetPasswordRequest;
import com.dodera.arni_fitness.service.RecoverPasswordService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/recover")
public class ResetPasswordController {
    private final RecoverPasswordService recoverPasswordService;


    public ResetPasswordController(RecoverPasswordService recoverPasswordService) {
        this.recoverPasswordService = recoverPasswordService;
    }

    @PostMapping("/forgot-password")
    public String forgetPassword(@RequestBody ForgotPassRequest forgotPassRequest) {
        recoverPasswordService.forgetPassword(forgotPassRequest.email());
        return "Daca exista un cont asociat cu aceasta adresa de email, un email de resetare a parolei a fost trimis.";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        recoverPasswordService.resetPassword(resetPasswordRequest.token(), resetPasswordRequest.password());
        return "Parola a fost resetata cu succes.";
    }
}
