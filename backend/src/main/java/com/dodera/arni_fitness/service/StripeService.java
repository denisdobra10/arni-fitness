package com.dodera.arni_fitness.service;

import com.dodera.arni_fitness.dto.request.MembershipRequest;
import com.dodera.arni_fitness.dto.SignUpRequest;
import com.dodera.arni_fitness.mail.MailService;
import com.dodera.arni_fitness.model.Membership;
import com.dodera.arni_fitness.model.Purchase;
import com.dodera.arni_fitness.model.Subscription;
import com.dodera.arni_fitness.model.User;
import com.dodera.arni_fitness.repository.MembershipRepository;
import com.dodera.arni_fitness.repository.PurchaseRepository;
import com.dodera.arni_fitness.repository.SubscriptionRepository;
import com.dodera.arni_fitness.repository.UserRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.Invoice;
import com.stripe.model.Product;
import com.stripe.model.checkout.Session;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.ProductCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class StripeService {

    private final UserRepository userRepository;
    private final MembershipRepository membershipRepository;
    private final PurchaseRepository purchaseRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final MailService mailService;

    public StripeService(UserRepository userRepository, MembershipRepository membershipRepository, PurchaseRepository purchaseRepository, SubscriptionRepository subscriptionRepository, MailService mailService) {
        this.userRepository = userRepository;
        this.membershipRepository = membershipRepository;
        this.purchaseRepository = purchaseRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.mailService = mailService;
        Stripe.apiKey = "sk_test_51PYYqtRoyfxq4ZhIzdbgKVjzRR4kurLUJryrHV5CCOfkUwQbrJvpGW5BTrQJTBMkqMUo3GS8DXFpZD8Nh3cOPDag00OpYD9wUm";
    }

    public String handleProductCreation(MembershipRequest membershipRequest) throws StripeException {
        ProductCreateParams params =
                ProductCreateParams.builder()
                        .setName(membershipRequest.title())
                        .setActive(true)
                        .setDescription(membershipRequest.description())
                        .setDefaultPriceData(ProductCreateParams.DefaultPriceData.builder()
                                .setCurrency("ron")
                                .setUnitAmount((long) membershipRequest.price() * 100)
                                .build())
                        .build();
        Product product = Product.create(params);

        return product.getId();
    }

    public String handleCustomerCreation(SignUpRequest signUpRequest) throws StripeException {
        CustomerCreateParams params =
                CustomerCreateParams.builder()
                        .setName(signUpRequest.name())
                        .setEmail(signUpRequest.email())
                        .setPhone(signUpRequest.phoneNumber())
                        .build();

        Customer customer = Customer.create(params);
        return customer.getId();
    }

    public Map<String, String> handleSubscriptionCreation(User user, Membership membership) throws StripeException {
        Customer customer = Customer.retrieve(user.getStripeCustomerId());
        Product product = Product.retrieve(membership.getStripeProductId());

        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setSuccessUrl("http://localhost:5173/payment-redirect")
                        .addLineItem(
                                SessionCreateParams.LineItem.builder()
                                        .setPrice(product.getDefaultPrice())
                                        .setQuantity(1L)
                                        .build()
                        )
                        .setBillingAddressCollection(SessionCreateParams.BillingAddressCollection.REQUIRED)
                        .setInvoiceCreation(SessionCreateParams.InvoiceCreation.builder().setEnabled(true).build())
                                .setCustomer(customer.getId())
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setTaxIdCollection(SessionCreateParams.TaxIdCollection.builder().setEnabled(true).build())
                        .setCustomerUpdate(SessionCreateParams.CustomerUpdate.builder()
                                .setName(SessionCreateParams.CustomerUpdate.Name.AUTO)
                                .setAddress(SessionCreateParams.CustomerUpdate.Address.AUTO).build())
                        .setConsentCollection(SessionCreateParams.ConsentCollection.builder()
                                .setPaymentMethodReuseAgreement(
                                        SessionCreateParams.ConsentCollection.PaymentMethodReuseAgreement.builder()
                                                .setPosition(SessionCreateParams.ConsentCollection.PaymentMethodReuseAgreement.Position.AUTO).build()).build())
                        .build();

        Session session = Session.create(params);

        return Map.of("checkoutLink", session.getUrl(), "sessionId", session.getId());
    }

    public void handleCheckoutSessionCompleted(Session session) {
        if (session == null) {
            return;
        }
        String stripeCustomer = session.getCustomer();
        String stripeInvoice = session.getInvoice();

        // Handle the checkout session completed event
        try {
            User user = userRepository.findByStripeCustomerId(stripeCustomer).orElseThrow(() -> new RuntimeException("User not found"));
            Invoice invoice = Invoice.retrieve(stripeInvoice);
            if (invoice.getPaid()) {
                Purchase purchase = purchaseRepository.findByStripeCheckoutSessionId(session.getId()).orElseThrow(() -> new RuntimeException("Purchase not found"));
                purchase.setStatus("PAID");
                purchase.setPaymentLink(invoice.getHostedInvoiceUrl());

                Membership membership = purchase.getMembership();
                Subscription subscription = new Subscription();
                subscription.setPurchase(purchase);
                subscription.setEntriesLeft(membership.getEntries());
                subscription.setPeriod(membership.getAvailability());
                subscription.setStartDate(LocalDateTime.now());

                user.setLastSubscription(subscription);
                userRepository.save(user);
                mailService.sendPaymentMessage(user.getEmail(), user.getName(), purchase.getPaymentLink(), membership);
            }
        } catch (Exception e) {
            return;
        }
    }
}
