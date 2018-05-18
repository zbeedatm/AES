package common.data;

import java.io.Serializable;

public class Request implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String action;
	private String query;
	
	public Request(String action, String query) {
		this.setAction(action);
		this.setQuery(query);
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}
}
