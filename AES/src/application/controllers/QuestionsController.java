package application.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class QuestionsController implements Initializable {

	private static ObservableList<Question> data;

	@FXML private TableView<Question> tblViewQuestions;
	@FXML private TableColumn<Question, SimpleStringProperty> colQuestionID;
	@FXML private TableColumn<Question, SimpleStringProperty> colTeacherName;
	@FXML private TableColumn<Question, SimpleStringProperty> colSubject;

	@FXML private TextArea questionText;
	@FXML private RadioButton rbAnswer1;
	@FXML private RadioButton rbAnswer2;
	@FXML private RadioButton rbAnswer3;
	@FXML private RadioButton rbAnswer4;

	@FXML private Label lblMessage;
	@FXML private Button btnUpdate;
	@FXML private Button btnBack;

	private String selectedQuestionID;

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

		tblViewQuestions.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				//tblViewQuestions.getSelectionModel().clearSelection();

				lblMessage.setText("");
				
				selectedQuestionID = newSelection.getQuestionID();
				questionText.setText(newSelection.questionText.getValue());

				if (newSelection.answer1.get() == 1) {
					rbAnswer1.setSelected(true);
				} else if (newSelection.answer2.get() == 1) {
					rbAnswer2.setSelected(true);
				} else if (newSelection.answer3.get() == 1) {
					rbAnswer3.setSelected(true);
				} else if (newSelection.answer4.get() == 1) {
					rbAnswer4.setSelected(true);
				}
			}
		});
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
			ResultSet rs = AESConnection.getQueryResult("SELECT * FROM questions;");  
			while(rs.next()){
				Question q = new Question();

				q.questionID.set(rs.getString("questionID"));
				q.questionNum.set(rs.getInt("questionNum"));                       
				q.questionText.set(rs.getString("questionText"));
				q.teacherName.set(rs.getString("teacherName"));

				q.answer1.set(rs.getInt("answer1"));
				q.answer2.set(rs.getInt("answer2"));
				q.answer3.set(rs.getInt("answer3"));
				q.answer4.set(rs.getInt("answer4"));

				data.add(q);                  
			}
			tblViewQuestions.setItems(data);
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println("Error on Building Data");            
		}
	}

	public void updateQuestion(ActionEvent event)
	{
		try
		{
			Connection conn = AESConnection.getConnection();

			// create the java mysql update preparedstatement
			String query = "update questions set questionText = ?, answer1 = ?, answer2 = ?, answer3 = ?, answer4 = ? where questionID = ?";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, questionText.getText());
			preparedStmt.setInt(2, rbAnswer1.isSelected() ? 1 : 0);
			preparedStmt.setInt(3, rbAnswer2.isSelected() ? 1 : 0);
			preparedStmt.setInt(4, rbAnswer3.isSelected() ? 1 : 0);
			preparedStmt.setInt(5, rbAnswer4.isSelected() ? 1 : 0);
			preparedStmt.setString(6, selectedQuestionID);

			// execute the java preparedstatement
			preparedStmt.executeUpdate();

			lblMessage.setText("Data was updated successfuly");
			lblMessage.setTextFill(Paint.valueOf("Green"));
			
			populateQuestionsListView("Java");

			//conn.close();
		}
		catch (Exception e)
		{
			lblMessage.setText("Error: " + e.getMessage());
			lblMessage.setTextFill(Paint.valueOf("Red"));
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
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
