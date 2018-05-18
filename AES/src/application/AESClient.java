package application;

import java.io.IOException;
import java.util.ArrayList;

import application.controllers.QuestionsController;
import common.data.Request;
import common.data.Response;
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
	public void handleMessageFromServer(Object obj) 
	{
		clientUI.display(obj.toString());
		
		Response response = (Response) obj;
		switch ( response.getAction().toLowerCase() ) {
			case "get_questions": 
					//(new QuestionsController()).populateQuestionsListView(response.getData());
					QuestionsController.populateQuestionsListView(response.getData());
					break;
		}
	}

	
	///////////////////////////////////////////////////////

	/**
	 * This method handles all data coming from the UI            
	 *
	 * @param message The message from the UI.    
	 */
	public void handleMessageFromClientUI(Request request)  
	{
		try
		{
			sendToServer(request);
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
			clientUI.display("Could not send message to server.  Terminating client.");
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
