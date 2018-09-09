
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
	private String query;
	private String queryType;

	private static DBConnect instance = null;

	private DBConnect() {

	}

	/*
	 * Makes this class a Singleton.
	 */
	public static DBConnect getInstance() {
		if (instance == null) {
			instance = new DBConnect();
		}

		return instance;
	}

	/*
	 * Establishes a connection to the MySQL database.
	 */
	public void connect() {
		// Setup the connection with the DB
		try {
			connect = DriverManager.getConnection(
					"jdbc:mysql://localhost/fitness_tracker?useUnicode=true&useJDBCCompliantTimezoneShift=true"
							+ "&useLegacyDatetimeCode=false&serverTimezone=UTC&verifyServerCertificate=false&useSSL=true"
							+ "&user=root&password=root");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public DBConnect select(String field, String table) {
		this.queryType = "SELECT";
		this.query = "SELECT " + field + " FROM " + table;
		return this;
	}

	public DBConnect select(String[] fields, String table) {
		this.queryType = "SELECT";
		this.query = "SELECT " + String.join(",", fields) + " FROM " + table;
		return this;
	}

	public DBConnect where(String field, String operator, String value) {
		this.query = this.query + " WHERE " + field + operator + value;
		return this;
	}

	public DBConnect where(String[][] clauses) {
		this.query = this.query + " WHERE ";

		for (int i = 0; i < clauses.length; i++) {
			this.query = this.query + clauses[i][0] + clauses[i][1] + clauses[i][2];

			if (i != clauses.length - 1) {
				this.query = this.query + " AND ";
			}
		}

		return this;
	}

	public DBConnect orWhere(String field, String operator, String value) {
		this.query = this.query + " OR " + field + operator + value;
		return this;
	}

	public DBConnect orWhere(String[][] clauses) {
		this.query = this.query + " OR ";

		for (int i = 0; i < clauses.length; i++) {
			this.query = this.query + clauses[i][0] + clauses[i][1] + clauses[i][2];

			if (i != clauses.length - 1) {
				this.query = this.query + " OR ";
			}
		}

		return this;
	}

	public DBConnect join(String table, String columnOne, String operator, String columnTwo) {
		this.query = this.query + " INNER JOIN " + table + " ON " + columnOne + operator + columnTwo;
		return this;
	}

	public DBConnect count(String field, String table) {
		this.queryType = "SELECT";
		this.query = "SELECT COUNT(" + field + ") FROM " + table;
		return this;
	}

	public DBConnect update(String table, String column, String value) {
		this.queryType = "UPDATE";
		this.query = "UPDATE " + table + " SET " + column + "=" + value;
		return this;
	}

	public ResultSet execute() {
		try {
			this.statement = connect.createStatement();
			
			switch (this.queryType) {
				case "SELECT":
					return this.statement.executeQuery(this.query);
				case "UPDATE":
					this.statement.executeUpdate(this.query);
				case "INSERT":
					this.statement.executeUpdate(this.query);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
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

	public ResultSet runQuery(String query) {
		try {
			statement = connect.createStatement();
			return resultSet = statement.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public void insertExercise(int exerciseId, int reps, double weight) {
		try {
			preparedStatement = connect.prepareStatement("INSERT INTO work_set VALUES (default,?,?,?)");
			preparedStatement.setInt(1, exerciseId);
			preparedStatement.setInt(2, reps);
			preparedStatement.setDouble(3, weight);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertWorkout(int code, int setId) {
		try {
			preparedStatement = connect.prepareStatement("insert into workout values (default,?,?,0)");
			preparedStatement.setInt(1, code);
			preparedStatement.setInt(2, setId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
