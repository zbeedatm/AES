package server;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 */
public class AEServer extends AbstractServer 
{
	//Class variables *************************************************

	/**
	 * The default port to listen on.
	 */
	final public static int DEFAULT_PORT = 5555;

	//Constructors ****************************************************

	/**
	 * Constructs an instance of the echo server.
	 *
	 * @param port The port number to connect on.
	 */
	public AEServer(int port) 
	{
		super(port);
	}


	//Instance methods ************************************************

	/**
	 * This method handles any messages received from the client.
	 *
	 * @param msg The message received from the client.
	 * @param client The connection from which the message originated.
	 */
	@Override
	public void handleMessageFromClient (Object msg, ConnectionToClient client)
	{
		System.out.println("Message received: " + msg + " from " + client);

//		ArrayList<String> list = new ArrayList<String>();
//		ResultSet result = AESConnection.getQuestions();
//		try {
//			while (result.next()) {
//				String current = result.getString("questionNum");
//				list.add(current);
//				//list = FXCollections.observableArrayList(current);
//			}
//
//			result.close();     
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//
//		this.sendToAllClients(list.toString()); //AESConnection.getQuestions());
		
		this.sendToAllClients(msg.toString());

		//TODO
		//How to distinguish between different UI operations?
		//To send operation name in msg Object?
		//this.sendToCurrentListeningClient(AESConnection.getQuestions());
	}
	
//	public void getQuestions(Object msg, ConnectionToClient client)
//	{
//		//System.out.println("Message received: " + msg + " from " + client);
//		this.sendToCurrentListeningClient(AESConnection.getQuestions());
//	}

	/**
	 * This method overrides the one in the superclass.  Called
	 * when the server starts listening for connections.
	 */
	protected void serverStarted()
	{
		System.out.println
		("Server listening for connections on port " + getPort());
	}

	/**
	 * This method overrides the one in the superclass.  Called
	 * when the server stops listening for connections.
	 */
	protected void serverStopped()
	{
		System.out.println
		("Server has stopped listening for connections.");
	}

	//Class methods ***************************************************

	/**
	 * This method is responsible for the creation of 
	 * the server instance (there is no UI in this phase).
	 *
	 * @param args[0] The port number to listen on.  Defaults to 5555 
	 *          if no argument is entered.
	 */
	public static void main(String[] args) 
	{
		int port = 0; //Port to listen on

		try
		{
			port = Integer.parseInt(args[0]); //Get port from command line
		}
		catch(Throwable t)
		{
			port = DEFAULT_PORT; //Set port to 5555
		}

		AEServer sv = new AEServer(port);

		try 
		{
			sv.listen(); //Start listening for connections
		} 
		catch (Exception ex) 
		{
			System.out.println("ERROR - Could not listen for clients!");
		}
	}
}
//End of EchoServer class
