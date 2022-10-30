package cat.kiwi.minecraft.metcd

import cat.kiwi.minecraft.metcd.command.MEtcdCommands
import cat.kiwi.minecraft.metcd.config.Config
import cat.kiwi.minecraft.metcd.listener.PlayerEvents
import cat.kiwi.minecraft.metcd.model.GameStatus
import cat.kiwi.minecraft.metcd.model.ServerStatus
import cat.kiwi.minecraft.metcd.service.ReportService
import cat.kiwi.minecraft.metcd.utils.Logger
import io.etcd.jetcd.Client
import io.etcd.jetcd.lease.LeaseGrantResponse
import io.etcd.jetcd.support.Observers
import org.bukkit.plugin.java.JavaPlugin
import java.util.UUID

class MEtcdPlugin : JavaPlugin() {
    companion object {
        @JvmStatic
        fun getInternalClient(): Client {
            return etcdClient
        }
        lateinit var etcdClient: Client
        lateinit var instance: MEtcdPlugin
        lateinit var uuid: String
        lateinit var serverStatus: ServerStatus
        lateinit var grant: LeaseGrantResponse
        var gameStatus: GameStatus = GameStatus.WAITING
            set(value) {
                ReportService.reportStatus()
                field = value
            }
    }


    override fun onEnable() {
        instance = this
        uuid = UUID.randomUUID().toString()

        try {
            Config.readConfig()
            etcdClient = Client.builder().endpoints(Config.etcdEndpoints).build()
        } catch (e: Exception) {
            e.printStackTrace()
            onDisable()
        }
        getCommand("metcd")?.setExecutor(MEtcdCommands())

        gameStatus = GameStatus.WAITING


        grant = etcdClient.leaseClient.grant(Config.ttl).get()
        etcdClient.leaseClient.keepAlive(grant.id, Observers.observer { Logger.debug("Service Keepalive") })

        ReportService.reportStatus()

        server.pluginManager.registerEvents(PlayerEvents(), this)

        logger.info("MEtcd is enabled!")
    }

    override fun onDisable() {

        try {
            etcdClient.leaseClient.revoke(grant.id)
            logger.info("Service Revoking")
            Thread.sleep(Config.vertxIOExitWait)

            etcdClient.close()
            logger.info("IO Closing")
            Thread.sleep(Config.vertxIOExitWait)
        } catch (e: ClassNotFoundException) {
            logger.info("vertx IO already closed!")
        }
        logger.info("MEtcd is disabled!")
    }
}