package com.mmo5.server.manager;

import com.mmo5.server.model.BoardGame;
import com.mmo5.server.model.Message;
import com.mmo5.server.model.MsgType;
import com.mmo5.server.model.messages.PlayerLoggedIn;
import com.mmo5.server.model.messages.PlayerLoggedOut;
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
    Message<?> msg = this.gson.fromJson(jsonMsg, Message.class);
    switch (msg.getMsgType()) {
      case PlayerMove:
        handlePlayerMove((PlayerMove) msg.getData(), session);
        break;
    }
  }

  public void handleLoginPlayer(Session session) {
    PlayerLoggedIn PlayerLoggedIn = new PlayerLoggedIn(this.playerCounter.incrementAndGet());
    this.sessionPlayerMap.put(session, PlayerLoggedIn);
    sendMessage(session, new Message<com.mmo5.server.model.messages.PlayerLoggedIn>(MsgType.PlayerLogin, PlayerLoggedIn));
  }

  public void handleLogoutPlayer(Session session) {
    PlayerLoggedIn loggedOutPlayerLoggedIn = this.sessionPlayerMap.remove(session);
    Message<PlayerLoggedOut> playerLoggedOutMessage = new Message<>(MsgType.PlayerLoggedOut, new PlayerLoggedOut(loggedOutPlayerLoggedIn));
    broadcastMessage(playerLoggedOutMessage);
  }

  private void broadcastMessage(Message<?> message) {
    for (Session playerSession : this.sessionPlayerMap.keySet()) {
      sendMessage(playerSession, message);
    }
  }

  private void sendMessage(Session session, Message<?> message) {
    try {
      session.getRemote().sendString(this.gson.toJson(message));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void handlePlayerMove(PlayerMove playerMove, Session session) {
    updateBoardGame(playerMove);
    Message playerLoggedOutMessage = new Message<>(MsgType.PlayerMove, playerMove);
    broadcastMessage(playerLoggedOutMessage);
  }

  private void updateBoardGame(PlayerMove playerMove) {
    // TODO: this.
  }
}
