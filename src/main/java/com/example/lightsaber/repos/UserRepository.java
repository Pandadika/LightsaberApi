package com.example.lightsaber.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jedi.jedishared.User;

public interface UserRepository extends JpaRepository<User, String> {
}
