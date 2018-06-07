package common.data;

import java.io.Serializable;

public class Request implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String action;
	private String target;
	private String query;
	private Object[] values;
	
	public Request(String action, String target, String query, Object[] values) {
		this.setAction(action);
		this.setTarget(target);
		this.setQuery(query);
		this.setValues(values);
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}
	
	public Object[] getValues() {
		return values;
	}

	public void setValues(Object[] values) {
		this.values = values;
	}
}
