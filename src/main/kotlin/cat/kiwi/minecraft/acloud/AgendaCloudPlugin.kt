package cat.kiwi.minecraft.acloud

import cat.kiwi.minecraft.acloud.command.ACloudCommands
import cat.kiwi.minecraft.acloud.config.Config
import cat.kiwi.minecraft.acloud.listener.PlayerEvents
import cat.kiwi.minecraft.acloud.model.GameStatus
import cat.kiwi.minecraft.acloud.model.ServerStatus
import cat.kiwi.minecraft.acloud.service.ReportService
import com.orbitz.consul.AgentClient
import com.orbitz.consul.Consul
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

class AgendaCloudPlugin : JavaPlugin() {
    companion object {
        @JvmStatic
        lateinit var consulClient: Consul
        lateinit var consulAgentClient: AgentClient
        lateinit var instance: AgendaCloudPlugin
        lateinit var serviceUUID: String
        lateinit var serverStatus: ServerStatus
        var gameStatus: GameStatus = GameStatus.WAITING
            set(value) {
                ReportService.reportStatus()
                field = value
            }
    }


    override fun onEnable() {
        instance = this
        serviceUUID = UUID.randomUUID().toString()

        try {
            Config.readConfig()
            consulClient = Consul.builder().withUrl(Config.consulUrl).build()
            consulAgentClient = consulClient.agentClient()
        } catch (e: Exception) {
            e.printStackTrace()
            onDisable()
        }
        getCommand("acloud")?.setExecutor(ACloudCommands())

        gameStatus = GameStatus.WAITING

        server.pluginManager.registerEvents(PlayerEvents(), this)

        logger.info("AgendaCloud is enabled!")
        ReportService.reportLoop()
    }

    override fun onDisable() {

        logger.info("Service Revoking")

        logger.info("AgendaCloud is disabled!")
    }
}