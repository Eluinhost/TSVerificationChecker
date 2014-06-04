package com.publicuhc.tsverificationchecker;

import org.bukkit.entity.Player;

import java.util.UUID;

public class DefaultVerificationChecker implements VerificationChecker {

    private final String m_baseURL;

    public DefaultVerificationChecker(String baseURL) {
        this.m_baseURL = baseURL;
    }

    @Override
    public boolean playerVerified(Player player) {
        return playerVerified(player.getUniqueId());
    }

    @Override
    public boolean playerVerified(UUID playerUUID) {
        //TODO use the URLFetcher and parsers to do stuff
        return false;
    }

    @Override
    public boolean playerOnline(Player player) {
        return playerOnline(player.getUniqueId());
    }

    @Override
    public boolean playerOnline(UUID playerUUID) {
        //TODO use the URLFetcher and parsers to do stuff
        return false;
    }
}
