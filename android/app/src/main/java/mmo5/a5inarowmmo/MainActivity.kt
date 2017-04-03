package mmo5.a5inarowmmo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.gson.Gson
import mu.KotlinLogging
import org.java_websocket.client.WebSocketClient

private val logger = KotlinLogging.logger {}

class MainActivity : AppCompatActivity() {

    var playerId: Int = -1
    lateinit var client: WebSocketClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val boardView = BoardView(this)
        setContentView(boardView)
        client = MessagesClient().connectWebSocket {
            messageString ->
            val message = Gson().fromJson(messageString, Message::class.java)
            when (message.msgType) {
                MsgType.PlayerLoggedIn -> {
                    playerId = message.playerLoggedIn?.playerId ?: throw IllegalArgumentException("malformed message $message")
                    logger.info("setting player id to $playerId")
                }
                MsgType.PlayerMove ->
                    runOnUiThread { boardView.setRectByIndex(message?.playerMove ?: throw IllegalArgumentException("malformed message $message")) }
                else -> logger.warn("!!!!! didnt handle $message")
            }
//            runOnUiThread { Toast.makeText(this, message, Toast.LENGTH_LONG).show() }
        }
    }

    fun sendTouch(matrixLocationByXy: Pair<Int, Int>) {
        val position = Position(x = matrixLocationByXy.first, y = matrixLocationByXy.second)
        val message = Gson().toJson(Message(msgType = MsgType.PlayerMove, playerMove = PlayerMove(playerId = playerId, position = position)))
        client.send(message)
    }
}
