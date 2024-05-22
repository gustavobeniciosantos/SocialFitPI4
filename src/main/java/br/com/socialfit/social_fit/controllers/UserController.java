package br.com.socialfit.social_fit.controllers;


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
import java.util.Optional;

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
    public ResponseEntity<Object> login(@RequestBody User user, HttpSession session)  {

        Optional<User> foundUser = userService.loginUser(user.getUsername(), user.getPassword());

        if (foundUser.isPresent()) {
            user = foundUser.get();
            String name = user.getName();

            session.setAttribute("user", user);

            return ResponseEntity.ok().body(user.getId());
        } else {
            return ResponseEntity.badRequest().body("Username or Passsword invalid");
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
    public ResponseEntity<Void> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok().build();
    }

}

