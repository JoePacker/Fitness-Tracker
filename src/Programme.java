
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

public class Programme {

	private String name;
	private int[] workoutCodes;
	private LinkedHashMap<Integer, Workout> workouts = new LinkedHashMap<Integer, Workout>();
	private int workoutPointer = 0;
	private DBConnect dbc;

	public Programme(String name) {
		dbc = dbc.getInstance();
		setName(name);
		findWorkoutCodes();
		// setWorkoutCodes(ids);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void findWorkoutCodes() {
		ResultSet resultSet = dbc.select("workout_codes", "programme").where("name", "=", "\"" + this.name + "\"")
				.execute();
		ResultSet rs = dbc.count("*", "programme").execute();

		try {
			if (rs.next()) {
				this.workoutCodes = new int[rs.getInt(1)];
			}

			int i = 0;

			while (resultSet.next()) {
				this.workoutCodes[i] = resultSet.getInt("workout_codes");
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void setWorkoutCodes(int[] ids) {
		this.workoutCodes = ids;
	}

	public int[] getWorkoutCodes() {
		return this.workoutCodes;
	}

	public void setWorkoutPointer(int pointer) {
		this.workoutPointer = pointer;
	}

	public int getWorkoutPointer() {
		return this.workoutPointer;
	}

	public LinkedHashMap<Integer, Workout> getWorkouts() {
		return this.workouts;
	}

	public void addWorkout(int code, Workout workout) {
		this.workouts.put(code, workout);
	}

	public Workout getCurrentWorkout() {
		return this.workouts.get(this.workoutCodes[this.workoutPointer]);
	}

	public void generateWorkouts() {
		for (int i = 0; i < this.getWorkoutCodes().length; i++) {
			Workout w = new Workout(this.getWorkoutCodes()[i]);
			this.addWorkout(w.getCode(), w);
		}
	}

	public Workout nextWorkout() {
		if (getWorkoutPointer() < getWorkoutCodes().length - 1) {
			this.workoutPointer++;
		} else {
			setWorkoutPointer(0);
		}

		return this.workouts.get(this.workoutCodes[this.workoutPointer]);
	}

	public Workout prevWorkout() {
		if (getWorkoutPointer() > 0) {
			this.workoutPointer--;
		} else {
			setWorkoutPointer(getWorkoutCodes().length - 1);
		}

		return this.workouts.get(this.workoutCodes[this.workoutPointer]);
	}

}
