package br.com.socialfit.social_fit.controllers;

import br.com.socialfit.social_fit.entity.Publication;
import br.com.socialfit.social_fit.entity.PublicationLike;
import br.com.socialfit.social_fit.entity.User;
import br.com.socialfit.social_fit.repositories.PublicationLikeRepository;
import br.com.socialfit.social_fit.service.PublicationLikeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/publication-likes")
public class PublicationLikeController {
    @Autowired
    private PublicationLikeService publicationLikeService;
    @Autowired
    PublicationLikeRepository publicationRepository;
    @Autowired
    PublicationController publicationService;

    @GetMapping("/get-all-publications")
    public List<PublicationLike> getAllPublicationLikes() {
        return publicationLikeService.getAllPublicationLikes();
    }

    @GetMapping("/{id}")
    public Optional<PublicationLike> getPublicationLikeById(@PathVariable UUID id) {
        return publicationLikeService.getPublicationLikeById(id);
    }

    @PostMapping("/like-publication/{publicationId}")
    public ResponseEntity<Map<String, Object>> likePublication(@PathVariable UUID publicationId, HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            throw new IllegalStateException("Usuário não está logado");
        }

        Optional<Publication> optionalPublication = publicationService.getPublicationById(publicationId);
        if (!optionalPublication.isPresent()) {
            throw new IllegalStateException("Publicação não encontrada");
        }

        Publication publication = optionalPublication.get();

        PublicationLike publicationLike = new PublicationLike();
        publicationLike.setUser(user);
        publicationLike.setPublication(publication);

        publication.setLikesCount(publication.getLikesCount() + 1);

        publicationService.savePublication(publication);

        PublicationLike savedLike = publicationLikeService.savePublicationLike(publicationLike);

        Map<String, Object> response = new HashMap<>();
        response.put("id", savedLike.getId());
        response.put("message", "Like realizado com sucesso");

        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{id}")
    public void deletePublicationLike(@PathVariable UUID id) {
        publicationLikeService.deletePublicationLike(id);
    }
}