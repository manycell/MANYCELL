package sim.mssim.action.user;

import sim.mssim.action.MSSimSupportAction;

public class LogonAction extends MSSimSupportAction{
	
	private static final long serialVersionUID = 5156288255337069381L;	 	
	
	private String userName;
	private String password;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String checkUser() throws Exception  {		  

		return SUCCESS;
	}
	
}
