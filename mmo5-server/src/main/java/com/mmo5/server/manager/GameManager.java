package com.mmo5.server.manager;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.mmo5.server.model.Message;
import com.mmo5.server.model.MsgType;
import com.mmo5.server.model.messages.*;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class GameManager {

  private final Gson gson;
  private final AtomicInteger playerCounter;
  private final Map<Session, PlayerLoggedInResponse> sessionPlayerMap;
  private final BoardManager boardManager;
  private final Cache<Integer, String> playerMoveCache = CacheBuilder.newBuilder()
          .maximumSize(200)
          .expireAfterWrite(1, TimeUnit.SECONDS)
          .build();

  public GameManager() {
    this.sessionPlayerMap = Maps.newConcurrentMap();
    this.playerCounter = new AtomicInteger(0);
    this.gson = new Gson();
    this.boardManager = new BoardManager();
  }

  public void handleIncomingMessage(Session session, String jsonMsg) {
    System.out.println("handling incoming message: " + jsonMsg);
    Message msg = this.gson.fromJson(jsonMsg, Message.class);
    switch (msg.getMsgType()) {
      case PlayerLoggedInRequest:
        handlePlayerLoggedInRequestMsg(session, msg);
        break;
      case PlayerMove:
        handlePlayerMove(msg);
        break;
      default:
        System.out.println("unhandled message: " + msg);
    }
  }

  public void handlePlayerLoggedInRequestMsg(Session session, Message msg) {
    PlayerLoggedInRequest playerLoggedInRequest = msg.getPlayerLoggedInRequest();
    PlayerLoggedInResponse playerLoggedInResponse = new PlayerLoggedInResponse(this.playerCounter.incrementAndGet(), playerLoggedInRequest.getPlayerName());
    this.sessionPlayerMap.put(session, playerLoggedInResponse);
    System.out.println("Player logged in: " + playerLoggedInResponse.getPlayerName() + ", Id: " + playerLoggedInResponse.getPlayerId());
    sendMessage(session, Message.newMessage(MsgType.PlayerLoggedInResponse).playerLoggedInResponse(playerLoggedInRequest).players(getPlayers()).build());
  }

  public void handleLoginPlayer(Session session) {
    System.out.println("Client Connected.");
  }

  public void handleLogoutPlayer(Session session) {
    PlayerLoggedInResponse loggedOutPlayerLoggedInResponse = this.sessionPlayerMap.remove(session);
    PlayerLoggedOutResponse playerLoggedOutResponse = new PlayerLoggedOutResponse(loggedOutPlayerLoggedInResponse.getPlayerId(), loggedOutPlayerLoggedInResponse.getPlayerName());
    System.out.println("Player logged Out: " + playerLoggedOutResponse);
    broadcastMessage(Message.newMessage(MsgType.PlayerLoggedOutResponse).playerLoggedOutResponse(playerLoggedOutResponse).players(getPlayers()).build());
  }

  private void handlePlayerMove(Message message) {
    PlayerMove playerMove = message.getPlayerMove();
    if (this.playerMoveCache.getIfPresent(playerMove.getPlayerId()) == null) {
      this.playerMoveCache.put(playerMove.getPlayerId(), "");
      synchronized (this.boardManager) {
        boolean isUpdated = this.boardManager.updatePlayerMove(playerMove);
        if (isUpdated) {
          broadcastMessage(message);
          Winner winner = this.boardManager.checkWinner();
          if (winner != null) {
            System.out.println("Player: " + playerMove.getPlayerId() + " Won!!");
            broadcastMessage(Message.newMessage(MsgType.Winner).winner(winner).players(getPlayers()).build());
            this.boardManager.initBoard();
          }
        } else {
          System.out.println("Ignore player move: " + playerMove);
        }
      }
    } else {
      System.out.println("Throttled player move: " + playerMove);
    }
  }

  private void broadcastMessage(Message message) {
    System.out.println("Sending Msg to all players: " + message);
    for (Session playerSession : this.sessionPlayerMap.keySet()) {
      sendMessage(playerSession, message);
    }
  }

  private void sendMessage(Session session, Message message) {
    try {
      session.getRemote().sendString(this.gson.toJson(message));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private Map<Integer, String> getPlayers() {
    return this.sessionPlayerMap.values().stream().collect(Collectors.toMap(PlayerLoggedInResponse::getPlayerId, PlayerLoggedInResponse::getPlayerName));
  }
}
