package com.mmo5.server.model;

import com.mmo5.server.model.messages.PlayerMove;
import com.mmo5.server.model.messages.Position;
import com.mmo5.server.model.messages.Winner;

import java.util.ArrayList;
import java.util.List;

public class Board {

  private final int SIZE = 15;

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
          final Winner topRightWinner = checkTopRight(playerId, new Position(i, j));
          if (topRightWinner != null) {
            return topRightWinner;
          }

          final Winner rightWinner = checkRight(playerId, new Position(i, j));
          if (rightWinner != null) {
            return rightWinner;
          }
          final Winner bottomRightWinner = checkBottomRight(playerId, new Position(i, j));
          if (bottomRightWinner != null) {
            return bottomRightWinner;
          }
          final Winner bottomWinner = checkBottom(playerId, new Position(i, j));
          if (bottomWinner != null) {
            return bottomWinner;
          }
        }
      }
    }
    return null;
  }

  private Winner checkTopRight(int playerId, Position position) {
    List<Position> winPositions = new ArrayList<>(5);
    winPositions.add(position);
    for (int i = 1; i < 5; i++) {
      position.setX(position.getX()+1);
      position.setY(position.getY()-1);
      if (checkPoint(playerId, position)) {
        return null;
      }
      winPositions.add(position);
    }

    return new Winner(playerId, winPositions);
  }

  private Winner checkRight(int playerId, Position position) {
    List<Position> winPositions = new ArrayList<>(5);
    winPositions.add(position);
    for (int i = 1; i < 5; i++) {
      position.setX(position.getX()+1);
      if (checkPoint(playerId, position))
        return new Winner(playerId, null);
    }
    return new Winner(playerId, winPositions);
  }

  private boolean checkPoint(int playerId, Position position) {
    return !validatePosition(position) || !isOccupiedByPlayer(position, playerId);
  }

  private Winner checkBottomRight(int playerId, Position position) {
    List<Position> winPositions = new ArrayList<>(5);
    winPositions.add(position);
    for (int i = 1; i < 5; i++) {

      position.setX(position.getX()+1);
      position.setY(position.getY()+1);
      if (checkPoint(playerId, position))
        return new Winner(playerId, null);
    }
    return new Winner(playerId, winPositions);
  }

  private Winner checkBottom(int playerId, Position position) {
    List<Position> winPositions = new ArrayList<>(5);
    winPositions.add(position);
    for (int i = 1; i < 5; i++) {
      position.setY(position.getY()+1);
      if (checkPoint(playerId, position))
        return new Winner(playerId, null);
    }
    return new Winner(playerId, winPositions);
  }
}