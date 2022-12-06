package cat.kiwi.minecraft.acloud.service

import cat.kiwi.minecraft.acloud.AgendaCloudPlugin
import cat.kiwi.minecraft.acloud.config.Config
import cat.kiwi.minecraft.acloud.model.ServerStatus
import cat.kiwi.minecraft.acloud.utils.Logger
import com.google.gson.Gson
import com.orbitz.consul.model.agent.ImmutableRegistration
import com.orbitz.consul.model.agent.Registration
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable
import java.net.InetAddress
import java.util.*

class ReportService {
    companion object {
        @JvmStatic
        fun reportStatus() {
            object : BukkitRunnable() {
                override fun run() {
                    internalReport()
                }
            }.runTaskAsynchronously(AgendaCloudPlugin.instance)
        }

        fun reportLoop() {
            object : BukkitRunnable() {
                override fun run() {
                    internalReport()
                    Logger.debug("Keep Alive")
                }
            }.runTaskTimerAsynchronously(AgendaCloudPlugin.instance, 0, 20 * (Config.ttl - 1))
        }

        fun internalReport() {
            val currentOnline = Bukkit.getOnlinePlayers().size
            val players = Bukkit.getOnlinePlayers().let {
                val list = mutableListOf<String>()
                it.forEach { player -> list.add(player.name) }
                list
            }
            val total = Bukkit.getMaxPlayers()
            val address = InetAddress.getLocalHost().hostAddress
            val port = Bukkit.getPort()
            val version = Bukkit.getVersion()
            val meta = Bukkit.getMotd()
            AgendaCloudPlugin.serverStatus = ServerStatus(
                AgendaCloudPlugin.serviceUUID,
                Config.gameType,
                Config.displayName,
                false,
                currentOnline,
                players,
                total,
                AgendaCloudPlugin.gameStatus,
                address,
                port,
                version,
                meta
            )
            val payload = Gson().toJson(AgendaCloudPlugin.serverStatus)
            try {
                val agentClient = AgendaCloudPlugin.consulAgentClient

                val service: Registration = ImmutableRegistration.builder()
                    .id(AgendaCloudPlugin.serviceUUID)
                    .name("agenda/server")
                    .port(port)
                    .check(Registration.RegCheck.ttl(Config.ttl))
                    .meta(Collections.singletonMap("serviceInfo", payload))
                    .tags(Collections.singletonList(Config.gameType))
                    .build()
                agentClient.register(service)
                agentClient.pass(AgendaCloudPlugin.serviceUUID)

            } catch (e: Exception) {
                AgendaCloudPlugin.instance.logger.warning("Report status failed!")
                e.printStackTrace()
                AgendaCloudPlugin.instance.onDisable()
            }
        }
    }
}