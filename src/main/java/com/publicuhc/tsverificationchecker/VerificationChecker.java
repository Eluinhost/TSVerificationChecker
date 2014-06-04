package com.publicuhc.tsverificationchecker;

import com.publicuhc.tsverificationchecker.exceptions.FetchException;
import com.publicuhc.tsverificationchecker.models.OnlineVerificationResponse;
import com.publicuhc.tsverificationchecker.models.VerificationResponse;
import org.bukkit.entity.Player;

import java.text.ParseException;
import java.util.UUID;

public interface VerificationChecker {

    /**
     * Get the response for the given player
     * @param player the player to check
     * @return the response
     * @throws FetchException if fetching from the api failed
     * @throws ParseException if parse the repsonse failed
     */
    VerificationResponse getVerificationResponseForPlayer(Player player) throws FetchException, ParseException;

    /**
     * Get the response for the given player, including online UUIDs
     * @param player the player to check
     * @return the response
     * @throws FetchException if fetching from the api failed
     * @throws ParseException if parse the repsonse failed
     */
    OnlineVerificationResponse getOnlineVerificationResponseForPlayer(Player player) throws FetchException, ParseException;

    /**
     * Get the response for the given player UUID
     * @param uuid the uuid of the player to check
     * @return the response
     * @throws FetchException if fetching from the api failed
     * @throws ParseException if parse the repsonse failed
     */
    VerificationResponse getVerificationResponseForUUID(UUID uuid) throws FetchException, ParseException;

    /**
     * Get the response for the given player uuid, including online UUIDs
     * @param uuid the player uuid to check
     * @return the response
     * @throws FetchException if fetching from the api failed
     * @throws ParseException if parse the repsonse failed
     */
    OnlineVerificationResponse getOnlineVerificationResponseForUUID(UUID uuid) throws FetchException, ParseException;
}
