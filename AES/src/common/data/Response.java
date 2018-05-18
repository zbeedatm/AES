package common.data;

import java.io.Serializable;

public class Response implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String action;
	private DataPage data;
	
	public Response(String action, DataPage data) {
		super();
		this.action = action;
		this.data = data;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public DataPage getData() {
		return data;
	}

	public void setData(DataPage data) {
		this.data = data;
	}
	
	
}
