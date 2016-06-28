package gg.uhc.tsverificationchecker

import org.bukkit.command.CommandSender
import java.lang.ref.WeakReference

sealed class CheckType(sender: CommandSender, val apiParam: String, private val passed: (CommandSender, Set<String>) -> String, private val failed: (CommandSender, Set<String>) -> String) {
    private val toMessage = WeakReference<CommandSender>(sender)

    fun sendMessage(passedNames: Set<String>, failedNames: Set<String>) {
        toMessage.get()?.let {
            if (failedNames.size > 0) {
                failed(it, failedNames)
            } else {
                passed(it, passedNames)
            }
        }
    }

    class Verified(sender: CommandSender) : CheckType(
        sender,
        apiParam = "verified",
        passed = { sender, names -> "All ${names.size} checked players have verified accounts" },
        failed = { sender, names -> "The following checked players are not verified: ${names.joinToString()}"}
    )

    class Online(sender: CommandSender) : CheckType(
        sender,
        apiParam = "online",
        passed = { sender, names -> "All ${names.size} checked players are verified and are online at the moment"},
        failed = { sender, names -> "The following checked players are either not verified or are not online: ${names.joinToString()}"}
    )
}