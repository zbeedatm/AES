package application;

import java.io.IOException;

import common.data.DataPage;
import common.data.Record;
import common.data.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class LoginController {
	
	@FXML
	private Button btnLogin;
	
	@FXML
	private TextField txtUserName;
	
	@FXML
	private PasswordField txtPassword;
	
	@FXML
	private Label lblMessage;
	
	@SuppressWarnings("unused")
	public static String userId;
	
	@SuppressWarnings("unused")
	public static String userName;
	
	public static String userRole;
	
	private static Object lock = null;
	
	//private final static CountDownLatch loginLatch = new CountDownLatch (2);
	
	public LoginController() throws IOException {
		//FXMLLoader.load(getClass().getResource("Login.fxml"));
	}
	
	public void login(ActionEvent event) throws IOException, InterruptedException
	{
		//Cleanup
		lblMessage.setText("");
		
		getUserRole();
		
		//Open the relevant scene according to the user who loged in
		if (userRole != null) {
			Parent parent = null;
			switch (userRole.toLowerCase()) {
			case "student" : parent = FXMLLoader.load(getClass().getResource("StudentScene.fxml"));
			break;
			case "lecturer" : parent = FXMLLoader.load(getClass().getResource("LecturerScene.fxml"));
			break;
			case "manager" : parent = FXMLLoader.load(getClass().getResource("ManagerScene.fxml"));
			break;
			}
			Scene scene = new Scene(parent);

			//This line gets the Stage information
			Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

			window.setScene(scene);
			window.show();
			window.setMaximized(true);
		} else {
			lblMessage.setTextFill(Paint.valueOf("Red"));
			lblMessage.setText("Please enter valid credentials");
		}
	}
	
	private void getUserRole() throws InterruptedException {
		String query = "SELECT * FROM users where username=? and password=?;";
		String[] values = new String[2];
		values[0] = txtUserName.getText();
		values[1] = txtPassword.getText();
		
		if (values[0].isEmpty() || values[1].isEmpty()) {
			lblMessage.setTextFill(Paint.valueOf("Red"));
			lblMessage.setText("Please fill both fileds: User name & Password");
		}else {
			Request request = new Request("get", "login", query, values);
			AESystem.application.retriveResultSet(request);
			
			//loginLatch.countDown();
			lock = new Object();
			synchronized (lock) {
				lock.wait();
			}
		}
		
	}
	
	public static void setUserRole(DataPage data) {
		for (Record record : data){
			userId = record.get(0);
			userName = record.get(1);
			userRole = record.get(2);
		}
		
		synchronized (lock) {
			lock.notifyAll();
		}
		//loginLatch.notify(); //countDown ();
	}

}
