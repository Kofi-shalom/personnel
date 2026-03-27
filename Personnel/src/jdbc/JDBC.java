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
			System.out.println("Pilote JDBC non installé.");
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
	}
	

	@Override
	public GestionPersonnel getGestionPersonnel() throws SQLException
	{
		GestionPersonnel gp = new GestionPersonnel();

		PreparedStatement ps = connection.prepareStatement(
			"SELECT * FROM employe WHERE role = 'ROOT'"
		);

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			
			int id = rs.getInt("id_employe");
			String nom = rs.getString("nom");
			String prenom = rs.getString("prenom");
			String mail = rs.getString("mail");
			String password = rs.getString("password");

			LocalDate dateArrivee = rs.getDate("date_arrivee").toLocalDate();

			LocalDate dateDepart = null;
			if (rs.getDate("date_depart") != null) {
				dateDepart = rs.getDate("date_depart").toLocalDate();
			}

			gp.addRoot(id, nom, prenom, mail, password, dateArrivee, dateDepart);

		} else {
			
			gp.addRoot(0, "root", "root", null, null, null, null);
		}

		return gp;
	}*/
	
	
	
	
	
	
	@Override
	public GestionPersonnel getGestionPersonnel() 
	{
		GestionPersonnel gestionPersonnel = new GestionPersonnel();

		try 
		{
			// 🔹 1. Charger le root
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

			// 🔹 2. Charger les ligues (ton code existant)
			String requete = "SELECT * FROM ligue";
			Statement instruction = connection.createStatement();
			ResultSet ligues = instruction.executeQuery(requete);

			while (ligues.next()) {
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
			PreparedStatement instruction;
			instruction = connection.prepareStatement("insert into employe (nom, prenom, email, password, date_arrivee, date_depart) values(?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			instruction.setString(1, employe.getNom());
			instruction.setString(2, employe.getPrenom());
			instruction.setString(3, employe.getMail());
			instruction.setDate(4, Date.valueOf(employe.getdate_arrivee()));
			instruction.setDate(5, Date.valueOf(employe.getdate_depart()));
			
			
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
}