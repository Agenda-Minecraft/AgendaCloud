package cat.kiwi.minecraft.metcd.model

import org.bukkit.entity.Player

data class ServerStatus(
    val uuid: String,
    val gameType: String,
    val currentOnline: Int,
    val players: Collection<String>,
    val total: Int,
    val gameStatus: GameStatus,
    val address: String,
    val port: Int,
    val version: String,
    val meta: String
)