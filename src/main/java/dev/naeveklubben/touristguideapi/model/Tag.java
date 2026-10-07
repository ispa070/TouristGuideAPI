package dev.naeveklubben.touristguideapi.model;

public enum Tag {
    BØRNEVENLIG("Børnevenlig"),
    ENTRÉ("Entré"),
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

    //Finds the Tag that matches the text from the database
    public static Tag fromDescription(String description) {
        for (Tag tag : values()) {
            if (tag.description.equals(description)) {
                return tag;
            }
        }
        throw new IllegalArgumentException("Ukendt by: " + description);
    }

}
