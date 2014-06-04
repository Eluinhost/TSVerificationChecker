package com.publicuhc.tsverificationchecker.models.parsers;

import com.publicuhc.tsverificationchecker.models.MinecraftAccount;

import java.text.ParseException;
import java.util.Map;
import java.util.UUID;

public class MinecraftAccountParser {

    private final APIModelParser m_modelParser;
    private final MinecraftUUIDParser m_uuidParser;

    public MinecraftAccountParser(APIModelParser modelParser, MinecraftUUIDParser uuidParser) {
        m_modelParser = modelParser;
        m_uuidParser = uuidParser;
    }

    /**
     * <p>Expects a map in the format:</p>
     * <code>
     * <p>createdAt: RFC2822 time</p>
     * <p>updatedAt: RFC2822 time</p>
     * <p>uuid: Minecraft UUID (without -)</p>
     * </code>
     * @param map the map to parse
     * @return A parsed MinecraftAccount
     * @throws ParseException on failing to parse the map
     */
    public MinecraftAccount parse(Map<String, Object> map) throws ParseException {
        Object uuidObject = map.get("uuid");
        if(uuidObject == null || !(uuidObject instanceof String)) {
            throw new ParseException("Minecraft account doesn't contain a valid UUID node", 0);
        }
        UUID uuid = m_uuidParser.parseUUID((String) uuidObject);

        MinecraftAccount account = new MinecraftAccount().setUUID(uuid);
        m_modelParser.addParsedDataToModel(map, account);

        return account;
    }

}
