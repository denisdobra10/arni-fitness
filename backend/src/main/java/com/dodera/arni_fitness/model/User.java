package com.dodera.arni_fitness.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {

    @JsonIgnore
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

    @Column
    private String phoneNumber;

    @JsonIgnore
    @Column
    private String stripeCustomerId;

    @Column
    private LocalDateTime createdAt;

    @OneToOne
    @JoinColumn(name = "last_subscription_id", referencedColumnName = "id")
    private Subscription lastSubscription;

    @JsonIgnore
    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<Purchase> purchases;
}
