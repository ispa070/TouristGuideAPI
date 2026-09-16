package dev.naeveklubben.touristguideapi.service;

import dev.naeveklubben.touristguideapi.model.Attraction;
import dev.naeveklubben.touristguideapi.model.Tag;
import dev.naeveklubben.touristguideapi.repository.AttractionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttractionService {
    private final AttractionRepository repository;

    //Dependency Injection:
    //Spring creates the AttractionRepository and injects it
    //into the service through the constructor
    public AttractionService(AttractionRepository repository) {
        this.repository = repository;
    }

    //Gets all attractions from the repository
    public List<Attraction> getAttractions() {
        return repository.getAllAttractions();
    }

    //Gets one attraction by name
    public Attraction getAttractionByName(String name) {
        return repository.findAttractionByName(name);
    }

    //Sends a new attraction to the repository
    public Attraction addAttraction(Attraction attraction) {
        repository.addAttraction(attraction);
        return attraction;
    }

    public void updateAttraction(String name, Attraction updatedAttraction) {
        repository.updateAttraction(name, updatedAttraction);
    }

    //Deletes an attraction
    public Attraction deleteAttraction(String name){
        return repository.deleteAttraction(name);
    }

    //Gets the tags of one attraction, found by name
    public List<Tag> getTags(String name) {
        Attraction attraction = repository.findAttractionByName(name);
        return attraction == null ? null : attraction.getTags();
    }

}