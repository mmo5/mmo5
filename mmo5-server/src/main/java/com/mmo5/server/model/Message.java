package com.mmo5.server.model;


import com.mmo5.server.model.messages.PlayerMove;
import com.mmo5.server.model.messages.Winner;
import com.google.common.base.MoreObjects;

public class Message {

  private final MsgType msgType;
  private final com.mmo5.server.model.messages.PlayerLoggedIn PlayerLoggedIn;
  private final com.mmo5.server.model.messages.PlayerLoggedOut PlayerLoggedOut;
  private final PlayerMove playerMove;
  private final Winner winner;

  private Message(Builder builder) {
    this.msgType = builder.msgType;
    this.PlayerLoggedIn = builder.PlayerLoggedIn;
    this.PlayerLoggedOut = builder.PlayerLoggedOut;
    this.playerMove = builder.playerMove;
    this.winner = builder.winner;
  }

  public static Builder newMessage() {
    return new Builder();
  }

  public static final class Builder {
    private MsgType msgType;
    private com.mmo5.server.model.messages.PlayerLoggedIn PlayerLoggedIn;
    private com.mmo5.server.model.messages.PlayerLoggedOut PlayerLoggedOut;
    private PlayerMove playerMove;
    private Winner winner;

    private Builder() {
    }

    public Message build() {
      return new Message(this);
    }

    public Builder msgType(MsgType msgType) {
      this.msgType = msgType;
      return this;
    }

    public Builder PlayerLoggedIn(com.mmo5.server.model.messages.PlayerLoggedIn PlayerLoggedIn) {
      this.PlayerLoggedIn = PlayerLoggedIn;
      return this;
    }

    public Builder PlayerLoggedOut(com.mmo5.server.model.messages.PlayerLoggedOut PlayerLoggedOut) {
      this.PlayerLoggedOut = PlayerLoggedOut;
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

  public com.mmo5.server.model.messages.PlayerLoggedIn getPlayerLoggedIn() {
    return PlayerLoggedIn;
  }

  public com.mmo5.server.model.messages.PlayerLoggedOut getPlayerLoggedOut() {
    return PlayerLoggedOut;
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
            .add("PlayerLoggedIn", PlayerLoggedIn)
            .add("PlayerLoggedOut", PlayerLoggedOut)
            .add("playerMove", playerMove)
            .add("winner", winner)
            .toString();
  }
}
