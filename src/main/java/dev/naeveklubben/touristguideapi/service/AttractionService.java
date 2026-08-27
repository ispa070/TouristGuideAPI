package dev.naeveklubben.touristguideapi.service;

import dev.naeveklubben.touristguideapi.model.Attraction;
import dev.naeveklubben.touristguideapi.repository.AttractionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttractionService {
    private final AttractionRepository repository;

    public AttractionService(AttractionRepository repository) {
        this.repository = repository;
    }

    public List<Attraction> getAttractions() {
        return repository.getAllAttractions();
    }

    public Attraction findTouristAttractionByName(String name) {
        Attraction attraction = repository.findAttractionByName(name);
        return attraction;
    }

}
