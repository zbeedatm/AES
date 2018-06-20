package application;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import common.data.DataPage;
import common.data.Record;
import common.data.Request;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

public class GetExamController {

	@FXML
	private Button btnSubmit;

	@FXML
	private TextField txtExamCode;

	@FXML
	private Label lblMessage;

	@FXML
	private Pane startExamPane;
	
	@FXML 
	private RadioButton rbManual;
	
	@FXML 
	private RadioButton rbComputerized;
	
	@FXML
	private Pane examCodePane;
	
	@FXML
	private Pane examQuestionsPane;
	
	@FXML
	private Pagination questionsPagination;
	
	@FXML
	private Label lblInstructions;
	
	@FXML
	private Label lblTimer;
	
	@FXML
	private Button btnSubmitExam;
	
	@FXML
	private Label lblScore;

	private static String examCode;
	private static String testID;
	private static int numOfQuestions;
	private static int testDuration;
	private int duration;
	private int solutionDuration;
	private int totalScore=0;
	private static int pointsPerQuestion;
	private static String remarksForStudents;
	private static Object lock = null;
	private static DataPage dataPage = null;
	private static boolean studentDidThisTestAlready=false;
	private Map<String,Integer> correctAnswers = new HashMap<>();
	
	private Timeline timeline = new Timeline();

	public GetExamController() throws IOException {
		//FXMLLoader.load(getClass().getResource("GetExam.fxml"));
	}

	public void getExamByCode(ActionEvent event) throws InterruptedException {
		//String query = "SELECT * FROM exams where examCode=?;";
		String query = "select examCode, testID, numOfQuestions, testDuration, pointsPerQuestion, remarksForStudents from exams inner join tests on  tests.testID = exams.tests_testID where exams.examCode = ?;";
		String[] values = new String[1];
		values[0] = txtExamCode.getText();

		//Cleanup
		lblMessage.setText("");

		if (values[0].isEmpty()) {
			lblMessage.setTextFill(Paint.valueOf("Red"));
			lblMessage.setText("Please enter exam code");
		}else {
			Request request = new Request("get", "exam_get_code", query, values);
			AESystem.application.retriveResultSet(request);

			//loginLatch.countDown();
			lock = new Object();
			synchronized (lock) {
				lock.wait();
			}

			if (examCode != null && !examCode.isEmpty()) {
				startExamPane.setVisible(true);
				//examQuestionsPane.setVisible(false); //TODO
			} else {
				lblMessage.setTextFill(Paint.valueOf("Red"));
				lblMessage.setText("No such exam, please enter a valid one");
			}
		}
	}

	public static void setExamCode(DataPage data) {
		for (Record record : data){
			examCode = record.get(0);
			testID = record.get(1);
			numOfQuestions = Integer.parseInt(record.get(2));
			testDuration = Integer.parseInt(record.get(3));
			pointsPerQuestion = Integer.parseInt(record.get(4));
			remarksForStudents = record.get(5);
		}

		synchronized (lock) {
			lock.notifyAll();
		}
	}

	public void startExam(ActionEvent event) throws IOException, InterruptedException {
		examQuestionsPane.setVisible(true);
		examCodePane.setDisable(true);
		startExamPane.setDisable(true);

		getTestQuestions();

		lock = new Object();
		synchronized (lock) {
			lock.wait();
		}

		lblInstructions.setText(remarksForStudents + "\nDuration: " + testDuration + " minutes");
		lblInstructions.setStyle("-fx-border-color: black;");

		duration = testDuration;

		// update timerLabel
		lblTimer.setText(String.valueOf(duration));
		lblTimer.setStyle("-fx-border-color: black;");

		//Timeline timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().add(
				new KeyFrame(Duration.seconds(60),
						new EventHandler() {
					// KeyFrame event handler
					@Override
					public void handle(Event event) {
						duration--;
						// update timerLabel
						lblTimer.setText(String.valueOf(duration));
						solutionDuration = testDuration - duration;
						if (solutionDuration <= 0) {
							timeline.stop();
							btnSubmitExam.fire();
							examQuestionsPane.setDisable(true);
						}
					}
				}));
		timeline.playFromStart();

