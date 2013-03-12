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
		
	
	//get tomecat home
	public String getTomcatHome() {
		return tomcatHome;
	}

	//get the CopasiWS address  
	public String getWebServiceAddress(){		
		String webServiceAddress = sConfig.getProperty("webServiceAddress");		
		return webServiceAddress;		
	}
	
	// get the user workspace directory
	public String getUserWorkspaceDirectory() {
		String directory = sConfig.getProperty("userWorkspaceDirectory");
		return directory;
	}
	
	//get the total resource a user is allowed to create at particular time
	public String getTotalResourceAllowed(){
		String totalResource = sConfig.getProperty("totalResourceAllowed");
		return totalResource;
	}

	// get the computer hostname where main ManyCell server is running
	public String getHostName() {
		String hostName = sConfig.getProperty("hostName");
		return hostName;
	}
	
	// get the computer hostname where main ManyCell server is running
	public String getHomeDirectory() {
		String homeDirectory = sConfig.getProperty("homeDirectory");
		return homeDirectory;
	}

	// get the user on the host computer
	public String getUser() {
		String user = sConfig.getProperty("user");
		return user;
	}

	public String getServerName() {
		return serverName;
	}

	public int getPort() {
		return port;
	}

	public String getDatabaseName() {
		return databaseName;
	}	
	

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public int getInitialConnections() {
		return initialConnections;
	}

	public int getMaxConnections() {
		return maxConnections;
	}

	public float getSimulationDuration() {
		return simulationDuration;
	}

	public int getNumberOfProcessors() {
		return numberOfProcessors;
	}

	public float getTolerance() {
		return tolerance;
	}

	public int getMaxCellGeologicalAge() {
		return maxCellGeologicalAge;
	}

	public float getStandardDeviation() {
		return standardDeviation;
	}

	public String getExecutableFile() {
		return executableFile;
	}

	public String getLibraryPath() {
		return libraryPath;
	}

	public double getAverageCellVolume() {
		return averageCellVolume;
	}	
	
	
	

}
