package sim.msscc.agents;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import sim.msscc.data.DBDataManager;

public class AgentManager {
	
	public static void main(String[] args) {		

		float simDuration = 9000.0f;
		float simStep = 0.0f;
		//get the start time of agents execution
		Long pTime= System.currentTimeMillis();		
		
		//testing the data from the database
		boolean isData = true;
	//	boolean isLocal = false;
		
		MasterAgent master =null;
	////	if(!isData){
		master = new MasterAgent("master", args[0]);	
		//master.setSimDuration(simDuration);
		master.initialize();  
	//	}
		
		isData = true;
	//	System.out.println("No of processor"+ Runtime.getRuntime().availableProcessors());
		//	SimulatorAgent simAgent = new SimulatorAgent("simAgent");
		//simAgent.addAgentEventListener(master);
		//	simAgent.initialize();
		//	master.setSimAgent(simAgent);		
	//	master.initialize();
		//	master.startAgentProcessing();	
		//master.stopAgentProcessing();
	
		/*	DBDataManager manager = new DBDataManager();
		List<Integer> list = manager.getDistribution(0.0f, 160f, 2600f);
		
		for(int i=0;i<list.size(); i++){
			System.out.println(list.get(i));
		}*/
		
		/*DBDataManager manager = new DBDataManager();
		List<Integer> list = manager.getCellDivisionDistribution(45);
		
		for(int i=0;i<list.size(); i++){
			System.out.println(list.get(i));
		}*/
//	if(isData){	
/*		DBDataManager manager = new DBDataManager();
		List<Float> list = manager.getGlucoseConsumedDistribution(0.0f, 5f, 900f);
		
		for(int i=0;i<list.size(); i++){
			System.out.println(list.get(i));
		}*/
		
		//agent division time distribution
	DBDataManager manager = new DBDataManager();
//	manager.setData(isData);
//	manager.setLocal(isLocal);
	manager.setDatabase(master.getDatabase());
	
	//post processing of data
	Random rand = new Random();
	//String model = "chen-245-model";
	String dir = System.getProperty("user.dir");
//	String dir = System.getProperty("user.dir") + "/test-results/analysis/"+model;
	int randInt = rand.nextInt(10000);
	float code = 2.000f;
	if(!master.usTabulator) {
		code = master.getNumberOfProcessors();	
	}else code = master.getTabulatorTol();	
	
	int cells = 0;
	float duration = master.getSimDuration();
	
	cells = master.getNoOfSeededCells();
	
	Properties prop = new Properties();		
	
	if(args.length>0){
		try{
			FileInputStream in = new FileInputStream(dir + "/output-config/data-process-config.txt");
			prop.load(in);
		}catch (IOException e){e.printStackTrace();}
	
		cells = Integer.parseInt(prop.getProperty("numberOfCells"));	
		duration = Float.valueOf(prop.getProperty("duration"));

	}
	
	dir = dir +"/results";
	manager.saveGrowthData(0, master.getStepSize(), duration, dir, randInt+".growth-"+code+".txt");
	manager.saveSummaryData(0, cells, duration, dir, randInt+".summary-"+code+".txt");
	
	//the cell age distribution
	manager.saveCellAgeDistribution(dir, randInt+".cellAgeDistribution-"+code+".txt");
	
	for(int i=0; i<master.getActiveVariables().length; i++){
		String variable = master.getActiveVariables()[i];			
		manager.saveCellVariableTimeEvolution(variable, cells, master.getStepSize(), duration, dir, randInt+".species-"+variable+"-"+code+".txt");		
	}	
		
	master.stopAgentProcessing();

	//	System.out.println(buff);
		
		//glucose evolution
	//	List<Float> list = manager.getGLUConsumedEvolution(0.0f, 50f, 1600f);
		
	//variable evolution
/*		List<Float> list = manager.getCellVariableTimeEvolution("CAID3", "B_R", 10, 1500); //2250); //180
	//	List<Float> list = manager.getCellVariableTimeEvolution("CAID6", "species_7", 10, 1650); //180
	//	List<Float> list = manager.getCellVariableTimeEvolution("CAID0", "CLB2", 5, 1250);
//		List<Integer> list = manager.getDivisionTimeDistribution(20.0f, 5f, 120f);
//		List<Integer> list = manager.getDivisionTimeDistribution(7.0f, 2f, 80f);
	//	List<Integer> list = manager.getDivisionTimeDistribution(0.0f, 50f, 3600); // 2250);
	//	List<Integer> list = manager.getCellDivisionDistribution(17);
		
		//List<Integer> list = manager.getCellDivisionDistribution(35);
	//	List<Integer> list = manager.getCellDivisionDistribution(17, 2250f);
		
	//	List<Integer> list = manager.getDivisionTimeDistribution(0.0f, 10f, 1800f);
		
		for(int i=0;i<list.size(); i++){
			System.out.println(list.get(i));
		}
*/		
// }
		//agent division time distribution
	/*	DBDataManager manager = new DBDataManager();
		float average =  manager.getAverageDivisionTime();		
			System.out.println(average);*/
		
		
		//testing the cell counter
	/*	DBDataManager manager = new DBDataManager();
		Hashtable<String, Integer> list = manager.getCellDistribution(5067);
		
		int [] cellCount = new int[15];
		for(int j=0; j<14; j++){
			int cellCounter = 0;
		for(int i=0;i<list.size(); i++){
			String agentId = "CAID"+i;
			if(list.get(agentId)==j){
				cellCounter++;
			}
			//System.out.println(agentId +" "+ list.get(agentId));
		}
		
		cellCount[j] = cellCounter;
		System.out.println(j +" "+ cellCount[j]);
		
		}*/
		
		//testing the cell counter/distribution
		/*	DBDataManager manager = new DBDataManager();
			Hashtable<String, Integer> list = manager.getCellAgeDistribution(1200.0f, 512);
			
			int [] cellCount = new int[15];
			for(int j=0; j<14; j++){
				String jagentId = "CAID"+j;
				int cellCounter = 0;
			for(int i=0;i<list.size(); i++){
				String agentId = "CAID"+i;
				if(list.get(agentId)==j){
					cellCounter++;
				}
				//System.out.println(agentId +" "+ list.get(agentId));
			}
			
			cellCount[j] = cellCounter;
			System.out.println(j +" "+ cellCount[j]);
			
			}*/
	}
	
	public boolean deleteDatabaseTable(DBDataManager dataManager) {
		boolean success = true;
		try {
			dataManager.dropORCreateTable("cellagents", true);
			dataManager.dropORCreateTable("cellstates", true);
			dataManager.dropORCreateTable("eventdata", true);
			dataManager.dropORCreateTable("timecoursedata", true);
			dataManager.dropORCreateTable("observations", true);
			dataManager.dropORCreateTable("aresultsummary", true);
		} catch (Exception e) {
			success = false;

		}
		return success;
	}
}
