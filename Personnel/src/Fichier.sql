CREATE TABLE LIGUE (
    num_ligue INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(100) NOT NULL
);


CREATE TABLE EMPLOYE (
    num_employe INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(50) NOT NULL,
    prenom VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    mail VARCHAR(100) UNIQUE NOT NULL,
    role VARCHAR(50),

    
    date_arrivee DATE,
    date_depart DATE,
   
   
    num_ligue INT,
    CONSTRAINT fk_ligue FOREIGN KEY (num_ligue) REFERENCES LIGUE(num_ligue)
);