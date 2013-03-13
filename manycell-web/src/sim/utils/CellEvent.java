package sim.utils;

import java.io.Serializable;

public class CellEvent implements Serializable {
	private String paramId;
	private String paramName;
	private float paramValue;
	private String evenName;
	/**
	 * @return the paramId
	 */
	public String getParamId() {
		return paramId;
	}
	/**
	 * @param paramId the paramId to set
	 */
	public void setParamId(String paramId) {
		this.paramId = paramId;
	}
	/**
	 * @return the paramName
	 */
	public String getParamName() {
		return paramName;
	}
	/**
	 * @param paramName the paramName to set
	 */
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	/**
	 * @return the paramValue
	 */
	public float getParamValue() {
		return paramValue;
	}
	/**
	 * @param paramValue the paramValue to set
	 */
	public void setParamValue(float paramValue) {
		this.paramValue = paramValue;
	}
	/**
	 * @return the evenName
	 */
	public String getEvenName() {
		return evenName;
	}
	/**
	 * @param evenName the evenName to set
	 */
	public void setEvenName(String evenName) {
		this.evenName = evenName;
	}
	

}
