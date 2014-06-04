package com.publicuhc.tsverificationchecker.models.parsers;

import java.text.ParseException;
import java.util.Date;

public interface DateParser {

    /**
     * Parses the given date string
     * @param dateString the string to parse
     * @return the parsed date if successful
     * @throws java.text.ParseException
     */
    Date parseDate(String dateString) throws ParseException;
}
