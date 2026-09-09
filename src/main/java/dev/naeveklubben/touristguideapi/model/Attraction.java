package dev.naeveklubben.touristguideapi.model;

public class Attraction {

    //Stores the name and description of an attraction
    private String name;
    private String description;
    private City city;
    private Tag tag;

    //Constructor used when creating a new Attraction object
    public Attraction(String name, String description, City city, Tag tag){
        this.name = name;
        this.description = description;
        this.city = city;
        this.tag =tag;
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

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

}
