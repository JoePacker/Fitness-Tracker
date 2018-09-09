
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class FitnessTracker extends Application{

	public static void main(String[] args) throws Exception {
		
		DBConnect dbc = DBConnect.getInstance();

		try {
			dbc.connect();
			launch(args);
		} catch (Exception e) {
			throw e;
		} finally {
			dbc.closeConnection();
		}
	}
	
	public void start(Stage primaryStage) throws Exception {
		
		DBConnect dbc = DBConnect.getInstance();
		
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
		
		Programme currentProgramme = loadProgramme(dbc);
		
		/* 
		 * ComboBox
		 */
		ComboBox<Integer> exerciseComboBox = new ComboBox<>();
		
		for(int i = 0; i < currentProgramme.getWorkoutCodes().length; i++) {
			exerciseComboBox.getItems().add(currentProgramme.getWorkoutCodes()[i]);
		}
		
		exerciseComboBox.valueProperty().addListener((obs, oldItem, newItem) -> {
			setTable.setItems(currentProgramme.getWorkouts().get(exerciseComboBox.getValue()).getExercises());
		});
		
		/*
		 * Button
		 */
		Button nextButton = new Button("Next");
		Button prevButton = new Button("Prev");
		Button completedButton = new Button("Completed");
		
		nextButton.setOnAction(actionEven -> {
			setTable.setItems(currentProgramme.nextWorkout().getExercises());
		});
		
		prevButton.setOnAction(actionEven -> {
			setTable.setItems(currentProgramme.prevWorkout().getExercises());
		});
		
		completedButton.setOnAction(actionEven -> {
			currentProgramme.getCurrentWorkout().newWorkout();
			setTable.setItems(currentProgramme.nextWorkout().getExercises());
		});
		
		setTable.setItems(currentProgramme.getCurrentWorkout().getExercises());
		
		HBox hbox = new HBox(exerciseComboBox, prevButton, nextButton, completedButton);
		
		primaryStage.setTitle("Fitness Tracker");
		BorderPane border = new BorderPane();
		border.setTop(workoutLabel);
		border.setLeft(setTable);
		border.setBottom(hbox);
		primaryStage.setScene(new Scene(border, 1280, 720));
		primaryStage.show();
	}
	
	public Programme loadProgramme(DBConnect dbc) {
		Programme p = new Programme("Power Hypertrophy Upper Lower");
		p.generateWorkouts();
		return p;
	}
	
}
