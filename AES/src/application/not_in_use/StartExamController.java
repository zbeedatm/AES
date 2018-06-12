package application.not_in_use;

import java.io.IOException;

import common.data.Request;
import javafx.event.ActionEvent;
import javafx.scene.control.Pagination;
import javafx.scene.paint.Paint;

public class StartExamController {
	private Pagination pagination;
	public StartExamController() throws IOException {
		//FXMLLoader.load(getClass().getResource("StartExam.fxml"));
		
		pagination = new Pagination(10);
		
	}
	
//	public void getExamByCode(ActionEvent event) throws InterruptedException {
//		String query = "SELECT * FROM exams where examCode=?;";
//		String[] values = new String[1];
//		values[0] = txtExamCode.getText();
//		
//		//Cleanup
//		lblMessage.setText("");
//		
//		if (values[0].isEmpty()) {
//			lblMessage.setTextFill(Paint.valueOf("Red"));
//			lblMessage.setText("Please enter exam code");
//		}else {
//			Request request = new Request("get", "start_exam", query, values);
//			AESystem.application.retriveResultSet(request);

}
