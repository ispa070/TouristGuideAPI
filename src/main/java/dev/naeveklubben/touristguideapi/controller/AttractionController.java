package dev.naeveklubben.touristguideapi.controller;

import dev.naeveklubben.touristguideapi.model.Attraction;
import dev.naeveklubben.touristguideapi.service.AttractionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

//Marks this class as a Spring MVC controller
//Tells Spring that this class handles HTTP requests
@Controller

//Sets the base URL for all endpoints in this controller
//All mappings in this class will start with "/attractions"
@RequestMapping("/attractions")
public class AttractionController {
    private final AttractionService attractionService;

    //Dependency Injection:
    //Spring injects the AttractionService through the constructor
    public AttractionController(AttractionService attractionService) {
        this.attractionService = attractionService;
    }

    //Returns all attractions
    @GetMapping()
    public ResponseEntity<List<Attraction>> getAttraction() {
        List<Attraction>attractions = attractionService.getAttractions();
        return new ResponseEntity<>(attractions, HttpStatus.OK);
    }

    //Finds an attraction using the name from the URL
    @GetMapping("{name}")
    public ResponseEntity<Attraction> getAttractionByName(@PathVariable String name) {
        Attraction attraction = attractionService.findTouristAttractionByName(name);
        if (attraction == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(attraction, HttpStatus.OK);
        }
    }

    //Creates a new attraction
    @PostMapping("/add")
    public ResponseEntity<Attraction> createAttraction(@RequestBody Attraction attraction) {

        //@RequestBody converts the JSON request into an Attraction object

        Attraction created = attractionService.createAttraction(attraction);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }


    //Updates an existing attraction
    @PutMapping("/update/{name}")
    public ResponseEntity<Attraction> updateAttraction(@PathVariable String name, @RequestBody Attraction attraction) {
        Attraction updated = attractionService.updateAttraction(name, attraction);

        if (updated == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    //Deletes an attraction
    @DeleteMapping("/delete/{name}")
    public ResponseEntity<Attraction> deleteAttraction(@PathVariable String name) {
        Attraction deleted = attractionService.deleteAttraction(name);

        if (deleted == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(deleted, HttpStatus.NO_CONTENT);
    }

}