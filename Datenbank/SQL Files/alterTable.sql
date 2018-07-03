alter table Zutat 
  add constraint Zutat_Zutatenkategorie foreign key(ZutatenkategorieID) references Zutatenkategorie(ID);
  
alter table Benutzer
  add constraint Benutzer_Adresse foreign key(AdressID) references Adresse(ID);
  
alter table Yogurt
  add constraint Yogurt_Benutzer foreign key(BenutzerID) references Benutzer(ID);


alter table Bewertung
  add constraint Bewertung_Benutzer foreign key(BenutzerID) references Benutzer(ID)
  add constraint Bewertung_Yogurt foreign key(YogurtID) references Yogurt(ID);
  
alter table Zutatenliste
  add constraint Zutatenliste_Yogurt foreign key(YogurtID) references Yogurt(ID)
  add constraint Zutatenliste_Zutaten foreign key(ZutatenID) references Zutat(ID);

alter table Bestellung
  add constraint Bestellung_Benutzer foreign key(BenutzerID) references Benutzer(ID);

alter table Bestellposition
  add constraint Bestellposition_Bestellung foreign key(BestellungID) references Bestellung(ID)
  add constraint Bestellposition_Yogurt foreign key(YogurtID) references Yogurt(ID);