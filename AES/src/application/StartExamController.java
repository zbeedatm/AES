package application;

import java.io.IOException;

import common.data.DataPage;
import common.data.Record;
import common.data.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;

public class StartExamController {
	
	@FXML
	private Button btnSubmit;
	
	@FXML
	private TextField txtExamCode;
	
	@FXML
	private Label lblMessage;
	
	@FXML
	private Pane startExamPane;
	
	private static String examCode;
	
	private static Object lock = null;
	
	
	public StartExamController() throws IOException {
		//FXMLLoader.load(getClass().getResource("StartExam.fxml"));
	}

	public void getExamByCode(ActionEvent event) throws InterruptedException {
		String query = "SELECT * FROM exams where examCode=?;";
		String[] values = new String[1];
		values[0] = txtExamCode.getText();
		
		//Cleanup
		lblMessage.setText("");
		
		if (values[0].isEmpty()) {
			lblMessage.setTextFill(Paint.valueOf("Red"));
			lblMessage.setText("Please enter exam code");
		}else {
			Request request = new Request("get", "start_exam", query, values);
			AESystem.application.retriveResultSet(request);
			
			//loginLatch.countDown();
			lock = new Object();
			synchronized (lock) {
				lock.wait();
			}
			
			if (examCode != null && !examCode.isEmpty()) {
				startExamPane.setVisible(true);
			} else {
				lblMessage.setTextFill(Paint.valueOf("Red"));
				lblMessage.setText("No such exam, please enter a valid one");
			}
		}
	}
	
	public static void setExamCode(DataPage data) {
		for (Record record : data){
			examCode = record.get(1);
		}
		
		synchronized (lock) {
			lock.notifyAll();
		}
	}
}
