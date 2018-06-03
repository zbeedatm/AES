package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
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
	
	public StudentController() throws IOException {
		//Pane startExamPane = FXMLLoader.load(getClass().getResource("StartExam.fxml"));
		//examPane.getChildren().add(startExamPane);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Pane startExamPane = null;
		try {
			startExamPane = FXMLLoader.load(getClass().getResource("StartExam.fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		examPane.getChildren().add(startExamPane);
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
