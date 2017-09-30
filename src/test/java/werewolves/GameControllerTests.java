/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package werewolves;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GameControllerTests {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private GameController controller;

//    @Test
//    public void noParamGreetingShouldReturnDefaultMessage() throws Exception {
//
//        MvcResult g = this.mockMvc.perform(post("/prepare")).andDo(print()).andExpect(status().isOk())
//
//                                  .andReturn();
//        System.out.println(g);
//        ModelAndViewAssert.assertAndReturnModelAttributeOfType(g.getModelAndView(), "Game", Game.class);
//    }

  @Test
  public void paramGreetingShouldReturnTailoredMessage() throws Exception {
    Game g = controller.prepare();
    controller.addPlayer(g.getId(), "Anne");
    assertThat(controller.get(g.getId()).getPlayers(), contains(playerCalled("Anne")));
  }

  private static TypeSafeMatcher<Player> playerCalled(String name) {
    return new TypeSafeMatcher<Player>() {

      @Override
      public void describeTo(Description description) {

      }

      @Override
      protected boolean matchesSafely(Player item) {
        return item.getName().equals(name);
      }
    };
  }

}
