package com.example.lightsaber;

import com.example.lightsaber.repos.UserRepository;
import com.jedi.jedishared.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findById(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

    List<String> roles = new ArrayList<>();
    roles.add("USER");
    if (user.isAdmin()) {
      roles.add("ADMIN");
    }

    List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(String.join(", ", roles));

    return org.springframework.security.core.userdetails.User.builder()
        .username(user.getUsername())
        .password(user.getPassword())
        .authorities(authorities)
        .build();
  }
}
