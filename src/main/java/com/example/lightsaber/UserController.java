package com.example.lightsaber;

import com.jedi.jedishared.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    AuthenticationManager authenticationManager;
    PasswordEncoder passwordEncoder;
    UserRepository userRepository;
    public UserController(
            UserRepository user,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager) {
        userRepository = user;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/create")
    boolean create (@RequestBody User user) {
        var existing = userRepository.findById(user.getUsername());
        if (existing.isEmpty()) {
            var newUser = new User();
            newUser.setUsername(user.getUsername().trim().toLowerCase());
            newUser.setPassword(passwordEncoder.encode(user.getPassword()));
            newUser.setBoughtItems(new ArrayList<>());
            newUser.setIsAdmin(user.isAdmin());
            userRepository.save(newUser);
            return true;
        }
        return false;
    }

    @PostMapping("/login")
    public boolean login (@RequestBody User user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername().trim().toLowerCase(), user.getPassword())
            );
            return authentication.isAuthenticated();
        } catch (BadCredentialsException e) {
            // ... authentication failed ...
        }
        return false;
    }

    @GetMapping()
    List<User> getUsers() {
        return userRepository.findAll();
    }


}
