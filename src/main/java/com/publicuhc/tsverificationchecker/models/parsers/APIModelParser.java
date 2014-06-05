package com.publicuhc.tsverificationchecker.models.parsers;

import com.publicuhc.tsverificationchecker.models.APIModel;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

public class APIModelParser {

    /**
     * <p>Expects a map with the following in it:</p>
     * <code>
     * <p>createdAt: millis since epoch</p>
     * <p>updatedAt: millis since epoch</p>
     * </code>
     * <p>Adds the createdAt and updatedAt values to the APIModel supplied</p>
     * @param map the map to parse
     * @throws ParseException on failing to parse the map
     */
    public void addParsedDataToModel(Map<String, Object> map, APIModel model) throws ParseException {
        Object createdAt = map.get("createdAt");
        if(createdAt == null || !(createdAt instanceof Number)) {
            throw new ParseException("Node doesn't contain a valid createdAt node", 0);
        }
        Number createdAtUnixTimeNumber = (Number) createdAt;
        long createdAtUnixTime = createdAtUnixTimeNumber.longValue();
        Date createdAtDate = new Date(createdAtUnixTime);

        Object updatedAt = map.get("updatedAt");
        if(updatedAt == null || !(updatedAt instanceof Number)) {
            throw new ParseException("Minecraft account doesn't contain a valid updatedAt node", 0);
        }
        Number updatedAtUnixTimeNumber = (Number) updatedAt;
        long updatedAtUnixTime = updatedAtUnixTimeNumber.longValue();
        Date updatedAtDate = new Date(updatedAtUnixTime);

        model.setCreatedAt(createdAtDate).setUpdatedAt(updatedAtDate);
    }
}
