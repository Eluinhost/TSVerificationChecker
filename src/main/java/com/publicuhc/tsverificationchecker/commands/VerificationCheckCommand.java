package com.publicuhc.tsverificationchecker.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.List;

public class VerificationCheckCommand implements TabExecutor {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] strings) {
        //TODO complete command
        return null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        //TODO complete command
        return false;
    }
}
