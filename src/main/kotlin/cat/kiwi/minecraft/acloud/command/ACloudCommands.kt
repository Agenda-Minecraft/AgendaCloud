package cat.kiwi.minecraft.acloud.command

import cat.kiwi.minecraft.acloud.AgendaCloud
import cat.kiwi.minecraft.acloud.AgendaCloudPlugin
import cat.kiwi.minecraft.acloud.model.GameStatus
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class ACloudCommands: CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) {
            return true
        }
        when (args[0].lowercase()) {
            "query" -> {
                if (!sender.hasPermission("acloud.command.query")) {
                    sender.sendMessage("§cYou don't have permission to use this command.")
                    return true
                }
                if (args.size == 2) {
                    sender.sendMessage(AgendaCloud.getServerStatusList(args[1]).joinToString("\n"))
                }
                return true

            }
            "setstatus" -> {
                if (!sender.hasPermission("acloud.command.setstatus")) {
                    sender.sendMessage("§cYou don't have permission to use this command.")
                    return true
                }
                if (args.size == 2) {
                    when (args[1].lowercase()) {
                        "waiting" -> {
                            AgendaCloudPlugin.gameStatus = GameStatus.WAITING
                            sender.sendMessage("§aGame status set to WAITING.")
                        }
                        "loading" -> {
                            AgendaCloudPlugin.gameStatus = GameStatus.LOADING
                            sender.sendMessage("§aGame status set to LOADING.")
                        }
                        "starting" -> {
                            AgendaCloudPlugin.gameStatus = GameStatus.STARTING
                            sender.sendMessage("§aGame status set to STARTING.")
                        }
                        "running" -> {
                            AgendaCloudPlugin.gameStatus = GameStatus.RUNNING
                            sender.sendMessage("§aGame status set to RUNNING.")
                        }
                        "ending" -> {
                            AgendaCloudPlugin.gameStatus = GameStatus.ENDING
                            sender.sendMessage("§aGame status set to ENDING.")
                        }
                        else -> {
                            sender.sendMessage("§cInvalid game status.")
                        }
                    }
                }
                return true
            }
            else -> {
                sender.sendMessage("/acloud query <serviceType>")
            }
        }
        return true
    }
}