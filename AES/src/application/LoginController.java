package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LoginController {
	
	@FXML
	private Button btnLogin;
	
	public LoginController() throws IOException {
		//FXMLLoader.load(getClass().getResource("Login.fxml"));
	}
	
	public void login(ActionEvent event) throws IOException
	{
		//TODO
		//Open the relevant scene according to the user who loged in
		Parent parent = FXMLLoader.load(getClass().getResource("StudentScene.fxml"));
		Scene scene = new Scene(parent);

		//This line gets the Stage information
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

		window.setScene(scene);
		window.show();
	}

}
