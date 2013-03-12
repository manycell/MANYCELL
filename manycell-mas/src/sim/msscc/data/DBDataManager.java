package sim.msscc.data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.sql.*;
//import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import org.manycell.config.DatabaseDocument.Database;
import org.postgresql.jdbc2.optional.ConnectionPool;

/**
 * @author dada
 *
 */
/**
 * @author dada
 *
 */
public class DBDataManager implements DataManagerInterface {
	
	private Connection connection;
	private Statement stmt;
	private DatabaseConnectionPool pool;
	private boolean usePool = false;
	boolean isData = false;
	boolean isLocal  = false;
	private Database database = null;
	
	public void initPool(DatabaseConnectionPool pool, boolean usePool){
	//	pool.initPool();
		this.usePool = usePool;
		this.pool = pool;
	}

	public Statement getStmt() {
		return stmt;
	}

	public void setStmt(Statement stmt) {
		this.stmt = stmt;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public int storeTimeCourseResult(String userId, String CAID,
			String simulationRun, String resultString, String resultMetadata) {
		return 0;
	}
	
	
	public Database getDatabase() {
		return database;
	}

	public void setDatabase(Database database) {
		this.database = database;
	}

	public boolean isLocal() {
		return isLocal;
	}

	public void setLocal(boolean isLocal) {
		this.isLocal = isLocal;
	}

	public boolean isData() {
		return isData;
	}

	public void setData(boolean isData) {
		this.isData = isData;
	}

	public int storeTimeCourseResult(String resultString) {
		int storeTCID = 0;
		try{
			stmt = this.getConnection().createStatement();
			stmt.executeUpdate("INSERT INTO timecoursedata (tcdata) VALUES('"+ resultString+"')");		
			ResultSet rset = stmt.executeQuery("SELECT MAX(tcid) FROM timecoursedata");			
			while(rset.next()){
				storeTCID = rset.getInt(1);
				System.out.println(storeTCID);
			}
			
			//release the resource
			stmt.close();

		}catch(Exception e){
			e.printStackTrace();

		}		

		return storeTCID;
	}

	public int storeModel(String userId, String CAID, String simulationRun,
			String modelString, String format, boolean isUpdatedModel) {
		return 0;
	}

	public String loadModel(String userId, String CAID, String simulationRun,
			String format, boolean updatedModelRequire) {
		return null;
	}

	public String retrieveTimeCourseResult(String userId, String CAID,
			String simulationRun) {
		try {
			stmt = this.getConnection().createStatement();
			ResultSet rset = stmt.executeQuery("SELECT * FROM timecoursedata");
			while(rset.next()){
				System.out.println(rset.getString("tcdata"));
			}
			//release the resource
			stmt.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see sim.msscc.data.DataManagerInterface#retrieveEventData(java.lang.String, java.lang.String, float)
	 */
	public EventData retrieveEventData(String CAID, String event, float simTime) {
		EventData eventData = null;
		try {
			stmt = this.getConnection().createStatement();
			ResultSet rset = stmt.executeQuery("SELECT eventtime, stateatevent, tcid FROM eventdata WHERE event='"+event+
					"' AND gtime="+simTime+ " AND caid='"+CAID +"'");
			while(rset.next()){
				eventData = new EventData(simTime,rset.getFloat("eventtime"), event, rset.getString("stateatevent"), CAID, rset.getInt("tcid"));
				eventData.setAgentgAge(rset.getInt("age"));
				System.out.println("Division==="+rset.getFloat("eventtime"));
			}
			//release the resource
			stmt.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return eventData;
	}
	
	public List <EventData> retrieveEventData(String event, float simTime) {
		List <EventData> eventData = new ArrayList();
		try {
			//this.getConnection()
			stmt = this.getConnection().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			ResultSet rset = stmt.executeQuery("SELECT * FROM eventdata WHERE event='"+event+
					"' AND gtime="+simTime);
			
			while(rset.next()){
				EventData eData = new EventData(simTime,rset.getFloat("eventtime"), event,rset.getString("stateatevent"), rset.getString("caid"),rset.getInt("tcid"));
				eventData.add(eData);
				System.out.println("Division==="+rset.getFloat("eventtime"));				
			}
			//release resource
			stmt.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return eventData;
	}
	
	public ResultSet fetchEventData(String event, float simTime, int fetchSize) {
		ResultSet rset = null;
		try {
			connection = this.getConnection();
			stmt = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			connection.setAutoCommit(false);
			stmt.setFetchSize(fetchSize);
			rset = stmt.executeQuery("SELECT * FROM eventdata WHERE event='"+event+
					"' AND gtime="+simTime);
			
		/*	while(rset.next()){
				EventData eData = new EventData(simTime,rset.getFloat("eventtime"), event,rset.getString("stateatevent"), rset.getString("caid"),rset.getInt("tcid"));
				eventData.add(eData);
				System.out.println("Division==="+rset.getFloat("eventtime"));				
			}*/
			//release resource
		//	stmt.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rset;
	}
	
	//get event data at particular simulation time
	public ResultSet fetchEventData(float simTime, int fetchSize) {
		ResultSet rset = null;
		try {
			connection = this.getConnection();
			stmt = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			connection.setAutoCommit(false);
			stmt.setFetchSize(fetchSize);
			rset = stmt.executeQuery("SELECT * FROM eventdata WHERE gtime="+simTime);
			
		/*	while(rset.next()){
				EventData eData = new EventData(simTime,rset.getFloat("eventtime"), event,rset.getString("stateatevent"), rset.getString("caid"),rset.getInt("tcid"));
				eventData.add(eData);
				System.out.println("Division==="+rset.getFloat("eventtime"));				
			}*/
			//release resource
		//	stmt.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rset;
	}
	
	public EventData fetchEventData(String event, float simTime, String CAID) {
		EventData eventData = null;
		try {
			stmt = this.getConnection().createStatement();
			ResultSet rset = stmt.executeQuery("SELECT * FROM eventdata WHERE event='"+event+
					"' AND gtime="+simTime+ " AND caid='"+CAID +"'");
			while(rset.next()){
				eventData = new EventData(simTime, rset.getString("caid"));
				eventData.setEventName("division");
				eventData.setStateAtEvent(rset.getString("stateatevent"));
				eventData.setEventTime(rset.getFloat("eventtime"));
				eventData.setCycleTime(rset.getFloat("cycletime"));				
			}
			//release the resource
			stmt.close();			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return eventData;
	}
	//get event data at particular simulation time
	public ResultSet fetchStateData(float simTime, int fetchSize) {
		ResultSet rset = null;
		try {
			connection = this.getConnection();
			stmt = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			connection.setAutoCommit(false);
			stmt.setFetchSize(fetchSize);
			rset = stmt.executeQuery("SELECT * FROM cellstates WHERE gtime="+simTime);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rset;
	}

	public int storeEventData(String CAID, String event, float eventTime,
			float globalTime, String stateatevent, float GLUconsumed, int TCID) {

		try {
			stmt = this.getConnection().createStatement();
			stmt.executeUpdate("INSERT INTO eventdata (caid, event, eventtime, gtime, stateatevent, glucoseconsumed, tcid)" +
					"VALUES('"+CAID+"','"+event+"',"+eventTime+","+globalTime+",'" + stateatevent +"'," +GLUconsumed +","+ TCID+")");
			//release the resource
			stmt.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}		

		return 0;
	}
	
	//storing the simulator state only
	//public int storeCellState(String CAID, float globalTime, String simState, float GLUconsumed, int stepCounter, String isDivided, int TCID) {
		public int storeCellState(String CAID, float globalTime, String simState, float GLUconsumed, float eventTime, int stepCounter, int gAge, String isDivided) {

		try {
			stmt = this.getConnection().createStatement();
			stmt.executeUpdate("INSERT INTO cellstates (caid, gtime, state, glucoseconsumed, eventtime, stepcounter, gage, isdivided)" +
					"VALUES('"+CAID+"',"+globalTime+",'" + simState +"'," +GLUconsumed +"," +eventTime+","+stepCounter+"," + gAge+ ",'"+isDivided +"')");
			//release the resource
			stmt.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}		

		return 0;
	}	
	
	//storing event state and simulator state
	public int storeEventData(String CAID, String event, float eventTime,
			float globalTime, String stateatevent,float cycleTime, String type, float cellSize, float gAge) {
		try {
			stmt = this.getConnection().createStatement();
			stmt.executeUpdate("INSERT INTO eventdata (caid, event, eventtime, gtime, stateatevent, cycletime, type, size, gage)" +
					"VALUES('"+CAID+"','"+event+"',"+eventTime+","+globalTime+",'" + stateatevent +"', "+cycleTime +",'" +type +"',"+ cellSize + "," + gAge +")");
			//release the resource
			stmt.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}		

		return 0;
	}
	
	
	//storing event state and simulator state
	public int storeEventData(String CAID, String event, float eventTime,
			float globalTime, String stateatevent, String simState, float GLUconsumed, float cycleTime, String type, float cellSize, int gAge, int TCID) {

		try {
			stmt = this.getConnection().createStatement();
			stmt.executeUpdate("INSERT INTO eventdata (caid, event, eventtime, gtime, stateatevent, simState, glucoseconsumed, cycletime, type, size, gage, tcid)" +
					"VALUES('"+CAID+"','"+event+"',"+eventTime+","+globalTime+",'" + stateatevent +"', '"+simState +"'," +GLUconsumed +","+cycleTime +",'" +type +"',"+ cellSize +","+ gAge+ "," + TCID+")");
			//release the resource
			stmt.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}		

		return 0;
	}
	
	public int storeAgentData(String CAID, float birthSize, int gAge, String motherId, int motherAgeAtBirth) {

		try {
			stmt = this.getConnection().createStatement();
			stmt.executeUpdate("INSERT INTO cellagents (caid, birthsize, gage, motherid, motherAgeAtBirth) VALUES('"+CAID+"',"+birthSize+","+gAge+ ",'"+motherId+"'," +motherAgeAtBirth +")");
			//release the resource
			stmt.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}		

		return 0;
	}
	
	//store the result summary for adaptive multi-scale method
	public int storeAResultSummary(double executionTime, float gTime, double availableGLU, double consumedGLU, int totalAgents) {

		try {
			stmt = this.getConnection().createStatement();
			stmt.executeUpdate("INSERT INTO aresultsummary (executiontime, gtime, availableglu, consumedglu, totalagents) VALUES("+executionTime+","+gTime+","+availableGLU +"," + consumedGLU +","+totalAgents+ ")");
			
			//release the resource
			stmt.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}		

		return 0;
	}
	
	//store the observation data
	public int storeObservations(String CAID, String []variables, float[][] values, float []fastTime, float gTime) {
		int row = values.length;
		int col = values[0].length;
		
		try {
			stmt = this.getConnection().createStatement();
			for(int i=0; i<row; i++){
				float time = fastTime[i]+gTime;				
				for(int j=0;j<col; j++){
					stmt.executeUpdate("INSERT INTO observations (caid, variableid, value, gTime) VALUES('"+CAID+"','"+variables[j]+"',"+values[i][j]+","+time+ ")");
					
					
				}
			}
			//release the resource
			stmt.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}		

		return 0;
	}
	
	public int updateAgentData(String CAID, float gAge) {

		try {
			stmt = this.getConnection().createStatement();
			stmt.executeUpdate("UPDATE cellagents SET gage ="+gAge+" where caid='"+ CAID+"'");
			//release the resource
			stmt.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}		

		return 0;
	}	
	
	public int retrieveAgentAge(String CAID) {
		int cellAge = 0;
		try {
			stmt = this.getConnection().createStatement();			
			ResultSet rset = stmt.executeQuery("SELECT gage FROM cellagents where caid='"+CAID+"'");			
			while(rset.next()){
				cellAge = rset.getInt(1);				 
			}
			//release the resource
			stmt.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}		

		return cellAge;
	}
	
	public int retrieveAgentGeneration(String CAID) {
		int generation = 0;
		try {
			stmt = this.getConnection().createStatement();			
			ResultSet rset = stmt.executeQuery("SELECT generation FROM cellagents where caid='"+CAID+"'");			
			while(rset.next()){
				generation = rset.getInt(1);				 
			}
			//release the resource
			stmt.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}		

		return generation;
	}
	
	public int retrieveMaxAgentGeneration() {
		int generation = 0;
		try {
			stmt = this.getConnection().createStatement();			
			ResultSet rset = stmt.executeQuery("SELECT MAX(generation) FROM cellagents");			
			while(rset.next()){
				generation = rset.getInt(1);				 
			}
			//release the resource
			stmt.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}		

		return generation;
	}	
	
	/*public void closeConnection(){		
		try {			
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();

		}		
	}*/
	
	public void cleanUp(){	
		if(stmt!=null){
			try {	
				stmt.close();				
			} catch (Exception e) {
				e.printStackTrace();

			}	
		}			
		/*if(connection!=null){
			try {				
				connection.close();			
			} catch (Exception e) {
				e.printStackTrace();

			}	
		}	*/	
	}
		
	public List<Integer> getDistribution(float beginGTime, float stepSize, float endGTime) {	
		List<Integer> list = new ArrayList();	
		
		try{
			stmt = this.getConnection().createStatement();	
			for(beginGTime=0.0f; beginGTime<=endGTime;beginGTime=beginGTime+stepSize){
			int count = 0;							
			ResultSet rset = stmt.executeQuery("SELECT COUNT(eventid) FROM eventdata where gTime="+beginGTime);			
			while(rset.next()){
				count = rset.getInt(1);
				list.add(count);
				//System.out.println(count);
			}
			}
			
			//release the resource
			stmt.close();

		}catch(Exception e){
			e.printStackTrace();

		}		

		return list;
	}
	
	public List<Float> getGlucoseConsumedDistribution(float beginGTime, float stepSize, float endGTime) {	
		List<Float> list = new ArrayList();	
	//	System.out.println(stepSize);
		try{
			stmt = this.getConnection().createStatement();	
			for(float startGTime = 0; startGTime<=endGTime; startGTime=startGTime+stepSize){
				System.out.println(startGTime);
				float sum = 0;							
				ResultSet rset = stmt.executeQuery("SELECT SUM(glucoseconsumed), gtime FROM cellstates where gtime="+startGTime+ "GROUP BY gtime");			
				while(rset.next()){
					sum = rset.getFloat(1);
					list.add(sum);
					System.out.println(rset.getFloat(2));
				}
			}

			//release the resource
			stmt.close();

		}catch(Exception e){
			e.printStackTrace();

		}		

		return list;
	}

	//calculate the sum of glucose consumed in a time step
	public float getSumOfGLUConsumed(float gTime) {		
		float sum = 0;			
		try{
			stmt = this.getConnection().createStatement();							
			//ResultSet rset = stmt.executeQuery("SELECT SUM(glucoseconsumed) FROM eventdata where gTime="+gTime);			
			ResultSet rset = stmt.executeQuery("SELECT SUM(glucoseconsumed) FROM cellstates where gTime="+gTime);			
			
			while(rset.next()){
				sum = rset.getFloat(1);		
			}

			//release the resource
			stmt.close();

		}catch(Exception e){
			e.printStackTrace();

		}		

		return sum;
	}
	
	public List<Float> getGLUConsumedEvolution(float lowerGTime, float interval, float gTime) {	
		List<Float> list = new ArrayList();	
		
		try{
			stmt = this.getConnection().createStatement();	
			//float iter = lowerDTime;
			for(float iter = lowerGTime; iter<=gTime; iter=iter+interval){												
			float sum = 0;			
			ResultSet rset = stmt.executeQuery("SELECT SUM(glucoseconsumed) FROM  cellstates where gtime="+iter);			
			
			
			while(rset.next()){
				sum = rset.getFloat(1);
				list.add(sum);
			//	System.out.println(sum);
			}
			}
			
			//release the resource
			stmt.close();

		}catch(Exception e){
			e.printStackTrace();

		}		

		return list;
	}
	
	//calculate the sum of glucose consumed in a time step
/*	public int getEventTimeCellDistribution(float lowerEventTime, float upperEventTime) {		
		int cellCounter = 0;			
		try{
			stmt = this.getConnection().createStatement();							
			ResultSet rset = stmt.executeQuery("SELECT SUM(caid) FROM eventdata where eventtime>="+lowerEventTime +"AND eventtime<="+lowerEventTime);			
			while(rset.next()){
				cellCounter = rset.getInt(1);		
			}

			//release the resource
			stmt.close();

		}catch(Exception e){
			e.printStackTrace();

		}		

		return cellCounter;
	}*/
	
	
	public Hashtable<String, Integer> getCellDistribution(int totalCell) {	
		Hashtable<String, Integer> list = new Hashtable<String, Integer>();	
		
		try{
			stmt = this.getConnection().createStatement();	
			for(int i = 0; i<totalCell; i++){
			int count = 0;
			String agentId = "CAID"+i;						
			ResultSet rset = stmt.executeQuery("SELECT COUNT(caid) FROM eventdata where caid='"+agentId+"'");			
			while(rset.next()){
				count = rset.getInt(1);
				list.put(agentId, count);
				//System.out.println(count);
			}
			}
			
			//release the resource
			stmt.close();

		}catch(Exception e){
			e.printStackTrace();

		}		

		return list;
	}	
	
	//cell division distribution
	public List<Integer> getCellDivisionDistribution(int maxDivision) {	
		List<Integer>list = new ArrayList();
		
		try{
			stmt = this.getConnection().createStatement();	
			for(int i = 0; i<=maxDivision; i++){
			int count = 0;	
			ResultSet rset = stmt.executeQuery("SELECT COUNT(caid) FROM cellagents where gage="+i);	
					
			while(rset.next()){
				count = rset.getInt(1);
				list.add(count);
				//System.out.println(count);
			}
			}
			
			//release the resource
			stmt.close();

		}catch(Exception e){
			e.printStackTrace();

		}		

		return list;
	}	
	
	/**
	 * get cell division distribution for specific global time
	 * @param maxDivision, maximum cell division
	 * @param gTime, specific simulation time 
	 * @return list, the list containing the distribution
	 */
	public List<Integer> getCellDivisionDistribution(int maxDivision, float gTime) {	
		List<Integer>list = new ArrayList<Integer>();
		
		try{
			stmt = this.getConnection().createStatement();
			StringBuffer contents = new StringBuffer();
			for(int i = 0; i<=maxDivision; i++){
			int count = 0;	
			ResultSet rset = stmt.executeQuery("SELECT COUNT(caid) FROM cellstates where gage="+i +"AND gtime<="+gTime);				 
			
			while(rset.next()){
				count = rset.getInt(1);
				contents.append(count);
				contents.append("\n");
			/////	list.add(count);
				
			///	System.out.println(count);
			}		
		  
			}
			//use buffering
		    FileWriter output = new FileWriter(new File("result.txt"));		     
		      output.write(contents.toString());		  
		      output.close();
			
			//release the resource
			stmt.close();

		}catch(Exception e){
			e.printStackTrace();

		}		

		return list;
	}
	
	/**
	 * get and save cell division distribution	 
	 */
	public void saveCellAgeDistribution(String dir, String dataFileName) {	
		String fileName = dir+"/" +dataFileName;
		File file = new File(fileName);		
		try{
			int maxAge = 0;
			stmt = this.getDataProcessConnection().createStatement();
			ResultSet rset = stmt.executeQuery("SELECT MAX(gage) FROM cellagents");
			if(rset.next()){
				maxAge = rset.getInt("gage");
			}
			StringBuffer buff = new StringBuffer();
			for(int i = 0; i<=maxAge; i++){
		
			rset = stmt.executeQuery("SELECT COUNT(caid) FROM cellagents where gage="+i);				 
			
			while(rset.next()){	
				buff.append(i+"\t");
				buff.append(rset.getInt(1));
				buff.append("\n");			
			}		
		  
			}
			//use buffering		 
		    this.writeToFile(file, buff.toString());
		    
			//release the resource
			stmt.close();

		}catch(Exception e){
			e.printStackTrace();

		}	
		
	}
	
	
	/**String 
	 * @param gTime
	 * @param totalCell
	 * @return
	 */
	public Hashtable<String, Integer> getCellAgeDistribution(float gTime, int totalCell) {	
		Hashtable<String, Integer> list = new Hashtable<String, Integer>();	
		
		try{
			stmt = this.getConnection().createStatement();	
			for(int i = 0; i<totalCell; i++){
			int count = 0;
			String agentId = "CAID"+i;						
			ResultSet rset = stmt.executeQuery("SELECT COUNT(caid) FROM eventdata where caid='"+agentId+"' AND gTime<="+gTime);			
			while(rset.next()){
				count = rset.getInt(1);
				list.put(agentId, count);
				//System.out.println(count);
			}
			}
			
			//release the resource
			stmt.close();

		}catch(Exception e){
			e.printStackTrace();

		}		

		return list;
	}
	
	public Connection getDataProcessConnection(){
		Connection con = null;
			String url = null; 
			Properties props = new Properties();
			// database connection paramters		
				url = "jdbc:postgresql://"+this.database.getServerName()+":5432/"+this.database.getDatabaseName();						
				props.setProperty("user", this.database.getUserName());
				props.setProperty("password", this.database.getPassword()); 
				
			try {			
				Class.forName("org.postgresql.Driver");
				con = DriverManager.getConnection(url, props);			
			
			} catch (Exception e) {
				e.printStackTrace();

			}				
		return con;
		}
	
	public Connection getConnection(){		
/*	if(this.isData){
		String url = null; 
		Properties props = new Properties();
		// database connection paramters
	//	String url = "jdbc:postgresql://dada.mib.man.ac.uk:5432/unicellsys";
		String hostName = "130.88.212.174"; //bernie.mib.manchester.ac.uk
		if(!isLocal){
			url = "jdbc:postgresql://"+hostName+":5432/unicellsys";//			
			props.setProperty("user", "postgres");
			props.setProperty("password", ""); 
		}
		if(isLocal){
			url = "jdbc:postgresql://localhost:5432/unicellsys";		
			props.setProperty("user", "unicellsys");	
			props.setProperty("password", "test_unicellsys");
		}
		// props.setProperty("ssl","true");	
		try {
		//	if(usePool){
		//		connection = pool.getCoonection(); ;
		//	}else{
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(url, props);			
		//	}
		} catch (Exception e) {
			e.printStackTrace();

		}
	}	*/	
		/*	//connection to mysql
		try{
		Class.forName("com.mysql.jdbc.Driver");
		String urlm = "jdbc:mysql://localhost:3306/mysql";		
		Connection con =  DriverManager.getConnection(urlm,"root", "");
		System.out.println("URL: " + urlm);
	    System.out.println("Connection: " + con);
	    
	    Statement stmt = con.createStatement();


		}catch(Exception e){
			
		}*/
		
		// String url =
		// "jdbc:postgresql://localhost/test?user=fred&password=secret&ssl=true";
		// Connection conn = DriverManager.getConnection(url1);
		
		return connection;
	}
	
	
	/**
	 * @param agentId
	 * @param variableid
	 * @param timeStep
	 * @param maxTime
	 * @return
	 */
	public List<Float>	 getCellVariableTimeEvolution(String agentId, String variableid, float timeStep, float maxTime) {	
		List<Float> list = new ArrayList<Float>();		
		float value = 0.0f;
		try{
			stmt = this.getDataProcessConnection().createStatement();	
			for(float stepTime = 0; stepTime<maxTime; stepTime=stepTime+timeStep){								
		//	ResultSet rset = stmt.executeQuery("SELECT value, gtime FROM observations where caid='"+agentId+"'AND variableid='"+variableid+"' AND gTime="+stepTime +"GROUP BY gtime, value");			
			ResultSet rset = stmt.executeQuery("SELECT value, gtime FROM observations where caid='"+agentId+"'AND variableid='"+variableid+"' AND gTime="+stepTime);			

	//			ResultSet rset = stmt.executeQuery("SELECT value, gtime FROM observations where caid='"+agentId+"'AND variableid='"+variableid+"' AND gTime="+stepTime +"GROUP BY gtime");			
			
		//	ResultSet rset = stmt.executeQuery("SELECT value FROM caid0 where gTime="+stepTime);		
			
	//		ResultSet rset = stmt.executeQuery("SELECT AVG(value) FROM observations where variableid='"+variableid+"' AND gTime="+stepTime +"GROUP BY gtime");			
		//	ResultSet rset = stmt.executeQuery("SELECT STDDEV(value) FROM observations where variableid='"+variableid+"' AND gTime="+stepTime +"GROUP BY gtime");			
					
//			
	//	ResultSet rset = stmt.executeQuery("SELECT AVG(value), gtime FROM observations where variableid='"+variableid+"' AND gTime="+stepTime +"GROUP BY gtime, value");			
	//		ResultSet rset = stmt.executeQuery("SELECT SUM(value), gtime FROM observations where variableid='"+variableid+"' AND gTime="+stepTime +"GROUP BY gtime");			
		//	
			//			ResultSet rset = stmt.executeQuery("SELECT SUM(value) FROM observations where variableid='"+variableid+"' AND gTime="+stepTime +"GROUP BY gtime");
			while(rset.next()){
				value = rset.getFloat(1);
			//	value = rset.getFloat(1);
			/*	System.out.print(rset.getFloat(1)+"\t");
				System.out.println(rset.getInt(2));
				System.out.println("\n");*/
				list.add(value);
				//System.out.println(count);
			}
			}
			
			//release the resource
			stmt.close();

		}catch(Exception e){
			e.printStackTrace();

		}		

		return list;
	}
	
	/**
	 * @param agentId
	 * @param variableid
	 * @param timeStep
	 * @param maxTime
	 * @return
	 */
	public void	saveCellVariableTimeEvolution(String variableId, int noOfAgents, float timeStep, float maxTime, String dir, String dataFileName) {
		String fileName = dir+"/" +dataFileName;
		File file = new File(fileName);
		
		
		StringBuffer buff = new StringBuffer();	
		
		
		float value = 0.0f;
		try{
			stmt = this.getDataProcessConnection().createStatement();	
			for(float stepTime = 0; stepTime<maxTime; stepTime=stepTime+timeStep){	
				float stddev =0.0f, avg=0.0f;
				//get the standard deviation
				ResultSet rsetStd = stmt.executeQuery("SELECT STDDEV(value) FROM observations where variableid='"+variableId+"' AND gTime="+stepTime +"GROUP BY gtime");			
				if(rsetStd.next())stddev = rsetStd.getFloat(1);
							
				buff.append(stepTime+"\t");
				buff.append(stddev+"\t"); 
				
				//get the average of data
				ResultSet rsetAvg = stmt.executeQuery("SELECT AVG(value) FROM observations where variableid='"+variableId+"' AND gTime="+stepTime +"GROUP BY gtime");			
				if(rsetAvg.next())avg = rsetAvg.getFloat(1);
					buff.append(avg-stddev+"\t");
					buff.append(avg+"\t");
					buff.append(avg+stddev+"\t");
						
				//get the Cell data
			for(int i=0; i<noOfAgents; i++){
				String agentId = "CAID"+i;
				ResultSet rset = stmt.executeQuery("SELECT value FROM observations where caid='"+agentId+"'AND variableid='"+variableId+"' AND gTime="+stepTime);			
				if(rset.next())buff.append(rset.getFloat(1)+"\t");
			}			
			buff.append("\n");	
		
			}			
			this.writeToFile(file, buff.toString());
			
			//release the resource			
			stmt.close();

		}catch(Exception e){
			e.printStackTrace();

		}		

	//	return buff;
	}
	
	public void saveSummaryData(float lowerDTime, float interval, float upperDTime, String dir, String dataFileName ) {	
		String fileName = dir+"/" +dataFileName;
		File file = new File(fileName);		

		StringBuffer buff = new StringBuffer();			
		try{
			stmt = this.getDataProcessConnection().createStatement();			
			for(float iter = lowerDTime; iter<=upperDTime; iter=iter+interval){

			//	buff.append(iter+"\t");

				ResultSet rset = stmt.executeQuery("SELECT * FROM aresultsummary where gtime="+iter);
				if(rset.next()) {
					buff.append(rset.getLong("arsid")+"\t");
					buff.append(rset.getFloat("executiontime")+"\t");
					buff.append(rset.getFloat("gtime")+"\t");
					buff.append(rset.getFloat("availableglu")+"\t");
					buff.append(rset.getFloat("consumedglu")+"\t");
					buff.append(rset.getInt("totalagents")+"\n");
				}

			}	

			this.writeToFile(file, buff.toString());
			//release the resource
			stmt.close();

		}catch(Exception e){
			e.printStackTrace();

		}	
		
	}
	
	public void saveGrowthData(float lowerDTime, float interval, float upperDTime, String dir, String dataFileName ) {	
		String fileName = dir+"/" +dataFileName;
		File file = new File(fileName);		
		
		StringBuffer buff = new StringBuffer();			
		try{
			stmt = this.getDataProcessConnection().createStatement();	
			//float iter = lowerDTime;
			for(float iter = lowerDTime; iter<=upperDTime; iter=iter+interval){
					
				buff.append(iter+"\t");
			int sum = 0;	
		//	float upperTime = iter+interval;
	
			ResultSet rset = stmt.executeQuery("SELECT COUNT(DISTINCT caid) FROM cellstates where eventtime<="+iter);
			if(rset.next()) buff.append(rset.getInt(1)+"\n");		
			
			}	
			
			this.writeToFile(file, buff.toString());
			//release the resource
			stmt.close();

		}catch(Exception e){
			e.printStackTrace();

		}	
		
	}
	
	
	public List<Integer> getDivisionTimeDistribution(float lowerDTime, float interval, float upperDTime) {	
		List<Integer> list = new ArrayList<Integer>();	
		
		try{
			stmt = this.getConnection().createStatement();	
			//float iter = lowerDTime;
			for(float iter = lowerDTime; iter<=upperDTime; iter=iter+interval){
												
			int sum = 0;	
			float upperTime = iter+interval;
	//	ResultSet rset = stmt.executeQuery("SELECT COUNT(caid) FROM observations where variableid='species_4' AND value BETWEEN "+iter+ " AND "+upperTime);	
			
	//		ResultSet rset = stmt.executeQuery("SELECT COUNT(caid) FROM observations where variableid='species_7' AND value BETWEEN "+iter+ " AND "+upperTime + "AND gtime<="+1950f);	
			
		//	ResultSet rset = stmt.executeQuery("SELECT COUNT(caid) FROM eventdata where size BETWEEN "+iter+ " AND "+upperTime);	
			
	//		ResultSet rset = stmt.executeQuery("SELECT COUNT(caid) FROM cellagents where birthsize BETWEEN "+iter+ " AND "+upperTime);	
			
			
			ResultSet rset = stmt.executeQuery("SELECT COUNT(DISTINCT caid) FROM cellstates where eventtime<="+upperTime);
			
	//		ResultSet rset = stmt.executeQuery("SELECT COUNT(caid) FROM eventdata where eventtime BETWEEN "+iter+ " AND "+upperTime); //+"AND gtime<="+1800f);
	//		ResultSet rset = stmt.executeQuery("SELECT COUNT(caid) FROM cellstates where eventtime BETWEEN "+iter+ " AND "+upperTime); 
			
	//		ResultSet rset = stmt.executeQuery("SELECT COUNT(caid) FROM eventdata where type='P' AND cycletime BETWEEN "+iter+ " AND "+upperTime);			
	//		ResultSet rset = stmt.executeQuery("SELECT COUNT(caid) FROM eventdata where cycletime BETWEEN "+iter+ " AND "+upperTime); // + "AND gtime="+1400);			
			
	//		ResultSet rset = stmt.executeQuery("SELECT COUNT(caid) FROM eventdata where (caid='CAID0' OR caid='CAID1' OR caid='CAID2' OR caid='CAID3') AND cycletime BETWEEN "+iter+ " AND "+upperTime);			
	//	ResultSet rset = stmt.executeQuery("SELECT COUNT(caid) FROM cellstates where gtime="+iter);			
			
			while(rset.next()){
				sum = rset.getInt(1);
			//	System.out.println(sum);
				list.add(sum);
			//	System.out.println(iter);
			}
			}
			
			//release the resource
			stmt.close();

		}catch(Exception e){
			e.printStackTrace();

		}		

		return list;
	}	
	
	public float getAverageDivisionTime() {	
		float sum = 0f;
		try{
			stmt = this.getConnection().createStatement();				
			
			ResultSet rset = stmt.executeQuery("SELECT AVG(cycletime) from eventdata where type='D'");			
			while(rset.next()){
				sum = rset.getFloat(1);				
				System.out.println(sum);			
			}
			
			//release the resource
			stmt.close();

		}catch(Exception e){
			e.printStackTrace();

		}		
		return sum;
		
	}
	
	
	//drop a table from the database
	public void dropORCreateTable(String tableName, boolean isDelete) {
		String sql = null;
		String createCellStatesTableSQL = "CREATE TABLE cellstates"
				+ "(cellstateid serial NOT NULL, gtime float4 NOT NULL,"
				+ "glucoseconsumed float4 NOT NULL, eventtime float4 NOT NULL, stepcounter int4 NOT NULL,"
				+ "caid varchar(25) NOT NULL, tcid int4, state text, isdivided varchar(3), gage int4,"
				+ "PRIMARY KEY (cellstateid))";
		
		String createEventDataTableSQL = "CREATE TABLE eventdata"
			+ "(eventid serial NOT NULL," + "event varchar(25),"
			+ "eventtime float8 NOT NULL," + "gtime float8 NOT NULL,"
			//+ "cellstateid int4 NOT NULL,"
			+ "caid varchar(25) NOT NULL," + "cycletime float4," + "type varchar(2), size float4, gage int4, stateatevent text,"
			+ "PRIMARY KEY (eventid))";
		
		String createEventDataTableSQLBacup = "CREATE TABLE eventdata"
			+ "(eventid serial NOT NULL," + "event 10varchar(25),"
			+ "eventtime float8 NOT NULL," + "gtime float8 NOT NULL,"
			+ "glucoseconsumed float4 NOT NULL,"
			+ "caid varchar(25) NOT NULL," + "cycletime float4," + "type varchar(2), size float4, tcid int4 NOT NULL,"+ "stateatevent text, simState text,"
			+ "PRIMARY KEY (eventid))";
		
		String createObservationsTableSQL = "CREATE TABLE observations"
			+ "(observationid serial NOT NULL," + "caid varchar(25) NOT NULL,"
			+ "variableid varchar(25) NOT NULL," + "gtime float4 NOT NULL,"
			+ "value float4 NOT NULL,"
			+ "PRIMARY KEY (observationid))";
		
		String createTCDataTableSQL = "CREATE TABLE timecoursedata"
			+ "(tcid serial NOT NULL," + "tcdata text NOT NULL,"
			+ "PRIMARY KEY (tcid))";
		
		String createCellAgentsTableSQL = "CREATE TABLE cellagents"
			+ "(caid varchar(25) NOT NULL," + "birthSize float4 NOT NULL, gage int4, motherid varchar(25), motherageatbirth int4,"
			+ "PRIMARY KEY (caid))";
		
		String createAResultSummaryTableSQL = "CREATE TABLE aresultsummary"
			+ "(arsid serial NOT NULL," + "executiontime float8 NOT NULL, gtime float8, availableglu float8, consumedglu float8 NOT NULL, totalagents int4 NOT NULL,"
			+ "PRIMARY KEY (arsid))";
		

		String dsql = "DROP TABLE " + tableName;
		if (isDelete) {
			sql = dsql;
		} else {
			if (tableName.equalsIgnoreCase("eventdata")) {
				sql = createEventDataTableSQL;
			}else if(tableName.equalsIgnoreCase("timecoursedata")){
				sql = createTCDataTableSQL;
			}else if(tableName.equalsIgnoreCase("cellagents")){
				sql = createCellAgentsTableSQL;
			}else if(tableName.equalsIgnoreCase("observations")){
				sql = createObservationsTableSQL;
			}else if(tableName.equalsIgnoreCase("cellstates")){
				sql = createCellStatesTableSQL;
			}else if(tableName.equalsIgnoreCase("aresultsummary")){
				sql = createAResultSummaryTableSQL;
			}
			
			
		}

		try {
			connection = this.getConnection();			
			stmt = connection.createStatement();			
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public void writeToFile(File file, String textToWrite){
	try {        
    //    File file = new File(fileName);
		BufferedWriter out = new BufferedWriter(new FileWriter(file));
   //     FileWriter fw = new FileWriter(file);
        out.write(textToWrite);
        out.close();

    } catch (Exception iox) {
        //do stuff with exception
        iox.printStackTrace();
    }
	}
	
	
}
