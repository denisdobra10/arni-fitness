package com.dodera.arni_fitness.controller;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class StripeWebhook {
    private static final String STRIPE_WEBHOOK_SECRET = "whsec_...";

    @PostMapping("/stripe/webhook")
    public ResponseEntity<?> handleStripeWebhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String signature) {
        // Verify the signature

        if(!isSignatureValid(payload, signature)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature");
        }
        Event event = null;
        try {
            event = Webhook.constructEvent(payload, signature, STRIPE_WEBHOOK_SECRET);
        } catch (SignatureVerificationException e) {
            // Invalid signature
            return null;
        }

        // Handle the event
        if ("checkout.session.completed".equals(event.getType())) {
            Session session = (Session) event.getDataObjectDeserializer().getObject().orElse(null);
            if (session == null) {
                return null;
            }
            String stripeCustomer = session.getCustomer();
            String stripeInvoice = session.getInvoice();

            // Handle the checkout session completed event

        }

        return ResponseEntity.ok().build();
    }

    private boolean isSignatureValid(String payload, String signature) {
        try {
            Webhook.Signature.verifyHeader(payload, signature, STRIPE_WEBHOOK_SECRET, 0);
            return true;
        } catch (SignatureVerificationException e) {
            return false;
        }
    }
}
