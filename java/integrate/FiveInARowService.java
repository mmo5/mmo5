package game;

public class FiveInARowService {

  public boolean hasFiveInARow(Player player, Board board) {
    for (Point point : player.getPlayerPoints()) {

      final boolean isWinner = checkTopRight(player.getId(), new Point(point), board) ||
              checkRight(player.getId(), new Point(point), board) ||
              checkBottomRight(player.getId(), new Point(point), board) ||
              checkBottom(player.getId(), new Point(point), board);

      if (isWinner) return true;
    }
      return false;
  }


  private boolean checkTopRight(Long playerId, Point point, Board board) {

    for (int i = 1; i < 5; i++) {
      point.setX(point.getX()+1);
      point.setY(point.getY()-1);
      if (checkPoint(playerId, point, board)) return false;
    }
    return true;
  }

  private boolean checkRight(Long playerId, Point point, Board board) {

    for (int i = 1; i < 5; i++) {
      point.setX(point.getX()+1);
      if (checkPoint(playerId, point, board)) return false;
    }
    return true;
  }

  private boolean checkPoint(Long playerId, Point point, Board board) {
    if (!board.contains(point) || !board.isOccupiedByPlayer(point, playerId)) {
      return true;
    }
    return false;
  }

  private boolean checkBottomRight(Long playerId, Point point, Board board) {

    for (int i = 1; i < 5; i++) {
      point.setX(point.getX()+1);
      point.setY(point.getY()+1);
      if (checkPoint(playerId, point, board)) return false;
    }
    return true;
  }

  private boolean checkBottom(Long playerId, Point point, Board board) {

    for (int i = 1; i < 5; i++) {
      point.setY(point.getY()+1);
      if (checkPoint(playerId, point, board)) return false;
    }
    return true;
  }
}
