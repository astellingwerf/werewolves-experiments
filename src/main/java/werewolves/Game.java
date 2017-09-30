package werewolves;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Game {
  private final UUID id;
  private final List<Player> players = new ArrayList<>();

  public Game(UUID id) {
    this.id = id;
  }

  public UUID getId() {
    return id;
  }

  public List<Player> getPlayers() {
    return players;
  }

  public Player addPlayer(String name) {
    Player p = new Player(name);
    players.add(p);
    return p;
  }
}
