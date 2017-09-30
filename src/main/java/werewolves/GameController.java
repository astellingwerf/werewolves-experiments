package werewolves;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

  private static Map<UUID, Game> games = new HashMap<>();

  @RequestMapping(value = "/list", method = GET)
  public Collection<Game> list() {
    return games.values();
  }

  @RequestMapping(value = "/prepare", method = POST)
  public Game prepare() {
    UUID key = UUID.randomUUID();
    while (games.containsKey(key)) {
      key = UUID.randomUUID();
    }
    Game result = new Game(key);
    games.put(key, result);
    return result;
  }

  @RequestMapping(value = "/game/{id}", method = GET)
  public Game get(@PathVariable("id") UUID id) {
    return games.get(id);
  }

  @RequestMapping(value = "/game/{id}/players", method = POST)
  public Player addPlayer(@PathVariable("id") UUID id, @RequestParam("name") String name) {
    return get(id).addPlayer(name);
  }

  @RequestMapping(value = "/game/{id}/players", method = GET)
  public List<Player> getPlayers(@PathVariable("id") UUID id) {
    return get(id).getPlayers();
  }

}
