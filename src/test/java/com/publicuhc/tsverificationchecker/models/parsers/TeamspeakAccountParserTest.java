package com.publicuhc.tsverificationchecker.models.parsers;

import com.publicuhc.tsverificationchecker.models.TeamspeakAccount;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(PowerMockRunner.class)
public class TeamspeakAccountParserTest {

    private TeamspeakAccountParser parser;

    private final String createdAtString = "Thu, 29 May 2014 18:08:48 +0000";
    private final String updatedAtString = "Tue, 03 Jun 2014 13:32:10 +0000";
    private final DateParser dateParser = new RFC2822DateParser();
    private final String uuid = "ewo4M0KT59ifNUKEV/FHEqoFCI4=";
    private Date createdAt;
    private Date updatedAt;

    @Before
    public void onStartUp() throws ParseException {
        parser = new TeamspeakAccountParser(new RFC2822DateParser());
        createdAt = dateParser.parseDate(createdAtString);
        updatedAt = dateParser.parseDate(updatedAtString);
    }

    private HashMap<String, Object> getValidMap() {
        HashMap<String, Object> account = new HashMap<String, Object>();
        account.put("createdAt", createdAtString);
        account.put("updatedAt", updatedAtString);
        account.put("uuid", uuid);
        return account;
    }

    @Test
    public void testValidTeamspeakAccountParse() throws ParseException {
        TeamspeakAccount tsAccount = parser.parse(getValidMap());

        assertThat(tsAccount.getUUID().toLowerCase()).isEqualTo(uuid.toLowerCase());
        assertThat(tsAccount.getCreatedAt()).isEqualTo(createdAt);
        assertThat(tsAccount.getUpdatedAt()).isEqualTo(updatedAt);
    }

    @Test(expected = ParseException.class)
    public void testInvalidFormatCreatedAt() throws ParseException {
        HashMap<String, Object> account = getValidMap();
        account.put("createdAt", "invalid date string");

        parser.parse(account);
    }

    @Test(expected = ParseException.class)
    public void testInvalidFormatUpdatedAt() throws ParseException {
        HashMap<String, Object> account = getValidMap();
        account.put("updatedAt", "invalid date string");

        parser.parse(account);
    }

    @Test(expected = ParseException.class)
    public void testMissingCreatedAt() throws ParseException {
        HashMap<String, Object> account = getValidMap();
        account.remove("createdAt");

        parser.parse(account);
    }

    @Test(expected = ParseException.class)
    public void testMissingUpdatedAt() throws ParseException {
        HashMap<String, Object> account = getValidMap();
        account.remove("updatedAt");

        parser.parse(account);
    }

    @Test(expected = ParseException.class)
    public void testMissingUUID() throws ParseException {
        HashMap<String, Object> account = getValidMap();
        account.remove("uuid");

        parser.parse(account);
    }
}
