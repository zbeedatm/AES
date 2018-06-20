package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import common.data.DataPage;
import common.data.Record;
import common.data.Request;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class StudentController implements Initializable  {

	@FXML
	private MenuBar menuBar;
	@FXML
	private AnchorPane contentPane;
	@FXML
	private Pane examPane;
	@FXML
	private ListView<String> computerizedExamsList;
	@FXML
	private ListView<String> manualExamsList;
	
	private Pane startExamPane = null;
	private static Object lock = null;
	public static final ObservableList<String> computerizedList = FXCollections.observableArrayList();
	public static final ObservableList<String> manualList = FXCollections.observableArrayList();
	
	public StudentController() throws IOException {
		//Pane startExamPane = FXMLLoader.load(getClass().getResource("GetExam.fxml"));
		//examPane.getChildren().add(startExamPane);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loadExamScene();
		
		try {
			getCompueterizedExams();
			getManualExams();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void loadExamScene() {
		try {
			examPane.getChildren().remove(startExamPane);
			startExamPane = FXMLLoader.load(getClass().getResource("GetExam.fxml"));
			examPane.getChildren().add(startExamPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void getCompueterizedExams() throws InterruptedException {
		String query = "SELECT exams_examCode FROM student_test where users_userID=? and lower(selectedExamType)='computerized';";
		String[] values = new String[1];
		values[0] = LoginController.userId;

		Request request = new Request("get", "student_get_computerized_exams", query, values);
		AESystem.application.retriveResultSet(request);

		lock = new Object();
		synchronized (lock) {
			lock.wait();
		}
		
		computerizedExamsList.setItems(computerizedList);
		computerizedExamsList.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent event) {
//		        if(event.getButton() == MouseButton.PRIMARY 
//		        		&& event.getClickCount() == 2 
//		        		&& (event.getTarget() instanceof LabeledText || ((GridPane) event.getTarget()).getChildren().size() > 0)
//		        		) {
		        	loadExamScene();
		        	//((Pane) ((AnchorPane) examPane.getChildren().get(1)).getChildren().get(0) ).getChildren();
		        	Pane examCodePane = (Pane) startExamPane.getChildren().get(0);
		        	
		        	TextField examCode =  (TextField) examCodePane.getChildren().get(1);
		        	examCode.setText(computerizedExamsList.getSelectionModel().getSelectedItem());
		        	
		        	Button btnSubmit = (Button) examCodePane.getChildren().get(2);
		        	btnSubmit.fire();
//		         }    
		    }
		});
	}

	public static void fillComputerizedExamsList(DataPage data) {
		for (Record record : data){
			computerizedList.add(record.get(0).toString());
		}
		
		synchronized (lock) {
			lock.notifyAll();
		}
	}
	
	public void getManualExams() throws InterruptedException {
		String query = "SELECT exams_examCode FROM student_test where users_userID=? and lower(selectedExamType)='manual';";
		String[] values = new String[1];
		values[0] = LoginController.userId;

		Request request = new Request("get", "student_get_manual_exams", query, values);
		AESystem.application.retriveResultSet(request);

		lock = new Object();
		synchronized (lock) {
			lock.wait();
		}
		
		manualExamsList.setItems(manualList);
	}
	
	public static void fillManualExamsList(DataPage data) {
		for (Record record : data){
			manualList.add(record.get(0).toString());
		}
		
		synchronized (lock) {
			lock.notifyAll();
		}
	}
	
	public void goBackToLoginPage(ActionEvent event) throws IOException
	{
		Parent parent = FXMLLoader.load(getClass().getResource("Login.fxml"));
		Scene scene = new Scene(parent);

		//This line gets the Stage information
		Stage window = (Stage)((Node) menuBar).getScene().getWindow();

		window.setScene(scene);
		window.show();
	}

}
