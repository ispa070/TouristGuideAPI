package dev.naeveklubben.touristguideapi.service;

import dev.naeveklubben.touristguideapi.model.Attraction;
import dev.naeveklubben.touristguideapi.model.Tag;
import dev.naeveklubben.touristguideapi.repository.AttractionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<Attraction> findAllAttractions() {
        return repository.findAll();
    }

    //Gets one attraction by name
    public Attraction getAttractionByName(String name) {
        return repository.findAttractionByName(name);
    }

    //Checks if an attraction name is already taken
    public boolean attractionExists(String name) {
        return repository.existsByName(name);
    }

    //Sends a new attraction to the repository
    //@Transactional: the attraction, its cities and its tags are saved together,
    //so if one of the SQL statements fails, nothing is saved
    @Transactional
    public Attraction addAttraction(Attraction attraction) {
        return repository.addAttraction(attraction);
    }

    //Updates an attraction (all or nothing, like addAttraction)
    @Transactional
    public boolean updateAttraction(String name, Attraction updatedAttraction) {
        return repository.updateAttraction(name, updatedAttraction);
    }

    //Deletes an attraction
    @Transactional
    public Attraction deleteAttraction(String name) {
        return repository.deleteAttraction(name);
    }

    //Gets the tags of one attraction, found by name
    //Returns null if the attraction does not exist
    public List<Tag> getTags(String name) {
        Attraction attraction = repository.findAttractionByName(name);
        return attraction == null ? null : attraction.getTags();
    }
}
