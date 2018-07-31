import java.sql.ResultSet;
import java.sql.SQLException;

public class Exercise {

	private String name;
	private int weight;
	private int reps;
	
	public Exercise(String name, int weight, int reps) {
		setName(name);
		setWeight(weight);
		setReps(reps);
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	/*
	 * Set name of exercise object by getting name from exercise table using id.
	 */
	public void loadName(int id) {
		DBConnect dbc = DBConnect.getInstance();
		
		try {
			ResultSet rs = dbc.selectFromTable("name", "exercise", "id = " + id);
			if(rs.next()) {
				this.name = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int getWeight() {
		return this.weight;
	}
	
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	public int getReps() {
		return this.reps;
	}
	
	public void setReps(int reps) {
		this.reps = reps;
	}
}
