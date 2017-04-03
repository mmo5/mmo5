package com.mmo5.server.model.messages;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class PlayerLoggedOut {

  private final int playerId;

  public PlayerLoggedOut(int playerId) {
    this.playerId = playerId;
  }

  public int getPlayerId() {
    return playerId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PlayerLoggedOut PlayerLoggedOut = (com.mmo5.server.model.messages.PlayerLoggedOut) o;
    return playerId == PlayerLoggedOut.playerId;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(playerId);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
            .add("playerId", playerId)
            .toString();
  }

}
