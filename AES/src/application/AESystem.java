package application;
import java.io.IOException;

import common.data.Request;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


/**
 * This class constructs the UI for a chat client.  It implements the
 * chat interface in order to activate the display() method.
 * Warning: Some of the code here is cloned in ServerConsole 
 */
public class AESystem extends Application implements IAESystem 
{
	//Class variables *************************************************
	
	public static AESystem application;

	/**
	 * The default port to connect on.
	 */
	final public static int DEFAULT_PORT = 5555;

	//Instance variables **********************************************

	/**
	 * The instance of the client that created this ConsoleChat.
	 */
	public static AESClient client;


	//Constructors ****************************************************

	public AESystem() {
	}
	
	/**
	 * Constructs an instance of the ClientConsole UI.
	 *
	 * @param host The host to connect to.
	 * @param port The port to connect on.
	 */
	public AESystem(String host, int port) 
	{
		try 
		{
			client= new AESClient(host, port, this);
		} 
		catch(IOException exception) 
		{
			System.out.println("Error: Can't setup connection!"
					+ " Terminating client.");
			System.exit(1);
		}
	}


	//Instance methods ************************************************

	/**
	 * This method overrides the method in the IAESystem interface.  It
	 * displays a message onto the screen.
	 *
	 * @param message The string to be displayed.
	 */
	@Override
	public void display(String message) 
	{
		System.out.println("> " + message);
	}

	@Override
	public void retriveResultSet(Request request) {
		client.handleMessageFromClientUI(request);
	}
	
	@Override
	public void update(Request request) {
		client.handleMessageFromClientUI(request);
	}
	
	//Class methods ***************************************************

	@Override
	public void start(Stage primaryStage) {
		try {
//			BorderPane root = new BorderPane();
//			Scene scene = new Scene(root,400,400);
//			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//			primaryStage.setScene(scene);
//			primaryStage.show();
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Pane page = loader.load();
            Scene scene = new Scene(page);
            primaryStage.setScene(scene);
            primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	 /**
	   * This method is responsible for the creation of the Client UI.
	   *
	   * @param args[0] The host to connect to.
	 * @throws IOException 
	   */
	  public static void main(String[] args) throws IOException 
	  {
		  String host = "";
		  @SuppressWarnings("unused")
		  int port = 0;  //The port number

		  try
		  {
			  host = args[0];
		  }
		  catch(ArrayIndexOutOfBoundsException e)
		  {
			  host = "localhost";
		  }
		  
		  application= new AESystem(host, DEFAULT_PORT);
		  
		  //Launch application
		  launch(args);
	  }

}
//End of ConsoleChat class
