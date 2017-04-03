package com.mmo5.server.model.messages;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class PlayerLoggedInRequest {

  private final String playerName;

  public PlayerLoggedInRequest(String playerName) {
    this.playerName = playerName;
  }

  public String getPlayerName() {
    return playerName;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
            .add("playerName", playerName)
            .toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PlayerLoggedInRequest that = (PlayerLoggedInRequest) o;
    return Objects.equal(playerName, that.playerName);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(playerName);
  }
}
