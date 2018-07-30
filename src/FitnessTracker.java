
import java.sql.ResultSet;

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
			dbc.printTable("work_set");
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
		
		ResultSet rs = dbc.selectFromTable("name", "exercise");
		while(rs.next()) {
			exerciseComboBox.getItems().add(rs.getString(1));
		}
		
		Label workoutLabel = new Label("Workout");
		
		TableView<Exercise> setTable = new TableView<>();
		setTable.setEditable(true);
		
		TableColumn<Exercise, String> exerciseCol = new TableColumn<>("Exercise");
		exerciseCol.setCellValueFactory(new PropertyValueFactory<Exercise, String>("name"));
		
		TableColumn<Exercise, Integer> weightCol = new TableColumn<>("Weight");
		weightCol.setCellValueFactory(new PropertyValueFactory<Exercise, Integer>("weight"));
		
		TableColumn<Exercise, Integer> repCol = new TableColumn<>("Reps");
		repCol.setCellValueFactory(new PropertyValueFactory<Exercise, Integer>("reps"));
		
		setTable.getColumns().addAll(exerciseCol, weightCol, repCol);
		
		ObservableList<Exercise> data = FXCollections.observableArrayList(new Exercise("Squat", 70, 5));
		setTable.setItems(data);
		
		BorderPane border = new BorderPane();
		border.setTop(workoutLabel);
		border.setLeft(setTable);
		border.setBottom(exerciseComboBox);
		primaryStage.setScene(new Scene(border, 1280, 720));
		primaryStage.show();
	}
}
