package com.mmo5.server.controller;


import com.mmo5.server.manager.GameManager;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@WebSocket
public class WebSocketHandler {

  private final GameManager gameManager;

  public WebSocketHandler(GameManager gameManager) {
    this.gameManager = gameManager;
  }

  @OnWebSocketConnect
  public void onConnect(Session session) throws Exception {
    this.gameManager.handleLoginPlayer(session);
  }

  @OnWebSocketClose
  public void onClose(Session session, int statusCode, String reason) {
    this.gameManager.handleLogoutPlayer(session);
  }

  @OnWebSocketMessage
  public void onMessage(Session session, String message) {
    this.gameManager.handleIncomingMessage(session, message);
  }


}
