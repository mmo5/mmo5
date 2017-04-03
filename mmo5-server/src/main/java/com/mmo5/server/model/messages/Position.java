package com.mmo5.server.model.messages;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class Position {

  private int x;
  private int y;

  public Position(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public void setX(int x) {
    this.x = x;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Position position = (Position) o;
    return x == position.x &&
            y == position.y;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(x, y);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
            .add("x", x)
            .add("y", y)
            .toString();
  }
}
