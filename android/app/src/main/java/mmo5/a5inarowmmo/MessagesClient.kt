package mmo5.a5inarowmmo

import android.os.Build
import mu.KotlinLogging
import org.java_websocket.client.WebSocketClient
import org.java_websocket.drafts.Draft_17
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import java.net.URISyntaxException
import javax.net.ssl.SSLSocketFactory


class MessagesClient() {

    private val logger = KotlinLogging.logger {}

    fun connectWebSocket(messageHandler: (String) -> Unit) {
        val uri: URI
        try {
            uri = URI("ws://mmo5.herokuapp.com/mmo5")
        } catch (e: URISyntaxException) {
            e.printStackTrace()
            return
        }

        val webSocketClient = object : WebSocketClient(uri, Draft_17()) {
            override fun onOpen(serverHandshake: ServerHandshake) {
                logger.info("Websocket Opened")
                this.send("Hello from " + Build.MANUFACTURER + " " + Build.MODEL)
            }

            override fun onMessage(s: String) {
                logger.info("Message recieved $s")
                messageHandler.invoke(s)
                val message = s
//                runOnUiThread(Runnable {
//                    val textView = findViewById(R.id.messages) as TextView
//                    textView.text = textView.text.toString() + "\n" + message
//                })
            }

            override fun onClose(i: Int, s: String, b: Boolean) {
                logger.info("Websocket Closed $s")
            }

            override fun onError(e: Exception) {
                logger.info("Websocket Error ", e)
            }

            init {
            }
        }
//        webSocketClient.setSocket(SSLSocketFactory.getDefault().createSocket(uri.host, 443))
        webSocketClient.connect()
    }
}