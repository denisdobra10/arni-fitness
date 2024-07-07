package com.dodera.arni_fitness.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User implements UserDetails {

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

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @JsonIgnore
    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<Purchase> purchases;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.role.getName().toUpperCase()));

        return authorities;
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
