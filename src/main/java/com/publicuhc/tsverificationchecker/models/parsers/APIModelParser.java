package com.publicuhc.tsverificationchecker.models.parsers;

import com.publicuhc.tsverificationchecker.models.APIModel;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

public class APIModelParser {

    private final DateParser m_parser;

    public APIModelParser(DateParser parser) {
        m_parser = parser;
    }

    /**
     * <p>Expects a map with the following in it:</p>
     * <code>
     * <p>createdAt: RFC2822 time</p>
     * <p>updatedAt: RFC2822 time</p>
     * </code>
     * <p>Adds the createdAt and updatedAt values to the APIModel supplied</p>
     * @param map the map to parse
     * @throws ParseException on failing to parse the map
     */
    public void addParsedDataToModel(Map<String, Object> map, APIModel model) throws ParseException {
        Object createdAt = map.get("createdAt");
        if(createdAt == null || !(createdAt instanceof String)) {
            throw new ParseException("Node doesn't contain a valid createdAt node", 0);
        }
        Date createdAtDate = m_parser.parseDate((String) createdAt);

        Object updatedAt = map.get("updatedAt");
        if(updatedAt == null || !(updatedAt instanceof String)) {
            throw new ParseException("Minecraft account doesn't contain a valid updatedAt node", 0);
        }
        Date updatedAtDate = m_parser.parseDate((String) updatedAt);

        model.setCreatedAt(createdAtDate).setUpdatedAt(updatedAtDate);
    }
}
