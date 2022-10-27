package cat.kiwi.minecraft.metcd

import cat.kiwi.minecraft.metcd.model.GameStatus

interface MEtcd {
    fun modifyGateStatus(gameStatus: GameStatus) {
        MEtcdPlugin.gameStatus = gameStatus
    }
}