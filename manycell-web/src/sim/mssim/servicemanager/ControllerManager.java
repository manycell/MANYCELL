package sim.mssim.servicemanager;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.copasi.processor.SBRMLProcessor;
import org.copasi.utils.FileProcessor;
import org.copasi.utils.SimpleGraphPloter;

import sim.msscc.msscontroller.MSSController;
import sim.msscc.msscontroller.MSSControllerInterface;

public class ControllerManager {
	//the processor	
	FileProcessor fileProcessor = new FileProcessor();
	SBRMLProcessor sbrmlProcessor = new SBRMLProcessor();
	SimpleGraphPloter ploter = new SimpleGraphPloter();
	Map<String, Object> operationMap = new HashMap<String, Object>();
	List<String> modelObjectList = new ArrayList<String>();
	
	public Map<String, String> simulateCulture(Map<String,String>dataMap){
		Map<String, String> map = new HashMap<String, String>();
		MSSControllerInterface controlInterface = new MSSController();
		String pathToData = null;
		//prepare the data file fot the graph
		String cellDataFile = null, biomassDataFile = null;
		String tomcatHome = System.getenv("TOMCAT_HOME");
		String directory = tomcatHome +"/webapps/MSSimWeb/UI/Simulations";

		String plotOut = "tmpPlot_" + Math.random();
		String tableDataOut = "table_" + Math.random();
		File tableDataFile = new File(directory +"/"+tableDataOut+".txt");
		File plotFile = new File(directory +"/"+plotOut+".png");		
		try{
			pathToData = controlInterface.simulateCulture(dataMap, 60,2);
			cellDataFile = pathToData + "/cellData.txt";
			fileProcessor.saveModelInSpecifiedDirectory(fileProcessor.readFileAsString(cellDataFile), directory, tableDataOut +".txt");
			//String dataFileName = directory+"/" +tableDataOut+".txt";
			System.out.println(tableDataFile.toString());
			String [] dataId ={"Iteration Count", "Population"};
			ploter.plotHistoGaph(tableDataFile.toString(), plotFile.toString(), dataId, "cell");
		//	ploter.plotLineGraph(tableDataFile.toString(), plotFile.toString(), "cell");	
		}catch (Exception e){
			e.printStackTrace();
		}

		// processing the biomass data
		String plotOut1 = "tmpPlot1_" + Math.random();
		String tableDataOut1 = "table1_" + Math.random();
		File tableDataFile1 = new File(directory +"/"+tableDataOut1+".txt");
		File plotFile1 = new File(directory +"/"+plotOut1+".png");		
		try{	
			biomassDataFile = pathToData + "/biomassData.txt";
			fileProcessor.saveModelInSpecifiedDirectory(fileProcessor.readFileAsString(biomassDataFile), directory, tableDataOut1 +".txt");
			//String dataFileName = directory+"/" +tableDataOut+".txt";
			System.out.println(tableDataFile1.toString());
			String [] dataId ={"Simulation Steps", "Biomass"};
		//	ploter.plotHistoGaph(tableDataFile1.toString(), plotFile1.toString(), dataId, "biomass");		
			ploter.plotLineGraph(tableDataFile1.toString(), plotFile1.toString(), "biomass");		
			
		}catch (Exception e){
			e.printStackTrace();
		}		

		//clear the mess after the restarting
		plotFile.deleteOnExit();
		plotFile1.deleteOnExit();
		tableDataFile1.deleteOnExit();
		tableDataFile.deleteOnExit();
		map.put("cell", plotOut+".png");
		map.put("biomass", plotOut1+".png");
		return map;
	}
	public  Map<String, String> runTimeCourseSimulator(String pathWay, String stepSize, String stepNumber, String startTime){

		Map<String, String> map = new HashMap<String, String>();
		int tmp1 =Integer.valueOf(stepNumber);
		if(tmp1>500l) tmp1 = 500;      	

		MSSControllerInterface controlInterface = new MSSController();
		String results = null;
		try{
			results = controlInterface.simulationSimulatorResultsInSBRML("http://dada.mib.man.ac.uk:8080/CopasiWS/services/TimeCourseService", pathWay, Float.valueOf(stepSize), tmp1, 0f);
		}catch (Exception e){
			System.out.println(e.toString());
		}


		String tomcatHome = System.getenv("TOMCAT_HOME");
		String directory = tomcatHome +"/webapps/MSSimWeb/UI/Simulations";

		String plotOut = "tmpPlot_" + Math.random();
		String sbrmlOut = "sbrml_" + Math.random();
		String tableDataOut = "table_" + Math.random();
		File plotFile = new File(directory +"/"+plotOut+".png");
		File sbrmlFile = new File(directory +"/"+sbrmlOut+".xml");
		File tableDataFile = new File(directory +"/"+tableDataOut+".txt");

		//testing the result by plotting it using Gnuplot
		SimpleGraphPloter ploter = new SimpleGraphPloter();

		try{
			fileProcessor.saveModelInSpecifiedDirectory(results, directory, "/" + sbrmlOut +".xml");
			operationMap = sbrmlProcessor.convertSBRMLToTableFileFormat(sbrmlFile.toString(), tableDataFile.toString()); //.directory + "/sbrmlTableData.txt");
			modelObjectList = (List<String>)operationMap.get("0modelObjectId");			

			//get the model id from the map
			String[] modelObjectId = modelObjectList.toArray((new String[0]));

			//create the plot graphic
			ploter.plotGraphFromSBRML(tableDataFile.toString(), plotFile.toString(), modelObjectId);			
		}catch(Exception e){
			System.out.println(e.toString());

		}	
		plotFile.deleteOnExit();
		sbrmlFile.deleteOnExit();
		tableDataFile.deleteOnExit();
		map.put("plot", plotOut +".png");
		map.put("data", sbrmlOut +".xml");
		return map;
		//return plotOut +".png";
	}

}
