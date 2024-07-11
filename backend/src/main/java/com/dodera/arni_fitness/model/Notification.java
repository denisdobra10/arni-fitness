package com.dodera.arni_fitness.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "notifications",uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_email", "session_id"})
})
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "session_id")
    private Long sessionId;
}
