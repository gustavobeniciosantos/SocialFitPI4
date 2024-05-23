package br.com.socialfit.social_fit.controllers;


import br.com.socialfit.social_fit.entity.Friend;
import br.com.socialfit.social_fit.entity.User;
import br.com.socialfit.social_fit.repositories.FriendRepository;
import br.com.socialfit.social_fit.repositories.UserRepository;
import br.com.socialfit.social_fit.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/friends")
public class FriendController {
    @Autowired
    private FriendService friendService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    FriendRepository friendRepository;
    @GetMapping("/getFriends")
    public ResponseEntity<Object> getAllFriends(@PathVariable UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("Usuário não encontrado"));

        List<String> friendNames = friendService.getFriendNamesOfUser(user);
        return ResponseEntity.ok().body(friendNames);
    }
    @GetMapping("/{id}")
    public Optional<Friend> getFriendById(@PathVariable UUID id) {
        return friendService.getFriendById(id);
    }
    @PostMapping("/addFriend/{currentUserId}")
    public ResponseEntity<Object> addFriend(@PathVariable UUID currentUserId, @RequestBody Map<String, String> requestBody) {
        User currentUser = userRepository.findById(currentUserId).orElse(null);
        String username = requestBody.get("username");
        User friendUser = userRepository.findByUsername(username);
        
        if (friendUser == null) {
            return ResponseEntity.notFound().build();
        }

        if (friendRepository.existsByUser1AndUser2(currentUser, friendUser) ||
                friendRepository.existsByUser1AndUser2(friendUser, currentUser)) {
            return ResponseEntity.badRequest().body("Esses usuários já são amigos.");
        }

        Friend friend = new Friend();
        friend.setUser1(currentUser);
        friend.setUser2(friendUser);
        friendRepository.save(friend);

        String message = String.format("Você adicionou um novo amigo", friendUser.getName(), friendUser.getId());

        return ResponseEntity.ok().body(message);
    }


    @DeleteMapping("/{id}")
    public void deleteFriend(@PathVariable UUID id) {
        friendService.deleteFriend(id);
    }
}