
import java.sql.Array;
import java.sql.ResultSet;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class FitnessTracker extends Application{

	public static void main(String[] args) throws Exception {
		
		DBConnect dbc = DBConnect.getInstance();

		try {
			dbc.connect();	
			//dbc.printTable("work_set");
			launch(args);
		} catch (Exception e) {
			throw e;
		} finally {
			dbc.closeConnection();
		}
	}
	
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Fitness Tracker");
		
		ComboBox<String> exerciseComboBox = new ComboBox<>();
		
		DBConnect dbc = DBConnect.getInstance();
		
		/* 
		 * ComboBox
		 */
		ResultSet rs = dbc.selectFromTable("name", "exercise");
		while(rs.next()) {
			exerciseComboBox.getItems().add(rs.getString(1));
		}
		
		/*
		 * Label
		 */
		Label workoutLabel = new Label("Workout");
		
		/*
		 * Table
		 */
		TableView<Exercise> setTable = new TableView<>();
		setTable.setEditable(true);
		
		TableColumn<Exercise, String> exerciseCol = new TableColumn<>("Exercise");
		exerciseCol.setCellValueFactory(new PropertyValueFactory<Exercise, String>("name"));
		
		TableColumn<Exercise, Integer> repCol = new TableColumn<>("Reps");
		repCol.setCellValueFactory(new PropertyValueFactory<Exercise, Integer>("reps"));
		
		TableColumn<Exercise, Integer> weightCol = new TableColumn<>("Weight");
		weightCol.setCellValueFactory(new PropertyValueFactory<Exercise, Integer>("weight"));
		
		setTable.getColumns().addAll(exerciseCol, repCol, weightCol);
	
		
		rs = dbc.selectFromTable("set_id", "workout", "code = 2");
		//ArrayList<Integer> setIds = new ArrayList<Integer>();
		
		String setIds = null;
		
		while(rs.next()) {
			setIds += "," + rs.getString("set_id");
		}
		
		System.out.println("setids = " + setIds);
		rs = dbc.selectFromTable("exercise_id, reps, weight", "work_set", "id in (" + setIds + ")");
		
		Workout workout = new Workout(2);
		
		while(rs.next()) {
			System.out.println("ResultSet: " + rs.getRow());
			System.out.println("Exercise id: " + rs.getInt("exercise_id"));
			Exercise e = new Exercise(rs.getInt("exercise_id"), rs.getInt("reps"), rs.getInt("weight"));
			workout.addExercise(e);
		}
		
		System.out.println("length: " + workout.getExercises().size());
		
		setTable.setItems(workout.getExercises());
		
		BorderPane border = new BorderPane();
		border.setTop(workoutLabel);
		border.setLeft(setTable);
		border.setBottom(exerciseComboBox);
		primaryStage.setScene(new Scene(border, 1280, 720));
		primaryStage.show();
	}
}
