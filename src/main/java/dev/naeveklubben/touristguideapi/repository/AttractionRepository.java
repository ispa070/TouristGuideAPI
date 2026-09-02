package dev.naeveklubben.touristguideapi.repository;

import dev.naeveklubben.touristguideapi.model.Attraction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AttractionRepository {
    private final List<Attraction> attractions = new ArrayList<>();

    public AttractionRepository() {
        attractions.add(new Attraction("Tivoli", "Forlystelsespark midt i København centrum"));
        attractions.add(new Attraction("Nyhavn", "Farverig kanal med historiske huse og skibe"));
    }

    public List<Attraction> getAllAttractions() {
        return attractions;
    }

    public Attraction findAttractionByName(String name) {
        for (Attraction attraction : attractions) {
            if (attraction.getName().equals(name)) {
                return attraction;
            }
        }
        return null;
    }

    public void addAttraction(Attraction attraction) {
        attractions.add(attraction);
    }

    public Attraction updateAttraction(String name, Attraction updated){
        Attraction existing = findAttractionByName(name);

        if (existing == null) {
            return null;
        }

        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());

        return existing;
    }


    public Attraction deleteAttraction(String name) {
        Attraction attraction = findAttractionByName(name);

        if (attraction == null){
            return null;
        }

        attractions.remove(attraction);

        return attraction;
    }
}