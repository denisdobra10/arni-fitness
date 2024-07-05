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
import com.stripe.param.PriceCreateParams;
import com.stripe.param.ProductCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.stereotype.Service;

@Service
public class StripeService {
    private final UserRepository userRepository;
    private final MembershipRepository membershipRepository;

    public StripeService(UserRepository userRepository, MembershipRepository membershipRepository) {
        this.userRepository = userRepository;
        this.membershipRepository = membershipRepository;
    }

    public String handleProductCreation(MembershipRequest membershipRequest) throws StripeException {
        ProductCreateParams params =
                ProductCreateParams.builder()
                        .setName(membershipRequest.title())
                        .setActive(true)
                        .setDescription(membershipRequest.description())
                        .build();
        Product product = Product.create(params);

        PriceCreateParams priceParams =
                PriceCreateParams.builder()
                        .setProduct(product.getId())
                        .setCurrency("ron")
                        .setUnitAmount((long) membershipRequest.price())
                        .build();

        Price price = Price.create(priceParams);

        return price.getId();
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

    public void handleSubscriptionCreation(Long userId, Long membershipId) throws StripeException {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Nu exista userul cu acest id."));

        Membership membership = membershipRepository.findById(membershipId).orElseThrow(() -> new IllegalArgumentException("Nu exista abonamentul cu acest id."));

        Customer customer = Customer.retrieve(user.getStripeCustomerId());
        Price productPrice = Price.retrieve(membership.getStripeProductId());

        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setSuccessUrl("https://example.com/success")
                        .addLineItem(
                                SessionCreateParams.LineItem.builder()
                                        .setPrice(productPrice.getId())
                                        .setQuantity(1L)
                                        .build()
                        )
                        .setCustomer(customer.getId())
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .build();
        Session session = Session.create(params);
        // Create a subscription for the customer
        // with the product
    }
}
