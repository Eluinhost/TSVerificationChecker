package com.publicuhc.tsverificationchecker.commands;

import com.publicuhc.tsverificationchecker.VerificationChecker;
import com.publicuhc.tsverificationchecker.exceptions.FetchException;
import com.publicuhc.tsverificationchecker.models.OnlineVerificationResponse;
import com.publicuhc.tsverificationchecker.models.VerificationResponse;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AsyncVerificationCheck implements Runnable {

    private final HashMap<UUID, String> m_players;
    private final VerificationChecker m_checker;
    private final boolean m_onlineCheck;
    private final WeakReference<CommandSender> m_sender;

    public AsyncVerificationCheck(CommandSender sender, HashMap<UUID, String> players, VerificationChecker checker, boolean onlineCheck) {
        m_players = players;
        m_checker = checker;
        m_onlineCheck = onlineCheck;
        m_sender = new WeakReference<CommandSender>(sender);
    }

    @Override
    public void run() {
        CommandSender sender = m_sender.get();
        if(null == sender) {
            return;
        }

        HashMap<UUID, String> failed = new HashMap<UUID, String>();
        HashMap<UUID, String> errors = new HashMap<UUID, String>();

        for(Map.Entry<UUID, String> entry : m_players.entrySet()) {
            try {
                if(m_onlineCheck) {
                    OnlineVerificationResponse response = m_checker.getOnlineVerificationResponseForUUID(entry.getKey());
                    if(!response.isVerified() || response.getOnlineUUIDs().size() == 0) {
                        failed.put(entry.getKey(), entry.getValue());
                    }
                } else {
                    VerificationResponse response = m_checker.getVerificationResponseForUUID(entry.getKey());
                    if(!response.isVerified()) {
                        failed.put(entry.getKey(), entry.getValue());
                    }
                }
            }
            catch (FetchException ignored) {
                errors.put(entry.getKey(), entry.getValue());
            }
            catch (ParseException ignored) {
                errors.put(entry.getKey(), entry.getValue());
            }
        }

        if(failed.size() > 0) {
            StringBuilder builder = new StringBuilder();
            builder.append(ChatColor.RED).append("Players not matching:");
            for(String playerName : failed.values()) {
                builder.append(" ").append(playerName);
            }
            sender.sendMessage(builder.toString());
        }

        if(errors.size() > 0) {
            StringBuilder builder = new StringBuilder();
            builder.append(ChatColor.RED).append("Failed to fetch data for:");
            for(String playerName : errors.values()) {
                builder.append(" ").append(playerName);
            }
            sender.sendMessage(builder.toString());
        }

        if(errors.size() == 0 && failed.size() == 0) {
            sender.sendMessage(ChatColor.GOLD + "All players are verified" + (m_onlineCheck ? " and online" : ""));
        }
    }
}
