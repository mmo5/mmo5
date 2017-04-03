package com.mmo5.server.manager

import com.mmo5.server.model.Position
import com.mmo5.server.model.messages.PlayerMove
import com.mmo5.server.model.messages.Winner

class BoardManagerV2(val boardSize: Int = 15, val winSeq: Int = 5) {

    private val NO_WINNER = -99
    private val NO_PLAYER = -1

    private var board: MutableList<MutableList<Int>> = (1..boardSize).map { (1..boardSize).map { NO_PLAYER }.toMutableList() }.toMutableList()

    fun initBoard() {
        board = (1..boardSize).map { (1..boardSize).map { NO_PLAYER }.toMutableList() }.toMutableList()
    }

    fun getPlayersMove(): List<PlayerMove> {
        val playerMoves = arrayListOf<PlayerMove>()
        for ((x, list) in board.withIndex()) {
            for ((y, playerId) in list.withIndex()) {
                if (playerId != -1) {
                    playerMoves.add(PlayerMove(playerId, Position(x, y)))
                }
            }
        }
        return playerMoves
    }

    fun getUserIdAtPosition(position: Position): Int {
        return this.board[position.x][position.y]
    }

    fun isValidPosition(position: Position): Boolean {
        return position.y in 0..(boardSize - 1) && position.x in 0..(boardSize - 1)
    }

    fun isOccupiedByPlayer(position: Position, playerId: Int): Boolean {
        return getUserIdAtPosition(position) == playerId
    }

    @Synchronized
    fun updatePlayerMove(playerMove: PlayerMove): Boolean {
        val position = playerMove.position
        if (!isValidPosition(position)) {
            return false
        }
        if (getUserIdAtPosition(position) != NO_PLAYER) {
            return false
        }
        board[position.x][position.y] = playerMove.playerId
        return true
    }

    fun checkWinner(): Winner? {
        val winnerPositions = mutableListOf<Position>()
        var winnerId = NO_WINNER

        for (i in 0..boardSize - 1) {
            for (j in 0..boardSize - 1) {
                val playerId = board[i][j]
                if (playerId != NO_PLAYER) {
                    val currentPosition = Position(i, j)
                    winnerPositions.addAll(checkTopRight(playerId, currentPosition, listOf(currentPosition)))
                    winnerPositions.addAll(checkRight(playerId, currentPosition, listOf(currentPosition)))
                    winnerPositions.addAll(checkBottomRight(playerId, currentPosition, listOf(currentPosition)))
                    winnerPositions.addAll(checkBottom(playerId, currentPosition, listOf(currentPosition)))
                    if (!winnerPositions.isEmpty() && winnerId == NO_WINNER) {
                        winnerId = playerId
                    }
                }
            }
        }

        return if (winnerId == NO_WINNER) null else Winner(winnerId, winnerPositions.toSet().toList())
    }

    private fun checkBottomRight(playerId: Int, position: Position, currentListOfPositions: List<Position>): List<Position> {
        val newPosition = Position(position.x + 1, position.y + 1)
        if (!isValidPosition(newPosition) || getUserIdAtPosition(newPosition) != playerId) {//stop searching
            if (currentListOfPositions.size >= winSeq) {
                return currentListOfPositions
            } else {
                return emptyList()
            }
        }
        return checkBottomRight(playerId, newPosition, currentListOfPositions + listOf(newPosition))
    }

    private fun checkTopRight(playerId: Int, position: Position, currentListOfPositions: List<Position>): List<Position> {
        val newPosition = Position(position.x + 1, position.y - 1)
        if (!isValidPosition(newPosition) || getUserIdAtPosition(newPosition) != playerId) {//stop searching
            if (currentListOfPositions.size >= winSeq) {
                return currentListOfPositions
            } else {
                return emptyList()
            }
        }
        return checkTopRight(playerId, newPosition, currentListOfPositions + listOf(newPosition))
    }

    private fun checkRight(playerId: Int, position: Position, currentListOfPositions: List<Position>): List<Position> {
        val newPosition = Position(position.x + 1, position.y)
        if (!isValidPosition(newPosition) || getUserIdAtPosition(newPosition) != playerId) {//stop searching
            if (currentListOfPositions.size >= winSeq) {
                return currentListOfPositions
            } else {
                return emptyList()
            }
        }
        return checkRight(playerId, newPosition, currentListOfPositions + listOf(newPosition))
    }

    private fun checkBottom(playerId: Int, position: Position, currentListOfPositions: List<Position>): List<Position> {
        val newPosition = Position(position.x, position.y + 1)
        if (!isValidPosition(newPosition) || getUserIdAtPosition(newPosition) != playerId) {//stop searching
            if (currentListOfPositions.size >= winSeq) {
                return currentListOfPositions
            } else {
                return emptyList()
            }
        }
        return checkBottom(playerId, newPosition, currentListOfPositions + listOf(newPosition))
    }


}
