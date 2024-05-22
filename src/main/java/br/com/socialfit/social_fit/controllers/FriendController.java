package br.com.socialfit.social_fit.controllers;


import br.com.socialfit.social_fit.entity.Friend;
import br.com.socialfit.social_fit.entity.User;
import br.com.socialfit.social_fit.repositories.FriendRepository;
import br.com.socialfit.social_fit.repositories.UserRepository;
import br.com.socialfit.social_fit.service.FriendService;
import jakarta.servlet.http.HttpSession;
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
    public ResponseEntity<Object> getAllFriends(HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não está logado");
        }

        List<String> friendNames = friendService.getFriendNamesOfUser(user);
        return ResponseEntity.ok().body(friendNames);
    }

    @GetMapping("/{id}")
    public Optional<Friend> getFriendById(@PathVariable UUID id) {
        return friendService.getFriendById(id);
    }

    @PostMapping("/addFriend")
    public ResponseEntity<Object> addFriend(@RequestBody Map<String, String> requestBody, HttpSession session) {
        String name = requestBody.get("name");

        User currentUser = (User) session.getAttribute("user");

        User friendUser = userRepository.findByName(name);

        if (friendUser == null) {
            return ResponseEntity.notFound().build();
        }

        if (friendRepository.existsByUser1AndUser2(currentUser, friendUser) ||
                friendRepository.existsByUser1AndUser2(friendUser, currentUser)) {
            return ResponseEntity.badRequest().body("Esses usuários já são amigos.");
        }

        if (currentUser.getId().equals(friendUser.getId())) {
            return ResponseEntity.badRequest().body("Você não pode se adicionar como amigo.");
        }

        Friend friend = new Friend();
        friend.setUser1(currentUser);
        friend.setUser2(friendUser);

        friendRepository.save(friend);

        String message = String.format("Você adicionou o usuário %s (ID: %s).", friendUser.getName(), friendUser.getId());

        return ResponseEntity.ok().body(message);
    }
    @DeleteMapping("/{id}")
    public void deleteFriend(@PathVariable UUID id) {
        friendService.deleteFriend(id);
    }
}