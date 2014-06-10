package com.publicuhc.tsverificationchecker.commands;

import com.publicuhc.tsverificationchecker.VerificationChecker;
import com.publicuhc.tsverificationchecker.models.Authentication;
import com.publicuhc.tsverificationchecker.models.UUIDResponse;
import com.publicuhc.tsverificationchecker.models.VerificationResponse;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
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
        //if the sender is no longer available no point in fetching e.t.c.
        CommandSender sender = m_sender.get();
        if(null == sender) {
            return;
        }

        try {
            VerificationResponse verificationResponse = m_checker.getVerificationResponseForUUIDs(new ArrayList<UUID>(m_players.keySet()), m_onlineCheck);

            HashMap<UUID, UUIDResponse> uuidMapping = verificationResponse.getResponseMap();
            for(Map.Entry<UUID, UUIDResponse> entry : uuidMapping.entrySet()) {
                UUIDResponse response = entry.getValue();

                if(!response.isVerified()) {
                    //they arn't verified, leave them in the map
                    continue;
                }

                //if we're also checking online
                if(m_onlineCheck) {
                    boolean online = false;
                    for(Authentication authentication : response.getAuthentications()) {
                        if(authentication.getTeamspeakAccount().isOnline()) {
                            online = true;
                            break;
                        }
                    }

                    //if they dont have an account online, leave them in the map
                    if(!online) {
                        continue;
                    }
                }

                //valid if they got to this stage, remove them from the list of players
                m_players.remove(entry.getKey());
            }

            if(m_players.size() > 0) {
                //all players left in the map now didn't match the restrictions
                StringBuilder builder = new StringBuilder();
                builder.append(ChatColor.RED).append("Players not matching:");
                for (String playerName : m_players.values()) {
                    builder.append(" ").append(playerName);
                }
                sender.sendMessage(builder.toString());
            } else {
                //all players were correct
                sender.sendMessage(ChatColor.GOLD + "All players are verified" + (m_onlineCheck ? " and online" : ""));
            }
        } catch (Exception e) {
            e.printStackTrace();
            sender.sendMessage(ChatColor.RED + "There was a problem checking the API for players, check the console for more information");
        }
    }
}
