package com.example.lightsaber.controllers;

import com.example.lightsaber.repos.LightsaberItemRepository;
import com.jedi.jedishared.Item;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    if (item.getId() == null) {
      item.setId(UUID.randomUUID());
    }
    lightsaberItemRepository.save(item);
    return item.getId();
  }

  @PostMapping("/createAlot")
  List<UUID> createAlot(@RequestBody List<Item> items){
    List<UUID> result = new ArrayList<UUID>();
    for (Item item : items) {
      if (item.getId() == null) {
        item.setId(UUID.randomUUID());
      }
      result.add(item.getId());
    }
    lightsaberItemRepository.saveAll(items);
    return result;
  }

  @GetMapping()
  List<Item> getAll(){
    return lightsaberItemRepository.findAll();
  }

  @DeleteMapping("/{id}")
  void delete(@PathVariable UUID id) {
    lightsaberItemRepository.deleteById(id);
  }

  @PostMapping("/update/{id}")
  void update(@PathVariable UUID id, @RequestBody Item item) {
    lightsaberItemRepository.save(item);
  }
}
