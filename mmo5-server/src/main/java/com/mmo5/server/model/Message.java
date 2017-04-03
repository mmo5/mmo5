package com.mmo5.server.model;


import com.google.common.base.MoreObjects;
import com.mmo5.server.model.messages.*;

import java.util.Map;

public class Message {

  private final MsgType msgType;
  private final PlayerLoggedInResponse playerLoggedInResponse;
  private final PlayerLoggedInRequest playerLoggedInRequest;
  private final PlayerLoggedOutResponse playerLoggedOutResponse;
  private final PlayerMove playerMove;
  private final Winner winner;
  private Map<Integer, String> players;

  private Message(Builder builder) {
    this.msgType = builder.msgType;
    this.playerLoggedInResponse = builder.playerLoggedInResponse;
    this.playerLoggedInRequest = builder.playerLoggedInRequest;
    this.playerMove = builder.playerMove;
    this.winner = builder.winner;
    this.players = builder.players;
    this.playerLoggedOutResponse = builder.playerLoggedOutResponse;
  }

  public static Builder newMessage(MsgType msgType) {
    return new Builder(msgType);
  }

  public static final class Builder {
    private MsgType msgType;
    private PlayerLoggedInResponse playerLoggedInResponse;
    private PlayerLoggedInRequest playerLoggedInRequest;
    private PlayerLoggedOutResponse playerLoggedOutResponse;
    private PlayerMove playerMove;
    private Winner winner;
    private Map<Integer, String> players;

    private Builder(MsgType msgType) {
      this.msgType = msgType;
    }

    public Message build() {
      return new Message(this);
    }

    public Builder playerLoggedInRequest(PlayerLoggedInResponse playerLoggedInResponse) {
      this.playerLoggedInRequest = playerLoggedInRequest;
      return this;
    }

    public Builder playerLoggedInResponse(PlayerLoggedInResponse playerLoggedInResponse) {
      this.playerLoggedInResponse = playerLoggedInResponse;
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

    public Builder players(Map<Integer, String> players) {
      this.players = players;
      return this;
    }

    public Builder playerLoggedOutResponse(PlayerLoggedOutResponse playerLoggedOutResponse) {
      this.playerLoggedOutResponse = playerLoggedOutResponse;
      return this;
    }
  }

  public MsgType getMsgType() {
    return msgType;
  }

  public PlayerLoggedInResponse getPlayerLoggedInResponse() {
    return playerLoggedInResponse;
  }

  public PlayerLoggedInRequest getPlayerLoggedInRequest() {
    return playerLoggedInRequest;
  }

  public PlayerMove getPlayerMove() {
    return playerMove;
  }

  public Winner getWinner() {
    return winner;
  }

  public Map<Integer, String> getPlayers() {
    return players;
  }

  public void setPlayers(Map<Integer, String> players) {
    this.players = players;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
            .add("msgType", msgType)
            .add("playerLoggedInResponse", playerLoggedInResponse)
            .add("playerLoggedInRequest", playerLoggedInRequest)
            .add("playerLoggedOutResponse", playerLoggedOutResponse)
            .add("playerMove", playerMove)
            .add("winner", winner)
            .add("players", players)
            .toString();
  }
}
