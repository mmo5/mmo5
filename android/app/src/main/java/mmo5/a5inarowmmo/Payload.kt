package mmo5.a5inarowmmo

data class Message(val msgType: MsgType,
                   val playerLoggedInResponse: PlayerLoggedInResponse? = null,
                   val playerLoggedInRequest: PlayerLoggedInRequest? = null,
                   val playerMove: PlayerMove? = null,
                   val winner: Winner? = null,
                   val players: Map<Int, String>? = null
)

enum class MsgType {
    PlayerLoggedInRequest,
    PlayerLoggedInResponse,
    PlayerLoggedOutResponse,
    PlayerMove,
    Winner
}

data class Winner(val playerId: Int, val positions: List<Position>?)
data class PlayerMove(val playerId: Int, val position: Position)
data class Position(val x: Int, val y: Int)
data class PlayerLoggedInResponse(val playerId: Int, val playerName: String)
data class PlayerLoggedInRequest(val playerName: String)
