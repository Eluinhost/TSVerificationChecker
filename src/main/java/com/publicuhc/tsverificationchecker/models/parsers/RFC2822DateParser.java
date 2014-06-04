package com.publicuhc.tsverificationchecker.models.parsers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RFC2822DateParser implements DateParser {

    public static final String RFC2822_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss Z";

    private final SimpleDateFormat m_formatter = new SimpleDateFormat(RFC2822_DATE_FORMAT);

    /**
     * Parse the string in RFC2822 date format
     * @param dateString the string to parse
     * @return the parsed date
     * @throws ParseException
     */
    @Override
    public Date parseDate(String dateString) throws ParseException {
        return m_formatter.parse(dateString);
    }
}
