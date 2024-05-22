package br.com.socialfit.social_fit.service;

import br.com.socialfit.social_fit.entity.Friend;
import br.com.socialfit.social_fit.entity.Publication;
import br.com.socialfit.social_fit.entity.User;
import br.com.socialfit.social_fit.repositories.FriendRepository;
import br.com.socialfit.social_fit.repositories.PublicationRepository;
import br.com.socialfit.social_fit.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PublicationService {
    @Autowired
    private PublicationRepository publicationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    FriendRepository friendRepository;

    public List<Publication> getAllPublications() {
        return publicationRepository.findAll();
    }

    public List<Publication> getAllFriendPublications(HttpSession session){
        User currentUser = (User) session.getAttribute("user");

        // Obter a lista de amigos do usuário atual
        List<Friend> friends = friendRepository.findFriendsByUser1OrUser2(currentUser, currentUser);

        // Obter todas as publicações dos amigos
        List<Publication> allFriendPublications = new ArrayList<>();
        for (Friend friend : friends) {
            User friendUser = friend.getUser1().equals(currentUser) ? friend.getUser2() : friend.getUser1();
            List<Publication> friendPublications = publicationRepository.findByUser(friendUser);
            allFriendPublications.addAll(friendPublications);
        }

        return allFriendPublications;
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