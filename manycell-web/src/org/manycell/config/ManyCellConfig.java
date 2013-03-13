package org.manycell.config;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ManyCellConfig {
	private Properties env, sConfig;
	private String tomcatHome = null;
	
	//databse configure
	private String serverName = null;
	private int port;
	private String databaseName = null;	
	private String userName = null;
	private String password;
	private int initialConnections;
	private int maxConnections;
	
	//Execution environment configure
	private float simulationDuration; //give total duration of multiscale simulation
	private int	numberOfProcessors; //number of processor to use for parallel execution code 
	private float tolerance; //tolerance for ISAT execution code
	private int maxCellGeologicalAge; //maximum geological cell age allow
	private float standardDeviation; //standard deviation for some cell variable that are distributed randomly
	private String executableFile; //manycell executable file
	private String libraryPath;
	
	//information about cell
	private double averageCellVolume;
	
	
	
	
	public ManyCellConfig(){		
		Process p = null;
		Runtime r = Runtime.getRuntime();
		
	 	try{
	 		//the environment variable
	 		env = new Properties();	 	
	        p = r.exec("env");	 		        
	        env.load(p.getInputStream());
	 		
	 		//get the installation directory for Tomcat server
	        tomcatHome = env.getProperty("TOMCAT_HOME");
	 		
	 		//the ManyCell Service configuration variable
	 		sConfig = new Properties();	 		
	 		FileInputStream in = new FileInputStream(tomcatHome + "/webapps/ManyCellWeb/WEB-INF/conf/manycell-config.txt");
	 		sConfig.load(in);
	 		
	 		//set the database parameters
	 		serverName = sConfig.getProperty("serverName");
	 		port = Integer.parseInt(sConfig.getProperty("port"));
	 		databaseName = sConfig.getProperty("databaseName");
	 		userName = sConfig.getProperty("userName");
	 		password =  sConfig.getProperty("password");
	 		initialConnections = Integer.parseInt(sConfig.getProperty("initialConnections"));
	 		maxConnections = Integer.parseInt(sConfig.getProperty("maxConnections"));	 		
	 		
	 		// execution environment
	 		simulationDuration = Float.parseFloat(sConfig.getProperty("simulationDuration"));
	 		numberOfProcessors = Integer.parseInt(sConfig.getProperty("numberOfProcessors"));
	 		tolerance = Float.parseFloat(sConfig.getProperty("tolerance"));
	 		maxCellGeologicalAge = Integer.parseInt(sConfig.getProperty("maxCellGeologicalAge"));
	 		standardDeviation = Float.parseFloat(sConfig.getProperty("standardDeviation"));
	 		executableFile = sConfig.getProperty("executableFile");
	 		libraryPath = sConfig.getProperty("libraryPath");
	 		
	 		averageCellVolume = Double.parseDouble(sConfig.getProperty("averageCellVolume"));
	 		env.clear();
	 		
	 		in.close();
	 		
	 	}catch (IOException e){e.printStackTrace();}
	 	
	 	finally {
			//	System.out.print("ExitValueis: " +exitValue);
			      if (p != null) {
			        close(p.getOutputStream());
			        close(p.getInputStream());
			        close(p.getErrorStream());
			        p.destroy();
			      }
			    }	
	}
	
	private static void close(Closeable c) {
	    if (c != null) {
	      try {
	        c.close();
	      } catch (IOException e) {
	        // ignored
	      }
	    }
	  }
		
	
	
	/**
	 * get tomecat home
	 * @return
	 */
	public String getTomcatHome() {
		return tomcatHome;
	}
	
	/**
	 * get the CopasiWS address 
	 * @return
	 */
	public String getWebServiceAddress(){		
		String webServiceAddress = sConfig.getProperty("webServiceAddress");		
		return webServiceAddress;		
	}	
	
	/**
	 * get the user workspace directory
	 * @return directory
	 */
	public String getUserWorkspaceDirectory() {
		String directory = sConfig.getProperty("userWorkspaceDirectory");
		return directory;
	}
	
	
	/**
	 * get the total resource a user is allowed to create at particular time
	 * @return
	 */
	public String getTotalResourceAllowed(){
		String totalResource = sConfig.getProperty("totalResourceAllowed");
		return totalResource;
	}


	/**
	 * get the computer hostname where main ManyCell server is running
	 * @return
	 */
	public String getHostName() {
		String hostName = sConfig.getProperty("hostName");
		return hostName;
	}
	
 
	/**
	 * get the computer hostname where main ManyCell server is running
	 * @return homeDirectory
	 */
	public String getHomeDirectory() {
		String homeDirectory = sConfig.getProperty("homeDirectory");
		return homeDirectory;
	}
	
	/**
	 * get the user on the host computer
	 * @return
	 */
	public String getUser() {
		String user = sConfig.getProperty("user");
		return user;
	}

	/**
	 * @return the env
	 */
	public Properties getEnv() {
		return env;
	}

	/**
	 * @param env the env to set
	 */
	public void setEnv(Properties env) {
		this.env = env;
	}

	/**
	 * @return the sConfig
	 */
	public Properties getsConfig() {
		return sConfig;
	}

	/**
	 * @param sConfig the sConfig to set
	 */
	public void setsConfig(Properties sConfig) {
		this.sConfig = sConfig;
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

	/**
	 * @return the simulationDuration
	 */
	public float getSimulationDuration() {
		return simulationDuration;
	}

	/**
	 * @param simulationDuration the simulationDuration to set
	 */
	public void setSimulationDuration(float simulationDuration) {
		this.simulationDuration = simulationDuration;
	}

	/**
	 * @return the numberOfProcessors
	 */
	public int getNumberOfProcessors() {
		return numberOfProcessors;
	}

	/**
	 * @param numberOfProcessors the numberOfProcessors to set
	 */
	public void setNumberOfProcessors(int numberOfProcessors) {
		this.numberOfProcessors = numberOfProcessors;
	}

	/**
	 * @return the tolerance
	 */
	public float getTolerance() {
		return tolerance;
	}

	/**
	 * @param tolerance the tolerance to set
	 */
	public void setTolerance(float tolerance) {
		this.tolerance = tolerance;
	}

	/**
	 * @return the maxCellGeologicalAge
	 */
	public int getMaxCellGeologicalAge() {
		return maxCellGeologicalAge;
	}

	/**
	 * @param maxCellGeologicalAge the maxCellGeologicalAge to set
	 */
	public void setMaxCellGeologicalAge(int maxCellGeologicalAge) {
		this.maxCellGeologicalAge = maxCellGeologicalAge;
	}

	/**
	 * @return the standardDeviation
	 */
	public float getStandardDeviation() {
		return standardDeviation;
	}

	/**
	 * @param standardDeviation the standardDeviation to set
	 */
	public void setStandardDeviation(float standardDeviation) {
		this.standardDeviation = standardDeviation;
	}

	/**
	 * @return the executableFile
	 */
	public String getExecutableFile() {
		return executableFile;
	}

	/**
	 * @param executableFile the executableFile to set
	 */
	public void setExecutableFile(String executableFile) {
		this.executableFile = executableFile;
	}

	/**
	 * @return the libraryPath
	 */
	public String getLibraryPath() {
		return libraryPath;
	}

	/**
	 * @param libraryPath the libraryPath to set
	 */
	public void setLibraryPath(String libraryPath) {
		this.libraryPath = libraryPath;
	}

	/**
	 * @return the averageCellVolume
	 */
	public double getAverageCellVolume() {
		return averageCellVolume;
	}

	/**
	 * @param averageCellVolume the averageCellVolume to set
	 */
	public void setAverageCellVolume(double averageCellVolume) {
		this.averageCellVolume = averageCellVolume;
	}

	/**
	 * @param tomcatHome the tomcatHome to set
	 */
	public void setTomcatHome(String tomcatHome) {
		this.tomcatHome = tomcatHome;
	}	

}
