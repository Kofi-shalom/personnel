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
	}
	
	@Test
	void testgetsetligue() throws SauvegardeImpossible {
		Ligue ligue = gestionPersonnel.addLigue("developpement");
		
		ligue.setNom("Java");
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