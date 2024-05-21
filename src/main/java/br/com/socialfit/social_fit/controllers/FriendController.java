package br.com.socialfit.social_fit.controllers;


import br.com.socialfit.social_fit.entity.Friend;
import br.com.socialfit.social_fit.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/friends")
public class FriendController {
    @Autowired
    private FriendService friendService;

    @GetMapping
    public List<Friend> getAllFriends() {
        return friendService.getAllFriends();
    }

    @GetMapping("/{id}")
    public Optional<Friend> getFriendById(@PathVariable UUID id) {
        return friendService.getFriendById(id);
    }

    @PostMapping
    public Friend createFriend(@RequestBody Friend friend) {
        return friendService.saveFriend(friend);
    }

    @DeleteMapping("/{id}")
    public void deleteFriend(@PathVariable UUID id) {
        friendService.deleteFriend(id);
    }
}