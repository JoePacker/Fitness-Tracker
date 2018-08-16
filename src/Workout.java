
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Workout {

	private int code;
	private ObservableList<Exercise> exercises = FXCollections.observableArrayList();
	
	public Workout(int code) {
		setCode(code);
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
}
