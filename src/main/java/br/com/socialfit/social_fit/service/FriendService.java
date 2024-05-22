package br.com.socialfit.social_fit.service;


import br.com.socialfit.social_fit.entity.Friend;
import br.com.socialfit.social_fit.entity.User;
import br.com.socialfit.social_fit.repositories.FriendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FriendService {
    @Autowired
    private FriendRepository friendRepository;

    public List<String> getFriendNamesOfUser(User user) {
        List<Friend> friends = new ArrayList<>();
        friends.addAll(friendRepository.findAllByUser1(user));
        friends.addAll(friendRepository.findAllByUser2(user));

        return friends.stream()
                .map(friend -> friend.getUser1().equals(user) ? friend.getUser2().getName() : friend.getUser1().getName())
                .collect(Collectors.toList());
    }

    public Optional<Friend> getFriendById(UUID id) {
       return friendRepository.findById(id);
    }

    public Friend saveFriend(Friend friend) {
        return friendRepository.save(friend);
    }

    public void deleteFriend(UUID id) {
        friendRepository.deleteById(id);
    }
}