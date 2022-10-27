package cat.kiwi.minecraft.metcd.command

import cat.kiwi.minecraft.metcd.MEtcdPlugin
import cat.kiwi.minecraft.metcd.controller.BungeeMenu
import cat.kiwi.minecraft.metcd.model.GameStatus
import cat.kiwi.minecraft.metcd.service.QueryService
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class MEtcdCommands: CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) {
            return true
        }
        when (args[0].lowercase()) {
            "query" -> {
                if (!sender.hasPermission("metcd.command.query")) {
                    sender.sendMessage("§cYou don't have permission to use this command.")
                    return true
                }
                if (args.size == 2) {
                    sender.sendMessage(QueryService.serverStatus(args[1]).joinToString("\n"))
                }
                return true

            }
            "setstatus" -> {
                if (!sender.hasPermission("metcd.command.setstatus")) {
                    sender.sendMessage("§cYou don't have permission to use this command.")
                    return true
                }
                if (args.size == 2) {
                    when (args[1].lowercase()) {
                        "waiting" -> {
                            MEtcdPlugin.gameStatus = GameStatus.WAITING
                            sender.sendMessage("§aGame status set to WAITING.")
                        }
                        "loading" -> {
                            MEtcdPlugin.gameStatus = GameStatus.LOADING
                            sender.sendMessage("§aGame status set to LOADING.")
                        }
                        "starting" -> {
                            MEtcdPlugin.gameStatus = GameStatus.STARTING
                            sender.sendMessage("§aGame status set to STARTING.")
                        }
                        "running" -> {
                            MEtcdPlugin.gameStatus = GameStatus.RUNNING
                            sender.sendMessage("§aGame status set to RUNNING.")
                        }
                        "ending" -> {
                            MEtcdPlugin.gameStatus = GameStatus.ENDING
                            sender.sendMessage("§aGame status set to ENDING.")
                        }
                        else -> {
                            sender.sendMessage("§cInvalid game status.")
                        }
                    }
                }
                return true
            }
            "opengui" -> {
                if (!sender.hasPermission("metcd.command.setstatus")) {
                    sender.sendMessage("§cYou don't have permission to use this command.")
                    return false
                }
                if (args.size == 2) {
                    BungeeMenu().initMenu(sender as Player, args[1])
                }
                return true
            }

            else -> {
                sender.sendMessage("/metcd query <serviceType>")
            }
        }
        return true
    }
}