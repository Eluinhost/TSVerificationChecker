package com.publicuhc.tsverificationchecker;

import org.bukkit.entity.Player;

import java.util.UUID;

public interface VerificationChecker {

    /**
     * Check if the player is verified
     * @param player the player to check
     * @return true if verified, false if not
     */
    boolean playerVerified(Player player);

    /**
     * Check if the player with the given UUID is verified
     * @param playerUUID the UUID to check
     * @return true if verified, false if not
     */
    boolean playerVerified(UUID playerUUID);

    /**
     * Check if the player is online in TS
     * @param player the player to check
     * @return true if online, false if not or if not verified
     */
    boolean playerOnline(Player player);

    /**
     * Check if the player is online in TS
     * @param playerUUID the UUID of the player to check
     * @return true if online, false if not or if not verified
     */
    boolean playerOnline(UUID playerUUID);
}
