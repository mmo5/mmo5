package com.mmo5.server.model.messages;

import com.google.common.base.Objects;

public class PlayerLoggedInRequest {

  private final Integer playerId;
  private final String playerName;

  public PlayerLoggedInRequest(Integer playerId, String playerName) {
    this.playerId = playerId;
    this.playerName = playerName;
  }

  public Integer getPlayerId() {
    return playerId;
  }

  public String getPlayerName() {
    return playerName;
  }

  @Override
  public String toString() {
    return super.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PlayerLoggedInRequest that = (PlayerLoggedInRequest) o;
    return playerId == that.playerId &&
            Objects.equal(playerName, that.playerName);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(playerId, playerName);
  }
}
