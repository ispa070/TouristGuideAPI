package dev.naeveklubben.touristguideapi.controller;

import dev.naeveklubben.touristguideapi.model.Attraction;
import dev.naeveklubben.touristguideapi.service.AttractionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/attractions")
public class AttractionController {
    private final AttractionService attractionService;

    public AttractionController(AttractionService attractionService) {
        this.attractionService = attractionService;
    }

    @GetMapping()
    public ResponseEntity<List<Attraction>> getAttraction() {
        List<Attraction>attractions = attractionService.getAttractions();
        return new ResponseEntity<>(attractions, HttpStatus.OK);
    }

    @GetMapping("{name}")
    public ResponseEntity<Attraction> getAttractionByName(@PathVariable String name) {
        Attraction attraction = attractionService.findTouristAttractionByName(name);
        if (attraction == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(attraction, HttpStatus.OK);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Attraction> createAttraction(@RequestBody Attraction attraction) {
        Attraction created = attractionService.createAttraction(attraction);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }


    @PutMapping("/update/{name}")
    public ResponseEntity<Attraction> updateAttraction(@PathVariable String name, @RequestBody Attraction attraction) {
        Attraction updated = attractionService.updateAttraction(name, attraction);

        if (updated == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(updated, HttpStatus.OK);
    }
    
    @DeleteMapping("/delete/{name}")
    public ResponseEntity<Attraction> deleteAttraction(@PathVariable String name) {
        Attraction deleted = attractionService.deleteAttraction(name);

        if (deleted == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(deleted, HttpStatus.NO_CONTENT);
    }

}