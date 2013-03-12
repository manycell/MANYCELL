package sim.msscc.data;

public interface DataManagerInterface {
	public int storeTimeCourseResult(String userId, String CAID,
			String simulationRun, String resultString, String resultMetadata);
	
	/**
	 * @param resultString
	 * @return
	 */
	public int storeTimeCourseResult(String resultString);

	/**
	 * @param userId
	 * @param CAID
	 * @param simulationRun
	 * @param modelString
	 * @param format
	 * @param isUpdatedModel
	 * @return
	 */
	public int storeModel(String userId, String CAID, String simulationRun,
			String modelString, String format, boolean isUpdatedModel);

	/**
	 * @param userId
	 * @param CAID
	 * @param simulationRun
	 * @param format
	 * @param updatedModelRequire
	 * @return
	 */
	public String loadModel(String userId, String CAID, String simulationRun,
			String format, boolean updatedModelRequire);

	/**
	 * @param userId
	 * @param CAID
	 * @param simulationRun
	 * @return
	 */
	public String retrieveTimeCourseResult(String userId, String CAID,
			String simulationRun);

	/**
	 * @param CAID
	 * @param event
	 * @param eventTime
	 * @param globalTime
	 * @param stateatevent
	 * @param TCID
	 * @return
	 */
	public int storeEventData(String CAID, String event, float eventTime,
			float globalTime, String stateatevent,  float GLUconsumed, int TCID);
	
	/**
	 * @param CAID
	 * @param event
	 * @param simTime
	 * @return
	 */
	public EventData retrieveEventData(String CAID, String event, float simTime);
	
	
}
