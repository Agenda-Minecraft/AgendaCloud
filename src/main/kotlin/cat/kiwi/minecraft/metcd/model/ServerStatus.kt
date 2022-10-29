package cat.kiwi.minecraft.metcd.model


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