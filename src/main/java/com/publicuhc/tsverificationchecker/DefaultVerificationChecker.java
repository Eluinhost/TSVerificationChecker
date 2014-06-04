package com.publicuhc.tsverificationchecker;

import com.publicuhc.tsverificationchecker.exceptions.FetchException;
import com.publicuhc.tsverificationchecker.fetchers.URLFetcher;
import com.publicuhc.tsverificationchecker.models.OnlineVerificationResponse;
import com.publicuhc.tsverificationchecker.models.VerificationResponse;
import com.publicuhc.tsverificationchecker.models.parsers.VerificationResponseParser;
import org.bukkit.entity.Player;
import org.json.simple.parser.JSONParser;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DefaultVerificationChecker implements VerificationChecker {

    private final URLFetcher m_verifiedURLFetcher;
    private final URLFetcher m_onlineURLFetcher;
    private final VerificationResponseParser m_parser;
    private final JSONParser m_jsonParser;

    public DefaultVerificationChecker(URLFetcher verifiedURL, URLFetcher onlineURL, VerificationResponseParser parser, JSONParser jsonParser) {
        m_verifiedURLFetcher = verifiedURL;
        m_onlineURLFetcher = onlineURL;
        m_parser = parser;
        m_jsonParser = jsonParser;
    }

    @Override
    public VerificationResponse getVerificationResponseForPlayer(Player player) throws FetchException, ParseException {
        return getVerificationResponseForUUID(player.getUniqueId());
    }

    @Override
    public OnlineVerificationResponse getOnlineVerificationResponseForPlayer(Player player) throws FetchException, ParseException {
        return getOnlineVerificationResponseForUUID(player.getUniqueId());
    }

    @SuppressWarnings("unchecked")
    @Override
    public VerificationResponse getVerificationResponseForUUID(UUID uuid) throws FetchException, ParseException {
        String urlContents = m_verifiedURLFetcher.fetch();
        try {
            Object parsedJSON = m_jsonParser.parse(urlContents);
            if(parsedJSON == null || !(parsedJSON instanceof Map)) {
                throw new ParseException("JSON is not of the correct format", 0);
            }
            HashMap<String, Object> jsonMap = (HashMap<String, Object>) parsedJSON;

            return m_parser.parseVerificationResponse(jsonMap);
        } catch (org.json.simple.parser.ParseException e) {
            throw new ParseException("Invalid JSON file", 0);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public OnlineVerificationResponse getOnlineVerificationResponseForUUID(UUID uuid) throws FetchException, ParseException {
        String urlContents = m_onlineURLFetcher.fetch();
        try {
            Object parsedJSON = m_jsonParser.parse(urlContents);
            if(parsedJSON == null || !(parsedJSON instanceof Map)) {
                throw new ParseException("JSON is not of the correct format", 0);
            }
            HashMap<String, Object> jsonMap = (HashMap<String, Object>) parsedJSON;

            return m_parser.parseOnlineVerificationResponse(jsonMap);
        } catch (org.json.simple.parser.ParseException e) {
            throw new ParseException("Invalid JSON file", 0);
        }
    }
}
