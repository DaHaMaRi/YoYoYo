create table Adresse(
    ID          int         primary key,
    Strasse     varchar(64) not null,
    Hausnummer  varchar(64) not null,
    Zusatz      varchar(64),
    PLZ         varchar(64) not null,
    Ort         varchar(64) not null
);

-- TO DO Profilbild
create table Benutzer(
    ID              int           primary key,
    Vorname         varchar(64)   not null,
    Nachname        varchar(64)   not null,
    Benutzername    varchar(64)   unique not null,
    Email           varchar(64)   unique not null,
    Passwort        varchar(1024) not null,
    Geburtsdatum    date          not null,
    Beitrittsdatum  date          not null,
    AdressID        int           not null,
    constraint checkDatum check(Geburtsdatum < Beitrittsdatum)
);

create table Zutatenkategorie(
    ID    int         primary key,
    Name  varchar(64) unique not null,
    Preis int         not null,
    constraint checkPreis check(Preis > 0)
);

create table Zutat(
    ID    int           primary key,
    Name  varchar(64)   unique not null,
    Vegan varchar(8)    not null,
    Haram varchar(8)    not null,
    ZutatenkategorieID  int      not null,
    constraint checkVegan check(Vegan in ('true', 'false')),
    constraint checkHaram check(Haram in ('true', 'false'))
);

-- TO DO Yogurtbild
create table Yogurt(
    ID              int          primary key,
    Name            varchar(64)  unique not null,
    BenutzerID      int          not null,
    Veröffentlicht  varchar(8)   not null,
    constraint checkVeröffentlicht check(Veröffentlicht in ('true', 'false'))
);

create table Zutatenliste(
    YogurtID  int,
    ZutatenID int,
    primary key(YogurtID, ZutatenID)
);

create table Bewertung(
    BenutzerID   int,
    YogurtID     int,
    primary key(BenutzerID,YogurtID),
    
    Wertung int not null,
    constraint checkWertung check(Wertung >= 1 and Wertung <= 5)
);

create table Bestellung(
    ID           int  primary key,
    BenutzerID   int  not null,
    Gesamtpreis  int  not null,
    Bestelldatum timestamp not null,
    constraint checkGesamtpreis check(Gesamtpreis > 0)
);

create table Bestellposition(
    BestellungID  int,
    YogurtID      int,
    primary key(BestellungID,YogurtID),
    
    Menge         int not null,
    constraint checkMenge check(Menge > 0)
);