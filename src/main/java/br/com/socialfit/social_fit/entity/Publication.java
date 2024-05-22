package br.com.socialfit.social_fit.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "publication_table")
public class Publication {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotBlank
    @Column(name = "publication_text", nullable = false)
    private String publicationText;

    @NotBlank
    @Column(name = "user_name", nullable = false)
    private String userName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userid", nullable = false)
    private User user;

    private int likesCount = 0;
}
