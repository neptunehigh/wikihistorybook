package testClasses;

import java.sql.ResultSet;
import java.sql.SQLException;

import dbTest.DBProvider;

/**
 * Testclass
 * @author michael sidler
 * github-account: revolucerne 
 */
public class MikeClass {
	
	public static void main(String[] args) {
		  long start = System.currentTimeMillis();

		// DB als Singleton
		DBProvider db = DBProvider.getInstance();		
		// DB Verbindung öffnen
		db.getConnection();

		// SQL Query als String formulieren
		String query="SELECT * FROM wikihistory.people where year_from > 1954 AND year_to < 2000";
		// Query ausführen
		ResultSet result= db.executeQuery(query);


		// Resultat ausgeben
		try {
			while ( result.next() )
				  System.out.printf( "name: %s, year_from: %s%n", result.getString(2),
						  result.getString(5));
		} catch (SQLException e) {
			e.printStackTrace();
		}


		// DB Verbindung schliessen
		db.closeConnection();		
	    System.out.println("finished in : "+(System.currentTimeMillis()-start));

	}

}
