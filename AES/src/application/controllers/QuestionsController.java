package application.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import application.AESystem;
import common.db.AESConnection;
import common.models.Question;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class QuestionsController implements Initializable {

	private static ObservableList<Question> data;
	
	@FXML private TableView<Question> tblViewQuestions;
	@FXML private TableColumn<Question, SimpleStringProperty> colQuestionID;
	@FXML private TableColumn<Question, SimpleStringProperty> colTeacherName;
	@FXML private TableColumn<Question, SimpleStringProperty> colSubject;
	
	@FXML private Button btnBack;

	public QuestionsController() {
		FXMLLoader loader = new FXMLLoader(
				getClass().getResource(
						"../Questions.fxml"
						)
				);

		//populateQuestionsListView("Java");
		//AESystem.client.handleMessageFromClientUI("Java"); //retriveResultSet("Java");
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		colQuestionID.setCellValueFactory(new PropertyValueFactory<>("questionID"));
		colTeacherName.setCellValueFactory(new PropertyValueFactory<>("teacherName"));
		
		populateQuestionsListView("Java");
	}

	public static void populateQuestionsListView(ResultSet result) {
		try {
			while (result.next()) {
				String qNum = result.getString("questionNum");
				String qText = result.getString("questionText");
				ObservableList<String> list = FXCollections.observableArrayList(qNum, qText);
				//lstQuestions.getItems().addAll(list);
			}

			result.close();    
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}	

	public void populateQuestionsListView(String subject){        
		data = FXCollections.observableArrayList();
	    try{      
	        ResultSet rs = AESConnection.getQuestions();  
	        while(rs.next()){
	        	Question q = new Question();
	        	
	        	q.questionID.set(rs.getString("questionID"));
	            q.questionNum.set(rs.getInt("questionNum"));                       
	            q.questionText.set(rs.getString("questionText"));
	            q.teacherName.set(rs.getString("teacherName"));
	            data.add(q);                  
	        }
	        tblViewQuestions.setItems(data);
	    }
	    catch(Exception e){
	          e.printStackTrace();
	          System.out.println("Error on Building Data");            
	    }
	}

	public void goBackToHomePage(ActionEvent event) throws IOException
	{
		Parent tableViewParent = FXMLLoader.load(getClass().getResource("../Home.fxml"));
		Scene tableViewScene = new Scene(tableViewParent);

		//This line gets the Stage information
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

		window.setScene(tableViewScene);
		window.show();
	}


}
