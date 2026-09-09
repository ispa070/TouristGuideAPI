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
}
