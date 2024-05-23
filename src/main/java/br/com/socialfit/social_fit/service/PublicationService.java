package br.com.socialfit.social_fit.service;

import br.com.socialfit.social_fit.DTO.PublicationDTO;
import br.com.socialfit.social_fit.DTO.UserDTO;
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
import java.util.stream.Collectors;

@Service
public class PublicationService {
    @Autowired
    private PublicationRepository publicationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    FriendRepository friendRepository;
    @Autowired
    UserService userService;

    public List<Publication> getAllPublications() {
        return publicationRepository.findAll();
    }
    public List<Publication> getAllFriendPublications(UserDTO userDTO){
        User currentUser = userService.findById(userDTO.getId()).orElse(null);

        List<Friend> friends = friendRepository.findFriendsByUser1OrUser2(currentUser, currentUser);

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

    public List<PublicationDTO> getAllUserPublications(User user) {
        List<Publication> publications = publicationRepository.findByUser(user);
        List<PublicationDTO> publicationDTOs = publications.stream()
                .map(publication -> {
                    PublicationDTO dto = new PublicationDTO();
                    dto.setId(publication.getId());
                    dto.setName(user.getName());
                    dto.setPublicationText(publication.getPublicationText());
                    dto.setUserName(user.getUsername());
                    dto.setLikes(publication.getLikesCount());
                    return dto;
                })
                .collect(Collectors.toList());
        return publicationDTOs;
    }
}