		if (rbComputerized.isSelected()) {
			questionsPagination.setVisible(true);
			questionsPagination.setPageCount(numOfQuestions);
			questionsPagination.setPageFactory(new Callback<Integer, Node>() {
				@Override
				public Node call(Integer pageIndex) {
					return createPage(pageIndex);
				}
			});
		} else if (rbManual.isSelected()) {
			questionsPagination.setVisible(false);
			createWordDocument();
		}
	}

	private void getTestQuestions() {
		StringBuilder query = new StringBuilder()
							.append("select * from tests ") 
							.append("inner join tests_has_questions on tests.testID = tests_has_questions.tests_testID " )
							.append("inner join questions on questions.questionID = tests_has_questions.questions_questionID ")
							.append("where tests.testID = ? ")
							.append("order by questions.questionNum asc;");
		
		String[] values = new String[1];
		values[0] = testID;
		
		Request request = new Request("get", "exam_start_exam", query.toString(), values);
		AESystem.application.retriveResultSet(request);
	}
	
	public VBox createPage(int pageIndex) {
		VBox box = new VBox(2);
		VBox element = new VBox();

		Label lblQuestion = new Label(dataPage.get(pageIndex).get(15) + "\n\n");
		ToggleGroup tg = new ToggleGroup();
		RadioButton rb1 = new RadioButton("1");
		rb1.setUserData("1");
		rb1.setToggleGroup(tg);
		RadioButton rb2 = new RadioButton("2");
		rb2.setUserData("2");
		rb2.setToggleGroup(tg);
		RadioButton rb3 = new RadioButton("3");
		rb3.setUserData("3");
		rb3.setToggleGroup(tg);
		RadioButton rb4 = new RadioButton("4");
		rb4.setUserData("4");
		rb4.setToggleGroup(tg);

		element.getChildren().addAll(lblQuestion, rb1, rb2, rb3, rb4);
		box.getChildren().add(element);
		box.setAlignment(Pos.CENTER);
		
		Integer selectedAnswer = correctAnswers.get(dataPage.get(pageIndex).get(12));
		if ( selectedAnswer != null) {
			switch (selectedAnswer) {
			case 1: tg.selectToggle(rb1);
					break;
			case 2: tg.selectToggle(rb2);
					break;
			case 3: tg.selectToggle(rb3);
					break;
			case 4: tg.selectToggle(rb4);
					break;
			}
		}

		tg.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> ov,
					Toggle old_toggle, Toggle new_toggle) {
				if (tg.getSelectedToggle() != null) {
					int answer = Integer.valueOf( tg.getSelectedToggle().getUserData().toString());
					correctAnswers.put(dataPage.get(pageIndex).get(12), answer);
					//System.out.println(correctAnswers);
					
					switch (answer) {
					case 1: 
							if (Integer.parseInt( dataPage.get(pageIndex).get(16) ) == 1) {
								totalScore = totalScore + pointsPerQuestion;
							}
					break;
					case 2: 
							if (Integer.parseInt( dataPage.get(pageIndex).get(17) ) == 1) {
								totalScore = totalScore + pointsPerQuestion;
							}
					break;
					case 3: 
							if (Integer.parseInt( dataPage.get(pageIndex).get(18) ) == 1) {
								totalScore = totalScore + pointsPerQuestion;
							}
					break;
					case 4: 
							if (Integer.parseInt( dataPage.get(pageIndex).get(19) ) == 1) {
								totalScore = totalScore + pointsPerQuestion;
							}
					break;
					}
				}
			}
		});

		return box;
	}
	
	public static void setExamQuestions(DataPage data) {
		dataPage = data;

		synchronized (lock) {
			lock.notifyAll();
		}
	}
	
	public void checkStudentTestExist() {
		String query = "SELECT * FROM student_test where users_userID=? and exams_examCode=? and lower(selectedExamType)=?;";
		String[] values = new String[3];
		values[0] = LoginController.userId;
		values[1] = txtExamCode.getText();
		values[2] = rbComputerized.getText().toLowerCase();

		Request request = new Request("get", "exam_get_student_test", query, values);
		AESystem.application.retriveResultSet(request);
	}
	
	public static void checkStudentTestExist(DataPage data) {
		if (data.size() > 0) {
			studentDidThisTestAlready = true;
		} else {
			studentDidThisTestAlready = false;
		}

		synchronized (lock) {
			lock.notifyAll();
		}
	}
	
	public void submitExam(ActionEvent event) throws InterruptedException {
		checkStudentTestExist();
		
		lock = new Object();
		synchronized (lock) {
			lock.wait();
		}
		
		if (rbComputerized.isSelected()) {
			Request updateRequest=null;
			Object[] values = new Object[8];
			values[0] = solutionDuration;
			values[1] = "Completed";
			values[2] = totalScore;
			values[3] = 1;
			values[4] = correctAnswers.toString();
			values[5] = LoginController.userId;
			values[6] = examCode;
			values[7] = rbComputerized.getText();

			if (studentDidThisTestAlready) {
				String query = new StringBuilder("")
						.append("update student_test set solutionDuration=?, status=?, grade=?, examSubmitted=?, answers=? ")
						.append("where users_userID=? and exams_examCode=? and lower(selectedExamType)=?;").toString();

				updateRequest = new Request("update", "exam_submit_exam", query, values);
			} else {
				String query = new StringBuilder("")
						.append("insert into student_test (solutionDuration, status, grade, examSubmitted, answers, users_userID, exams_examCode, selectedExamType)")
						.append("values(?, ?, ?, ?, ?, ?, ?, ?);").toString();

				updateRequest = new Request("update", "exam_submit_exam", query, values);
			}

			AESystem.application.update(updateRequest);

			lblScore.setText("Your Score: " + totalScore);
			examQuestionsPane.setDisable(true);
			
		} else if (rbManual.isSelected()) {
			selectFile(event);
		}
		
		timeline.stop();
	}
	
	private void createWordDocument(/*List<String> lines*/) throws IOException {
		int index=0;

		//Blank Document
		XWPFDocument document = new XWPFDocument();
		//Write the Document in file system
		String path = Paths.get(".").toAbsolutePath().normalize().toString() + File.separator + "exams" + File.separator;
		String fileName = path + LoginController.userName + "_" + examCode + ".docx";
		FileOutputStream out = new FileOutputStream(
									new File(fileName) );

		//for (String line : lines) {
		for (Record record : dataPage){
			//create Paragraph
			XWPFParagraph paragraph = document.createParagraph();
			XWPFRun run = paragraph.createRun();
			
			run.setText( (index+1) + ") " + dataPage.get(index).get(15) + "\n\n");
			index++;
		}
		
		document.write(out);
		
		//Close document
		out.close();
		
		openDocument(fileName);
	}
	
	private void openDocument(String fileName) throws IOException {
		File file = new File(fileName);
		
		Desktop desktop = Desktop.getDesktop();
        if(file.exists()) desktop.open(file);
	}
	
	private void selectFile(ActionEvent event) {
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select Exam");
		fileChooser.showOpenDialog(window);
	}
}
