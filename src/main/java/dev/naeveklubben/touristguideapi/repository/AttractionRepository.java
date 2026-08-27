package dev.naeveklubben.touristguideapi.repository;

import dev.naeveklubben.touristguideapi.model.Attraction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AttractionRepository {
    private final List<Attraction> attractions = new ArrayList<>();
    private String attractionName;


    public List<Attraction> getAllAttractions(){
        return attractions;
    }

    public Attraction findAttractionByName(String name){
        for(Attraction attraction : attractions){
            if(attraction.getName().equals(name)){
                return attraction;
            }
        }
        return null;
    }

    public void addAttraction(Attraction attraction){
        attractions.add(attraction);
    }



}
