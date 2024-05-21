package br.com.socialfit.social_fit.controllers;

import br.com.socialfit.social_fit.entity.PublicationLike;
import br.com.socialfit.social_fit.service.PublicationLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/publication-likes")
public class PublicationLikeController {
    @Autowired
    private PublicationLikeService publicationLikeService;

    @GetMapping("/get-all-publications")
    public List<PublicationLike> getAllPublicationLikes() {
        return publicationLikeService.getAllPublicationLikes();
    }

    @GetMapping("/{id}")
    public Optional<PublicationLike> getPublicationLikeById(@PathVariable UUID id) {
        return publicationLikeService.getPublicationLikeById(id);
    }

    @PostMapping("/insert-like-publication")
    public PublicationLike createPublicationLike(@RequestBody PublicationLike publicationLike) {
        return publicationLikeService.savePublicationLike(publicationLike);
    }

    @DeleteMapping("/{id}")
    public void deletePublicationLike(@PathVariable UUID id) {
        publicationLikeService.deletePublicationLike(id);
    }
}