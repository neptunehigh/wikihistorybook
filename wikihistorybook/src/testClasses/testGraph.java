package testClasses;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import dbTest.DBProvider;

public class testGraph {
	public static void main(String[] args) {
		  long start = System.currentTimeMillis();
		  
		  HashMap<String, String> people = new HashMap<String, String>();
		  HashMap<String, String> connections = new HashMap<String, String>();
		  Graph graph = new SingleGraph("Test Graph");

		// DB als Singleton
		DBProvider db = DBProvider.getInstance();
		// DB Verbindung �ffnen
		db.getConnection();
		// SQL Query als String formulieren und unbedingt die Tabellencols mitnehmen
		String query="SELECT id, name FROM wikihistory.people WHERE id = 38904 OR id = 7559";
		String query2="SELECT person_to, person_from FROM wikihistory.connections WHERE person_to = 38904 ";
		
		// Query ausf�hren
		ResultSet result= db.executeQuery(query);
  	  	ResultSet result2= db.executeQuery(query2);
		
		// Resultat ausgeben
		try {
			while ( result.next() ){
				people.put(result.getString("id"), result.getString("name"));
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			while (result2.next() ){
				if(people.containsKey(result2.getString("person_from")) && people.containsKey(result2.getString("person_to"))){
					connections.put(result2.getString("person_from"), result2.getString("person_to"));
				}
				
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// DB Verbindung schliessen
		db.closeConnection();
		
		 Set<String> keys = people.keySet();
	      for(String key: keys){
	    	  graph.addNode(people.get(key));
	      }
	 
	      Set<String> keys2 = connections.keySet();
	      for(String key2: keys2){
	    	  graph.addEdge(key2+connections.get(key2),people.get(key2),people.get(connections.get(key2)));
	      }
	      			
		graph.display();
	    System.out.println("finished in : "+(System.currentTimeMillis()-start));

	}
}
