package application;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;

import application.controllers.QuestionsController;
import ocsf.client.AbstractClient;

/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 */
public class AESClient extends AbstractClient
{
	//Instance variables **********************************************

	/**
	 * The interface type variable.  It allows the implementation of 
	 * the display method in the client.
	 */
	IAESystem clientUI; 


	//Constructors ****************************************************

	/**
	 * Constructs an instance of the chat client.
	 *
	 * @param host The server to connect to.
	 * @param port The port number to connect on.
	 * @param clientUI The interface type variable.
	 */

	public AESClient(String host, int port, IAESystem clientUI) 
			throws IOException 
	{
		super(host, port); //Call the superclass constructor
		this.clientUI = clientUI;
		openConnection();
	}


	//Instance methods ************************************************

	/**
	 * This method handles all data that comes in from the server.
	 *
	 * @param msg The message from the server.
	 */
	@Override
	public void handleMessageFromServer(Object msg) 
	{
		clientUI.display(msg.toString());
		
		//QuestionsController.populateQuestionsListView(msg.toString());
	}

//	public ResultSet callServerToGetQuestionsList(Object msg)  
//	{
//		return clientUI.populateQuestionsList((ResultSet) msg);
//	}
	
	///////////////////////////////////////////////////////

	/**
	 * This method handles all data coming from the UI            
	 *
	 * @param message The message from the UI.    
	 */
	public void handleMessageFromClientUI(String message)  
	{
		try
		{
			sendToServer(message);
		}
		catch(IOException e)
		{
			clientUI.display("Could not send message to server. Terminating client.");
			quit();
		}
	}

	public void updateQuestionAnswer(ArrayList<String> questionStruct)  
	{
		try
		{
			sendToServer(questionStruct);
		}
		catch(IOException e)
		{
			clientUI.display
			("Could not send message to server.  Terminating client.");
			quit();
		}
	}


	/**
	 * This method terminates the client.
	 */
	public void quit()
	{
		try
		{
			closeConnection();
		}
		catch(IOException e) {}
		System.exit(0);
	}
}
//End of ChatClient class
