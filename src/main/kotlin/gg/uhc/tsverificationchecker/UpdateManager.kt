package gg.uhc.tsverificationchecker

import gg.uhc.githubreleasechecker.ReleaseChecker
import gg.uhc.githubreleasechecker.deserialization.LatestReleaseQueryer
import gg.uhc.githubreleasechecker.zafarkhaja.semver.Version
import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.chat.BaseComponent
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.Plugin
import java.io.IOException

class UpdateManager(private val plugin: Plugin) : Runnable, Listener {
    private var latestUpdate: Version? = null
    private var message: Pair<String, BaseComponent>? = null

    private val checker = ReleaseChecker(plugin, LatestReleaseQueryer("Eluinhost", "TSVerificationChecker"), true)

    init {
        plugin.server.pluginManager.registerEvents(this, plugin)
    }

    @EventHandler fun on(event: PlayerJoinEvent) {
        if (event.player.isOp) {
            message?.let {
                event.player.sendMessage(it.first);
                event.player.spigot().sendMessage(it.second);
            }
        }
    }

    override fun run() {
        try {
            val response = checker.checkForUpdate();

            if (!response.hasUpdate()) return;

            val update = response.updateDetails;

            if (update.version == latestUpdate) return;

            latestUpdate = update.version;

            plugin.logger.info("[TSVerificationChecker] An update is available (${response.installed} -> ${update.version}. More info: ${update.url})")

            val component = TextComponent("[TSVerificationChecker] ${ChatColor.UNDERLINE}Click me for more info on ${update.version}")
            component.clickEvent = ClickEvent(ClickEvent.Action.OPEN_URL, update.url)
            component.color = ChatColor.AQUA

            val basic = "${ChatColor.AQUA}[TSVerificationChecker] An update is available (${response.installed} -> ${update.version})"

            message = Pair(basic, component)

            plugin.server.onlinePlayers
                .filter { it.isOp }
                .forEach {
                    it.sendMessage(basic)
                    it.spigot().sendMessage(component)
                }

        } catch (ex: IOException) {
            ex.printStackTrace()
        }
    }
}