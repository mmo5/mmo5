package com.mmo5.server.model;

import com.mmo5.server.model.messages.PlayerMove;
import com.mmo5.server.model.messages.Position;
import com.mmo5.server.model.messages.Winner;

public class Board {

  private final int SIZE = 10;

  private Integer[][] board;

  public void initBoard() {
    this.board = new Integer[SIZE][SIZE];
  }

  public Integer getUserIdAtPosition(Position position) {
    return this.board[position.getX()][position.getY()];
  }

  public boolean validatePosition(Position position) {
    return position.getY() < SIZE &&
            position.getY() >=0 &&
            position.getX() < SIZE &&
            position.getX() >= 0;
  }

  public boolean isOccupiedByPlayer(Position position, int playerId) {
    Integer candidatePlayerId = getUserIdAtPosition(position);
    return candidatePlayerId != null && candidatePlayerId == playerId;
  }

  public boolean updatePlayerMove(PlayerMove playerMove) {
    Position position = playerMove.getPosition();
    if (validatePosition(position)) {
      return false;
    }
    if (getUserIdAtPosition(position) != null) {
      return false;
    }
    markCell(playerMove, position);
    return true;
  }


  private void markCell(PlayerMove playerMove, Position position) {
    board[position.getX()][position.getY()] = playerMove.getPlayerId();
  }

  public Winner checkWinner() {
    // TODO: this!!
    return null;
  }
}
