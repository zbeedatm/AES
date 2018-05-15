package common.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Question{    

	public SimpleStringProperty questionID = new SimpleStringProperty();
	public SimpleIntegerProperty questionNum = new SimpleIntegerProperty();
	public SimpleStringProperty questionText = new SimpleStringProperty(); 
	public SimpleStringProperty teacherName = new SimpleStringProperty(); 

	public String getQuestionID() {
		return questionID.get();
	}

	public Integer questionNum() {
		return questionNum.get();
	}

	public String getQuestionText() {
		return questionText.get();
	}

	public String getTeacherName() {
		return teacherName.get();
	}


}
