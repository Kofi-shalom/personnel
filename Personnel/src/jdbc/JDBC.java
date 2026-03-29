package jdbc;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import personnel.*;

public class JDBC implements Passerelle 
{
	Connection connection;

	public JDBC()
	{
		try
		{
			Class.forName(Credentials.getDriverClassName());
			connection = DriverManager.getConnection(Credentials.getUrl(), Credentials.getUser(), Credentials.getPassword());
		}
		catch (ClassNotFoundException e)
		{
			System.out.println("Pilote JDBC non installÃ©.");
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
	}
	
/*	@Override
	public GestionPersonnel getGestionPersonnel() 
	{
		GestionPersonnel gestionPersonnel = new GestionPersonnel();
		try 
		{
			String requete = "select * from ligue";
			Statement instruction = connection.createStatement();
			ResultSet ligues = instruction.executeQuery(requete);
			while (ligues.next())
				gestionPersonnel.addLigue(ligues.getInt(1), ligues.getString(2));
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		return gestionPersonnel;
	}*/
	
	
	
	
	
	@Override
	public GestionPersonnel getGestionPersonnel() 
	{
		GestionPersonnel gestionPersonnel = new GestionPersonnel();

		try 
		{
			// Charger le root
			String reqRoot = "SELECT * FROM employe WHERE role = 'ROOT'";
			PreparedStatement psRoot = connection.prepareStatement(reqRoot);
			ResultSet rsRoot = psRoot.executeQuery();

			if (rsRoot.next()) {
				int id = rsRoot.getInt("id_employe");
				String nom = rsRoot.getString("nom");
				String prenom = rsRoot.getString("prenom");
				String mail = rsRoot.getString("mail");
				String password = rsRoot.getString("password");

				LocalDate dateArrivee = rsRoot.getDate("date_arrivee").toLocalDate();

				LocalDate dateDepart = null;
				if (rsRoot.getDate("date_depart") != null) {
					dateDepart = rsRoot.getDate("date_depart").toLocalDate();
				}

				gestionPersonnel.addRoot(id, nom, prenom, mail, password, dateArrivee, dateDepart);
			}

			// Charger les ligues 
			String requete = "SELECT * FROM ligue";
			Statement instruction = connection.createStatement();
			ResultSet ligues = instruction.executeQuery(requete);
			

			while (ligues.next()) {
				

				    String req = "SELECT * FROM employe WHERE idLigue = ?";
				    PreparedStatement stmt = connection.prepareStatement(req);
				    stmt.setInt(1, ligues.getRow());

				    ResultSet rs = stmt.executeQuery();
				    
			
				
				gestionPersonnel.addLigue(ligues.getInt(1), ligues.getString(2));
			}
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}

		return gestionPersonnel;
	}
	
	
	
	
	@Override
	public void sauvegarderGestionPersonnel(GestionPersonnel gestionPersonnel) throws SauvegardeImpossible 
	{
		close();
	}
	
	public void close() throws SauvegardeImpossible
	{
		try
		{
			if (connection != null)
				connection.close();
		}
		catch (SQLException e)
		{
			throw new SauvegardeImpossible(e);
		}
	}
	
	@Override
	public int insert(Ligue ligue) throws SauvegardeImpossible 
	{
		try 
		{
			PreparedStatement instruction;
			instruction = connection.prepareStatement("insert into ligue (nom) values(?)", Statement.RETURN_GENERATED_KEYS);
			instruction.setString(1, ligue.getNom());		
			instruction.executeUpdate();
			ResultSet id = instruction.getGeneratedKeys();
			id.next();
			return id.getInt(1);
		} 
		catch (SQLException exception) 
		{
			exception.printStackTrace();
			throw new SauvegardeImpossible(exception);
		}		
	}



	@Override
	public int insert(Employe employe) throws SauvegardeImpossible 
	{
	    try 
	    {
	        // 1. La requête SQL avec TOUTES les colonnes nécessaires
	        String query = "INSERT INTO employe (nom, prenom, mail, password, date_arrivee, date_depart, idLigue) VALUES (?, ?, ?, ?, ?, ?, ?)";
	        
	        PreparedStatement instruction = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	        
	        // 2. On remplit les "?" (Attention à l'ordre !)
	        instruction.setString(1, employe.getNom());
	        instruction.setString(2, employe.getPrenom());
	        instruction.setString(3, employe.getMail());
	        instruction.setString(4, "password123"); // Mot de passe par défaut
	        instruction.setDate(5, java.sql.Date.valueOf(employe.getdate_arrivee()));
	        
	        // Gestion de la date de départ (elle peut être vide/null au début)
	        if (employe.getdate_depart() != null)
	            instruction.setDate(6, java.sql.Date.valueOf(employe.getdate_depart()));
	        else
	            instruction.setNull(6, java.sql.Types.DATE);

	        // 3. ON LIE L'EMPLOYÉ À SA LIGUE (C'est le plus important)
	        instruction.setInt(7, employe.getLigue().getId());
	        
	        instruction.executeUpdate();
	        
	        // 4. On récupère l'ID que la base de données a créé automatiquement
	        ResultSet idGenerated = instruction.getGeneratedKeys();
	        idGenerated.next();
	        return idGenerated.getInt(1);
	    } 
	    catch (SQLException exception) 
	    {
	        exception.printStackTrace();
	        throw new SauvegardeImpossible(exception);
	    }       
	}
	
	
	@Override
	public void update(Ligue ligue) throws SauvegardeImpossible 
	{
		try 
		{
			PreparedStatement instruction;
			instruction = connection.prepareStatement("UPDATE ligue  SET nom = ? WHERE id = ?");
			instruction.setString(1, ligue.getNom());
			instruction.setInt(2, ligue.getId());
			instruction.executeUpdate();
		} 
		catch (SQLException exception) 
		{
			exception.printStackTrace();
			throw new SauvegardeImpossible(exception);
		}		
	}
	
	@Override
	public void update(Employe employe) throws SauvegardeImpossible 
	{
		try 
		{
			PreparedStatement instruction;
			instruction = connection.prepareStatement("UPDATE employe  SET nom = ? WHERE id = ?");
			instruction.setString(1, employe.getNom());
			instruction.setInt(2, employe.getId());
			instruction.executeUpdate();
		} 
		catch (SQLException exception) 
		{
			exception.printStackTrace();
			throw new SauvegardeImpossible(exception);
		}		
	}
	
}