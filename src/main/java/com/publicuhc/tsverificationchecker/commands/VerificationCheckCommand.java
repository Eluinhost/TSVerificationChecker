package com.publicuhc.tsverificationchecker.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.List;

public class VerificationCheckCommand implements TabExecutor {

    public static final String TS_VERIFY_PERMISSION = "tsverify.command";

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] strings) {
        //TODO complete command
        return null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(command.getName().equalsIgnoreCase("tsverify")) {
            if(!sender.hasPermission(TS_VERIFY_PERMISSION)) {
                sender.sendMessage(ChatColor.RED + "You need the permission (" + TS_VERIFY_PERMISSION + ") to run this command");
                return true;
            }
            //TODO complete command
        }
        return false;
    }
}
