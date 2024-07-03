package com.dodera.arni_fitness.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String authid;

    @Column(nullable = false)
    private String name;

    @JsonIgnore
    @Column
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(length = 4, unique = true)
    private Integer pin;

    @Column(nullable = false)
    private String phoneNumber;

    @Column
    private String stripeCustomerId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "last_subscription_id", referencedColumnName = "id")
    private Subscription lastSubscription;

    @JsonIgnore
    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<Purchase> purchases;
}
