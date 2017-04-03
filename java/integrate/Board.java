package game;

import java.util.ArrayList;

public class Board {

  private final int SIZE = 10;
  private ArrayList<ArrayList<Long>> board;


  public Board() {
    board = new ArrayList<>();
    for (int i = 0; i < SIZE; i++) {
      final ArrayList<Long> array = new ArrayList<>(SIZE);
      for (int j = 0; j < SIZE; j++) {
        array.add(null);
      }
      board.add(array);
    }
  }

  public ArrayList<ArrayList<Long>> getBoard() {
    return board;
  }

  public boolean isOccupied(Point point) {
      return getVal(point) != null;
  }

  private Long getVal(Point point) {
    return board.get(point.getY()).get(point.getX());
  }

  public boolean isOccupiedByPlayer(Point point, Long playerId) {
    return isOccupied(point) && playerId.equals(getVal(point));
  }

  public void markCell(Point point, Long playerId) {
    board.get(point.getY()).set(point.getX(), playerId);
  }

  public boolean contains(Point point) {
    return point.getY() < SIZE &&
            point.getY() >=0 &&
            point.getX() < SIZE &&
            point.getX() >= 0;
  }

}
