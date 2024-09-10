package com.example.lightsaber;

import com.jedi.jedishared.Item;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/lightsaberItem")
public class LightsaberItemController {
    LightsaberItemRepository lightsaberItemRepository;
    public LightsaberItemController(LightsaberItemRepository lightsaber) {
        lightsaberItemRepository = lightsaber;
    }

    @PostMapping()
    UUID create(@RequestBody Item item) {
        lightsaberItemRepository.save(item);
        return item.getId();
    }
    @GetMapping()
    List<Item> getAll(){
        return lightsaberItemRepository.findAll();
    }
}
