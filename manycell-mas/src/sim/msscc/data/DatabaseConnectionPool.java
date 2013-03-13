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
	

	/**
	 * @return the source
	 */
	public PoolingDataSource getSource() {
		return source;
	}



	/**
	 * @param source the source to set
	 */
	public void setSource(PoolingDataSource source) {
		this.source = source;
	}



	/**
	 * @return the serverName
	 */
	public String getServerName() {
		return serverName;
	}



	/**
	 * @param serverName the serverName to set
	 */
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}



	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}



	/**
	 * @param port the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}



	/**
	 * @return the databaseName
	 */
	public String getDatabaseName() {
		return databaseName;
	}



	/**
	 * @param databaseName the databaseName to set
	 */
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}



	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}



	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
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
	 * @return the initialConnections
	 */
	public int getInitialConnections() {
		return initialConnections;
	}



	/**
	 * @param initialConnections the initialConnections to set
	 */
	public void setInitialConnections(int initialConnections) {
		this.initialConnections = initialConnections;
	}



	/**
	 * @return the maxConnections
	 */
	public int getMaxConnections() {
		return maxConnections;
	}



	/**
	 * @param maxConnections the maxConnections to set
	 */
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
