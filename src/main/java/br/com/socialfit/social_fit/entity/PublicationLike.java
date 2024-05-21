package br.com.socialfit.social_fit.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "publication_like_table")
public class PublicationLike {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "publicationId", nullable = false)
    private Publication publication;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;
}