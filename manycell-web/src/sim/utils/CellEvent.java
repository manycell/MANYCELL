package sim.utils;

import java.io.Serializable;

public class CellEvent implements Serializable {
	private String paramId;
	private String paramName;
	private float paramValue;
	private String evenName;
	public String getParamId() {
		return paramId;
	}
	public void setParamId(String paramId) {
		this.paramId = paramId;
	}
	public String getParamName() {
		return paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	
	public float getParamValue() {
		return paramValue;
	}
	public void setParamValue(float paramValue) {
		this.paramValue = paramValue;
	}
	public String getEvenName() {
		return evenName;
	}
	public void setEvenName(String evenName) {
		this.evenName = evenName;
	}	

}
