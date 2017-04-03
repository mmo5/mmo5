package model;

import com.mmo5.server.model.Board;
import com.mmo5.server.model.messages.PlayerMove;
import com.mmo5.server.model.messages.Position;
import com.mmo5.server.model.messages.Winner;
import org.junit.Assert;
import org.junit.Test;

public class BoardTest {

  final Board board = new Board();


  @Test
  public void testVertical() {
    PlayerMove playerMove1 = new PlayerMove(1, new Position(2,4));
    PlayerMove playerMove2 = new PlayerMove(1, new Position(3,4));
    PlayerMove playerMove4 = new PlayerMove(1, new Position(5,4));
    PlayerMove playerMove5 = new PlayerMove(1, new Position(6,4));

    board.updatePlayerMove(playerMove1);
    board.updatePlayerMove(playerMove2);
    board.updatePlayerMove(playerMove4);
    board.updatePlayerMove(playerMove5);

    final Winner winnerBeforeUpdate = board.checkWinner();
    Assert.assertNull(winnerBeforeUpdate);

    PlayerMove playerMove3 = new PlayerMove(1, new Position(4,4));
    board.updatePlayerMove(playerMove3);
    final Winner winner = board.checkWinner();
    Assert.assertTrue(winner.getPlayerId() == 1);
  }

  @Test
  public void testDiagonal() {
    PlayerMove playerMove1 = new PlayerMove(1, new Position(2,4));
    PlayerMove playerMove2 = new PlayerMove(1, new Position(3,5));
    PlayerMove playerMove4 = new PlayerMove(1, new Position(4,6));
    PlayerMove playerMove5 = new PlayerMove(1, new Position(6,8));

    board.updatePlayerMove(playerMove1);
    board.updatePlayerMove(playerMove2);
    board.updatePlayerMove(playerMove4);
    board.updatePlayerMove(playerMove5);

    final Winner winnerBeforeUpdate = board.checkWinner();
    Assert.assertNull(winnerBeforeUpdate);

    PlayerMove playerMove3 = new PlayerMove(1, new Position(5,7));
    board.updatePlayerMove(playerMove3);
    final Winner winner = board.checkWinner();
    Assert.assertTrue(winner.getPlayerId() == 1);
  }
}