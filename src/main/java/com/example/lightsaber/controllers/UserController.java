package com.example.lightsaber.controllers;

import com.example.lightsaber.JwtResponse;
import com.example.lightsaber.JwtUtil;
import com.example.lightsaber.repos.UserRepository;
import com.jedi.jedishared.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
      newUser.setFirstName(user.getFirstName());
      newUser.setSurName(user.getSurName());
      newUser.setEmail(user.getEmail());
      userRepository.save(newUser);
      return true;
    }
    return false;
  }

  @PostMapping("/login")
  public  ResponseEntity<?> login (@RequestBody User user) {
    try {
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(user.getUsername().trim().toLowerCase(), user.getPassword())
      );

      SecurityContextHolder.getContext().setAuthentication(authentication);
      String jwt = JwtUtil.generateJwtToken(authentication.getName(), (List<GrantedAuthority>) authentication.getAuthorities());

      return ResponseEntity.ok(new JwtResponse(jwt));
    } catch (BadCredentialsException e) {
      // ... authentication failed ...
    }
    return ResponseEntity.badRequest().build();
  }

  @GetMapping()
  List<User> getUsers() {
    return userRepository.findAll();
  }

  @GetMapping("/{username}")
  ResponseEntity<User> getUser(@PathVariable String username) {
    return userRepository.findById(username)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @Transactional
  @PostMapping("/update/{username}")
  public ResponseEntity<User> updateUser(@PathVariable String username, @RequestBody User user) {
    if (!username.equals(user.getUsername())) {
      return ResponseEntity.badRequest().body(null);
    }
    Optional<User> existingUser = userRepository.findById(username);
    if (existingUser.isPresent()) {
      User updatedUser = existingUser.get();
      if (user.getPassword() != null && !user.getPassword().isBlank()){
        updatedUser.setPassword(passwordEncoder.encode(user.getPassword()));
      }
      updatedUser.setBoughtItems(new ArrayList<>());
      updatedUser.setIsAdmin(user.isAdmin());
      updatedUser.setFirstName(user.getFirstName());
      updatedUser.setSurName(user.getSurName());
      updatedUser.setEmail(user.getEmail());
      updatedUser.getBoughtItems().clear();

      //updatedUser.getBoughtItems().addAll(user.getBoughtItems());

      userRepository.save(updatedUser);
      return ResponseEntity.ok(updatedUser);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

}
