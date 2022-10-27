package cat.kiwi.minecraft.metcd.config

import cat.kiwi.minecraft.metcd.MEtcdPlugin
import java.net.URI

object Config {
    var debug = false
    lateinit var etcdEndpoints: ArrayList<URI>
    var ttl = 30L
    var vertxIOExitWait = 2000L
    var paddingTo = 10
    lateinit var title: String
    lateinit var switchServerCommand: String

    fun readConfig() {
        val instance = MEtcdPlugin.instance
        val logger = instance.logger

        instance.saveDefaultConfig()
        val unFindConfig = mutableListOf<String>()
        try {
            debug = instance.config.getBoolean("debug")
            etcdEndpoints = instance.config.getStringList("etcd.endpoints").let { it ->
                val list = arrayListOf<URI>()
                it.forEach {
                    list.add(URI.create(it))
                    }
                list
            }
            ttl = instance.config.getLong("etcd.ttl")
            vertxIOExitWait = instance.config.getLong("etcd.vertxIOExitWait")
            paddingTo = instance.config.getInt("menu.paddingTo")
            title = instance.config.getString("menu.title")?: "MEtcd"
            switchServerCommand = instance.config.getString("menu.switchServerCommand")?: "/atp %server%"

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