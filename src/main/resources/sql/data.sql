USE touristguide;

-- ===== Parent tables =====
INSERT INTO attraction (name, description)
VALUES ('Den Lille Havfrue', 'Mega lille og røvsyg, spild af tid.'),
       ('H.C. Andersens Hus', 'Et internationalt anerkendt museum dedikeret til eventyrforfatteren, Hans Christian Andersen.'),
       ('Tivoli', 'Forlystelsespark midt i København centrum.'),
       ('Glyptoteket', 'Museum med antik kunst og værker.'),
       ('Gavlen', 'Hyggelig bar på Nørrebro med billig øl.'),
       ('Christiania', 'Et selvstyrende område, som især er kendt for sit alternative miljø, kreative fællesskab og anderledes livsstil.'),
       ('Nyhavn', 'En ikonisk havnefront og kanal i København, der er berømt for sine farvestrålende huse, udendørs caféer og historiske træskibe.'),
       ('ARoS', 'Et af Nordeuropas største og mest ikoniske kunstmuseer med over 8.000 værker fra den danske guldalder og modernisme til international nutidskunst.');

INSERT INTO city (city)
VALUES ('Aarhus'),
       ('København'),
       ('Odense');

INSERT INTO tag (tag)
VALUES ('Børnevenlig'),
       ('Entré'),
       ('Forlystelsespark'),
       ('Gratis'),
       ('Museum'),
       ('Natur'),
       ('Restaurant');

-- ===== Den Lille Havfrue =====
INSERT INTO attraction_city (attraction_id, city_id, address)
SELECT a.id, c.id, 'Langelinie, 2100 København Ø'
FROM attraction a, city c
WHERE a.name = 'Den Lille Havfrue'
  AND c.city = 'København';

INSERT INTO attraction_tag (attraction_id, tag_id)
SELECT a.id, t.id
FROM attraction a, tag t
WHERE a.name = 'Den Lille Havfrue'
  AND t.tag IN ('Gratis');

-- ===== H.C. Andersens Hus =====
INSERT INTO attraction_city (attraction_id, city_id, address)
SELECT a.id, c.id, 'Hans Jensens Stræde 45, 5000 Odense C'
FROM attraction a, city c
WHERE a.name = 'H.C. Andersens Hus'
  AND c.city = 'Odense';

INSERT INTO attraction_tag (attraction_id, tag_id)
SELECT a.id, t.id
FROM attraction a, tag t
WHERE a.name = 'H.C. Andersens Hus'
  AND t.tag IN ('Museum', 'Børnevenlig', 'Entré');

-- ===== Tivoli =====
INSERT INTO attraction_city (attraction_id, city_id, address)
SELECT a.id, c.id, 'Vesterbrogade 3, 1630 København V'
FROM attraction a, city c
WHERE a.name = 'Tivoli'
  AND c.city = 'København';

INSERT INTO attraction_tag (attraction_id, tag_id)
SELECT a.id, t.id
FROM attraction a, tag t
WHERE a.name = 'Tivoli'
  AND t.tag IN ('Forlystelsespark', 'Entré');

-- ===== Glyptoteket =====
INSERT INTO attraction_city (attraction_id, city_id, address)
SELECT a.id, c.id, 'Dantes Plads 7, 1556 København V'
FROM attraction a, city c
WHERE a.name = 'Glyptoteket'
  AND c.city = 'København';

INSERT INTO attraction_tag (attraction_id, tag_id)
SELECT a.id, t.id
FROM attraction a, tag t
WHERE a.name = 'Glyptoteket'
  AND t.tag IN ('Museum', 'Entré');

-- ===== Gavlen =====
INSERT INTO attraction_city (attraction_id, city_id, address)
SELECT a.id, c.id, 'Ryesgade 1, 2200 København N'
FROM attraction a, city c
WHERE a.name = 'Gavlen'
  AND c.city = 'København';

INSERT INTO attraction_tag (attraction_id, tag_id)
SELECT a.id, t.id
FROM attraction a, tag t
WHERE a.name = 'Gavlen'
  AND t.tag IN ('Restaurant');

-- ===== Christiania =====
INSERT INTO attraction_city (attraction_id, city_id, address)
SELECT a.id, c.id, 'Prinsessegade, 1440 København K'
FROM attraction a, city c
WHERE a.name = 'Christiania'
  AND c.city = 'København';

INSERT INTO attraction_tag (attraction_id, tag_id)
SELECT a.id, t.id
FROM attraction a, tag t
WHERE a.name = 'Christiania'
  AND t.tag IN ('Gratis', 'Natur');

-- ===== Nyhavn =====
INSERT INTO attraction_city (attraction_id, city_id, address)
SELECT a.id, c.id, 'Nyhavn, 1051 København K'
FROM attraction a, city c
WHERE a.name = 'Nyhavn'
  AND c.city = 'København';

INSERT INTO attraction_tag (attraction_id, tag_id)
SELECT a.id, t.id
FROM attraction a, tag t
WHERE a.name = 'Nyhavn'
  AND t.tag IN ('Gratis', 'Restaurant');

-- ===== ARoS =====
INSERT INTO attraction_city (attraction_id, city_id, address)
SELECT a.id, c.id, 'Aros Allé 2, 8000 Aarhus C'
FROM attraction a, city c
WHERE a.name = 'ARoS'
  AND c.city = 'Aarhus';

INSERT INTO attraction_tag (attraction_id, tag_id)
SELECT a.id, t.id
FROM attraction a, tag t
WHERE a.name = 'ARoS'
  AND t.tag IN ('Museum', 'Entré');