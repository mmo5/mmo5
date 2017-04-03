package com.mmo5.server.model;

import com.mmo5.server.model.messages.PlayerMove;
import com.mmo5.server.model.messages.Position;
import com.mmo5.server.model.messages.Winner;

public class Board {

  private final int SIZE = 10;

  private Integer[][] board;

  public Board() {
   initBoard();
  }

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
    if (!validatePosition(position)) {
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
    return hasFiveInARow();
  }

  private Winner hasFiveInARow() {
    for (int i = 0; i < SIZE; i++) {
      for (int j = 0; j < SIZE; j++) {
        Integer playerId = board[i][j];
        if (playerId != null) {
          final boolean isWinner = checkTopRight(playerId, new Position(i, j)) ||
                  checkRight(playerId, new Position(i, j)) ||
                  checkBottomRight(playerId, new Position(i, j)) ||
                  checkBottom(playerId, new Position(i, j));
          if (isWinner) {
            return new Winner(playerId, null);
          }
        }
      }
    }
    return null;
  }

  private boolean checkTopRight(int playerId, Position position) {
    for (int i = 1; i < 5; i++) {
      position.setX(position.getX()+1);
      position.setY(position.getY()-1);
      if (checkPoint(playerId, position))
        return false;
    }
    return true;
  }

  private boolean checkRight(int playerId, Position position) {
    for (int i = 1; i < 5; i++) {
      position.setX(position.getX()+1);
      if (checkPoint(playerId, position))
        return false;
    }
    return true;
  }

  private boolean checkPoint(int playerId, Position position) {
    return !isOccupiedByPlayer(position, playerId);
  }

  private boolean checkBottomRight(int playerId, Position position) {
    for (int i = 1; i < 5; i++) {
      position.setX(position.getX()+1);
      position.setY(position.getY()+1);
      if (checkPoint(playerId, position))
        return false;
    }
    return true;
  }

  private boolean checkBottom(int playerId, Position position) {
    for (int i = 1; i < 5; i++) {
      position.setY(position.getY()+1);
      if (checkPoint(playerId, position))
        return false;
    }
    return true;
  }
}