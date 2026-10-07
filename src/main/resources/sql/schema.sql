CREATE DATABASE IF NOT EXISTS touristguide;
USE touristguide;

DROP TABLE IF EXISTS attraction_tag;
DROP TABLE IF EXISTS attraction_city;
DROP TABLE IF EXISTS attraction;
DROP TABLE IF EXISTS city;
DROP TABLE IF EXISTS tag;

CREATE TABLE attraction (
                            id          INT AUTO_INCREMENT PRIMARY KEY,
                            name        VARCHAR(255) NOT NULL UNIQUE,
                            description VARCHAR(1000)
);

CREATE TABLE city (
                      id   INT AUTO_INCREMENT PRIMARY KEY,
                      city VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE tag (
                     id  INT AUTO_INCREMENT PRIMARY KEY,
                     tag VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE attraction_tag (
                                attraction_id INT,
                                tag_id        INT,
                                PRIMARY KEY (attraction_id, tag_id),
                                FOREIGN KEY (attraction_id) REFERENCES attraction(id) ON DELETE CASCADE,
                                FOREIGN KEY (tag_id)        REFERENCES tag(id)
);

CREATE TABLE attraction_city (
                                 attraction_id INT,
                                 city_id       INT,
                                 address       VARCHAR(255) NOT NULL,
                                 PRIMARY KEY (attraction_id, city_id),
                                 FOREIGN KEY (attraction_id) REFERENCES attraction(id) ON DELETE CASCADE,
                                 FOREIGN KEY (city_id)       REFERENCES city(id)
);