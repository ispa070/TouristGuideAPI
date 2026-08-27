package dev.naeveklubben.touristguideapi.repository;

import dev.naeveklubben.touristguideapi.model.TouristAttraction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TouristRepository {
    private final List<TouristAttraction> attractions = new ArrayList<>();
    private String attractionName;


    public List<TouristAttraction> getAllAttractions(){
        return attractions;
    }

    public TouristAttraction findAttractionByName(String name){
        for(TouristAttraction attraction : attractions){
            if(attraction.getName().equals(name)){
                return attraction;
            }
        }
        return null;
    }




}
