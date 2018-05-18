package common.data;

import java.io.Serializable;

public class UpdateRequest extends Request implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Object[] values;
	
	public UpdateRequest(String action, String query, Object[] values) {
		super(action, query);
		this.setValues(values);
	}
	
	public Object[] getValues() {
		return values;
	}

	public void setValues(Object[] values) {
		this.values = values;
	}
}
