package com.mmo5.server.model.messages;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class PlayerMove {
  private final int playerId;
  private final Position position;

  public PlayerMove(int playerId, Position position) {
    this.playerId = playerId;
    this.position = position;
  }

  public int getPlayerId() {
    return playerId;
  }

  public Position getPosition() {
    return position;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PlayerMove that = (PlayerMove) o;
    return playerId == that.playerId &&
            Objects.equal(position, that.position);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(playerId, position);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
            .add("playerId", playerId)
            .add("position", position)
            .toString();
  }
}
