package com.publicuhc.tsverificationchecker.models.parsers;

import com.publicuhc.tsverificationchecker.models.MinecraftAccount;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import java.text.ParseException;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(PowerMockRunner.class)
public class MinecraftAccountParserTest {

    private MinecraftAccountParser parser;

    private final String uuid = "6ac803fd-132f-4540-a741-cb18ffeed8ce";
    private final long createdAt = 1415440923;
    private final long updatedAt = 1415540923;

    @Before
    public void onStartUp() throws ParseException {
        parser = new MinecraftAccountParser(new APIModelParser(), new MinecraftUUIDParser());
    }

    private HashMap<String, Object> getValidMap() {
        HashMap<String, Object> account = new HashMap<String, Object>();
        account.put("createdAt", createdAt);
        account.put("updatedAt", updatedAt);
        account.put("uuid", uuid);
        return account;
    }

    @Test
    public void testValidMinecraftAccountParse() throws ParseException {
        MinecraftAccount mcAccount = parser.parse(getValidMap());

        assertThat(mcAccount.getUUID().toString().toLowerCase()).isEqualTo(uuid.toLowerCase());
        assertThat(mcAccount.getCreatedAt().getTime()).isEqualTo(createdAt);
        assertThat(mcAccount.getUpdatedAt().getTime()).isEqualTo(updatedAt);
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
    public void testInvalidFormatUUID() throws ParseException {
        HashMap<String, Object> account = getValidMap();
        account.put("uuid", "invalid uuid string");

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
