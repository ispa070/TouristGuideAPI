package dev.naeveklubben.touristguideapi.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Attraction {

    //Stores the id, name, description, address, cities and tags of an attraction
    private int id;
    private String name;
    private String description;
    private String address;
    private List<City> city = new ArrayList<>();
    private List<Tag> tags = new ArrayList<>();

    //Empty constructor used by Spring when it binds form data to a new Attraction
    public Attraction() {}

    //Constructor used when creating a full Attraction object
    public Attraction(int id, String name, String description, String address, List<City> city, List<Tag> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.city = city;
        this.tags = tags;
    }

    //Constructor used when creating a new attraction that has no id yet (before it is saved)
    public Attraction(String name, String description, String address, List<City> city, List<Tag> tags) {
        this(0, name, description, address, city, tags);
    }

    //Constructor used when reading an attraction from the database without city and tags
    public Attraction(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    //Getters to return values of the fields
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getAddress() {
        return address;
    }

    public List<City> getCity() {
        return city;
    }

    public List<Tag> getTags() {
        return tags;
    }

    //Setters to update the attraction
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(List<City> city) {
        this.city = city;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    //Two attractions are equal if all their fields are equal (used by the tests)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Attraction other)) return false;
        return id == other.id
                && Objects.equals(name, other.name)
                && Objects.equals(description, other.description)
                && Objects.equals(address, other.address)
                && Objects.equals(city, other.city)
                && Objects.equals(tags, other.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, address, city, tags);
    }
}
