package br.com.socialfit.social_fit.controllers;


import br.com.socialfit.social_fit.DTO.UserDTO;
import br.com.socialfit.social_fit.repositories.UserRepository;
import br.com.socialfit.social_fit.service.*;
import com.fasterxml.jackson.annotation.JsonView;
import br.com.socialfit.social_fit.entity.User;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    UserRepository userRepository;

    @JsonView(User.WithoutPasswordView.class)
    @PostMapping("/signup")
    public ResponseEntity<Object> createUser(@RequestBody @Valid User user ){

        try {
           this.userService.createUser(user);
           return ResponseEntity.created(URI.create("/"+user.getId())).body(user);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/signin")
    public ResponseEntity<UserDTO> login(@RequestBody User user, HttpSession session)  {
        Optional<User> foundUser = userService.loginUser(user.getUsername(), user.getPassword());

        if (foundUser.isPresent()) {
            User currentUser = userService.getUserById(foundUser.get().getId());

            // Criar um objeto UserDTO com os dados do usuário
            UserDTO userDTO = new UserDTO();
            userDTO.setId(currentUser.getId());
            userDTO.setName(currentUser.getName());
            userDTO.setUsername(currentUser.getUsername());

            session.setAttribute("user", userDTO);

            return ResponseEntity.ok().body(userDTO);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<Object> getUser(@PathVariable String username){
        Optional<User> userOptional = userService.getUserRepository(username);

        if(userOptional.isPresent()){
            User user = userOptional.get();
            return ResponseEntity.ok().body(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session, User user) {
        session.invalidate();
        return ResponseEntity.ok().body("Usuário deslogado");
    }
    @PatchMapping("/user/changeData/{userId}")
    public ResponseEntity<Object> updateUserData(@PathVariable UUID userId, @RequestBody Map<String, String> updates){

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("Usuário não encontrado"));

        if (updates.containsKey("email") && updates.get("email") != null) {
            user.setEmail(updates.get("email"));
        }
        if (updates.containsKey("password") && updates.get("password") != null) {
            user.setPassword(updates.get("password"));
        }
        if (updates.containsKey("telephone") && updates.get("telephone") != null) {
            user.setTelephone(updates.get("telephone"));
        }
        if (updates.containsKey("fullAddress") && updates.get("fullAddress") != null) {
            user.setFullAddress(updates.get("fullAddress"));
        }

        userRepository.save(user);
        return ResponseEntity.ok().body("Dados alterados");
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    @GetMapping("/getAllUsersByName/{name}")
    public ResponseEntity<List<User>> getAllUsersByName(@PathVariable String name){
        List<User> users = userRepository.findAllByName(name);
        return ResponseEntity.ok(users);
    }

}

