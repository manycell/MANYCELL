package sim.msscc.agents;

import java.io.*;


/**
 * The <code>CIAgentState</code> contains the state of an agent (UNINITIATED,
 * INITIATED, ACTIVE, SUSPENDED, UNKNOWN).
 *
 * @author Joseph O. Dada
 * 
 * @copyright
 * The University of manchester
 *
 */
public class AgentState {
  public static final int UNINITIATED = 0;
  public static final int INITIATED = 1;
  public static final int ACTIVE = 2;
  public static final int SUSPENDED = 3;
  public static final int UNKNOWN = 4;
  private int state;


  /**
   * Creates a <code>AgentState</code> object.
   */
  public AgentState() {
    state = UNINITIATED;
  }

  /**
   * Sets the state.
   *
   * @param state the state of an agent
   */
  public synchronized void setState(int state) {
    this.state = state;
  }


  /**
   * Retrieves the state.
   *
   * @return an integer that represents the state
   */
  public int getState() {
    return state;
  }


  /**
   * Coverts the state to a printable format.
   *
   * @return a formatted String that represents the state
   */
  public String toString() {
    switch (state) {
      case UNINITIATED : {
        return "Uninitiated";
      }
      case INITIATED : {
        return "Initiated";
      }
      case ACTIVE : {
        return "Active";
      }
      case SUSPENDED : {
        return "Suspended";
      }
      case UNKNOWN : {
        return "Unknown";
      }
    }
    return "Unknown";
  }
}

