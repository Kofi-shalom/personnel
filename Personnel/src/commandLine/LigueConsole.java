package commandLine;

import static commandLineMenus.rendering.examples.util.InOut.getString;

import java.time.LocalDate;
import java.util.ArrayList;

import commandLineMenus.List;
import commandLineMenus.Menu;
import commandLineMenus.Option;

import personnel.*;

public class LigueConsole 
{
	private GestionPersonnel gestionPersonnel;
	private EmployeConsole employeConsole;

	public LigueConsole(GestionPersonnel gestionPersonnel, EmployeConsole employeConsole)
	{
		this.gestionPersonnel = gestionPersonnel;
		this.employeConsole = employeConsole;
	}

	Menu menuLigues()
	{
		Menu menu = new Menu("Gérer les ligues", "l");
		menu.add(afficherLigues());
		menu.add(ajouterLigue());
		menu.add(selectionnerLigue());
		menu.addBack("q");
		return menu;
	}

	private Option afficherLigues()
	{
		return new Option("Afficher les ligues", "l", () -> {System.out.println(gestionPersonnel.getLigues());});
	}

	private Option afficher(final Ligue ligue)
	{
		return new Option("Afficher la ligue", "l", 
				() -> 
				{
					System.out.println(ligue);
					System.out.println("administrée par " + ligue.getAdministrateur());
				}
		);
	}
	private Option afficherEmployes(final Ligue ligue)
	{
		return new Option("Afficher les employes", "l", () -> {System.out.println(ligue.getEmployes());});
	}

	private Option ajouterLigue()
	{
		return new Option("Ajouter une ligue", "a", () -> 
		{
			try
			{
				gestionPersonnel.addLigue(getString("nom : "));
			}
			catch(SauvegardeImpossible exception)
			{
				System.err.println("Impossible de sauvegarder cette ligue");
			}
		});
	}
	
	private Menu editerLigue(Ligue ligue)
	{
		Menu menu = new Menu("Editer " + ligue.getNom());
		menu.add(afficher(ligue));
		menu.add(gererEmployes(ligue));
		menu.add(changerAdministrateur(ligue));
		menu.add(changerNom(ligue));
		menu.add(supprimer(ligue));
		menu.addBack("q");
		return menu;
	}

	private Option changerNom(final Ligue ligue)
	{
		return new Option("Renommer", "r", 
				() -> {ligue.setNom(getString("Nouveau nom : "));});
	}

	private List<Ligue> selectionnerLigue()
	{
		return new List<Ligue>("Sélectionner une ligue", "e", 
				() -> new ArrayList<>(gestionPersonnel.getLigues()),
				(element) -> editerLigue(element)
				);
	}
	

	
	
	private Option ajouterEmploye(final Ligue ligue)
	{
	    return new Option("ajouter un employé", "a",
	        () ->
	        {
	            String nom = getString("nom : ");
	            String prenom = getString("prenom : ");
	            String mail = getString("mail : ");
	            String password = getString("password : ");

	            LocalDate dateArrivee = LocalDate.parse(
	                getString("date d'arrivée (yyyy-MM-dd) : ")
	            );

	            LocalDate dateDepart = LocalDate.parse(
	                getString("date de départ (yyyy-MM-dd) : ")
	            );

	            ligue.addEmploye(
	                nom,
	                prenom,
	                mail,
	                password,
	                dateArrivee,
	                dateDepart
	            );
	        }
	    );
	}

	
	private Menu gererEmployes(Ligue ligue)
	{
		Menu menu = new Menu("Gérer les employés de " + ligue.getNom(), "e");
		menu.add(afficherEmployes(ligue));
		menu.add(ajouterEmploye(ligue));
		menu.add(selectionnerEmploye(ligue));
		//menu.add(supprimerEmploye(ligue));
		menu.addBack("q");
		return menu;
	}
	
	
	private List<Employe> changerAdministrateur(final Ligue ligue)
	{

		    return new List<>( "Changer l'administrateur","c",
		        () -> new ArrayList<>(ligue.getEmployes()),
		        (index,employe) -> {
		            ligue.setAdministrateur(employe);
		            System.out.println(employe.getNom() + " est maintenant administrateur de la ligue.");
		     }
		);
	}	
	

	
	
	/*private List<Employe> changerAdministrateur(final Ligue ligue)
	{
	    return new List<>(
	        "Changer l'administrateur",
	        "a",
	        () -> new ArrayList<>(ligue.getEmployes()),
	        (employe) -> {
	            ligue.setAdministrateur(employe);
	            System.out.println(
	                
	            );
	        }
	    );
	}*/

	
	
	/*private List<Employe> selectionnerEmploye(final Ligue ligue)
	{
	    return new List<>(
	        "Sélectionner un employé à modifier",
	        "e",
	        () -> new ArrayList<>(ligue.getEmployes()),
	        (index,employe) -> editerEmploye(employe)
	    );
	}*/



	private Employe employeCourant;

	
	private List<Employe> selectionnerEmploye(final Ligue ligue)
	{
	    return new List<>(
	        "Sélectionner un employé à modifier",
	        "e",
	        () -> new ArrayList<>(ligue.getEmployes()),
	        (employe) -> {
	            employeCourant = employe;
	            return menuEditerEmploye(employe);
	        }
	    );
	}
	
	
	
	
	
	private Menu menuEditerEmploye(final Employe employe)
	{
		this.employeCourant = employe;
		
	    Menu menu = new Menu("Éditer " + employe.getNom() , "e");

	    menu.add(new Option(
	        "Modifier l'employé",
	        "m",
	        () -> employeConsole.editerEmploye(employeCourant)
	    ));
	    

	    menu.add(new Option(
	        "Supprimer l'employé",
	        "s",
	        () -> employeCourant.remove()
	    ));

	    menu.addBack("q");

	    return menu;
	}


	
	/*private Menu editerEmploye(final Employe employe)
	{
	    Menu menu = new Menu("Éditer l'employé : " + employe.getNom());

	    menu.add(modifierEmploye(employe));
	    menu.add(supprimerEmploye(employe));

	    menu.addBack("q");
	    return menu;
	}



	private Option modifierEmploye(final Employe employe)
	{
	    return new Option(
		        "Modifier l'employé", "m",
		        () -> employeConsole.editerEmploye(employe)
				);
	}
	
	
	/*private Option supprimerEmploye(final  Employe employe)
	{
		return new List<>("Supprimer un employé", "s", 
				() -> new ArrayList<>(ligue.getEmployes()),
				(index, element) -> {element.remove();}
				);
	}


	}*/

	

	
	private Option supprimer(Ligue ligue)
	{
		return new Option("Supprimer", "d", () -> {ligue.remove();});
	}
	
}
