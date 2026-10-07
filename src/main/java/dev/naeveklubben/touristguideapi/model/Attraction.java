package dev.naeveklubben.touristguideapi.model;

import java.util.ArrayList;
import java.util.List;

public class Attraction {

    //Stores the name and description of an attraction
    private int id;
    private String name;
    private String description;
    private List<City> city = new ArrayList<>();
    private List<Tag> tags = new ArrayList<>();

    public Attraction() {}

    //Constructor used when creating a new Attraction object
    public Attraction(int id, String name, String description, List<City> city, List<Tag> tags){
        this.id = id;
        this.name = name;
        this.description = description;
        this.city = city;
        this.tags = tags;
    }

    //Constructor used when reading an attraction from the database without city and tags
    public Attraction(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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

    public List<City> getCity() {
        return city;
    }

    public void setCity(List<City> city) {
        this.city = city;
    }

    public List<Tag> getTags(){
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

}
