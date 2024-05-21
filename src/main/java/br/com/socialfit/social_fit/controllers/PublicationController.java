package br.com.socialfit.social_fit.controllers;

import br.com.socialfit.social_fit.entity.Publication;
import br.com.socialfit.social_fit.service.PublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/publications")
public class PublicationController {
    @Autowired
    private PublicationService publicationService;

    @GetMapping
    public List<Publication> getAllPublications() {
        return publicationService.getAllPublications();
    }

    @GetMapping("/{id}")
    public Optional<Publication> getPublicationById(@PathVariable UUID id) {
        return publicationService.getPublicationById(id);
    }

    @PostMapping("/create-post")
    public Publication createPublication(@RequestBody Publication publication) {
        return publicationService.savePublication(publication);
    }

    @DeleteMapping("/{id}")
    public void deletePublication(@PathVariable UUID id) {
        publicationService.deletePublication(id);
    }
}