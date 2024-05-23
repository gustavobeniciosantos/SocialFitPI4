package br.com.socialfit.social_fit.DTO;

import lombok.Data;

import java.util.UUID;
@Data
public class UserDTO {

    private UUID id;
    private String name;
    private String username;

}
