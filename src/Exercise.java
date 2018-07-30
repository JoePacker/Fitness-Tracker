
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
