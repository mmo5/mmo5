package com.mmo5.server.model;


import com.google.common.base.MoreObjects;
import com.mmo5.server.model.messages.PlayerLoggedIn;
import com.mmo5.server.model.messages.PlayerLoggedOut;
import com.mmo5.server.model.messages.PlayerMove;
import com.mmo5.server.model.messages.Winner;

public class Message {

  private final MsgType msgType;
  private final PlayerLoggedIn playerLoggedIn;
  private final PlayerLoggedOut playerLoggedOut;
  private final PlayerMove playerMove;
  private final Winner winner;

  private Message(Builder builder) {
    this.msgType = builder.msgType;
    this.playerLoggedIn = builder.playerLoggedIn;
    this.playerLoggedOut = builder.playerLoggedOut;
    this.playerMove = builder.playerMove;
    this.winner = builder.winner;
  }

  public static Builder newMessage(MsgType msgType) {
    return new Builder(msgType);
  }

  public static final class Builder {
    private MsgType msgType;
    private PlayerLoggedIn playerLoggedIn;
    private PlayerLoggedOut playerLoggedOut;
    private PlayerMove playerMove;
    private Winner winner;

    private Builder(MsgType msgType) {
      this.msgType = msgType;
    }

    public Message build() {
      return new Message(this);
    }

    public Builder playerLoggedIn(PlayerLoggedIn playerLoggedIn) {
      this.playerLoggedIn = playerLoggedIn;
      return this;
    }

    public Builder playerLoggedOut(PlayerLoggedOut playerLoggedOut) {
      this.playerLoggedOut = playerLoggedOut;
      return this;
    }

    public Builder playerMove(PlayerMove playerMove) {
      this.playerMove = playerMove;
      return this;
    }

    public Builder winner(Winner winner) {
      this.winner = winner;
      return this;
    }
  }

  public MsgType getMsgType() {
    return msgType;
  }

  public PlayerLoggedIn getPlayerLoggedIn() {
    return playerLoggedIn;
  }

  public PlayerLoggedOut getPlayerLoggedOut() {
    return playerLoggedOut;
  }

  public PlayerMove getPlayerMove() {
    return playerMove;
  }

  public Winner getWinner() {
    return winner;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
            .add("msgType", msgType)
            .add("playerLoggedIn", playerLoggedIn)
            .add("playerLoggedOut", playerLoggedOut)
            .add("playerMove", playerMove)
            .add("winner", winner)
            .toString();
  }
}
