package gg.uhc.tsverificationchecker

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import org.bukkit.Server
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

const val PERMISSION = "tsverify.command"

class Entry : JavaPlugin() {
    override fun onEnable() {
        config.options().copyDefaults(true)
        saveConfig()
        FuelManager.instance.basePath = config.getString("base path")

        if (!config.getBoolean("skip update check")) {
            server.scheduler.runTaskTimerAsynchronously(this, UpdateManager(this), 0, 60 * 60 * 20)
        }

        //set up commands
        val checkCommand = VerificationCheckCommand(this, { players, checkType -> Runnable {
            // map of player uuids -> name
            val mapping = players.associateBy({ it.uniqueId }, { it.name })

            // fetch a list of passed/failed uuids and convert them back into passed/failed names
            val (passedNames, failedNames) = checkVerification(mapping.keys, checkType).let {
                Pair(it.first.map { mapping[it]!! }, it.second.map { mapping[it]!! })
            }

            checkType.sendMessage(passedNames.toSet(), failedNames.toSet())
        }})

        server.getPluginCommand("tsverify").apply {
            this.tabCompleter = checkCommand
            this.executor = checkCommand
        }
    }
}

class VerificationCheckException(val error: FuelError) : Exception(error.exception)

/**
 * Calls the api with the given uuids
 * @return first = passed uuids, second = failed uuids
 */
@Throws fun checkVerification(uuids: Set<UUID>, checkType: CheckType) : Pair<Set<UUID>, Set<UUID>> {
    val (request, response, result) = "minecraft_account"
        .httpGet(listOf(
            "type"  to checkType.apiParam,
            "uuids" to uuids.joinToString(separator = ",") { it.toString().replace("-", "")}
        ))
        .responseObject(ApiResponseParser())

    return when (result) {
        is Result.Success -> {
            val valid = result.value.map { it.uuid }.toSet()
            val invalid = uuids.filter { !valid.contains(it) }.toSet()

            Pair(valid, invalid)
        }
        is Result.Failure -> throw VerificationCheckException(result.error)
    }
}

/**
 * @return a pair, first = list of offline names, second = list of online players
 */
fun Server.getOnlinePlayers(names: List<String>) : Pair<List<String>, List<Player>> =
    names
        .map { Pair(it, this.getPlayerExact(it)) }
        .partition { it.second == null }
        .let { Pair(it.first.map { it.first }, it.second.map { it.second })}