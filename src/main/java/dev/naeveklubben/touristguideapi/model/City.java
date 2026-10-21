package dev.naeveklubben.touristguideapi.model;

public enum City {
    AARHUS("Aarhus"),
    KØBENHAVN("København"),
    ODENSE("Odense");

    private final String description;

    private City(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    //Finds the City that matches the text from the database
    public static City fromDescription(String description) {
        for (City city : values()) {
            if (city.description.equals(description)) {
                return city;
            }
        }
        throw new IllegalArgumentException("Ukendt by: " + description);
    }

}
