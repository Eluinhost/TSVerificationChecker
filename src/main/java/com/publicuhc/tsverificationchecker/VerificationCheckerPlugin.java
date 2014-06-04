package com.publicuhc.tsverificationchecker;

import com.publicuhc.tsverificationchecker.commands.VerificationCheckCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class VerificationCheckerPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        VerificationCheckCommand checkCommand = new VerificationCheckCommand();

        PluginCommand tsverify = Bukkit.getPluginCommand("tsverify");

        tsverify.setTabCompleter(checkCommand);
        tsverify.setExecutor(checkCommand);
    }
}
