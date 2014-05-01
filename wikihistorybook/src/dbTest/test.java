package dbTest;

import java.sql.ResultSet;
import java.sql.SQLException;


public class test {

	/**
	 * Testclass
	 * @author simon fl?hmann
	 */
		
	// main method
	public static void main(String[] args) {
		
		//DB als Singleton
		DBProvider db = DBProvider.getInstance();		
		//DB Verbindung ?ffnen
		db.getConnection();
		
		//SQL Query als String formulieren
		String query="SELECT * FROM wikihistory.people where year_from > 1964";
		//Query ausf?hren
		ResultSet result= db.executeQuery(query);
		
		
		//Resultat ausgeben
		try {
			while ( result.next() )
				  System.out.printf( "name: %s, year_from: %s%n", result.getString(2),
						  result.getString(5));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		//DB Verbindung schliessen
		db.closeConnection();		
	}

}
