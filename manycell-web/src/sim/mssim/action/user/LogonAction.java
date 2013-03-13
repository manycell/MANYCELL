package sim.mssim.action.user;

import sim.mssim.action.MSSimSupportAction;

public class LogonAction extends MSSimSupportAction{
	
	private static final long serialVersionUID = 5156288255337069381L;	 	
	
	private String userName;
	private String password;	
	
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}



	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}



	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}



	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}



	public String checkUser() throws Exception  {		  

		return SUCCESS;
	}
	
}
