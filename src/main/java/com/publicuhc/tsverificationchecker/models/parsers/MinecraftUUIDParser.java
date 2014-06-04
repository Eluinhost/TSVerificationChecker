package com.publicuhc.tsverificationchecker.models.parsers;

import java.text.ParseException;
import java.util.UUID;

public class MinecraftUUIDParser {

    /**
     * Parses a UUID string.
     * <p>32 char UUID string without the '-' in it</p>
     * <p>36 char UUID string with the '-' in it</p>
     * @param uuidString the string to parse
     * @return the UUID
     * @throws ParseException if something fails
     */
    public UUID parseUUID(String uuidString) throws ParseException {
        if(uuidString.length() == 32) {
            String timeLow = uuidString.substring(0, 8);
            String timeMid = uuidString.substring(8, 12);
            String timeHighVersion = uuidString.substring(12, 16);
            String variantSequence = uuidString.substring(16, 20);
            String node = uuidString.substring(20, 32);
            uuidString = timeLow + "-" + timeMid + "-" + timeHighVersion + "-" + variantSequence + "-" + node;
        }
        if(uuidString.length() != 36) {
            throw new ParseException("Given UUID string is incorrect length", uuidString.length());
        }
        try {
            return UUID.fromString(uuidString);
        } catch (IllegalArgumentException ex) {
            throw new ParseException("Given UUID string does not match UUID format", 0);
        }
    }
}
