package br.com.socialfit.social_fit.service;

import br.com.socialfit.social_fit.entity.Publication;
import br.com.socialfit.social_fit.entity.User;
import br.com.socialfit.social_fit.repositories.PublicationRepository;
import br.com.socialfit.social_fit.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PublicationService {
    @Autowired
    private PublicationRepository publicationRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Publication> getAllPublications() {
        return publicationRepository.findAll();
    }

    public Optional<Publication> getPublicationById(UUID id) {
        return publicationRepository.findById(id);
    }

    public Publication savePublication(Publication publication) {
        return publicationRepository.save(publication);
    }

    public void deletePublication(UUID id) {
        publicationRepository.deleteById(id);
    }
}