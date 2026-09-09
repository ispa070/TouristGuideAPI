package dev.naeveklubben.touristguideapi.controller;

import dev.naeveklubben.touristguideapi.model.Attraction;
import dev.naeveklubben.touristguideapi.model.Tag;
import dev.naeveklubben.touristguideapi.service.AttractionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    //Shows all attractions on an HTML page
    //Model carries the data to the template,
    //and the returned String is the filename in templates/ without .html
    @GetMapping
    public String getAttractions(Model model) {
        model.addAttribute("attractions", attractionService.getAttractions());
        return "attractions";
    }

    //....
    //Finds an attraction using the name from the URL
    //http://localhost:8080/attractions/{name}
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
    //http://localhost:8080/attractions/add
    @PostMapping("/add")
    public ResponseEntity<Attraction> createAttraction(@RequestBody Attraction attraction) {

        //@RequestBody converts the JSON request into an Attraction object

        Attraction created = attractionService.createAttraction(attraction);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }


    //Updates an existing attraction
    //http://localhost:8080/attractions/update/{name}
    @PutMapping("/update/{name}")
    public ResponseEntity<Attraction> updateAttraction(@PathVariable String name, @RequestBody Attraction attraction) {
        Attraction updated = attractionService.updateAttraction(name, attraction);

        if (updated == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    //Deletes an attraction
    //http://localhost:8080/attractions/delete/{name}
    @DeleteMapping("/delete/{name}")
    public ResponseEntity<Attraction> deleteAttraction(@PathVariable String name) {
        Attraction deleted = attractionService.deleteAttraction(name);

        if (deleted == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(deleted, HttpStatus.NO_CONTENT);
    }

    //Shows the tags of one attraction on an HTML page
    //http://localhost:8080/attractions/{name}/tags
    @GetMapping("/{name}/tags")
    public String showAttractionTags(@PathVariable String name, Model model) {
        List<Tag> tags = attractionService.getTags(name);
        model.addAttribute("attractionName", name);
        model.addAttribute("tags", tags == null ? List.of() : tags);
        return "tags";
    }

}