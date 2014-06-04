package com.publicuhc.tsverificationchecker.models.parsers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import java.text.ParseException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(PowerMockRunner.class)
public class MinecraftUUIDParserTest {

    public static final String VALID_FULL = "01234567-89AB-CDEF-0123-456789012345";
    public static final String VALID_SHORT = "0123456789ABCDEF0123456789012345";

    private MinecraftUUIDParser parser;

    @Before
    public void onStartUp() {
        parser = new MinecraftUUIDParser();
    }

    @Test
    public void testUUIDValidParse() throws ParseException {
        UUID uuid = parser.parseUUID(VALID_FULL);

        assertThat(uuid.toString().toLowerCase()).isEqualTo(VALID_FULL.toLowerCase());
    }

    @Test
    public void testShortUUIDValidParse() throws ParseException {
        UUID uuid = parser.parseUUID(VALID_SHORT);

        assertThat(uuid.toString().toLowerCase()).isEqualTo(VALID_FULL.toLowerCase());
    }

    @Test(expected = ParseException.class)
    public void testInvalidLengthUUID() throws ParseException {
        parser.parseUUID("0123456789ABCDEF0123456789012");
    }

    @Test(expected = ParseException.class)
    public void testInvalidCharsUUID() throws ParseException {
        parser.parseUUID("0123456789|CDEF0123456Z89012345");
    }
}
