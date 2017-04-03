package game;

import java.util.ArrayList;
import java.util.List;

public class Player {

  private String name;
  private Long id;
  private List<Point> playerPoints;

  public Player(String name, Long id) {
    this.name = name;
    this.id = id;
    this.playerPoints = new ArrayList<>();
  }

  public void addPoint(Point point) {
    playerPoints.add(point);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public List<Point> getPlayerPoints() {
    return playerPoints;
  }

  public void setPlayerPoints(List<Point> playerPoints) {
    this.playerPoints = playerPoints;
  }
}
