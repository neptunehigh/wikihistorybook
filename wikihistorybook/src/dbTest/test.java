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
		
		 long startTime = System.currentTimeMillis();
		
		//DB als Singleton
		DBProvider db = DBProvider.getInstance();		
		//DB Verbindung ?ffnen
		db.getConnection();
		
		System.out.println("connected. ("+(System.currentTimeMillis()-startTime)+" ms)");
		System.out.println();
		
		String query1 = "SELECT COUNT(*) FROM wikihistory.people";
		ResultSet result1= db.executeQuery(query1);
		try {
			result1.first();
			System.out.println("Table Size: "+result1.getString(1));
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String query3="SELECT COUNT(*) FROM wikihistory.people where year_from > 1964";
		ResultSet result3= db.executeQuery(query3);
		try {
			result3.first();
			System.out.println("Result Size: "+result3.getString(1));
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println("processing....");
		//SQL Query als String formulieren
		String query2="SELECT * FROM wikihistory.people "
				+ "WHERE year_from > 1964";
		//Query ausf?hren
		ResultSet result= db.executeQuery(query2);
		//Resultat ausgeben
		try {
			while ( result.next() )
				  System.out.printf( "name: %s, year_from: %s%n", result.getString(2),
						  result.getString(5));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("finished. ("+(System.currentTimeMillis()-startTime)+" ms)");
		
		//DB Verbindung schliessen
		db.closeConnection();		
	}

}
