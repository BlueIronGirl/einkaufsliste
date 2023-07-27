drop table artikel;
drop table kategorie;

create table artikel
(
    id                    BIGINT NOT NULL AUTO_INCREMENT,
    name                  VARCHAR(255)   NOT NULL,
    kategorie_id          BIGINT,
    anzahl                INT,
    gekauft               BOOLEAN DEFAULT 0,
    erstellungs_zeitpunkt DATETIME,
    kauf_zeitpunkt        DATETIME,
    primary key (id)
);

create table kategorie
(
    id   BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255)   NOT NULL,
    primary key (id)
);