
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Workout {

	private int code;
	private ObservableList<Exercise> exercises = FXCollections.observableArrayList();
	private DBConnect dbc;
	
	public Workout(int code) {
		dbc = dbc.getInstance();
		setCode(code);
		createWorkout();
	}

	public int getCode() {
		return this.code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void addExercise(Exercise e) {
		exercises.add(e);
	}

	public ObservableList<Exercise> getExercises() {
		return this.exercises;
	}

	public void createWorkout() {
		DBConnect dbc = DBConnect.getInstance();

		ResultSet rs = dbc.select(new String[] { "work_set.exercise_id, work_set.reps, work_set.weight, workout.workout_id" }, "work_set")
				.join("workout", "work_set.set_id", "=", "workout.set_id").where(new String[][] {
						{ "workout.completed", "=", "0" }, { "workout.code", "=", Integer.toString(this.code) } })
				.execute();

		try {
			while (rs.next()) {
				Exercise e = new Exercise(rs.getInt("exercise_id"), rs.getInt("reps"), rs.getDouble("weight"), rs.getInt("workout_id"));
				this.addExercise(e);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void newWorkout() {	
		dbc.update("workout", "completed", "1").where("code", "=", Integer.toString(this.code)).execute();
		
		for(Exercise e : this.exercises) {
			e.setWeight(e.getWeight() + 2.5);
			e.updateExercise();
			dbc.insertWorkout(this.code, e.getId());
		}
	}
}
