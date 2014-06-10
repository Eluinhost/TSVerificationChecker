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

public class VerificationCheckerPlugin extends JavaPlugin {

    @Override
    public void onEnable() {

        FileConfiguration configuration = getConfig();
        configuration.options().copyDefaults(true);
        saveConfig();

        APIFetcher apiFetcher = new APIFetcher(new URLFetcher(), configuration.getString("baseURL"));

        //set up all the parsers
        APIModelParser modelParser = new APIModelParser();
        TeamspeakAccountParser tsAccountParser = new TeamspeakAccountParser(modelParser);
        AuthenticationParser authenticationParser = new AuthenticationParser(modelParser, tsAccountParser);
        MinecraftUUIDParser uuidParser = new MinecraftUUIDParser();
        UUIDResponseParser uuidResponseParser = new UUIDResponseParser(new MinecraftAccountParser(modelParser, uuidParser), authenticationParser);
        VerificationResponseParser verificationResponseParser = new VerificationResponseParser(uuidResponseParser, uuidParser);
        JSONParser jsonParser = new JSONParser();
        VerificationChecker verificationChecker = new DefaultVerificationChecker(apiFetcher, verificationResponseParser, jsonParser);

        //set up commands
        VerificationCheckCommand checkCommand = new VerificationCheckCommand(this, verificationChecker);

        PluginCommand tsverify = Bukkit.getPluginCommand("tsverify");

        tsverify.setTabCompleter(checkCommand);
        tsverify.setExecutor(checkCommand);
    }
}
