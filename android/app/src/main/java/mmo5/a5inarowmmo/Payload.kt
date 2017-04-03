package mmo5.a5inarowmmo

data class Message(val msgType: MsgType,
                   val playerLoggedIn: PlayerLoggedEvent? = null,
                   val playerLoggedOut: PlayerLoggedEvent? = null,
                   val playerMove: PlayerMove? = null,
                   val winner: Winner? = null
)

enum class MsgType {
    PlayerLoggedIn,
    PlayerLoggedOut,
    PlayerMove,
    Winner
}

data class Winner(val playerId: Int, val positions: List<Position>)
data class PlayerMove(val playerId: Int, val position: Position)
data class Position(val x: Int, val y: Int)
data class PlayerLoggedEvent(val playerId: Int)
