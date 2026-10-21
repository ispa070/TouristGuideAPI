package dev.naeveklubben.touristguideapi.repository;

import dev.naeveklubben.touristguideapi.model.Attraction;
import dev.naeveklubben.touristguideapi.model.City;
import dev.naeveklubben.touristguideapi.model.Tag;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class AttractionRepository {

    //JdbcTemplate runs the SQL statements against the database
    private final JdbcTemplate jdbcTemplate;

    public AttractionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //Turns one row from the attraction table into an Attraction object (without city and tags)
    private final RowMapper<Attraction> attractionRowMapper = (rs, rowNum) ->
            new Attraction(rs.getInt("id"), rs.getString("name"), rs.getString("description"));

    // ===================== READ =====================

    //Finds all the attractions through the attraction table,
    //and fills in the cities, address and tags for each of them
    public List<Attraction> findAll() {
        String sql = "SELECT id, name, description FROM attraction ORDER BY name";
        List<Attraction> attractions = jdbcTemplate.query(sql, attractionRowMapper);
        for (Attraction attraction : attractions) {
            fillCitiesAndTags(attraction);
        }
        return attractions;
    }

    //Searches for an attraction by name
    //Returns null if no attraction was found
    public Attraction findAttractionByName(String name) {
        String sql = "SELECT id, name, description FROM attraction WHERE name = ?";
        List<Attraction> result = jdbcTemplate.query(sql, attractionRowMapper, name);
        if (result.isEmpty()) {
            return null;
        }
        Attraction attraction = result.get(0);
        fillCitiesAndTags(attraction);
        return attraction;
    }

    //Checks if an attraction with the given name already exists
    public boolean existsByName(String name) {
        String sql = "SELECT COUNT(*) FROM attraction WHERE name = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, name);
        return count != null && count > 0;
    }

    //Fills in the cities, address and tags of one attraction from the join tables
    private void fillCitiesAndTags(Attraction attraction) {
        attraction.setCity(findCitiesByAttractionId(attraction.getId()));
        attraction.setAddress(findAddressByAttractionId(attraction.getId()));
        attraction.setTags(findTagsByAttractionId(attraction.getId()));
    }

    //Finds the cities of one attraction through the attraction_city table
    private List<City> findCitiesByAttractionId(int attractionId) {
        String sql = "SELECT c.city FROM city c " +
                "JOIN attraction_city ac ON ac.city_id = c.id " +
                "WHERE ac.attraction_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> City.fromDescription(rs.getString("city")), attractionId);
    }

    //Finds the address of one attraction through the attraction_city table
    //Returns null if the attraction has no city/address yet
    private String findAddressByAttractionId(int attractionId) {
        String sql = "SELECT address FROM attraction_city WHERE attraction_id = ? LIMIT 1";
        List<String> addresses = jdbcTemplate.query(sql, (rs, rowNum) -> rs.getString("address"), attractionId);
        return addresses.isEmpty() ? null : addresses.get(0);
    }

    //Finds the tags of one attraction through the attraction_tag table
    private List<Tag> findTagsByAttractionId(int attractionId) {
        String sql = "SELECT t.tag FROM tag t " +
                "JOIN attraction_tag at ON at.tag_id = t.id " +
                "WHERE at.attraction_id = ? " +
                "ORDER BY t.tag";
        return jdbcTemplate.query(sql, (rs, rowNum) -> Tag.fromDescription(rs.getString("tag")), attractionId);
    }

    // ===================== CREATE =====================

    //Adds a new attraction to the attraction table,
    //and links it to its cities and tags through the join tables
    //Returns the attraction with the id the database gave it
    public Attraction addAttraction(Attraction attraction) {
        String sql = "INSERT INTO attraction (name, description) VALUES (?, ?)";

        //KeyHolder catches the id that MySQL generates with AUTO_INCREMENT
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, attraction.getName());
            ps.setString(2, attraction.getDescription());
            return ps;
        }, keyHolder);

        int id = keyHolder.getKey().intValue();
        attraction.setId(id);

        insertCities(id, attraction.getCity(), attraction.getAddress());
        insertTags(id, attraction.getTags());
        return attraction;
    }

    //Links an attraction to its cities in the attraction_city table
    //The city id is looked up from the city table using the city name
    private void insertCities(int attractionId, List<City> cities, String address) {
        if (cities == null) {
            return;
        }
        String sql = "INSERT INTO attraction_city (attraction_id, city_id, address) " +
                "SELECT ?, id, ? FROM city WHERE city = ?";
        //address is NOT NULL in the database, so an empty string is saved if none is given
        String safeAddress = address == null ? "" : address;
        for (City city : cities) {
            jdbcTemplate.update(sql, attractionId, safeAddress, city.getDescription());
        }
    }

    //Links an attraction to its tags in the attraction_tag table
    //The tag id is looked up from the tag table using the tag name
    private void insertTags(int attractionId, List<Tag> tags) {
        if (tags == null) {
            return;
        }
        String sql = "INSERT INTO attraction_tag (attraction_id, tag_id) " +
                "SELECT ?, id FROM tag WHERE tag = ?";
        for (Tag tag : tags) {
            jdbcTemplate.update(sql, attractionId, tag.getDescription());
        }
    }

    // ===================== UPDATE =====================

    //Updates the description, address, cities and tags of an existing attraction, found by name
    //The join tables are cleared and filled again with the new cities and tags
    //Returns false if no attraction with that name exists
    public boolean updateAttraction(String name, Attraction updatedAttraction) {
        Attraction existingAttraction = findAttractionByName(name);
        if (existingAttraction == null) {
            return false;
        }
        int id = existingAttraction.getId();

        String sql = "UPDATE attraction SET description = ? WHERE id = ?";
        jdbcTemplate.update(sql, updatedAttraction.getDescription(), id);

        jdbcTemplate.update("DELETE FROM attraction_city WHERE attraction_id = ?", id);
        insertCities(id, updatedAttraction.getCity(), updatedAttraction.getAddress());

        jdbcTemplate.update("DELETE FROM attraction_tag WHERE attraction_id = ?", id);
        insertTags(id, updatedAttraction.getTags());
        return true;
    }

    // ===================== DELETE =====================

    //Finds and removes an attraction, found by name
    //The rows in attraction_city and attraction_tag are removed automatically (ON DELETE CASCADE)
    //Returns the deleted attraction, or null if it did not exist
    public Attraction deleteAttraction(String name) {
        Attraction attraction = findAttractionByName(name);
        if (attraction == null) {
            return null;
        }
        jdbcTemplate.update("DELETE FROM attraction WHERE id = ?", attraction.getId());
        return attraction;
    }
}
