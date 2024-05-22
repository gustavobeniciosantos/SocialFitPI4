package br.com.socialfit.social_fit.controllers;

import br.com.socialfit.social_fit.entity.Publication;
import br.com.socialfit.social_fit.entity.User;
import br.com.socialfit.social_fit.service.PublicationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/publications")
public class PublicationController {

    @Autowired
    private PublicationService publicationService;

    @GetMapping("/list-publications")
    public List<Publication> getAllPublications() {
        return publicationService.getAllPublications();
    }

    @GetMapping("/{id}")
    public Optional<Publication> getPublicationById(@PathVariable UUID id) {
        return publicationService.getPublicationById(id);
    }

    @PostMapping("/create-post")
    public Publication createPublication(@RequestBody Publication publication, HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            throw new IllegalStateException("Usuário não está logado");
        }

        publication.setUser(user);
        publication.setUserName(user.getUsername());

        return publicationService.savePublication(publication);
    }

    @GetMapping("/friendsPublications")
    public ResponseEntity<List<Publication>> getAllFriendPublications(HttpSession session) {
        List<Publication> friendPublications = publicationService.getAllFriendPublications(session);
        return ResponseEntity.ok(friendPublications);
    }


    @DeleteMapping("/{id}")
    public void deletePublication(@PathVariable UUID id) {
        publicationService.deletePublication(id);
    }
}
