package com.publicuhc.tsverificationchecker.models.parsers;

import com.publicuhc.tsverificationchecker.models.TeamspeakAccount;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

public class TeamspeakAccountParser {

    private final DateParser m_dateParser;

    public TeamspeakAccountParser(DateParser dateParser) {
        m_dateParser = dateParser;
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
        Object createdAt = map.get("createdAt");
        if(createdAt == null || !(createdAt instanceof String)) {
            throw new ParseException("Minecraft account doesn't contain a valid createdAt node", 0);
        }
        Date createdAtDate = m_dateParser.parseDate((String) createdAt);

        Object updatedAt = map.get("updatedAt");
        if(updatedAt == null || !(updatedAt instanceof String)) {
            throw new ParseException("Minecraft account doesn't contain a valid updatedAt node", 0);
        }
        Date updatedAtDate = m_dateParser.parseDate((String) updatedAt);

        Object uuidObject = map.get("uuid");
        if(uuidObject == null || !(uuidObject instanceof String)) {
            throw new ParseException("UUID doesn't contain a valid UUID node", 0);
        }
        String uuid = (String) uuidObject;

        TeamspeakAccount account = new TeamspeakAccount().setUUID(uuid);
        account.setCreatedAt(createdAtDate).setUpdatedAt(updatedAtDate);
        return account;
    }
}
