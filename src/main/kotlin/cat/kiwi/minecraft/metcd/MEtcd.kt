package cat.kiwi.minecraft.metcd

import cat.kiwi.minecraft.metcd.model.GameStatus

object MEtcd {
    fun modifyGateStatus(gameStatus: GameStatus) {
        MEtcdPlugin.gameStatus = gameStatus
    }
}