package com.mmo5.server.manager

import com.mmo5.server.model.Position
import com.mmo5.server.model.messages.PlayerMove
import com.mmo5.server.model.messages.Winner
import org.junit.Assert.*
import org.junit.Test

class BoardManagerV2Test {


    @Test fun winToRight() {
        val tested = BoardManagerV2(boardSize = 3, winSeq = 2)
        tested.updatePlayerMove(PlayerMove(1, Position(0, 0)))
        tested.updatePlayerMove(PlayerMove(1, Position(1, 0)))
        val winner = tested.checkWinner()
        validateWinner(setOf(Position(0, 0), Position(1, 0)), winner)
    }

    private fun validateWinner(winSet: Set<Position>, winner: Winner?) {
        if (winner == null) {
            fail()
        } else {
            assertEquals(1, winner.playerId)
            assertEquals(winSet, winner.positions.toSet())
        }
    }

    @Test fun winToBottom() {
        val tested = BoardManagerV2(boardSize = 3, winSeq = 2)
        tested.updatePlayerMove(PlayerMove(1, Position(0, 0)))
        tested.updatePlayerMove(PlayerMove(1, Position(0, 1)))
        val winner = tested.checkWinner()
        validateWinner(setOf(Position(0, 0), Position(0, 1)), winner)
    }

    @Test fun winToTopRight() {
        val tested = BoardManagerV2(boardSize = 3, winSeq = 2)
        tested.updatePlayerMove(PlayerMove(1, Position(1, 0)))
        tested.updatePlayerMove(PlayerMove(1, Position(0, 1)))
        val winner = tested.checkWinner()
        validateWinner(setOf(Position(0, 1), Position(1, 0)), winner)
    }

    @Test fun winToBottomRight() {
        val tested = BoardManagerV2(boardSize = 3, winSeq = 2)
        tested.updatePlayerMove(PlayerMove(1, Position(0, 0)))
        tested.updatePlayerMove(PlayerMove(1, Position(1, 1)))
        val winner = tested.checkWinner()
        validateWinner(setOf(Position(0, 0), Position(1, 1)), winner)
    }

    @Test fun `winToRight - More Than 2`() {
        val tested = BoardManagerV2(boardSize = 3, winSeq = 2)
        tested.updatePlayerMove(PlayerMove(1, Position(0, 0)))
        tested.updatePlayerMove(PlayerMove(1, Position(1, 0)))
        tested.updatePlayerMove(PlayerMove(1, Position(2, 0)))
        val winner = tested.checkWinner()
        validateWinner(setOf(Position(0, 0), Position(1, 0), Position(2, 0)), winner)
    }

    @Test fun `winToRight - real size board`() {
        val tested = BoardManagerV2()
        tested.updatePlayerMove(PlayerMove(1, Position(0, 0)))
        tested.updatePlayerMove(PlayerMove(1, Position(1, 0)))
        tested.updatePlayerMove(PlayerMove(1, Position(2, 0)))
        tested.updatePlayerMove(PlayerMove(1, Position(3, 0)))
        tested.updatePlayerMove(PlayerMove(1, Position(4, 0)))
        val winner = tested.checkWinner()
        validateWinner(setOf(Position(0, 0), Position(1, 0), Position(2, 0), Position(3, 0), Position(4, 0)), winner)
    }

    @Test fun `no winner - real size board`() {
        val tested = BoardManagerV2()
        tested.updatePlayerMove(PlayerMove(1, Position(0, 0)))
        tested.updatePlayerMove(PlayerMove(1, Position(1, 0)))
        tested.updatePlayerMove(PlayerMove(1, Position(3, 0)))
        tested.updatePlayerMove(PlayerMove(1, Position(4, 0)))
        val winner = tested.checkWinner()
        assertTrue(winner == null)
    }

    @Test fun `win in complex shape`() {
        val tested = BoardManagerV2(boardSize = 3, winSeq = 2)
        tested.updatePlayerMove(PlayerMove(1, Position(0, 0)))
        tested.updatePlayerMove(PlayerMove(1, Position(1, 0)))
        tested.updatePlayerMove(PlayerMove(1, Position(0, 1)))
        val winner = tested.checkWinner()
        validateWinner(setOf(Position(0, 0), Position(1, 0), Position(0, 1)), winner)
    }
}
