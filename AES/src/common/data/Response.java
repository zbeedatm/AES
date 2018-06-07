package common.data;

import java.io.Serializable;

public class Response implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String target;
	private DataPage data;
	
	public Response(String action, DataPage data) {
		super();
		this.target = action;
		this.data = data;
	}

	public String gettarget() {
		return target;
	}

	public void setTarget(String action) {
		this.target = action;
	}

	public DataPage getData() {
		return data;
	}

	public void setData(DataPage data) {
		this.data = data;
	}
	
	
}
