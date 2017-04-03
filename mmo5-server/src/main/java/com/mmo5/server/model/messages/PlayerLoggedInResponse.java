package com.mmo5.server.model.messages;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class PlayerLoggedInResponse {

  private final int playerId;
  private final String playerName;

  public PlayerLoggedInResponse(int playerId, String playerName) {
    this.playerId = playerId;
    this.playerName = playerName;
  }

  public int getPlayerId() {
    return playerId;
  }

  public String getPlayerName() {
    return playerName;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
            .add("playerId", playerId)
            .add("playerName", playerName)
            .toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PlayerLoggedInResponse that = (PlayerLoggedInResponse) o;
    return playerId == that.playerId &&
            Objects.equal(playerName, that.playerName);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(playerId, playerName);
  }
}
