package dev.naeveklubben.touristguideapi.service;

import dev.naeveklubben.touristguideapi.model.Attraction;
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
    public Attraction findTouristAttractionByName(String name) {
        return repository.findAttractionByName(name);
    }

    //Sends a new attraction to the repository
    public Attraction createAttraction(Attraction attraction) {
        repository.addAttraction(attraction);
        return attraction;
    }

    //Updates an existing attraction
    public Attraction updateAttraction(String name, Attraction attraction){
        return repository.updateAttraction(name, attraction);
    }

    //Deletes an attraction
    public Attraction deleteAttraction(String name){
        return repository.deleteAttraction(name);
    }


}