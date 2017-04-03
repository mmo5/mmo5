package mmo5.a5inarowmmo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.common.collect.Maps
import com.google.gson.Gson
import mu.KotlinLogging
import org.java_websocket.client.WebSocketClient


private val logger = KotlinLogging.logger {}

class MainActivity : AppCompatActivity() {

    var playerId: Int = -1
    lateinit var client: WebSocketClient
    lateinit var playerName: String
    var lastPlayers = mapOf<Int, String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val boardView = BoardView(this)

        playerName = intent.getStringExtra("USER_NAME")

        val loginRequest = Message(msgType = MsgType.PlayerLoggedInRequest, playerLoggedInRequest = PlayerLoggedInRequest(playerName = playerName))
        client = MessagesClient().connectWebSocket(loginRequest) {
            messageString ->
            val message = Gson().fromJson(messageString, Message::class.java)
            when (message.msgType) {
                MsgType.Winner -> {
                    runOnUiThread { boardView.announceWinner(message.winner!!) }
                    runOnUiThread {
                        val winNotice = if (message.winner?.playerId == playerId) "You won!!!" else "You Loose :-("
                        Toast.makeText(this, winNotice, Toast.LENGTH_LONG).show()
                    }
                }
                MsgType.PlayerLoggedInResponse -> {
                    if (message.playerLoggedInResponse?.playerName != playerName) {
                        logger.warn("wrong username $playerName != ${message.playerLoggedInResponse?.playerName}")
                    }
                    playerId = message.playerLoggedInResponse?.playerId ?: throw IllegalArgumentException("malformed message $message")
                    logger.info("setting player id to $playerId")
                }
                MsgType.PlayerMove ->
                    runOnUiThread { boardView.setRectByIndex(message?.playerMove ?: throw IllegalArgumentException("malformed message $message")) }
                else -> logger.warn("!!!!! didnt handle $message")
            }
            message.players?.let { newPlayersMap ->
                val difference = Maps.difference(lastPlayers, newPlayersMap)
                difference.entriesOnlyOnRight().forEach { key, value -> runOnUiThread {Toast.makeText(this, "$value joined", Toast.LENGTH_SHORT).show() }}
                difference.entriesOnlyOnLeft().forEach { key, value -> runOnUiThread {Toast.makeText(this, "$value dropped", Toast.LENGTH_SHORT).show() }}
                lastPlayers = newPlayersMap
            }
        }
        setContentView(boardView)

    }

    fun sendTouch(matrixLocationByXy: Pair<Int, Int>) {
        val position = Position(x = matrixLocationByXy.first, y = matrixLocationByXy.second)
        val message = Message(msgType = MsgType.PlayerMove, playerMove = PlayerMove(playerId = playerId, position = position))
        val messageString = Gson().toJson(message)
        logger.info("sending sendTouch request $message")
        client.send(messageString)
    }
}
