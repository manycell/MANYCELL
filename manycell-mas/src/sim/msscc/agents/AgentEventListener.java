package sim.msscc.agents;

import java.util.*;


/**
 * The <code>AgentEventListener</code> defines the interface for the event
 * listener.
 *
 * @author Joseph O. Dada 
 *
 * @copyright
 * The University of Manchester
 *
 */
public interface AgentEventListener extends java.util.EventListener {


  /**
   * Processes the event.
   *
   * @param e the CIAgentEvent to be processed
   */
  public void processAgentEvent(AgentEvent e);


  /**
   * Adds the event to the asynchronous event queue.
   *
   * @param e the event to be added to the event queue
   */
  public void postAgentEvent(AgentEvent e);
}