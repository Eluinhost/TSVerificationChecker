package gg.uhc.tsverificationchecker

import org.apache.commons.lang.BooleanUtils
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

class VerificationCheckCommand(private val plugin: Plugin, private val task: (List<Player>, CheckType) -> Runnable) : TabExecutor {

    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<out String>) = when {
        args.size == 0 -> listOf() // is this possible?
        args.size == 1 -> listOf("true", "false")
        else -> plugin.server.onlinePlayers.map { it.name }
    }.filter { it.startsWith(args.last(), ignoreCase = true) }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (!sender.hasPermission(PERMISSION)) {
            sender.sendMessage("${ChatColor.RED}You need the permission ($PERMISSION) to run this command")
            return true
        }

        if (args.size <= 1) {
            sender.sendMessage("${ChatColor.RED}Syntax: /tsverify true/false player1 player2 player3 OR /tsverify true/false *")
            return true
        }

        val checkType = if (BooleanUtils.toBoolean(args[0])) CheckType.Online(sender) else CheckType.Verified(sender)

        // List<String>, List<Player>
        val (invalid, valid) = when {
            args[1] == "*" -> Pair(listOf<String>(), plugin.server.onlinePlayers.toList())
            else -> plugin.server.getOnlinePlayers(args.drop(1))
        }

        if (invalid.size > 0) {
            sender.sendMessage("${ChatColor.RED}Unknown player(s): ${invalid.joinToString()}")
        }

        if (valid.size == 0) {
            sender.sendMessage("${ChatColor.RED}Must supply at least 1 online player")
            return true
        }

        plugin.server.scheduler.runTaskAsynchronously(plugin, task(valid, checkType))
        return true
    }
}