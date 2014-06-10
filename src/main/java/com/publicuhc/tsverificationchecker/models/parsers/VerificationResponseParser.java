package com.publicuhc.tsverificationchecker.models.parsers;

import com.publicuhc.tsverificationchecker.models.UUIDResponse;
import com.publicuhc.tsverificationchecker.models.VerificationResponse;

import java.text.ParseException;
import java.util.*;

public class VerificationResponseParser {

    private final UUIDResponseParser m_uuidRepsonseParser;
    private final MinecraftUUIDParser m_minecraftUUIDParser;

    public VerificationResponseParser(UUIDResponseParser parser, MinecraftUUIDParser mcParser) {
        m_uuidRepsonseParser = parser;
        m_minecraftUUIDParser = mcParser;
    }

    /**
     * <p>Expects a list in the format:</p>
     * <code>
     * <p>UUID: uuid response to parse</p>
     * </code>
     * @param list the list to parse
     * @return A parsed VerificationResponse
     * @throws java.text.ParseException on failing to parse the map
     */
    public VerificationResponse parseVerificationResponse(HashMap<String, Object> list) throws ParseException {
        HashMap<UUID, UUIDResponse> responses = new HashMap<UUID, UUIDResponse>();

        for(Map.Entry<String, Object> entry : list.entrySet()) {
            UUID mcUUID = m_minecraftUUIDParser.parseUUID(entry.getKey());
            UUIDResponse response = m_uuidRepsonseParser.parse(entry.getValue());

            responses.put(mcUUID, response);
        }

        return new VerificationResponse().setResponseMap(responses);
    }
}
