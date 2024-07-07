package com.dodera.arni_fitness.service;

import com.dodera.arni_fitness.dto.request.MembershipRequest;
import com.dodera.arni_fitness.dto.SignUpRequest;
import com.dodera.arni_fitness.model.Membership;
import com.dodera.arni_fitness.model.User;
import com.dodera.arni_fitness.repository.MembershipRepository;
import com.dodera.arni_fitness.repository.UserRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.model.checkout.Session;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.InvoiceCreateParams;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.ProductCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class StripeService {
    private final UserRepository userRepository;
    private final MembershipRepository membershipRepository;

    public StripeService(UserRepository userRepository, MembershipRepository membershipRepository) {
        this.userRepository = userRepository;
        this.membershipRepository = membershipRepository;
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

    public String handleSubscriptionCreation(String email, Long membershipId) throws StripeException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Nu exista userul cu acest id."));

        Membership membership = membershipRepository.findById(membershipId).orElseThrow(() -> new IllegalArgumentException("Nu exista abonamentul cu acest id."));

        Customer customer = Customer.retrieve(user.getStripeCustomerId());
        Product product = Product.retrieve(membership.getStripeProductId());

        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setSuccessUrl("https://example.com/success")
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
//                        .putAllMetadata(Map.of("customer", customer.toString(), "product", product.toString()))
                        .build();
        Session session = Session.create(params);

        return session.getUrl();
        // Create a subscription for the customer
        // with the product
    }
}
