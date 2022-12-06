package cat.kiwi.minecraft.acloud.utils

import cat.kiwi.minecraft.acloud.AgendaCloudPlugin
import cat.kiwi.minecraft.acloud.config.Config

object Logger {
    fun debug(any: Any?) {
        if (Config.debug) {
            AgendaCloudPlugin.instance.logger.info("[DEBUG] $any")
        }
    }
    fun debug(any: Any?, javaClass: Class<*>) {
        if (Config.debug) {
            AgendaCloudPlugin.instance.logger.info("[DEBUG] ${javaClass.simpleName}: $any")
        }
    }

    fun panic(any: Any?, javaClass: Class<*>) {
        AgendaCloudPlugin.instance.logger.warning("[PANIC] ==============================")
        AgendaCloudPlugin.instance.logger.warning("[PANIC] ")
        AgendaCloudPlugin.instance.logger.warning("[PANIC] LOGIC ERROR, PLEASE REPORT THIS TO DEVELOPER!")
        AgendaCloudPlugin.instance.logger.warning("[PANIC] ")
        AgendaCloudPlugin.instance.logger.warning("[PANIC] ==============================")
        AgendaCloudPlugin.instance.logger.warning("[PANIC] ${javaClass.simpleName}: $any")
    }
}