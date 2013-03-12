package sim.msscc.utils;

import java.io.File;

import org.copasi.utils.SimpleGraphPloter;

public class PlotGraph {

	/**
	 * @param args
	 */
	public static final String[] tableHeader ={"MASS","BUD", "CLB2", "MASS-1", "BUD-1", "CLB2-1"};
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub		
		double[][] data =null;
		//set the file name		
		//String plotOut = "tmpPlot_" + Math.random();
		String plotOut = "speciesGraph2.png";
		File plotFile = new File(plotOut);	
		
		//testing the result by plotting it using Gnuplot
		SimpleGraphPloter ploter = new SimpleGraphPloter();
		String title = "Time course";
		String yLabel = "Species concentration";
		String xLabel = "Time";
		
		try{			
			ploter.plotGraphFromFile("tabelData.csv",plotFile.toString(), tableHeader, title, yLabel, xLabel);
		}catch(Exception e){
			System.out.println(e.toString());
			
		}

	}

}
