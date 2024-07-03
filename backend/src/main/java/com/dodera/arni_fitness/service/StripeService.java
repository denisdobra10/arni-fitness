package com.dodera.arni_fitness.service;

import com.dodera.arni_fitness.dto.MembershipRequest;
import com.dodera.arni_fitness.dto.SignUpRequest;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.Product;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.ProductCreateParams;
import org.springframework.stereotype.Service;

@Service
public class StripeService {
    public StripeService() {
        Stripe.apiKey = "sk_test_51OcDljEsypb6dhvITecWmL4EQXoWSlxjEzKyW5kZBQ7WoKfov8KzfplvSG5jm4dmfLe6upg8xsr0zamjg9TnWsO100YATqBEYm";
    }

    public String handleProductCreation(MembershipRequest membershipRequest) throws StripeException {
        ProductCreateParams params =
                ProductCreateParams.builder()
                        .setName(membershipRequest.title())
                        .setActive(true)
                        .setDescription(membershipRequest.description())
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
}
