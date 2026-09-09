package dev.naeveklubben.touristguideapi.model;

public enum Tag {
    BØRNEVENLIG("Børnevenlig"),
    FORLYSTELSESPARK("Forlystelsespark"),
    GRATIS("Gratis"),
    MUSEUM("Museum"),
    NATUR("Natur"),
    RESTAURANT("Restaurant");

    private final String description;

    private Tag(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
