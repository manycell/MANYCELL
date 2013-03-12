package sim.msscc.agents;

import java.util.*;
import java.io.*;

/**
 * The <code>AgentEventListener</code> class defines event queue that contains
 * agent events.
 *
 * @author Joseph O. Dada 
 *
 * @copyright
 * The University of Manchester
 *
 */
public class AgentEventQueue {
  private Queue<AgentEvent> eventQueue;


  /**
   * Creates an event queue.
   */
  public AgentEventQueue() {
    eventQueue = new LinkedList<AgentEvent>();
  }


  /**
   * Adds an event to the end of the queue.
   *
   * @param event the AgentEvent to be added to the queue
   */
  public synchronized void addEvent(AgentEvent event) {
    eventQueue.add(event);
  }


  /**
   * Retrieves the first element from the queue (if any) after removing it from
   * the queue.
   *
   * @return the first event on the queue
   */
  public synchronized AgentEvent getNextEvent() {
    return  eventQueue.poll();   
   
  }

  /**
   * Retrieves the first element from the queue (if any) without removing it.
   *
   * @return the first event on the queue
   */
  public synchronized AgentEvent peekEvent() {
    return eventQueue.peek();
  }
}

