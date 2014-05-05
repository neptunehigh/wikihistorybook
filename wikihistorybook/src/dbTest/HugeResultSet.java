package dbTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Set;

public class HugeResultSet {

  public static void main(String [] args) {
	  long start = System.currentTimeMillis();
	  HashMap<Integer, String> people = new HashMap<Integer, String>();
	  Connection connection = null;
      String url = "jdbc:mysql://91.250.82.104:3306/";
      String dbName = "wikihistory";
      String driverName = "com.mysql.jdbc.Driver";
      String userName = "wikipulse";
      String password = "COINcourse2014";
      
      
      try {
        Class.forName(driverName).newInstance();
        connection = DriverManager.getConnection(url+dbName, userName, password);
        PreparedStatement  stmt = connection.prepareStatement("SELECT id, name FROM wikihistory.people where year_from > ? AND year_to < ?");
        stmt.setInt(1, 1964);
        stmt.setInt(2, 2014);
        ResultSet res = stmt.executeQuery();
        if (res.getType() == ResultSet.TYPE_FORWARD_ONLY) {
          System.out.println("ResultSet Type Forword-Only.");
        } else {
          System.out.println("ResultSet scrollable.");
        }
        res.first();
       
        while (res.next()){
        	people.put(res.getInt("id"), res.getString("name"));
        }
        res.close();
        stmt.close();
        
        connection.close();
      } catch (Exception e) {
          System.err.println("Exception: "+ e.getMessage());
      }
      Set<Integer> keys = people.keySet();
      for(Integer key: keys){
          System.out.println("Value of "+key+" is: "+people.get(key));
      }
      System.out.println(people.size());
      System.out.println("finished in : "+(System.currentTimeMillis()-start));
    }
}