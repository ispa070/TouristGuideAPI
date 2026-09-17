package dev.naeveklubben.touristguideapi.repository;

import dev.naeveklubben.touristguideapi.model.Attraction;
import dev.naeveklubben.touristguideapi.model.City;
import dev.naeveklubben.touristguideapi.model.Tag;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class AttractionRepository {
    //The attractions are stored in an ArrayList
    private final List<Attraction> attractions = new ArrayList<>();

    public AttractionRepository() {
        //Starting data when the repository is created
        attractions.add(new Attraction("Den Lille Havfrue", "Røvsyg og mega lille, spild af tid", City.KØBENHAVN,
                List.of(Tag.GRATIS)));
        attractions.add(new Attraction("Tivoli", "Forlystelsespark midt i København centrum", City.KØBENHAVN,
                List.of(Tag.FORLYSTELSESPARK)));
        attractions.add(new Attraction("Glyptoteket", "Museum med antik kunst og værker", City.KØBENHAVN,
                List.of(Tag.MUSEUM)));
        attractions.add(new Attraction("Gavlen", "Hyggelig bar på Nørrebro med billig øl", City.KØBENHAVN,
                List.of(Tag.RESTAURANT)));
        attractions.add(new Attraction("Christiania", "Et selvstyrende område, som især er kendt for sit alternative miljø, kreative fællesskab og anderledes livsstil", City.KØBENHAVN,
                List.of(Tag.GRATIS, Tag.NATUR)));
        attractions.add(new Attraction("Nyhavn", "En ikonisk havnefront og kanal i København, der er berømt for sine farvestrålende huse, udendørs caféer og historiske træskibe", City.KØBENHAVN,
                List.of(Tag.GRATIS, Tag.RESTAURANT)));
        attractions.add(new Attraction("ARoS", "Et af Nordeuropas største og mest ikoniske kunstmuseer, der har over 8.000 værker, der dækker perioden fra den danske guldalder og modernisme til international nutidskunst", City.KØBENHAVN,
                List.of(Tag.MUSEUM)));
        attractions.add(new Attraction("H.C. Andersens Hus", "et internationalt anerkendt museum dedicated til eventyrforfatteren Hans Christian Andersen.", City.ODENSE,
                List.of(Tag.MUSEUM, Tag.BØRNEVENLIG)));
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

    public void updateAttraction(String name, Attraction updatedAttraction) {
        Attraction existingAttraction = findAttractionByName(name);
        if (existingAttraction != null) {
            existingAttraction.setDescription(updatedAttraction.getDescription());
            existingAttraction.setCity(updatedAttraction.getCity());
            existingAttraction.setTags(updatedAttraction.getTags());
        }
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

    //public Tag showTag(String name) {


}