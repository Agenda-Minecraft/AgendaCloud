package cat.kiwi.minecraft.metcd.utils

import cat.kiwi.minecraft.metcd.MEtcdPlugin
import cat.kiwi.minecraft.metcd.config.Config

object Logger {
    fun debug(any: Any?) {
        if (Config.debug) {
            MEtcdPlugin.instance.logger.info("[DEBUG] $any")
        }
    }
    fun debug(any: Any?, javaClass: Class<*>) {
        if (Config.debug) {
            MEtcdPlugin.instance.logger.info("[DEBUG] ${javaClass.simpleName}: $any")
        }
    }

    fun panic(any: Any?, javaClass: Class<*>) {
        MEtcdPlugin.instance.logger.warning("[PANIC] ==============================")
        MEtcdPlugin.instance.logger.warning("[PANIC] ")
        MEtcdPlugin.instance.logger.warning("[PANIC] LOGIC ERROR, PLEASE REPORT THIS TO DEVELOPER!")
        MEtcdPlugin.instance.logger.warning("[PANIC] ")
        MEtcdPlugin.instance.logger.warning("[PANIC] ==============================")
        MEtcdPlugin.instance.logger.warning("[PANIC] ${javaClass.simpleName}: $any")
    }
}