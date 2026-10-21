package dev.naeveklubben.touristguideapi.controller;

import dev.naeveklubben.touristguideapi.model.Attraction;
import dev.naeveklubben.touristguideapi.model.City;
import dev.naeveklubben.touristguideapi.model.Tag;
import dev.naeveklubben.touristguideapi.service.AttractionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    //Finds an attraction or throws 404 Not Found, so the pages below don't crash on null
    private Attraction findAttractionOr404(String name) {
        Attraction attraction = attractionService.getAttractionByName(name);
        if (attraction == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Attraktionen '" + name + "' findes ikke");
        }
        return attraction;
    }

    //Adds the lists of cities and tags that the form dropdown/checkboxes need
    private void addFormOptions(Model model) {
        model.addAttribute("cities", City.values());
        model.addAttribute("tags", Tag.values());
    }

    // ===================== READ =====================

    //Shows all attractions on an HTML page
    //Model carries the data to the template,
    //and the returned String is the filename in templates/ without .html
    @GetMapping
    public String getAttractions(Model model) {
        List<Attraction> attractions = attractionService.findAllAttractions();
        model.addAttribute("attractions", attractions);
        return "attractions";
    }

    //Finds an attraction using the name from the URL and returns it as JSON
    //http://localhost:8080/attractions/{name}
    @GetMapping("/{name}")
    public ResponseEntity<Attraction> getAttractionByName(@PathVariable String name) {
        Attraction attraction = attractionService.getAttractionByName(name);
        if (attraction == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(attraction, HttpStatus.OK);
    }

    //Shows the tags of one attraction on an HTML page
    //http://localhost:8080/attractions/{name}/tags
    @GetMapping("/{name}/tags")
    public String showAttractionTags(@PathVariable String name, Model model) {
        List<Tag> tags = attractionService.getTags(name);
        if (tags == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Attraktionen '" + name + "' findes ikke");
        }
        model.addAttribute("attractionName", name);
        model.addAttribute("tags", tags);
        return "tags";
    }

    // ===================== CREATE =====================

    //Shows the form for adding an attraction
    //http://localhost:8080/attractions/add
    @GetMapping("/add")
    public String showAttractionForm(Model model) {
        model.addAttribute("attraction", new Attraction());
        addFormOptions(model);
        return "add-attraction";
    }

    //Handles the add form submission
    //If the name is already taken, the form is shown again with an error message
    @PostMapping("/add")
    public String saveAttractionForm(@ModelAttribute Attraction attraction, Model model) {
        if (attractionService.attractionExists(attraction.getName())) {
            model.addAttribute("attraction", attraction);
            model.addAttribute("error", "Der findes allerede en attraktion med navnet '" + attraction.getName() + "'");
            addFormOptions(model);
            return "add-attraction";
        }
        attractionService.addAttraction(attraction);
        return "redirect:/attractions";
    }

    // ===================== UPDATE =====================

    //Shows the form for updating an attraction, filled with its current data
    //http://localhost:8080/attractions/{name}/edit
    @GetMapping("/{name}/edit")
    public String showUpdateForm(@PathVariable String name, Model model) {
        model.addAttribute("attraction", findAttractionOr404(name));
        addFormOptions(model);
        return "update-attraction";
    }

    //Handles the update form submission
    @PostMapping("/{name}/edit")
    public String updateAttraction(@PathVariable String name, @ModelAttribute Attraction attraction) {
        boolean updated = attractionService.updateAttraction(name, attraction);
        if (!updated) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Attraktionen '" + name + "' findes ikke");
        }
        return "redirect:/attractions";
    }

    // ===================== DELETE =====================

    //Shows a confirmation page before deleting
    //http://localhost:8080/attractions/{name}/delete
    @GetMapping("/{name}/delete")
    public String showDeleteConfirmation(@PathVariable String name, Model model) {
        model.addAttribute("attraction", findAttractionOr404(name));
        return "delete-attraction";
    }

    //Deletes the attraction when the user confirms
    @PostMapping("/{name}/delete")
    public String deleteAttraction(@PathVariable String name) {
        attractionService.deleteAttraction(name);
        return "redirect:/attractions";
    }
}
