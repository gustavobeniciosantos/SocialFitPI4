package br.com.socialfit.social_fit.controllers;


import br.com.socialfit.social_fit.service.*;
import com.fasterxml.jackson.annotation.JsonView;
import br.com.socialfit.social_fit.entity.User;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Controller
@RequestMapping("/teste")
public class UserController {

    private LoginUserService loginUserService;
    @Autowired
    private UserService userService;

    GetUserService getUserService;
    GenerateCode generateCode;
    EmailService emailService;
    AuthCodeService authCodeService = new AuthCodeService();


    @JsonView(User.WithoutPasswordView.class)
    @PostMapping("/signup")
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }//cadastrar

    @PostMapping("/signin")
    public ResponseEntity<Object> login(@RequestBody User user)  {

        Optional<User> foundUser = loginUserService.loginUser(user.getUsername(), user.getPassword());

        if (foundUser.isPresent()) {
            user = foundUser.get();
            String email = user.getEmail();
            String name = user.getName();
            generateCode.generateNewCode();
            try {
                ModelAndView modelAndView = new ModelAndView("sendCode");
                modelAndView.addObject("name", name);
                modelAndView.addObject("code", GenerateCode.generatedCode());
                emailService.sendMailAuth(email, name, GenerateCode.generatedCode());
            } catch (MessagingException e) {
                e.printStackTrace();
            }

            return ResponseEntity.ok().body(user);
        } else {
            return ResponseEntity.badRequest().body("Username or Passsword invalid");
        }

    }

    @PostMapping("/signin/verify")
    public ResponseEntity<Object> authCode(@RequestBody VerificationCode verificationCode){

        if(authCodeService.isHim(GenerateCode.generatedCode(),verificationCode.getCode())){

            return ResponseEntity.ok().body("Corrigo correto");
        }

        return ResponseEntity.badRequest().body("Código incorreto");
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<Object> getUser(@PathVariable String username){
        Optional<User> userOptional = getUserService.getUserRepository(username);

        if(userOptional.isPresent()){
            User user = userOptional.get();
            return ResponseEntity.ok().body(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable UUID id) {
    userService.deleteUser(id);
    }


}
