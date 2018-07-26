import java.sql.DriverManager;

public class Main {

	public static void main(String[] args) throws Exception {
		DBConnect dbc = new DBConnect();
		
		try {
			dbc.connect();
			dbc.printTable("exercise");
		} catch(Exception e) {
			throw e;
		} finally {
			dbc.closeConnection();
		}
	}
}
