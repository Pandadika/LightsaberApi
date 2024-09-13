package com.example.lightsaber.repos;

import com.jedi.jedishared.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ImgeRepository extends JpaRepository<Image, UUID> {
}
