package br.com.socialfit.social_fit.entity;

import jakarta.persistence.*;
import lombok.Data;

import org.hibernate.validator.constraints.UUID;


@Data
@Entity
@Table(name = "friends_table")
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "userId1", nullable = false)
    private User user1;

    @ManyToOne
    @JoinColumn(name = "userId2", nullable = false)
    private User user2;
}

