package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class Main extends Application {
//	final public static int DEFAULT_PORT = 5555;
//	public static AESystem system = null;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			//primaryStage.setScene(scene);
			//primaryStage.show();
			
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("Home.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            scene = new Scene(page);
            primaryStage.setScene(scene);
            primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
//	public static void main(String[] args) {
//		String host = "";
//	    
//		//Initiate the client oscf module
//		try
//		{
//			host = args[0];
//		}
//		catch(ArrayIndexOutOfBoundsException e)
//		{
//			host = "localhost";
//		}
//		system = new AESystem(host, DEFAULT_PORT);
//		
//		//Launch application
//		launch(args);
//	}
	
}
