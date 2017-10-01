package werewolves;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.statemachine.ObjectStateMachine;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineSystemConstants;
import org.springframework.statemachine.state.AbstractState;

public class GameFSMTest {
  private AnnotationConfigApplicationContext context;

  private StateMachine<States, Events> machine;


  @Test
  public void playGame() {
    System.out.println("===============================");
    machine.start();
    assertStates(States.Distribution, States.CupidAssignment);

    sendToSubmachine(Events.AssignCouple);
    machine.sendEvent(Events.AssignCouple);
    assertState(States.Night);
    System.out.println("===============================");
  }

  public void assertStates(States... states) {
    assertState(states[0]);
    for (int i = 1; i < states.length; i++) {
      assertTrue(machine.getState() instanceof AbstractState);
      assertState(states[i], ((AbstractState<States, Events>) machine.getState()).getSubmachine());
    }
  }

  public void sendToSubmachine(Events message) {
    assertTrue(machine.getState() instanceof AbstractState);
    ((AbstractState<States, Events>) machine.getState()).getSubmachine().sendEvent(message);
  }

  public void assertState(States state, StateMachine machine) {
    assertEquals(state, machine.getState().getId());
  }

  public void assertState(States state) {
    assertState(state, machine);
  }

  static class Config {

    @Autowired
    private StateMachine<States, Events> machine;

//    @Bean
//    public StateMachineListener<States, Events> stateMachineListener() {
//      TestListener listener = new TestListener();
//      machine.addStateListener(listener);
//      return listener;
//    }
  }


  @SuppressWarnings("unchecked")
  @Before
  public void setup() {
    context = new AnnotationConfigApplicationContext();
    context.register(Application.class, Config.class);
    context.refresh();
    machine = context.getBean(StateMachineSystemConstants.DEFAULT_ID_STATEMACHINE, ObjectStateMachine.class);
    machine.start();
  }

  @After
  public void clean() {
    machine.stop();
    context.close();
    context = null;
    machine = null;
  }


}
