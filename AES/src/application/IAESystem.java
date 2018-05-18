package application;

import common.data.Request;

/**
 * This interface implements the abstract method used to display
 * objects onto the client or server UIs.
 */
public interface IAESystem 
{
	/**
	 * Method that when overriden is used to display objects onto
	 * a UI.
	 */
	public abstract void display(String message);
	
	public abstract void retriveResultSet(Request request);

	//public abstract ResultSet populateQuestionsList(ResultSet message);
}
