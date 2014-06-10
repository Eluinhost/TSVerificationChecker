package com.publicuhc.tsverificationchecker;

import com.publicuhc.tsverificationchecker.exceptions.FetchException;
import com.publicuhc.tsverificationchecker.models.VerificationResponse;
import org.bukkit.entity.Player;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;

public interface VerificationChecker {

    /**
     * Get the response for the given player
     * @param players the players to check
     * @param checkOnline return online status too
     * @return the response
     * @throws FetchException if fetching from the api failed
     * @throws ParseException if parse the repsonse failed
     */
    VerificationResponse getVerificationResponseForPlayers(List<Player> players, boolean checkOnline) throws FetchException, ParseException;

    /**
     * Get the response for the given player UUID
     * @param uuids the uuid of the player to check
     * @param checkOnline return online status too
     * @return the response
     * @throws FetchException if fetching from the api failed
     * @throws ParseException if parse the repsonse failed
     */
    VerificationResponse getVerificationResponseForUUIDs(List<UUID> uuids, boolean checkOnline) throws FetchException, ParseException;
}
