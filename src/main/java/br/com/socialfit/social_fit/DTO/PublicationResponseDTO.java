package br.com.socialfit.social_fit.DTO;

import lombok.Data;

import java.util.UUID;

@Data
public class PublicationResponseDTO {
    private UUID id;
    private String publicationText;
    private String userName;
}
