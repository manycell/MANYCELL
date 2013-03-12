package sim.msscc.agents;

import java.util.*;

/**
 * The <code>CIAgentEvent</code> class defines the common event that is sent by
 * an intelligent agent.
 *
 * @author Joseph O. Dada * 
 *
 * @copyright
 * The University of Manchester
 *
 */
public class AgentEvent extends java.util.EventObject {
  private Object argObject = null;
  private String action = null;


  /**
   * Creates an <code>AgentEvent</code> object.
   *
   * @param source the Object that is a source of this event
   */
  public AgentEvent(Object source) {
    super(source);
  }


  /**
   * Creates an <code>AgentEvent</code> object that includes an
   * object related to the event.
   *
   * @param source    the Object that is a source of this event
   * @param argObject the Object that is used to supply additional data about
   *                  this event
   */
  public AgentEvent(Object source, Object argObject) {
    this(source);
    this.argObject = argObject;
  }


  /**
   * Creates an <code>AgentEvent</code> object that includes an
   * object related to the event and an action.
   *
   * @param source    the Object that is a source of this event
   * @param action    the String that represents the action for this event
   * @param argObject the Object that is used to supply additional data about
   *                  this event
   */
  public AgentEvent(Object source, String action, Object argObject) {
    this(source);
    this.action = action;
    this.argObject = argObject;
  }


  /**
   * Retrieves the object related to this event.
   *
   * @return the argument Object (if any)
   */
  public Object getArgObject() {
    return argObject;
  }


  /**
   * Retrieves the action related to this event.
   *
   * @return the action (if any)
   */
  public String getAction() {
    return action;
  }


  /**
   * Converts the event to a string formatted for display or printing.
   *
   * @return the formatted <code>String</code> object
   */
  public String toString() {
    StringBuffer buf = new StringBuffer();

    buf.append("CIAgent ");
    buf.append("source: " + source);
    buf.append("action: " + action);
    buf.append("argObject: " + argObject);
    return buf.toString();
  }
}
