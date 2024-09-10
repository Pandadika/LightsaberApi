package com.example.lightsaber;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jedi.jedishared.User;

public interface UserRepository extends JpaRepository<User, String> {
}
