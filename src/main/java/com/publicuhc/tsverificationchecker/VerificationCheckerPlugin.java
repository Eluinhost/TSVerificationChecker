package com.publicuhc.tsverificationchecker;

import com.publicuhc.tsverificationchecker.commands.VerificationCheckCommand;
import com.publicuhc.tsverificationchecker.fetchers.APIFetcher;
import com.publicuhc.tsverificationchecker.fetchers.URLFetcher;
import com.publicuhc.tsverificationchecker.models.parsers.*;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.parser.JSONParser;

import java.net.URL;

public class VerificationCheckerPlugin extends JavaPlugin {

    @Override
    public void onEnable() {

        FileConfiguration configuration = getConfig();
        configuration.options().copyDefaults(true);
        saveConfig();

        APIFetcher apiFetcher = new APIFetcher(new URLFetcher(), configuration.getString("baseURL"));

        DateParser dateParser = new RFC2822DateParser();
        APIModelParser modelParser = new APIModelParser(dateParser);
        MinecraftUUIDParser uuidParser = new MinecraftUUIDParser();
        MinecraftAccountParser mcAccountParser = new MinecraftAccountParser(modelParser, uuidParser);
        TeamspeakAccountParser tsAccountParser = new TeamspeakAccountParser(modelParser);
        AuthenticationParser authenticationParser = new AuthenticationParser(modelParser, mcAccountParser, tsAccountParser);
        VerificationResponseParser verificationResponseParser = new VerificationResponseParser(authenticationParser);
        JSONParser jsonParser = new JSONParser();
        VerificationChecker verificationChecker = new DefaultVerificationChecker(apiFetcher, verificationResponseParser, jsonParser);

        VerificationCheckCommand checkCommand = new VerificationCheckCommand(this, verificationChecker);

        PluginCommand tsverify = Bukkit.getPluginCommand("tsverify");

        tsverify.setTabCompleter(checkCommand);
        tsverify.setExecutor(checkCommand);
    }
}
