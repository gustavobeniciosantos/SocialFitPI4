package br.com.socialfit.social_fit.controllers;

import br.com.socialfit.social_fit.DTO.PublicationDTO;
import br.com.socialfit.social_fit.DTO.PublicationResponseDTO;
import br.com.socialfit.social_fit.DTO.UserDTO;
import br.com.socialfit.social_fit.entity.Publication;
import br.com.socialfit.social_fit.entity.User;
import br.com.socialfit.social_fit.repositories.UserRepository;
import br.com.socialfit.social_fit.service.PublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/publications")
public class PublicationController {

    @Autowired
    private PublicationService publicationService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/listPublications")
    public List<Publication> getAllPublications() {
        return publicationService.getAllPublications();
    }
    @GetMapping("/{id}")
    public Optional<Publication> getPublicationById(@PathVariable UUID id) {
        return publicationService.getPublicationById(id);
    }
    @PostMapping("/createPost")
    public PublicationResponseDTO createPublication(@PathVariable UUID userId, @RequestBody Publication publication) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("Usuário não encontrado"));

        publication.setUser(user);
        publication.setUserName(user.getUsername());

        Publication savedPublication = publicationService.savePublication(publication);

        PublicationResponseDTO responseDTO = new PublicationResponseDTO();
        responseDTO.setId(savedPublication.getId());
        responseDTO.setPublicationText(savedPublication.getPublicationText());
        responseDTO.setUserName(savedPublication.getUserName());

        return responseDTO;
    }
    @GetMapping("/friendsPublications/{id}")
    public ResponseEntity<List<PublicationDTO>> getAllFriendPublications(@PathVariable UUID id) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(id);

        List<PublicationDTO> friendPublications = publicationService.getAllFriendPublications(userDTO)
                .stream()
                .map(publication -> {
                    PublicationDTO dto = new PublicationDTO();
                    dto.setId(publication.getId());
                    dto.setPublicationText(publication.getPublicationText());
                    dto.setName(publication.getUser().getName());
                    dto.setUserName(publication.getUser().getUsername());
                    dto.setLikes(publication.getLikesCount());
                    return dto;
                })
                .collect(Collectors.toList());
        Collections.shuffle(friendPublications);
        return ResponseEntity.ok(friendPublications);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PublicationDTO>> getUserPublications(@PathVariable UUID userId) {
        User currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("Usuário não encontrado"));
        List<PublicationDTO> userPublications = publicationService.getAllUserPublications(currentUser);
        return ResponseEntity.ok(userPublications);
    }
    @DeleteMapping("/{id}")
    public void deletePublication(@PathVariable UUID id) {
        publicationService.deletePublication(id);
    }

    public void savePublication(Publication publication) {
    }
}
