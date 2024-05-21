package br.com.socialfit.social_fit.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "publication_table")
public class Publication {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String userName;

    private String publicationText;
}
