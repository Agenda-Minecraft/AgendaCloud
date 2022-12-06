package cat.kiwi.minecraft.acloud.config

import cat.kiwi.minecraft.acloud.AgendaCloudPlugin
import java.net.URI

object Config {
    var debug = false
    var ttl = 10L
    var useDisplayName = false

    lateinit var consulUrl: String
    lateinit var gameType: String
    lateinit var displayName: String

    fun readConfig() {
        val instance = AgendaCloudPlugin.instance
        val logger = instance.logger

        instance.saveDefaultConfig()
        val unFindConfig = mutableListOf<String>()
        try {
            debug = instance.config.getBoolean("debug")
            consulUrl = instance.config.getString("consul.url").toString()
            ttl = instance.config.getLong("consul.ttl")

            gameType = instance.config.getString("gameType") ?: "unknown"
            displayName = instance.config.getString("displayName") ?: "unknown"
            useDisplayName = instance.config.getBoolean("useDisplayName")

        } catch (e: Exception) {
            e.printStackTrace()
            logger.warning("[UltimateInventoryShop]Config file is not correct!")
            logger.warning("[UltimateInventoryShop]配置文件读取失败")
            instance.onDisable()
        }
        if (unFindConfig.isNotEmpty()) {
            instance.logger.warning("未找到以下配置项(Unable to find these config): ${unFindConfig.joinToString(", ")}")
        }
    }
}