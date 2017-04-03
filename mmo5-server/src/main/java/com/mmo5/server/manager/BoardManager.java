package com.mmo5.server.manager;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mmo5.server.model.messages.PlayerMove;
import com.mmo5.server.model.messages.Position;
import com.mmo5.server.model.messages.Winner;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BoardManager {

  private final int SIZE = 15;

  private Integer[][] board;

  public BoardManager() {
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
    for (int i = 0; i < SIZE; i++) {
      for (int j = 0; j < SIZE; j++) {
        Integer playerId = board[i][j];
        if (playerId != null) {
          Set<Position> positions = Sets.newHashSet();
          positions.addAll(checkTopRight(playerId, new Position(i, j)));
          positions.addAll(checkRight(playerId, new Position(i, j)));
          positions.addAll(checkBottomRight(playerId, new Position(i, j)));
          positions.addAll(checkBottom(playerId, new Position(i, j)));
          return new Winner(playerId, Lists.newArrayList(positions));
        }
      }
    }
    return null;
  }

  private List<Position> checkTopRight(int playerId, Position position) {
    List<Position> winPositions = new ArrayList<>(5);
    winPositions.add(position);
    for (int i = 1; i < 5; i++) {
      position.setX(position.getX()+1);
      position.setY(position.getY()-1);
      if (isNotValidPositionToWin(playerId, position)) {
        return Lists.newArrayList();
      }
      winPositions.add(position);
    }

    return winPositions;
  }

  private List<Position> checkRight(int playerId, Position position) {
    List<Position> winPositions = new ArrayList<>(5);
    winPositions.add(position);
    for (int i = 1; i < 5; i++) {
      position.setX(position.getX()+1);
      if (isNotValidPositionToWin(playerId, position)) {
        return Lists.newArrayList();
      }
    }
    return winPositions;
  }

  private boolean isNotValidPositionToWin(int playerId, Position position) {
    return !validatePosition(position) || !isOccupiedByPlayer(position, playerId);
  }

  private List<Position> checkBottomRight(int playerId, Position position) {
    List<Position> winPositions = new ArrayList<>(5);
    winPositions.add(position);
    for (int i = 1; i < 5; i++) {
      position.setX(position.getX()+1);
      position.setY(position.getY()+1);
      if (isNotValidPositionToWin(playerId, position)) {
        return Lists.newArrayList();
      }
    }
    return winPositions;
  }

  private List<Position> checkBottom(int playerId, Position position) {
    List<Position> winPositions = new ArrayList<>(5);
    winPositions.add(position);
    for (int i = 1; i < 5; i++) {
      position.setY(position.getY()+1);
      if (isNotValidPositionToWin(playerId, position)) {
        return Lists.newArrayList();
      }
    }
    return winPositions;
  }
}