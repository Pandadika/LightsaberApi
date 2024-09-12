package com.example.lightsaber;

import com.jedi.jedishared.Image;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/image")
public class ImageController {
    ImgeRepository imgeRepository;

    public ImageController(ImgeRepository repo){
        this.imgeRepository = repo;
    }

    @PostMapping("/upload")
    ResponseEntity<UUID> create(@RequestBody Image image){
        Image newImage = new Image();
        if (image.getId() == null) {
            newImage.setId(UUID.randomUUID());
        } else{
            newImage.setId(image.getId());
        }
        newImage.setImage(image.getImage());
        this.imgeRepository.save(newImage);

        return ResponseEntity.ok(newImage.getId());
    }

    @PostMapping("update/{id}")
    ResponseEntity<UUID> update(@PathVariable UUID id, @RequestBody Image image){
        Optional<Image> exits = imgeRepository.findById(id);
        if (!exits.isPresent()){
            return ResponseEntity.notFound().build();
        }
        this.imgeRepository.save(image);
        return ResponseEntity.ok(id);
    }

    @GetMapping("/{id}")
    ResponseEntity<Image> get (@PathVariable UUID id){
        Optional<Image> exits = imgeRepository.findById(id);
        if (!exits.isPresent()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(exits.get());
    }

    @GetMapping()
        ResponseEntity<List<Image>> getAll(){
            return ResponseEntity.ok(imgeRepository.findAll());
        }

}
