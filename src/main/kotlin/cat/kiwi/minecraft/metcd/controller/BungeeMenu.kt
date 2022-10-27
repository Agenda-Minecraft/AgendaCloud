package cat.kiwi.minecraft.metcd.controller

import cat.kiwi.minecraft.metcd.MEtcd
import cat.kiwi.minecraft.metcd.MEtcdPlugin
import cat.kiwi.minecraft.metcd.config.Config
import cat.kiwi.minecraft.metcd.model.GameStatus
import cat.kiwi.minecraft.metcd.service.QueryService
import cat.kiwi.minecraft.metcd.utils.setMEtcdCondition
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Item
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

class BungeeMenu {
    fun initMenu(player: Player, serverType: String) {
        val inventory = Bukkit.createInventory(null, 54, Config.title)
        player.openInventory(inventory)
        Bukkit.getScheduler().runTaskAsynchronously(MEtcdPlugin.instance, Runnable {
            getItemStacks(serverType).forEachIndexed { index, itemStack ->
                inventory.setItem(index, itemStack)
            }
        })
    }

    fun getItemStacks(serverType: String): List<ItemStack> {
        val servers = QueryService.serverStatus(serverType)
        val result = arrayListOf<ItemStack>()
        servers.forEach {
            var itemStack: ItemStack
            when (it.gameStatus) {
                GameStatus.WAITING -> {
                    itemStack = ItemStack(Material.GREEN_WOOL)
                }

                GameStatus.LOADING -> {
                    itemStack = ItemStack(Material.YELLOW_WOOL)
                }

                GameStatus.STARTING -> {
                    itemStack = ItemStack(Material.RED_WOOL)
                }

                GameStatus.RUNNING -> {
                    itemStack = ItemStack(Material.RED_WOOL)
                }

                GameStatus.ENDING -> {
                    itemStack = ItemStack(Material.RED_WOOL)
                }

                GameStatus.EXITED -> {
                    itemStack = ItemStack(Material.RED_WOOL)
                }
            }
            val itemMeta: ItemMeta = itemStack.itemMeta!!
            itemMeta.lore = arrayListOf(
                "§aServer: ${it.uuid}",
                "§aGameType: ${it.gameType}",
                "§aCurrentOnline: ${it.currentOnline}",
                "§aPlayers: ${it.players}",
                "§aTotal: ${it.total}",
                "§aGameStatus: ${it.gameStatus}",
                "§aAddress: ${it.address}",
                "§aPort: ${it.port}",
                "§aVersion: ${it.version}",
                "§aMeta: ${it.meta}"
            )
            val serverConnectionName = it.address.replace(".", "-") + "-" + it.port
            itemStack.itemMeta = itemMeta
            itemStack = itemStack.setMEtcdCondition(it.gameStatus, serverConnectionName)
            result.add(itemStack)
        }
        // padding to 10
        for (i in 1..Config.paddingTo - result.size) {
            var itemStack = ItemStack(Material.GRAY_WOOL)
            itemStack = itemStack.setMEtcdCondition(GameStatus.EXITED)
            result.add(itemStack)
        }

        return result
    }
}