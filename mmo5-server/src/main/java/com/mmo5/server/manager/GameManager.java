package com.mmo5.server.manager;

import com.mmo5.server.model.Message;
import com.mmo5.server.model.MsgType;
import com.mmo5.server.model.messages.PlayerLoggedIn;
import com.mmo5.server.model.messages.PlayerMove;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.mmo5.server.model.messages.Winner;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class GameManager {

  private final Gson gson;
  private final AtomicInteger playerCounter;
  private final Map<Session, PlayerLoggedIn> sessionPlayerMap;
  private final BoardManager boardManager;

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
      case PlayerMove:
        handlePlayerMove(msg);
        break;
      default:
        System.out.println("unhandled message: " + msg);
    }
  }

  public void handleLoginPlayer(Session session) {
    PlayerLoggedIn playerLoggedIn = new PlayerLoggedIn(this.playerCounter.incrementAndGet());
    this.sessionPlayerMap.put(session, playerLoggedIn);
    System.out.println("Player logged in: " + playerLoggedIn.getPlayerId());
    sendMessage(session, Message.newMessage(MsgType.PlayerLoggedIn).playerLoggedIn(playerLoggedIn).build());
  }

  public void handleLogoutPlayer(Session session) {
    PlayerLoggedIn loggedOutPlayerLoggedIn = this.sessionPlayerMap.remove(session);
    System.out.println("Player logged Out: " + loggedOutPlayerLoggedIn.getPlayerId());
  }


  private void handlePlayerMove(Message message) {
    PlayerMove playerMove = message.getPlayerMove();
    synchronized (this.boardManager) {
      boolean isUpdated = this.boardManager.updatePlayerMove(playerMove);
      if (isUpdated) {
        broadcastMessage(message);
        Winner winner = this.boardManager.checkWinner();
        if (winner != null) {
          System.out.println("Player: " + playerMove.getPlayerId() + " Won!!");
          broadcastMessage(Message.newMessage(MsgType.Winner).winner(winner).build());
          this.boardManager.initBoard();
        }
      } else {
        System.out.println("Ignore player move: " + playerMove);
      }
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
}
