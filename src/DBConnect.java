
/*
 * Interacts with MySQL database.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class DBConnect {

	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	private static DBConnect instance = null;
	
	private DBConnect() {
		
	}
	
	/*
	 * Makes this class a Singleton.
	 */
	public static DBConnect getInstance() {
		 if (instance == null)
	            instance = new DBConnect();
	 
	      return instance;
	}
	
	/*
	 * Establishes a connection to the MySQL database.
	 */
	public void connect() throws Exception {
		// Setup the connection with the DB
		connect = DriverManager.getConnection(
				"jdbc:mysql://localhost/fitness_tracker?useUnicode=true&useJDBCCompliantTimezoneShift=true"
						+ "&useLegacyDatetimeCode=false&serverTimezone=UTC&verifyServerCertificate=false&useSSL=true"
						+ "&user=root&password=root");

	}

	/*
	 * Print all fields of table.
	 */
	public void printTable(String table) throws SQLException {
		resultSet = selectFromTable("*", table);

		int columnCount = resultSet.getMetaData().getColumnCount();

		while (resultSet.next()) {
			for (int i = 1; i <= columnCount; i++) {
				System.out.println(resultSet.getString(i));
			}
		}
	}

	/*
	 * Select fields from table.
	 * OPTIONAL: Enter a where clause.
	 */
	public ResultSet selectFromTable(String fields, String table, String... options) throws SQLException {
		statement = connect.createStatement();
		
		String query = "select " + fields + " from fitness_tracker." + table;
		String opt = " where ";
		
		if(options.length == 0) {
			resultSet = statement.executeQuery(query);
		} else {
			for(String s : options) {
				opt = opt + s;
			}
			resultSet = statement.executeQuery("select " + fields + " from fitness_tracker." + table + opt);
		}
		return resultSet;
	}
	
	/*
	 * Close all connections to database.
	 */
	public void closeConnection() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {
		}

		System.out.println("Closed Connections!");
	}
}
