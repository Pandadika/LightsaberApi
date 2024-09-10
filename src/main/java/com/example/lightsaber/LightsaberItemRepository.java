package com.example.lightsaber;

import com.jedi.jedishared.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LightsaberItemRepository extends JpaRepository<Item, UUID> {
}
