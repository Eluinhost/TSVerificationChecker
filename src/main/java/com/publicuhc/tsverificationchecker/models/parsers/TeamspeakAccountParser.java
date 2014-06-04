package com.publicuhc.tsverificationchecker.models.parsers;

import com.publicuhc.tsverificationchecker.models.TeamspeakAccount;

import java.text.ParseException;
import java.util.Map;

public class TeamspeakAccountParser {

    private final APIModelParser m_modelParser;

    public TeamspeakAccountParser(APIModelParser modelParser) {
        m_modelParser = modelParser;
    }

    /**
     * <p>Expects a map in the format:</p>
     * <code>
     * <p>createdAt: RFC2822 time</p>
     * <p>updatedAt: RFC2822 time</p>
     * <p>uuid: Teamspeak UUID</p>
     * </code>
     * @param map the map to parse
     * @return A parsed TeamspeakAccount
     * @throws java.text.ParseException on failing to parse the map
     */
    public TeamspeakAccount parse(Map<String, Object> map) throws ParseException {
        Object uuidObject = map.get("uuid");
        if(uuidObject == null || !(uuidObject instanceof String)) {
            throw new ParseException("UUID doesn't contain a valid UUID node", 0);
        }
        String uuid = (String) uuidObject;

        TeamspeakAccount account = new TeamspeakAccount().setUUID(uuid);
        m_modelParser.addParsedDataToModel(map, account);
        return account;
    }
}
