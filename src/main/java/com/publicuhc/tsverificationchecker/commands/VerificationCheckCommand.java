package com.publicuhc.tsverificationchecker.commands;

import com.publicuhc.tsverificationchecker.VerificationChecker;
import org.apache.commons.lang.BooleanUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class VerificationCheckCommand implements TabExecutor {

    public static final String TS_VERIFY_PERMISSION = "tsverify.command";

    private Plugin m_plugin;
    private VerificationChecker m_checker;

    public VerificationCheckCommand(Plugin plugin, VerificationChecker checker) {
        m_plugin = plugin;
        m_checker = checker;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] strings) {
        //TODO complete command
        return null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("tsverify")) {
            if(!sender.hasPermission(TS_VERIFY_PERMISSION)) {
                sender.sendMessage(ChatColor.RED + "You need the permission (" + TS_VERIFY_PERMISSION + ") to run this command");
                return true;
            }
            if(args.length <= 1) {
                sender.sendMessage(ChatColor.RED + "Syntax: /tsverify true/false player1 player2 player3 OR /tsverify true/false *");
                return true;
            }
            boolean onlineCheck = BooleanUtils.toBoolean(args[0]);

            final HashMap<UUID, String> players = new HashMap<UUID, String>();

            if(args[1].equals("*")) {
                Player[] onlinePlayers = Bukkit.getOnlinePlayers();
                for(Player p : onlinePlayers) {
                    players.put(p.getUniqueId(), p.getName());
                }
            } else {
                for (int i = 1; i < args.length; i++) {
                    Player player = Bukkit.getPlayer(args[i]);
                    if (player == null) {
                        sender.sendMessage(ChatColor.RED + "Unknown player: " + args[i]);
                        continue;
                    }
                    players.put(player.getUniqueId(), player.getName());
                }
            }

            Bukkit.getScheduler().runTaskAsynchronously(
                    m_plugin,
                    new AsyncVerificationCheck(
                        sender,
                        players,
                        m_checker,
                        onlineCheck
                    )
            );
            return true;
        }
        return false;
    }
}
