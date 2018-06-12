package application;

import java.io.IOException;

import common.data.DataPage;
import common.data.Record;
import common.data.Request;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
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

	private static String examCode;
	private static String testID;
	private static int numOfQuestions;
	private static int testDuration;
	private static int pointsPerQuestion;
	private static String remarksForStudents;
	private static Object lock = null;
	private static DataPage dataPage = null;

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
			Request request = new Request("get", "get_exam", query, values);
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
		if (rbComputerized.isSelected()) {
			
//			Pane startExamPane = null;
//			try {
//				startExamPane = FXMLLoader.load(getClass().getResource("StartExam.fxml"));
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			examQuestionsPane.getChildren().add(startExamPane);
			
			examQuestionsPane.setVisible(true);
			examCodePane.setDisable(true);
			startExamPane.setDisable(true);
			
			getTestQuestions();
			
			lock = new Object();
			synchronized (lock) {
				lock.wait();
			}
			
			lblInstructions.setText(remarksForStudents + "\n\n Duration: " + testDuration + " minutes");
//			Timer timer = new Timer();
//			TimerTask task = new TimerTask()
//			{
//			        public void run()
//			        {
//			        	lblTimer.setText(String.valueOf((testDuration*1000-1)/1000));      
//			        }
//
//			};
//			timer.schedule(task,5000l);
			// update timerLabel
			lblTimer.setText(String.valueOf(testDuration));
	        Timeline timeline = new Timeline();
	        timeline.setCycleCount(Timeline.INDEFINITE);
	        timeline.getKeyFrames().add(
	                new KeyFrame(Duration.seconds(1),
	                  new EventHandler() {
	                    // KeyFrame event handler
	                	@Override
	                    public void handle(Event event) {
	                    	testDuration--;
	                        // update timerLabel
	                        lblTimer.setText(String.valueOf(testDuration));
	                        if (testDuration <= 0) {
	                            timeline.stop();
	                        }
	                      }
	                }));
	        timeline.playFromStart();
			
			questionsPagination.setPageCount(numOfQuestions);
			questionsPagination.setPageFactory(new Callback<Integer, Node>() {
	            @Override
	            public Node call(Integer pageIndex) {
	                return createPage(pageIndex);
	            }
	        });
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
		
		Request request = new Request("get", "start_exam", query.toString(), values);
		AESystem.application.retriveResultSet(request);
	}
	
	public VBox createPage(int pageIndex) {
		VBox box = new VBox(2);
		VBox element = new VBox();
		
		Label lblQuestion = new Label("\n\n" + dataPage.get(pageIndex).get(15));
		ToggleGroup tg = new ToggleGroup();
		RadioButton rb1 = new RadioButton("a");
		rb1.setToggleGroup(tg);
		RadioButton rb2 = new RadioButton("b");
		rb2.setToggleGroup(tg);
		RadioButton rb3 = new RadioButton("c");
		rb3.setToggleGroup(tg);
		RadioButton rb4 = new RadioButton("d");
		rb4.setToggleGroup(tg);
		
		element.getChildren().addAll(lblQuestion, rb1, rb2, rb3, rb4);
		box.getChildren().add(element);
		box.setAlignment(Pos.CENTER);
		
		return box;
	}
	
	public static void setExamQuestions(DataPage data) {
		dataPage = data;

		synchronized (lock) {
			lock.notifyAll();
		}
	}
}
