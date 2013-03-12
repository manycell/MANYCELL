package sim.msscc.agents;

import sim.jnative.PSConnector;
import sim.msscc.data.DBDataManager;
import sim.msscc.data.DataManagerInterface;
import sim.msscc.data.DivisionTimeEventData;
import sim.msscc.data.SimulationRequestMessage;
import sim.msscc.data.TimeCourseSimulationResult;

public class SimulatorAgent extends Agent{

	private PSConnector psconnector = new PSConnector();
	private DBDataManager dataManager = new DBDataManager();
	SimulationRequestMessage simMsg;

	SimulatorAgent(String agentId){
		super(agentId);
	}	

	public void initialize() {			
		this.startAgentProcessing();
	}

	/**
	 * Provides the synchronous processing done by this agent.
	 */
	public void process() {
		// PSConnector psconnector = new PSConnector();
		int simulatorId = 1;
		long res = psconnector.initSimulator(simulatorId);
		
		double[] sInit = simMsg.getSpeciesInitialCondition(); 		
		String[] sId = simMsg.getSpeciesId(); 	
		//System.out.println(res);
		
		//TODO comment out for later use
	//	psconnector.runTCSimulatior(simMsg.getModel(), "test", simMsg.getTolerance(), sId, sInit, simMsg.getStepSize(), 
	//			simMsg.getnTimeSteps(), simMsg.getnSpecies(), simMsg.getnSpecies(),res);
	//	System.out.println(psconnector.getuModel());		
	}

	/**
	 * Provides the asynchronous, autonomous behavior of this agent that occurs
	 * periodically, depending on the sleep time for this agent.
	 */
	public void processTimerPop() {
	}


	/**
	 * Processes a AgentEvent.
	 *
	 * @param e the AgentEvent object to be processed
	 *
	 */
	public void processAgentEvent(AgentEvent e) {

		if (traceLevel > 0) {
			trace("CellAgent:  AgentEvent received by " + agentId + " from " + e.getSource() + " with args " + e.getArgObject() + "\n");
		}
		Object arg = e.getArgObject();
		Object action = e.getAction();

		float gTime;
		if ((action != null) && (action.equals("processSimulationMessage"))) {
			CellAgent agent = (CellAgent)e.getSource();
			String agentId = agent.getAgentId();
			float ldEventTime = agent.getLastDivisionEventTime();
			simMsg = (SimulationRequestMessage) arg;
			gTime =simMsg.getGlobalTime();			
			if (traceLevel > 0) {
				//  simMsg.display();  // show message contents
			}
			process();					
			String simResultString = psconnector.getSimResult();
			TimeCourseSimulationResult simResult = new TimeCourseSimulationResult(simResultString, simMsg);			
			float divTime = ldEventTime + simResult.getDivisionTime();
			int storeTCID = dataManager.storeTimeCourseResult(simResultString);
			dataManager.storeEventData(agentId, "division",divTime, gTime, simResult.getStateAtEvent(), simResult.getGLUconsumed(), storeTCID);
			
			simMsg.setHeaderString(simResult.getHeaderString());
			//testing data manager
			System.out.println("New: "+agentId);
			dataManager.retrieveEventData(agentId, "division",gTime);			
			System.out.println("Division Time Event: "+simResult.getDivisionTime());
		/*	for(int i = 0; i<simResult.getsInit().length;i++){
				System.out.println("Initial condition: "+simResult.getsInit()[i]);
			}*/
		}else
			if ((action != null) && (action.equals("CheckSimulatorMessage"))) {
				System.out.println("Am Her==================");
				this.notifyAgentEventListeners(new AgentEvent(this,"processNextMessage", simMsg.getHeaderString()));

			}		

	}

	/**
	 * @param agentId
	 * @param gTime
	 * @param simResult
	 * @return
	 */
	public DivisionTimeEventData extractDivisionEventData(String agentId, float gTime, String simResult){

		return null;
	}

	/*  public void runSimulator(){
			int simulatorId = 1;
			int res=psconnector.initSimulator(simulatorId);		
			System.out.println(res);
		//	psconnector.runTCSimulatior(model, modelId, tolerance, speciesId, sInitialValue, stepSize, nTimeSteps, nSpecies, nNonConstSpecies, res);
		}*/


}
