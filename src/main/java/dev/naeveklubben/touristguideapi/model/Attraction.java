package dev.naeveklubben.touristguideapi.model;

import java.util.ArrayList;
import java.util.List;

public class Attraction {

    //Stores the name and description of an attraction
    private String name;
    private String description;
    private City city;
    private List<Tag> tags = new ArrayList<>();

    //Constructor used when creating a new Attraction object
    public Attraction(String name, String description, City city, List<Tag> tags){
        this.name = name;
        this.description = description;
        this.city = city;
        this.tags = tags;
    }

    //Getters to return values of the fields
    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    //Setters to update the attraction
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public List<Tag> getTags(){
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

}
