package sim.msscc.agents;

import java.beans.PropertyChangeSupport;
import java.util.*;

/**
 * The <code>Agent</code> class defines the common programming interface
 * and behavior for culture agents.
 * <p>
 * The lifecycle of a Agent is as follows:<ol>
 *  <li> construct an agent bean  (state = UNINITIATED)
 *  <li> set the bean properties
 *  <li> call initialize() method (state changes to INITIATED)
 *  <li> call startAgentProcessing() method (state changes to ACTIVE)
 *     use agent in applications
 *  <li> call stopAgentProcessing() method (state changed to UNKNOWN)
 *  </ol><p>
 *  Call process() to perform synchronous agent processing of data.
 *  Use  processTimerPop() to perform asynchronous agent processing.
 *
 *  Call processAgentEvent() to perform synchronous event processing
 *  Call postAgentEvent() to perform asynchronous event processing
 *
 * @author Joseph O. Dada * 
 *
 * @copyright
 * The University of Manchester
 *
 *
 */
public abstract class Agent implements AgentEventListener{
	
	protected String agentId;
	private AgentState state = new AgentState();
	private AgentTimer timer = new AgentTimer(this);
	transient private Vector listeners = new Vector();  // list of listeners
	transient private AgentEventQueue eventQueue = new AgentEventQueue();
	transient private PropertyChangeSupport changes = new PropertyChangeSupport(this);
	
	 protected int traceLevel = 0;
	/**
	   * Creates a Agent with specified ID.
	   *
	   * @param name the String name given to this agent
	   */
	  public Agent(String agentId) {
	    this.agentId = agentId;
	 //   timer.setAsyncTime(DEFAULT_ASYNCTIME);  // set asynchTime
	  //  timer.setSleepTime(DEFAULT_SLEEPTIME);  // set sleepTime
	   // state.setState(AgentState.UNINITIATED);
	  }


	  /**
	   *  Retrieves the id of this agent.
	   *
	   * @return the String that contains the id of this agent
	   */
	public String getAgentId() {
		return agentId;
	}

	  /**
	   * Sets the id of this agent.
	   *
	   * @param id the String that contains the id of this agent
	   */
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	  
	/**
	   *  Sets the current state of this agent (UNINITIATED, INITIATED, ACTIVE,
	   *  SUSPENDED, UNKNOWN).
	   *
	   *  @param state the new state of this agent
	   *
	   * @param newState the int object
	   *
	   */
	  protected void setState(int newState) {
	    int oldState = state.getState();

	    changes.firePropertyChange("state", oldState, newState);
	    this.state.setState(newState);
	  }


	  /**
	   *  Retrieves the current state of this agent (UNINITIATED, INITIATED, ACTIVE,
	   *  SUSPENDED, UNKNOWN).
	   *
	   *  @return the state of this agent
	   */
	  public AgentState getState() {
	    return state;
	  }

	  /**
	   * Resets this agent so that it is in a known state.
	   */
	  public void reset() {}


	  /**
	   * Initializes this agent for processing.
	   */
	  public abstract void initialize();


	  /**
	   * Starts the agent timer and asynchronous event processing and sets the agent
	   * state to ACTIVE.
	   */
	  public synchronized void startAgentProcessing() {
	    timer.startTimer();
	    setState(AgentState.ACTIVE);
	  }


	  /**
	   * Stops the agent timer and asynchronous event processing and sets the agent
	   * state to UNKNOWN.
	   */
	  public synchronized void stopAgentProcessing() {

	    // stop processing async events and timer events
	    // change agent state to unknown
	    timer.quitTimer();
	    setState(AgentState.UNKNOWN);
	  }


	  /**
	   * Temporarily stops the agent timer so that the autonomous behavior is
	   * suspended and sets the agent state to SUSPENDED.
	   */
	  public void suspendAgentProcessing() {

	    // turn asynch event processing off
	    // turn timer event processing off
	    timer.stopTimer();
	    setState(AgentState.SUSPENDED);
	  }


	  /**
	   * Resumes agent processing of the timer and asynchronous events and sets the
	   * agent state to ACTIVE.
	   */
	  public void resumeAgentProcessing() {

	    // turn asynch event processing on
	    // turn timer event processing on
	    timer.startTimer();
	    setState(AgentState.ACTIVE);
	  }


	  /**
	   * Provides the synchronous processing done by this agent.
	   */
	  public abstract void process();


	  /**
	   * Provides the asynchronous, autonomous behavior of this agent that
	   * occurs periodically, depending on the sleep time for this agent.
	   */
	  public abstract void processTimerPop();


	  /**
	   * Processes all events on the asynchronous event queue periodically,
	   * depending on the asynchronous event time for this agent.
	   */
	  public void processAsynchronousEvents() {
	    AgentEvent event = null;

	    while ((event = eventQueue.getNextEvent()) != null) {
	  //    System.out.println("Agent: " + name + " dispatching an Async event");
	      processAgentEvent(event);
	    }
	  }


	  /**
	   * Performs synchronous event processing for this agent.
	   *
	   * @param event the AgentEvent object
	   */
	  public void processAgentEvent(AgentEvent event) {
	  }


	  /**
	   * Posts an event to this agent's event queue.
	   *
	   * @param event the AgentEvent to be posted
	   */
	  public void postAgentEvent(AgentEvent event) {
	    eventQueue.addEvent(event);
	  }


	  /**
	   * Adds a listener for Agent events.
	   *
	   * @param listener the AgentEventListener to be added
	   */
	  public synchronized void addAgentEventListener(AgentEventListener listener) {
	     listeners.addElement(listener);
	  }


	  /**
	   * Removes a listener for Agent events.
	   *
	   * @param listener the AgentEventListener to be removed
	   */
	  public synchronized void removeAgentEventListener(AgentEventListener listener) {
	    listeners.removeElement(listener);
	  }


	  /**
	   * Delivers the Agent event to all registered listeners.
	   *
	   * @param e the AgentEvent to be sent to all listeners
	   */
	  protected void notifyAgentEventListeners(AgentEvent e) {
	    Vector l;

	    synchronized (this) {
	      l = (Vector) listeners.clone();
	    }
	    for (int i = 0; i < l.size(); i++) {  // deliver the event
	      ((AgentEventListener) l.elementAt(i)).processAgentEvent(e);
	    }
	  }
	  
	  /**
	   * Sends a trace event to all registered listeners.
	   *
	   * @param msg the String that is the message portion of the trace event
	   */
	  public void trace(String msg) {

	    // create a data event
	    AgentEvent event = new AgentEvent(this, "trace", msg);

	    // and send it to any registered listeners
	    notifyAgentEventListeners(event);
	  }
 

}
