package cat.kiwi.minecraft.acloud.listener

import cat.kiwi.minecraft.acloud.service.ReportService
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class PlayerEvents: Listener {
    @EventHandler
    fun onPlayerJoinEvent(e: PlayerJoinEvent) {
        ReportService.reportStatus()
    }
    @EventHandler
    fun onPlayerQuitEvent(e: PlayerQuitEvent) {
        ReportService.reportStatus()
    }
}