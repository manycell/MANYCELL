package sim.msscc.agents;

import java.util.*;
import java.io.*;

/**
 * The <code>CIAgentTimer</code> allows autonomous behavior of an agent through
 * timed method calls.
 *
 * @author Joseph P. Bigus
 * @author Jennifer Bigus
 * @modified by Joseph O. Dada
 *
 * @copyright
 * Constructing Intelligent Agents using Java
 * (C) Joseph P.Bigus and Jennifer Bigus 2001
 *
 */
public class AgentTimer implements Runnable, Serializable {
  private Agent agent;          // owner agent
  private int sleepTime = 1000;   // mSec - default to 1 second
  private boolean timerEnabled = true;
  private int asyncTime = 500;    // mSec - default to 0.5 seconds
  transient private Thread runnit = new Thread(this);
  private boolean quit = false;   // used to exit thread run() method
  private boolean debug = false;  // turn on to see timer vars


  /**
   * Creates a timer, specifying the agent that owns the timer.
   *
   * @param agent the Agent that owns this timer object
   *
   */
  public AgentTimer(Agent agent) {
    this.agent = agent;
  }


  /**
   * Sets the time (in milliseconds) that determines how often an agent performs
   * the autonomous behavior that is defined in its <code>processTimerPop</code>
   * method.
   *
   * @param sleepTime the amount of sleep time in milliseconds
   */
  public void setSleepTime(int sleepTime) {
    this.sleepTime = sleepTime;
  }


  /**
   * Retrieves the sleep time (in milliseconds).
   *
   * @return the amount of sleep time in milliseconds
   */
  public int getSleepTime() {
    return sleepTime;
  }


  /**
   * Sets the time (in milliseconds) that determines how often the agent processes
   * asynchronous events on its event queue.
   *
   * @param asyncTime the interval time in milliseconds
   */
  public void setAsyncTime(int asyncTime) {
    this.asyncTime = asyncTime;
  }


  /**
   * Retrieves the asynchronous interval time (in milliseconds).
   *
   * @return the asychronous interval time in milliseconds
   */
  public int getAsyncTime() {
    return asyncTime;
  }


  /**
   * Starts the timer thread.
   */
  public void startTimer() {
    timerEnabled = true;
    if (!runnit.isAlive()) {
      runnit.start();  // start on first time only
    }
  }


  /**
   * Indicates that timer events events should not be fired.
   */
  public void stopTimer() {
    timerEnabled = false;
  }


  /**
   * Indicates that the timer thread should be ended.
   */
  public void quitTimer() {
    quit = true;
  }


  /**
   * Processes the asynchronous events and autonomous timer events periodically,
   * with the interval based on the sleep time and/or the asynchronous event time.
   */
  public void run() {

    // NOTE: sleepTime and asyncTime must be set before
    // startTimerEvents() is called
    long startTime = 0;
    long curTime = 0;

    if (debug) {
      startTime = new Date().getTime();  // get start time in mSec
      curTime = startTime;               // current time in mSec
    }
    if (sleepTime < asyncTime) {
      asyncTime = sleepTime;
    }
    int numEventChecks = sleepTime / asyncTime;

    if (debug) {
      System.out.println("sleepTime= " + sleepTime + " asyncTime= " + asyncTime + "numEventChecks= " + numEventChecks);
    }
    while (quit == false) {
      try {
        for (int i = 0; i < numEventChecks; i++) {
          Thread.sleep(asyncTime);
          if (debug) {
            curTime = new Date().getTime();  // current time in mSec
            System.out.println("async events timer at " + (curTime - startTime));
          }
          if (quit) {
            break;                           // exit loop
          }
          agent.processAsynchronousEvents();
        }
        if (timerEnabled && (quit == false)) {
          if (debug) {
            curTime = new Date().getTime();  // current time in mSec
            System.out.println("timer event at " + (curTime - startTime));
          }
          agent.processTimerPop();           // call the timer processing method
        }
      } catch (InterruptedException e) {}
    }
  }


  /**
   * De-serialize the object from the specified input stream by
   * re-initializing the object's transient variables, de-serializing
   * the object with defaultReadObject(), and then hooking up the
   * de-serialized stuff to the re-initialized stuff.
   *
   *
   * @param     theObjectInputStream from which this object is to be read
   *            <p>
   * @exception ClassNotFoundException
   *            if any class file is not found
   * @exception IOException
   *            on any IO error
   */
  private void readObject(ObjectInputStream theObjectInputStream) throws ClassNotFoundException, IOException {
    // Restore the transient variables.
    runnit          = new Thread(this);
    // Restore the rest of the object.
    theObjectInputStream.defaultReadObject();

   }

}
