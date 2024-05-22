package br.com.socialfit.social_fit.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "userId1", nullable = false)
    private User user1;

    @ManyToOne
    @JoinColumn(name = "userId2", nullable = false)
    private User user2;

}
