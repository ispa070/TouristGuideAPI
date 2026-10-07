package dev.naeveklubben.touristguideapi.repository;

import dev.naeveklubben.touristguideapi.model.Attraction;
import dev.naeveklubben.touristguideapi.model.City;
import dev.naeveklubben.touristguideapi.model.Tag;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class AttractionRepository {

    //The attractions are stored in an ArrayList
    private final JdbcTemplate jdbcTemplate;

    public AttractionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //Finds all the attractions through the attraction table
    public List<Attraction> findAll() {
        String sql = "SELECT id, name, description FROM attraction";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Attraction(rs.getInt("id"), rs.getString("name"), rs.getString("description"))
        );
    }

    //Finds the cities of one attraction through the attraction_city table
    private List<City> findCitiesByAttractionId(int attractionId) {
        String sql = "SELECT c.city FROM city c " +
                "JOIN attraction_city ac ON ac.city_id = c.id " +
                "WHERE ac.attraction_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> City.fromDescription(rs.getString("city")), attractionId);
    }

    //Finds the tags of one attraction through the attraction_tag table
    private List<Tag> findTagsByAttractionId(int attractionId) {
        String sql = "SELECT t.tag FROM tag t " +
                "JOIN attraction_tag at ON at.tag_id = t.id " +
                "WHERE at.attraction_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> Tag.fromDescription(rs.getString("tag")), attractionId);
    }

/*
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
*/
    //public Tag showTag(String name) {


}