import java.sql.ResultSet;
import java.sql.SQLException;

public class Exercise {

	private String name;
	private int id;
	private int reps;
	private double weight;
	private int workoutCode;
	private DBConnect dbc;

	public Exercise(int id, int reps, double weight, int code) {
		dbc = dbc.getInstance();
		setId(id);
		setName(id);
		setReps(reps);
		setWeight(weight);
		setWorkoutCode(code);
	}

	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return this.name;
	}

	/*
	 * Set name of exercise object by getting name from exercise table using id.
	 */
	private void setName(int id) {
		ResultSet rs = dbc.select("name", "exercise").where("exercise_id", "=", Integer.toString(id)).execute();

		try {
			if (rs.next()) {
				this.name = rs.getString("name");
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

	public double getWeight() {
		return this.weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	public void updateExercise() {
		dbc.insertExercise(this.getId(), this.reps, this.weight);
	}

	
	public int getWorkoutCode() {
		return this.workoutCode;
	}

	public void setWorkoutCode(int code) {
		this.workoutCode = code;
	}
	
}
