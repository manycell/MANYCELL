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
	
	public String getcPassword() {
		return cPassword;
	}
	public void setcPassword(String cPassword) {
		this.cPassword = cPassword;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	

	public String getAccesscode() {
		return accesscode;
	}
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
