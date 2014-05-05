package dbTest;

import java.sql.ResultSet;
import java.sql.SQLException;


public class test2 {
	

	/**
	 * Testclass
	 * @author simon fl?hmann
	 */

	// main method
	public static void main(String[] args) {
		  long start = System.currentTimeMillis();

		//DB als Singleton
		DBProvider db = DBProvider.getInstance();		
		//DB Verbindung ?ffnen
		db.getConnection();

		//SQL Query als String formulieren
		String query="SELECT Count(id) FROM wikihistory.connections";
		//Query ausf?hren
		ResultSet result= db.executeQuery(query);


		//Resultat ausgeben
		try {
			while ( result.next() )
				  System.out.println( result.getString(1));
		} catch (SQLException e) {
			e.printStackTrace();
		}


		//DB Verbindung schliessen
		db.closeConnection();		
	    System.out.println("finished in : "+(System.currentTimeMillis()-start));

	}

}