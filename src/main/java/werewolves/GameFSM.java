package werewolves;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

@Configuration
@EnableStateMachine
public class GameFSM extends StateMachineConfigurerAdapter<States, Events> {

  @Override
  public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
    states.withStates()
          .initial(States.Distribution)
          .state(States.Night)
          .state(States.Day)
          .end(States.Disclosure)
    .and().withStates()
          .parent(States.Distribution)
          .initial(States.RolesAssignment)
          .state(States.CupidAssignment)
          .exit(States.DistributionExit)
    .and().withStates()
          .parent(States.Night)
          .initial(States.WerewolvesDiscussion)
          .state(States.SeerDiscovery)
          .state(States.WitchActivity)
          .end(States.NightKilling)
    .and().withStates()
          .parent(States.Day)
          .initial(States.Discussion)
          .end(States.DayKilling)
    ;

  }

  @Override
  public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
    transitions.withLocal().source(States.RolesAssignment).target(States.CupidAssignment);
    transitions.withExternal().source(States.CupidAssignment).target(States.DistributionExit).event(Events.AssignCouple);
//    transitions.withLocal().source(States.DistributionExit).target(States.Night);

  }
}

enum States {
  // Major states
  Distribution, Night, Day, Disclosure,
  // Distribution substates
  RolesAssignment, CupidAssignment, DistributionExit,
  // Night substates
  WerewolvesDiscussion, SeerDiscovery, WitchActivity, NightKilling,
  // Day substates
  Discussion, DayKilling
}

enum Events {
  AssignCouple,
  E1
}
