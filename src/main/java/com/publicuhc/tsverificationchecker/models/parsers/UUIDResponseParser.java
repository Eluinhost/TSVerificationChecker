package com.publicuhc.tsverificationchecker.models.parsers;

import com.publicuhc.tsverificationchecker.models.Authentication;
import com.publicuhc.tsverificationchecker.models.MinecraftAccount;
import com.publicuhc.tsverificationchecker.models.UUIDResponse;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UUIDResponseParser {

    private final MinecraftAccountParser m_minecraftAccountParser;
    private final AuthenticationParser m_authenticationParser;

    public UUIDResponseParser(MinecraftAccountParser minecraftAccountParser, AuthenticationParser authenticationParser) {
        m_minecraftAccountParser = minecraftAccountParser;
        m_authenticationParser = authenticationParser;
    }

    /**
     * <p>Expects a map in the format:</p>
     * <code>
     * <p>minecraftAccount: RFC2822 time</p>
     * <p>authentications: List of authentications to parse</p>
     * </code>
     * <p>OR just a boolean</p>
     *
     * @param uuidResponseNode the object to parse
     * @return A parsed TeamspeakAccount
     * @throws java.text.ParseException on failing to parse the map
     */
    @SuppressWarnings("unchecked")
    public UUIDResponse parse(Object uuidResponseNode) throws ParseException {
        if(uuidResponseNode instanceof Boolean) {
            return new UUIDResponse().setIsVerified(false);
        }
        if(!(uuidResponseNode instanceof Map)) {
            throw new ParseException("UUID response is not a boolean nor a map", 0);
        }
        Map<String, Object> map = (Map<String, Object>) uuidResponseNode;

        Object mcAccountObject = map.get("minecraftAccount");
        if(mcAccountObject == null || !(mcAccountObject instanceof Map)) {
            throw new ParseException("UUID response doesn't contain a valid minecraft account node", 0);
        }
        Map<String, Object> mcAccountMap = (Map<String, Object>) mcAccountObject;

        MinecraftAccount mcAccount = m_minecraftAccountParser.parse(mcAccountMap);

        Object authListObject = map.get("authentications");
        if(authListObject == null || !(authListObject instanceof List)) {
            throw new ParseException("UUID response doesn't contain a valid authentications list", 0);
        }
        List authList = (List) authListObject;

        List<Authentication> authentications = new ArrayList<Authentication>();

        for(Object authenticationObject : authList) {
            if(authenticationObject == null || !(authenticationObject instanceof Map)) {
                throw new ParseException("UUID Response contains an invalid authenticaion node", 0);
            }
            Map<String, Object> authenticationMap = (Map<String, Object>) authenticationObject;

            authentications.add(m_authenticationParser.parseAuthentication(authenticationMap));
        }

        return new UUIDResponse().setAuthentications(authentications).setMinecraftAccount(mcAccount);
    }
}
