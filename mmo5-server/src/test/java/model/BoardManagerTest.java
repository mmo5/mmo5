package model;

import com.google.common.collect.Sets;
import com.mmo5.server.manager.BoardManager;
import com.mmo5.server.model.messages.PlayerMove;
import com.mmo5.server.model.messages.Position;
import com.mmo5.server.model.messages.Winner;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

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

  @Test
  public void testCross() {
    Position position1 = new Position(2, 2);
    PlayerMove playerMove1 = new PlayerMove(1, position1);
    Position position2 = new Position(2, 3);
    PlayerMove playerMove2 = new PlayerMove(1, position2);
    Position position3 = new Position(2, 5);
    PlayerMove playerMove3 = new PlayerMove(1, position3);
    Position position4 = new Position(2, 6);
    PlayerMove playerMove4 = new PlayerMove(1, position4);
    Position position6 = new Position(0, 4);
    PlayerMove playerMove6 = new PlayerMove(1, position6);
    Position position7 = new Position(1, 4);
    PlayerMove playerMove7 = new PlayerMove(1, position7);
    Position position8 = new Position(3, 4);
    PlayerMove playerMove8 = new PlayerMove(1, position8);
    Position position9 = new Position(4, 4);
    PlayerMove playerMove9 = new PlayerMove(1, position9);

    boardManager.updatePlayerMove(playerMove1);
    boardManager.updatePlayerMove(playerMove2);
    boardManager.updatePlayerMove(playerMove3);
    boardManager.updatePlayerMove(playerMove4);
    boardManager.updatePlayerMove(playerMove6);
    boardManager.updatePlayerMove(playerMove7);
    boardManager.updatePlayerMove(playerMove8);
    boardManager.updatePlayerMove(playerMove9);

    final Winner winnerBeforeUpdate = boardManager.checkWinner();
    Assert.assertNull(winnerBeforeUpdate);

    Position position5 = new Position(2, 4);
    PlayerMove playerMove5 = new PlayerMove(1, position5);
    boardManager.updatePlayerMove(playerMove5);

    Set ExpectedWinningPositions = new HashSet<Position>() {{
      add(position1);
      add(position2);
      add(position3);
      add(position4);
      add(position5);
      add(position6);
      add(position7);
      add(position8);
      add(position9);
    }};

    final Winner winner = boardManager.checkWinner();
    Assert.assertTrue(winner.getPlayerId() == 1);
    Assert.assertEquals(ExpectedWinningPositions, Sets.newHashSet(winner.getPositions()));
  }
}
