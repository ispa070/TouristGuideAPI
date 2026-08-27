package dev.naeveklubben.touristguideapi.controller;

import dev.naeveklubben.touristguideapi.model.Attraction;
import dev.naeveklubben.touristguideapi.repository.AttractionRepository;
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
    private final AttractionRepository attractionRepository;

    public AttractionController(AttractionService attractionService, AttractionRepository attractionRepository){
        this.attractionService = attractionService;
        this.attractionRepository = attractionRepository;
    }

    @GetMapping()
    public ResponseEntity<List<Attraction>> getAttraction(){
        List<Attraction> attractions = attractionService.getAttractions();
        return new ResponseEntity<>(attractions, HttpStatus.OK);
    }

    @GetMapping("{name}")
        public ResponseEntity<Attraction> getAttractionByName(@PathVariable String name) {
        Attraction attraction = attractionService.findTouristAttractionByName(name);
        if (attraction == null) {
            return new ResponseEntity<>(attraction, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(attraction, HttpStatus.OK);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Attraction> createAttraction(@RequestBody Attraction attraction){
        attractionRepository.addAttraction(attraction);
        return new ResponseEntity<>(attraction, HttpStatus.CREATED);
    }

}
