package com.publicuhc.tsverificationchecker.models.parsers;

import com.publicuhc.tsverificationchecker.models.TeamspeakAccount;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import java.text.ParseException;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(PowerMockRunner.class)
public class TeamspeakAccountParserTest {

    private TeamspeakAccountParser parser;

    private final String uuid = "ewo4M0KT59ifNUKEV/FHEqoFCI4=";
    private final long createdAt = 1415440923;
    private final long updatedAt = 1415540923;
    private final String lastName = "Eluinhost";

    @Before
    public void onStartUp() throws ParseException {
        parser = new TeamspeakAccountParser(new APIModelParser());
    }

    private HashMap<String, Object> getValidMap() {
        HashMap<String, Object> account = new HashMap<String, Object>();
        account.put("createdAt", createdAt);
        account.put("updatedAt", updatedAt);
        account.put("uuid", uuid);
        account.put("lastName", lastName);
        account.put("online", true);
        return account;
    }

    @Test
    public void testValidTeamspeakAccountParse() throws ParseException {
        TeamspeakAccount tsAccount = parser.parse(getValidMap());

        assertThat(tsAccount.getUUID().toLowerCase()).isEqualTo(uuid.toLowerCase());
        assertThat(tsAccount.getCreatedAt().getTime()).isEqualTo(createdAt);
        assertThat(tsAccount.getUpdatedAt().getTime()).isEqualTo(updatedAt);
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

    @Test(expected = ParseException.class)
    public void testMissingLastName() throws ParseException {
        HashMap<String, Object> account = getValidMap();
        account.remove("lastName");

        parser.parse(account);
    }

    @Test(expected = ParseException.class)
    public void testInvalidLastName() throws ParseException {
        HashMap<String, Object> account = getValidMap();
        account.put("lastName", 1299);

        parser.parse(account);
    }

    @Test(expected = ParseException.class)
    public void testMissingOnline() throws ParseException {
        HashMap<String, Object> account = getValidMap();
        account.remove("online");

        parser.parse(account);
    }

    @Test(expected = ParseException.class)
    public void testInvalidOnline() throws ParseException {
        HashMap<String, Object> account = getValidMap();
        account.put("online", 1299);

        parser.parse(account);
    }
}
