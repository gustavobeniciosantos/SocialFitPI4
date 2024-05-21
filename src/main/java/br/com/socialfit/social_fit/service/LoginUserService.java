package br.com.socialfit.social_fit.service;

import br.com.socialfit.social_fit.entity.User;
import br.com.socialfit.social_fit.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class LoginUserService {

    
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    public Optional<User> loginUser(String username, String password){
        Optional<User> userOptional = userRepository.findUserByUsernameAndPassword(username, password);
        if (userOptional.isPresent()) {
            UsernamePasswordAuthenticationToken authReq
                    = new UsernamePasswordAuthenticationToken(username, password);
            Authentication auth = authenticationManager.authenticate(authReq);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        return userOptional;
    }
}

