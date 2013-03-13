package sim.mssim.action.user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import sim.mssim.action.MSSimSupportAction;

public class RegistrationAction extends MSSimSupportAction{
	
	private static final long serialVersionUID = 5156288255337069381L;	 

	private String username;
	private String name;
	private String password;
	private String cPassword;
	private String email;	
	private String accesscode;	
	
	
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}


	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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


	/**
	 * @return the cPassword
	 */
	public String getcPassword() {
		return cPassword;
	}


	/**
	 * @param cPassword the cPassword to set
	 */
	public void setcPassword(String cPassword) {
		this.cPassword = cPassword;
	}


	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}


	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}


	/**
	 * @return the accesscode
	 */
	public String getAccesscode() {
		return accesscode;
	}


	/**
	 * @param accesscode the accesscode to set
	 */
	public void setAccesscode(String accesscode) {
		this.accesscode = accesscode;
	}


	public String register() throws Exception{		
		String url = "jdbc:postgresql://localhost:5432/unicellsys/";
		String dbName = "unicellsys";
		String driverName = "org.postgresql.Driver";
		String rUserName = "unicellsys";
		String rPassword = "";
		Connection con=null;
		Statement stmt=null;
		
		try{
			Class.forName(driverName).newInstance();
			con=DriverManager.getConnection(url+dbName, rUserName, rPassword);
			stmt=con.createStatement();
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		String uname=getUsername();
		String pws=getPassword();
		String name=getName();
		String email=getEmail();
		stmt = con.createStatement();
		int val = stmt.executeUpdate("INSERT INTO users(username, password, email, name) VALUES('"+uname+"','"+pws+"', '"+ email+"', '"+name+"')");	
		val = stmt.executeUpdate("INSERT INTO roles(username, rolename) VALUES('"+uname+"','unicellsys')");	
		Runtime r = Runtime.getRuntime();

		if(val == 0){
			return ERROR;
		}
		else{
			try{
				r.exec("/home/dada/applications/apache-tomcat-6.0.14/bin/shutdown.sh");
				r.exec("/home/dada/applications/apache-tomcat-6.0.14/bin/startup.sh");
			}catch(Exception e){
				e.printStackTrace();
			}
			return SUCCESS;
		}		
	}


}
