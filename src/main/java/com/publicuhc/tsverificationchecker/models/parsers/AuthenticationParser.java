package com.publicuhc.tsverificationchecker.models.parsers;

import com.publicuhc.tsverificationchecker.models.Authentication;
import com.publicuhc.tsverificationchecker.models.MinecraftAccount;
import com.publicuhc.tsverificationchecker.models.TeamspeakAccount;

import java.text.ParseException;
import java.util.Map;

public class AuthenticationParser {

    private final APIModelParser m_modelParser;
    private final MinecraftAccountParser m_mcAccountParser;
    private final TeamspeakAccountParser m_tsAccountParser;

    public AuthenticationParser(APIModelParser modelParser, MinecraftAccountParser mcAccountParser, TeamspeakAccountParser tsAccountParser) {
        m_modelParser = modelParser;
        m_mcAccountParser = mcAccountParser;
        m_tsAccountParser = tsAccountParser;
    }

    /**
     * <p>Expects a map in the format:</p>
     * <code>
     * <p>createdAt: RFC2822 time</p>
     * <p>updatedAt: RFC2822 time</p>
     * <p>minecraftAccount: A minecraft account object to be parsed</p>
     * <p>teamspeakAccount: A teamspeak account object to be parsed</p>
     * </code>
     * @param map the map to parse
     * @return A parsed MinecraftAccount
     * @throws java.text.ParseException on failing to parse the map
     */
    @SuppressWarnings("unchecked")
    public Authentication parseAuthentication(Map<String, Object> map) throws ParseException {
        Object minecraftAccountObject = map.get("minecraftAccount");
        if(null == minecraftAccountObject || !(minecraftAccountObject instanceof Map)) {
            throw new ParseException("Authentication doesn't contain a valid minecraftAccount node", 0);
        }
        Map<String, Object> minecraftAccountMap = (Map<String, Object>) minecraftAccountObject;

        MinecraftAccount mcAccount = m_mcAccountParser.parse(minecraftAccountMap);

        Object teamspeakAccountObject = map.get("teamspeakAccount");
        if(null == teamspeakAccountObject || !(teamspeakAccountObject instanceof Map)) {
            throw new ParseException("Authentication doesn't contain a valid teamspeakAccount node", 0);
        }
        Map<String, Object> teamspeakAccountMap = (Map<String, Object>) teamspeakAccountObject;

        TeamspeakAccount tsAccount = m_tsAccountParser.parse(teamspeakAccountMap);

        Authentication authentication = new Authentication().setMinecraftAccount(mcAccount).setTeamspeakAccount(tsAccount);

        m_modelParser.addParsedDataToModel(map, authentication);

        return authentication;
    }
}
