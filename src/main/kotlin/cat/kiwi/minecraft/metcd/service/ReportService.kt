package cat.kiwi.minecraft.metcd.service

import cat.kiwi.minecraft.metcd.MEtcdPlugin
import cat.kiwi.minecraft.metcd.config.Config
import cat.kiwi.minecraft.metcd.model.ServerStatus
import com.google.gson.Gson
import io.etcd.jetcd.ByteSequence
import io.etcd.jetcd.options.PutOption
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable
import java.net.InetAddress

class ReportService {
    companion object {
        @JvmStatic
        fun reportStatus() {
            object : BukkitRunnable() {
                override fun run() {
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
                    MEtcdPlugin.serverStatus = ServerStatus(
                        MEtcdPlugin.uuid,
                        Config.gameType,
                        currentOnline,
                        players,
                        total,
                        MEtcdPlugin.gameStatus,
                        address,
                        port,
                        version,
                        meta
                    )
                    val payload = ByteSequence.from(Gson().toJson(MEtcdPlugin.serverStatus), Charsets.UTF_8)
                    val option = PutOption.newBuilder().withLeaseId(MEtcdPlugin.grant.id).build()
                    try {
                        MEtcdPlugin.etcdClient.kvClient.put(
                            ByteSequence.from(
                                "/agenda/service/${Config.gameType}/${MEtcdPlugin.uuid}",
                                Charsets.UTF_8
                            ),
                            payload,
                            option
                        )
                    } catch (e: Exception) {
                        MEtcdPlugin.instance.logger.warning("Report status failed!")
                        e.printStackTrace()
                        MEtcdPlugin.instance.onDisable()
                    }

                }
            }.runTaskAsynchronously(MEtcdPlugin.instance)
        }
    }
}