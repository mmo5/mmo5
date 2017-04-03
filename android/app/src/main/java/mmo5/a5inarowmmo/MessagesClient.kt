package mmo5.a5inarowmmo

import com.google.gson.Gson
import mu.KotlinLogging
import org.java_websocket.client.WebSocketClient
import org.java_websocket.drafts.Draft_17
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import java.net.URISyntaxException


private val logger = KotlinLogging.logger {}

class MessagesClient {


    fun connectWebSocket(loginRequest: Message, messageHandler: (String) -> Unit): SelfRecoverWebSocketHolder {
        return SelfRecoverWebSocketHolder(loginRequest, messageHandler)
    }
}

class SelfRecoverWebSocketHolder(val loginRequest: Message, val messageHandler: (String) -> Unit) {

    private var websocket = createWebSocketClient()

    private fun createWebSocketClient(): WebSocketClient {
        val uri: URI
        try {
            uri = URI("ws://mmo5.herokuapp.com/mmo5")
        } catch (e: URISyntaxException) {
            throw e
        }

        val webSocketClient = object : WebSocketClient(uri, Draft_17()) {
            override fun onOpen(serverHandshake: ServerHandshake) {
                logger.info("Websocket Opened")
                logger.info("sending login request $loginRequest")
                tryAndReconnect { send(Gson().toJson(loginRequest)) }
            }

            override fun onMessage(s: String) {
                logger.info("Message recieved $s")
                messageHandler.invoke(s)
            }

            override fun onClose(i: Int, s: String, b: Boolean) {
                logger.info("Websocket Closed $s")
                websocket = createWebSocketClient()
            }

            override fun onError(e: Exception) {
                logger.info("Websocket Error ", e)
                websocket = createWebSocketClient()
            }

            init {
            }
        }
        webSocketClient.connect()
        return webSocketClient
    }

    private fun tryAndReconnect(function: () -> Unit) {
        try {
            function.invoke()
        } catch (e: Exception) {
            logger.warn("websocket execution failed", e)
            websocket = createWebSocketClient()
        }
    }

    fun send(messageString: String) = tryAndReconnect { websocket.send(messageString) }

}