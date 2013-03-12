package sim.msscc.data;

import java.sql.Connection;
import java.sql.SQLException;

import org.postgresql.ds.common.BaseDataSource;
import org.postgresql.jdbc2.optional.ConnectionPool;
import org.postgresql.jdbc2.optional.PoolingDataSource;
//import org.postgresql.jdbc2.optional.
import javax.sql.ConnectionPoolDataSource;
public class DatabaseConnectionPool {	
	PoolingDataSource source = new PoolingDataSource();	
	
	//database connection parameters
	private String serverName = "localhost";
	private int port = 5432;
	private String databaseName = null;	
	private String user = null;
	private String password;
	private int initialConnections;
	private int maxConnections;
	
	//initialise the pool
	public void initPool(){
		//source.setDataSourceName(dataSourceName);
		source.setServerName(serverName);
		source.setDatabaseName(databaseName);
		source.setUser(user);	
		source.setPortNumber(port);
		source.setPassword(password);
		source.setMaxConnections(maxConnections);
		source.setInitialConnections(initialConnections);			
	}
	
	
	public String getServerName() {
		return serverName;
	}


	public void setServerName(String serverName) {
		this.serverName = serverName;
	}


	public int getPort() {
		return port;
	}


	public void setPort(int port) {
		this.port = port;
	}


	public String getDatabaseName() {
		return databaseName;
	}


	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}


	public String getUser() {
		return user;
	}


	public void setUser(String user) {
		this.user = user;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public int getInitialConnections() {
		return initialConnections;
	}


	public void setInitialConnections(int initialConnections) {
		this.initialConnections = initialConnections;
	}


	public int getMaxConnections() {
		return maxConnections;
	}


	public void setMaxConnections(int maxConnections) {
		this.maxConnections = maxConnections;
	}


	//close the pool
	public void closePool(){		
	source.close();
	}
	
	public void closeConnection(Connection con){
		if(con!=null){
			try {				
				con.close();			
			} catch (Exception e) {
				e.printStackTrace();

			}	
		}	
	}
	
	//get connection from the pool
	public Connection getCoonection(){
		Connection con = null;
		try {
			con = source.getConnection();			
			// use connection
		} catch (SQLException e) {
			e.printStackTrace();			
		} /*finally {
			if (con != null) {
				try { con.close(); } catch (SQLException e) {}
			}
		}*/
		return con;

	}

}
