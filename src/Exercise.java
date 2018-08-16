import java.sql.ResultSet;
import java.sql.SQLException;

public class Exercise {

	private String name;
	private int reps;
	private int weight;
	private int workoutCode;
	
	public Exercise(int name, int reps, int weight) {
		setName(name);
		setReps(reps);
		setWeight(weight);
	}
	
	public String getName() {
		return this.name;
	}
	
	/*
	 * Set name of exercise object by getting name from exercise table using id.
	 */
	public void setName(int id) {
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
	
	public int getReps() {
		return this.reps;
	}
	
	public void setReps(int reps) {
		this.reps = reps;
	}
	
	public int getWeight() {
		return this.weight;
	}
	
	public void setWeight(int weight) {
		this.weight = weight;
	}	
	
	public int getWorkoutCode() {
		return this.workoutCode;
	}
	
	public void setWorkoutCode(int code) {
		this.workoutCode = code;
	}
}
