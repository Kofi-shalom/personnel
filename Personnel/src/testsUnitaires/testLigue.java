package testsUnitaires;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import personnel.*;

class testLigue 
{
	GestionPersonnel gestionPersonnel = GestionPersonnel.getGestionPersonnel();
	
	@Test
	void createLigue() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		assertEquals("Fléchettes", ligue.getNom());
	}

	@Test
	void addEmploye() throws SauvegardeImpossible
	{
        java.time.LocalDate date_arrivee = java.time.LocalDate.of(2012,4,6);
        java.time.LocalDate date_depart = java.time.LocalDate.of(2026,8,10);
        
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty", date_arrivee, date_depart); 
		assertEquals(employe, ligue.getEmployes().first());
	}
	
	
	@Test
    void suppressionEmploye() throws SauvegardeImpossible
    {
        Ligue ligue = gestionPersonnel.addLigue("LigueSuppression");
        
            java.time.LocalDate date_arrivee = java.time.LocalDate.of(2012,4,6);
            java.time.LocalDate date_depart = java.time.LocalDate.of(2026,8,10);
            Employe admin = ligue.addEmploye("Malick", "Bryan", "malick@mail", "king", date_arrivee, date_depart);
            Employe user = ligue.addEmploye("shalom", "Darius", "shalom@mail", "sacha", date_arrivee, date_depart);
            
            ligue.setAdministrateur(admin);
            ligue.remove(admin);
            
            assertFalse(ligue.getEmployes().contains(admin));

    }



	@Test
	void testsetDateArriveeetdatedepart() throws SauvegardeImpossible {

        java.time.LocalDate date_arrivee = java.time.LocalDate.of(2012,4,6);
        java.time.LocalDate date_depart = java.time.LocalDate.of(2026,8,10);
        
        Ligue ligue = gestionPersonnel.addLigue("LigueSuppression");
        Employe user = ligue.addEmploye("shalom", "Darius", "shalom@mail", "sacha", date_arrivee, date_depart);

		    user.setdate_arrivee(LocalDate.of(2023, 2, 1));
		    user.setdate_depart(LocalDate.of(2027, 4, 6));

		    assertEquals(LocalDate.of(2023, 2, 1), user.getdate_arrivee());
		    assertEquals(LocalDate.of(2027, 4, 6), user.getdate_depart());
		}

	
	@Test
	void testsuppressionligue( ) throws SauvegardeImpossible {
		 Ligue ligue = gestionPersonnel.addLigue("LigueSuppression");
		 
		 ligue.remove();
		 
		 assertFalse(gestionPersonnel.getLigues().contains(ligue));

	}
	
	
	@Test
	void testgetsetligue() throws SauvegardeImpossible {
		Ligue ligue = gestionPersonnel.addLigue("developpement");
		
		ligue.setNom("Java");
		assertEquals("Java", ligue.getNom());

	    // setAdministrateur
        java.time.LocalDate date_arrivee = java.time.LocalDate.of(2012,4,6);
        java.time.LocalDate date_depart = java.time.LocalDate.of(2026,8,10);

	    Employe admin = ligue.addEmploye(
	        "Malick", "Shalom", "ranelle@mail", "bts",
	        date_arrivee, date_depart
	    );

	    ligue.setAdministrateur(admin);
	    assertEquals(admin, ligue.getAdministrateur());
	}

	
	
	@Test
	void testgetsetemploye() throws SauvegardeImpossible {
        Ligue ligue = gestionPersonnel.addLigue("LigueSuppression");
        
        java.time.LocalDate date_arrivee = java.time.LocalDate.of(2012,4,6);
        java.time.LocalDate date_depart = java.time.LocalDate.of(2026,8,10);
        
        Employe user = ligue.addEmploye("shalom", "Darius", "shalom@mail", "sacha", date_arrivee, date_depart);
        
        user.setNom("Bryan");
        assertEquals("Bryan", user.getNom());


	    user.setPrenom("Ranelle");
	    assertEquals("Ranelle", user.getPrenom());
	    
	    java.time.LocalDate newdate_arrivee = java.time.LocalDate.of(2010,2,6);
	    user.setdate_arrivee(newdate_arrivee);
	    assertEquals(newdate_arrivee, user.getdate_arrivee());

	    java.time.LocalDate newdate_depart = java.time.LocalDate.of(2027,1,10);
	    user.setdate_depart(newdate_depart);
	    assertEquals(newdate_depart, user.getdate_depart());
	}

	
	
	@Test
	void testchangementadmin() throws SauvegardeImpossible {
        Ligue ligue = gestionPersonnel.addLigue("admin_ligue");
        
        java.time.LocalDate date_arrivee = java.time.LocalDate.of(2012,4,6);
        java.time.LocalDate date_depart = java.time.LocalDate.of(2026,8,10);
        
        Employe admin = ligue.addEmploye("Malick", "Bryan", "malick@mail", "king", date_arrivee, date_depart);
        Employe user = ligue.addEmploye("shalom", "Darius", "shalom@mail", "sacha", date_arrivee, date_depart);
        
        ligue.setAdministrateur(user);
        
        assertEquals(user, ligue.getAdministrateur());
	}
}