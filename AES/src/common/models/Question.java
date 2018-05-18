package common.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Question{    

	public SimpleStringProperty questionID = new SimpleStringProperty();
	public SimpleIntegerProperty questionNum = new SimpleIntegerProperty();
	public SimpleStringProperty questionText = new SimpleStringProperty(); 
	public SimpleStringProperty teacherName = new SimpleStringProperty(); 
	
	public SimpleIntegerProperty answer1 = new SimpleIntegerProperty();
	public SimpleIntegerProperty answer2 = new SimpleIntegerProperty();
	public SimpleIntegerProperty answer3 = new SimpleIntegerProperty();
	public SimpleIntegerProperty answer4 = new SimpleIntegerProperty();

	public String getQuestionID() {
		return questionID.get();
	}

	public Integer getQuestionNum() {
		return questionNum.get();
	}

	public String getQuestionText() {
		return questionText.get();
	}

	public String getTeacherName() {
		return teacherName.get();
	}

	public SimpleIntegerProperty getAnswer1() {
		return answer1;
	}

	public void setAnswer1(SimpleIntegerProperty answer1) {
		this.answer1 = answer1;
	}

	public SimpleIntegerProperty getAnswer2() {
		return answer2;
	}

	public void setAnswer2(SimpleIntegerProperty answer2) {
		this.answer2 = answer2;
	}

	public SimpleIntegerProperty getAnswer3() {
		return answer3;
	}

	public void setAnswer3(SimpleIntegerProperty answer3) {
		this.answer3 = answer3;
	}

	public SimpleIntegerProperty getAnswer4() {
		return answer4;
	}

	public void setAnswer4(SimpleIntegerProperty answer4) {
		this.answer4 = answer4;
	}

}
