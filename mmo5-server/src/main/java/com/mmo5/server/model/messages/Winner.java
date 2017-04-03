package com.mmo5.server.model.messages;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.util.List;

public class Winner {

  private final int playerId;
  private final List<Position> positions;

  public Winner(int playerId, List<Position> positions) {
    this.playerId = playerId;
    this.positions = positions;
  }

  public int getPlayerId() {
    return playerId;
  }

  public List<Position> getPositions() {
    return positions;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Winner winner = (Winner) o;
    return playerId == winner.playerId &&
            Objects.equal(positions, winner.positions);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(playerId, positions);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
            .add("playerId", playerId)
            .add("positions", positions)
            .toString();
  }


}
