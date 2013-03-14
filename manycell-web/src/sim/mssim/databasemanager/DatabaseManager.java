package sim.mssim.databasemanager;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.manycell.config.ManyCellConfig;

public class DatabaseManager {
	private Statement stmt = null;
//	private Connection con = null;
	public Statement getStatement(){		
		return stmt;
	}
	/*public Connection getConnection(){
		return con;
	}*/
	public Connection getConnection() throws Exception{
		Connection con = null;
		String url = "jdbc:postgresql://localhost:5432/unicellsys/";
		String dbName = "unicellsys";
		String driverName = "org.postgresql.Driver";
		String rUserName = "unicellsys";
		String rPassword = "";
	//	Connection con=null;
		
	/*	ManyCellConfig config = new ManyCellConfig();
		String url = "jdbc:postgresql://"+config.getServerName()+":"+ config.getPort()+"/"+config.getDatabaseName()+"/";
		String dbName = config.getDatabaseName();
		String driverName = "org.postgresql.Driver";
		String rUserName = config.getUserName();
		String rPassword = config.getPassword();
		Connection con=null;		*/
		try{
			Class.forName(driverName).newInstance();
			con=DriverManager.getConnection(url+dbName, rUserName, rPassword);
		//	stmt = con.createStatement();
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		return con;
	}
	
/*		String uname=getUsername();
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
	}*/
		
	public String processTreeData(){
		StringBuffer buff = new StringBuffer();
		Statement stmt=null;
		try{
		this.getStatement();
		}catch (Exception e){
			System.out.println(e.getMessage());			
		}
		buff.append("[\n");
		buff.append("{\"title\": \"Cells\"},");
		
		buff.append("]");
		return null;
	}
	
	public StringBuffer processChildren(String parentId){
		StringBuffer buff = new StringBuffer();
		
		return buff;
		
	}
	public JSONObject createTreeNode(){
		JSONObject node = new JSONObject();
		node.put("title", "Mother Cell 1");
		node.put("title", "Mother Cell 2");
		
		return node;
	}
	
	public JSONObject createObject(String title, String name){
		JSONObject obj = new JSONObject();
		obj.put(title, name);			
		return obj;
	}
	
	public JSONObject createObjectWithArray(String title, JSONObject leftValue, JSONObject rightValue){
		JSONArray list = new JSONArray();
		
		JSONObject obj = new JSONObject();		
		obj.put(title, leftValue);	
		obj.put(title, rightValue);
		return obj;
	}
	
	public JSONObject createObjectArray(){
		JSONArray list = new JSONArray();

		JSONObject obj = new JSONObject();
		obj.put("title", "Cells");
		obj.put("isFolder", true);
	//	obj.put("key", "DCells");
		
	//	JSONObject parent = null;
		for(int i=0; i<8; i++){
			String daughterId = retrieveCellAgentData("CAID"+i, 1);
			if(daughterId!=null){
				String motherId = "CAID"+i;
				int motherAge = 1;
				JSONObject obj1 = new JSONObject();
			//	obj1.put("title", "Cell " +motherId.substring(4)+ " Age "+motherAge);
				
				list.add(this.createTree(motherId, motherAge, daughterId, obj1));
			//	list.add(addChildren(motherId, motherAge, daughterId, obj1));
		//		parent = new JSONObject();
			//	parent.put("children", createTreeLeave("CAID"+i, 1, daughterId));
			//	list.add(parent);
		//		list.add("test"+i);
		//	list.add(createTreeLeave("CAID"+i, 1, daughterId));
		//	list.add(createTreeLeave("CAID"+i, 1, daughterId));
			}
		}		
	//	System.out.print(list+" End of list\n");
		
		obj.put("children", list);	
		
		obj.put("key", "DCells");
		
		//testing the output
		try {
			FileWriter file = new FileWriter("test.json");
			file.write(obj.toString());
			file.flush();
			file.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.print(obj); 

		return obj;
	}
	
	public JSONObject addChildren(String motherId, int motherAge, String daughterId, JSONObject parent ){
		JSONObject motherLeave = new JSONObject();
		JSONObject daughterLeave = new JSONObject();	
				
		motherLeave.put("title", "Cell " +motherId.substring(4)+ " Age "+motherAge);
		daughterLeave.put("title", "Cell " + daughterId.substring(4)+" Age "+0);	
		
//		int i = 0;
		//	String id = this.retrieveCellAgentData(motherId, i);
			String motherDId = this.retrieveCellAgentData(motherId, motherAge);
			String daughterDId = this.retrieveCellAgentData(daughterId, 1);
		//	motherAge++;
			
		//	if(motherDId!=null&& daughterDId!=null){
			if(motherDId!=null){
				motherLeave = addChildren(motherId, motherAge++, motherDId, motherLeave);
			}
			
			if(daughterDId!=null){
				daughterLeave = addChildren(daughterId, 1, daughterDId, daughterLeave);
			}
		
		JSONArray list = new JSONArray();
		list.add(motherLeave);
		list.add(daughterLeave);
		
		parent.put("title", "Cell "+ motherId.substring(4) +" Age "+motherAge);
		parent.put("isFolder", true);
		parent.put("key", motherId+motherAge);
		
		parent.put("children", list);
					
		return parent;
	}
	
	public JSONObject createTree(String motherId, int motherAge, String daughterId, JSONObject parent  ){		
		JSONObject motherLeave = new JSONObject();
		JSONObject daughterLeave = new JSONObject();
				
		motherLeave.put("title", "Cell " +motherId.substring(4)+ " Age "+motherAge);
		daughterLeave.put("title", "Cell " + daughterId.substring(4)+" Age "+0);		
			
		JSONArray list = new JSONArray();
		list.add(motherLeave);
		list.add(daughterLeave);
		
		motherAge--;
		parent.put("title", "Cell "+ motherId.substring(4) +" Age "+motherAge);
		parent.put("isFolder", true);
		parent.put("key", motherId+motherAge);
		
	//	parent.put("children", list);		
		
		return parent;
	}
	
	public JSONObject createTreeLeave(String motherId, int motherAge, String daughterId ){
		JSONObject motherLeave = new JSONObject();
		JSONObject daughterLeave = new JSONObject();
		JSONObject leave = new JSONObject();
		
		motherLeave.put("title", "Cell " +motherId.substring(4)+ " Age "+motherAge);
		daughterLeave.put("title", "Cell " + daughterId.substring(4)+" Age "+0);		
		
	//	int i = 0;
	//	String id = this.retrieveCellAgentData(motherId, i);
		String motherDId = this.retrieveCellAgentData(motherId, motherAge);
		String daughterDId = this.retrieveCellAgentData(daughterId, 1);
	//	motherAge++;
		
	//	if(motherDId!=null&& daughterDId!=null){
		if(motherDId!=null){
			motherLeave = addChildren(motherId, motherAge, motherDId, motherLeave);
		}
		
		if(daughterDId!=null){
			daughterLeave = addChildren(daughterId, 1, daughterDId, daughterLeave);
		}
	//	}
		
		JSONArray list = new JSONArray();
		list.add(motherLeave);
		list.add(daughterLeave);
		
		leave.put("title", "Cell "+ motherId.substring(4) +" Age "+motherAge);
		leave.put("isFolder", true);
		leave.put("key", motherId+motherAge);
		
		leave.put("children", list);		
		
		return leave;
	}

public String retrieveCellAgentData(String motherId) {
	//	Connection conn = null;
		StringBuffer buff = new StringBuffer();
		Statement stmt = null;
		String id = null;
		try {
			stmt = this.getConnection().createStatement();
			ResultSet rset = stmt.executeQuery("SELECT caid FROM cellagents where motherid='"+motherId+"'");			
			while(rset.next()){				
				id = rset.getString(1);				 
			}
			System.out.print(buff.toString());
			//release the resource
			stmt.close();
		//	conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}		

		return id;
	}
	
	/**
	 * @param motherId, cell mother id
	 * @param motherAgeAtBirth, cell mother age at birth of daughter cell
	 * @return daughterId, the id of the daughter
	 */
	public String retrieveCellAgentData(String motherId, int motherAgeAtBirth) {
	//	Connection conn = null;
		StringBuffer buff = new StringBuffer();
		Statement stmt = null;
		String daughterId = null;
		try {
			stmt = this.getConnection().createStatement();
			ResultSet rset = stmt.executeQuery("SELECT motherid, caid, motherageatbirth, gage FROM cellagents ORDER BY gage DESC, motherageatbirth, motherid");			
			
		//	ResultSet rset = stmt.executeQuery("SELECT caid FROM cellagents where motherid='"+motherId+"' AND motherageatbirth="+motherAgeAtBirth);			
			while(rset.next()){
				buff.append(rset.getString(1)+"\t"+rset.getString(2)+"\t"+rset.getInt(3)+"\t"+rset.getInt(4)+"\n");
			//	daughterId = rset.getString(1);				 
			}
			System.out.print(buff.toString());
			//release the resource
	//		stmt.close();
		//	conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}		

		return daughterId;
	}

}
