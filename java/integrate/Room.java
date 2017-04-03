package game;

import java.util.Map;

public class Room {

  private final Map<Long, Player> players;
  private Board board = new Board();
  private boolean gameInSession = false;
  private FiveInARowService fiveInARowService = new FiveInARowService();

  public Room(Map<Long, Player> players, Board board, boolean gameInSession) {
    this.players = players;
    this.board = board;
    this.gameInSession = gameInSession;
  }

  public synchronized Response makeMove(Long playerId, Point point) {
    if (board.isOccupied(point)) {
      return new Response(false, false);
    }

    board.markCell(point, playerId);

    Player player = players.get(playerId);
    player.getPlayerPoints().add(point);

    return new Response(true, fiveInARowService.hasFiveInARow(player, board));
  }

  public void addPlayer(Long playerId, String name) {
    this.players.put(playerId, new Player(name, playerId));
  }


}
