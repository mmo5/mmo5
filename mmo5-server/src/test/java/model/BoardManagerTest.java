package model;

import com.mmo5.server.manager.BoardManager;
import com.mmo5.server.model.messages.PlayerMove;
import com.mmo5.server.model.messages.Position;
import com.mmo5.server.model.messages.Winner;
import org.junit.Assert;
import org.junit.Test;

public class BoardManagerTest {

  final BoardManager boardManager = new BoardManager();


  @Test
  public void testVertical() {
    PlayerMove playerMove1 = new PlayerMove(1, new Position(0,4));
    PlayerMove playerMove2 = new PlayerMove(1, new Position(1,4));
    PlayerMove playerMove4 = new PlayerMove(1, new Position(3,4));
    PlayerMove playerMove5 = new PlayerMove(1, new Position(4,4));

    boardManager.updatePlayerMove(playerMove1);
    boardManager.updatePlayerMove(playerMove2);
    boardManager.updatePlayerMove(playerMove4);
    boardManager.updatePlayerMove(playerMove5);

    final Winner winnerBeforeUpdate = boardManager.checkWinner();
    Assert.assertNull(winnerBeforeUpdate);

    PlayerMove playerMove3 = new PlayerMove(1, new Position(2,4));
    boardManager.updatePlayerMove(playerMove3);
    final Winner winner = boardManager.checkWinner();
    Assert.assertTrue(winner.getPlayerId() == 1);
  }


  @Test
  public void testHorizontal() {
    PlayerMove playerMove1 = new PlayerMove(1, new Position(4,10));
    PlayerMove playerMove2 = new PlayerMove(1, new Position(4,11));
    PlayerMove playerMove4 = new PlayerMove(1, new Position(4,12));
    PlayerMove playerMove5 = new PlayerMove(1, new Position(4,14));

    boardManager.updatePlayerMove(playerMove1);
    boardManager.updatePlayerMove(playerMove2);
    boardManager.updatePlayerMove(playerMove4);
    boardManager.updatePlayerMove(playerMove5);

    final Winner winnerBeforeUpdate = boardManager.checkWinner();
    Assert.assertNull(winnerBeforeUpdate);

    PlayerMove playerMove3 = new PlayerMove(1, new Position(4,13));
    boardManager.updatePlayerMove(playerMove3);
    final Winner winner = boardManager.checkWinner();
    Assert.assertTrue(winner.getPlayerId() == 1);
  }


  @Test
  public void testDiagonal() {
    PlayerMove playerMove1 = new PlayerMove(1, new Position(0,4));
    PlayerMove playerMove2 = new PlayerMove(1, new Position(1,5));
    PlayerMove playerMove4 = new PlayerMove(1, new Position(2,6));
    PlayerMove playerMove5 = new PlayerMove(1, new Position(4,8));

    boardManager.updatePlayerMove(playerMove1);
    boardManager.updatePlayerMove(playerMove2);
    boardManager.updatePlayerMove(playerMove4);
    boardManager.updatePlayerMove(playerMove5);

    final Winner winnerBeforeUpdate = boardManager.checkWinner();
    Assert.assertNull(winnerBeforeUpdate);

    PlayerMove playerMove3 = new PlayerMove(1, new Position(3,7));
    boardManager.updatePlayerMove(playerMove3);
    final Winner winner = boardManager.checkWinner();
    Assert.assertTrue(winner.getPlayerId() == 1);
  }
}
