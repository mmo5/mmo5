package com.mmo5.server.manager;

import com.mmo5.server.model.BoardGame;
import com.mmo5.server.model.Message;
import com.mmo5.server.model.MsgType;
import com.mmo5.server.model.messages.PlayerLoggedIn;
import com.mmo5.server.model.messages.PlayerMove;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class GameManager {

  private final Gson gson;
  private final AtomicInteger playerCounter;
  private final Map<Session, PlayerLoggedIn> sessionPlayerMap;
  private final BoardGame boardGame;

  public GameManager() {
    this.sessionPlayerMap = Maps.newConcurrentMap();
    this.playerCounter = new AtomicInteger(0);
    this.gson = new Gson();
    this.boardGame = new BoardGame();
  }

  public void handleIncomingMessage(Session session, String jsonMsg) {
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
    broadcastMessage(Message.newMessage(MsgType.PlayerLoggedIn).PlayerLoggedIn(playerLoggedIn).build());
  }

  public void handleLogoutPlayer(Session session) {
    PlayerLoggedIn loggedOutPlayerLoggedIn = this.sessionPlayerMap.remove(session);
    System.out.println("Player logged Out: " + loggedOutPlayerLoggedIn.getPlayerId());
  }

  private void broadcastMessage(Message message) {
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

  private void handlePlayerMove(Message message) {
    broadcastMessage(message);
  }

  private void updateBoardGame(PlayerMove playerMove) {
    // TODO: this.
  }
}
