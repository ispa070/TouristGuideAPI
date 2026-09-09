package dev.naeveklubben.touristguideapi.repository;

import dev.naeveklubben.touristguideapi.model.Attraction;
import dev.naeveklubben.touristguideapi.model.City;
import dev.naeveklubben.touristguideapi.model.Tag;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AttractionRepository {
    //The attractions are stored in an ArrayList
    private final List<Attraction> attractions = new ArrayList<>();

    public AttractionRepository() {
        //Starting data when the repository is created
        attractions.add(new Attraction("Tivoli", "Forlystelsespark midt i København centrum", City.KØBENHAVN, Tag.FORLYSTELSESPARK));
        attractions.add(new Attraction("Nyhavn", "Farverig kanal med historiske huse og skibe",City.KØBENHAVN, Tag.GRATIS));
    }

    //Returns all attractions
    public List<Attraction> getAllAttractions() {
        return attractions;
    }

    //Searches for an attraction by name
    public Attraction findAttractionByName(String name) {
        for (Attraction attraction : attractions) {
            if (attraction.getName().equals(name)) {
                return attraction;
            }
        }

        //Returns null if no attraction was found
        return null;
    }

    //Adds a new attraction to the list
    public void addAttraction(Attraction attraction) {
        attractions.add(attraction);
    }

    //Finds and updates an already existing attraction, found by name
    public Attraction updateAttraction(String name, Attraction updated){
        Attraction existing = findAttractionByName(name);

        //If the attraction does not exist, nothing can be updated
        if (existing == null) {
            return null;
        }

        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());

        return existing;
    }

    //Finds and removes an attraction, found by name
    public Attraction deleteAttraction(String name) {
        Attraction attraction = findAttractionByName(name);

        if (attraction == null){
            return null;
        }

        attractions.remove(attraction);

        return attraction;
    }
}