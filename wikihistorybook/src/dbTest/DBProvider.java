package dbTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBProvider {

	//singleton
	private DBProvider() {}
	
	private static DBProvider provider = null;
	private static Connection connection;
	private Statement statement;
	private final String CONN_QUERY= "jdbc:mysql://91.250.82.104:3306/wikihistory?user=wikipulse&password=COINcourse2014";

	public static DBProvider getInstance() {
		if (provider == null)
			provider = new DBProvider();
		return provider;
	}
	
	public Connection getConnection()
	{
		if(connection==null)		
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				connection = DriverManager
						.getConnection(CONN_QUERY);
			} catch (ClassNotFoundException e) {
				System.out.println("Class not found!");
			} catch (SQLException e) {
				System.out.println("SQL Exception!");
			} catch (Exception ex) {
				System.out.println("Exception");
			}
		return connection;		
	}
	
	public void closeConnection()
	{
		try {
			if (connection!=null && !connection.isClosed())
			{
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * F?hrt mysql select query aus
	 * 
	 * @param query
	 *            String Query, die ausgef?hrt werden soll
	 * @return ResultSet oder null
	 */
	public ResultSet executeQuery(String query){
		ResultSet res=null;
		if(connection==null)getConnection();
		
		try {
			statement = connection.createStatement();
			res = statement.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return res;
	}


}
