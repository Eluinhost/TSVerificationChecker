package com.publicuhc.tsverificationchecker.models.parsers;

import com.publicuhc.tsverificationchecker.models.Authentication;
import com.publicuhc.tsverificationchecker.models.OnlineVerificationResponse;
import com.publicuhc.tsverificationchecker.models.VerificationResponse;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VerificationResponseParser {

    private final AuthenticationParser m_authParser;

    public VerificationResponseParser(AuthenticationParser authParser) {
        m_authParser = authParser;
    }

    /**
     * <p>Expects a map in the format:</p>
     * <code>
     * <p>verified: bool</p>
     * <p>authentications: list of authentication objects to parse</p>
     * </code>
     * @param map the map to parse
     * @return A parsed VerificationResponse
     * @throws java.text.ParseException on failing to parse the map
     */
    @SuppressWarnings("unchecked")
    public VerificationResponse parseVerificationResponse(HashMap<String, Object> map) throws ParseException {
        Object verifiedObject = map.get("verified");
        if(verifiedObject == null || !(verifiedObject instanceof Boolean)) {
            throw new ParseException("Response doesn't contain a valid verified node", 0);
        }
        Boolean verified = (Boolean) verifiedObject;

        Object authenticationsObject = map.get("authentications");
        if(authenticationsObject == null || !(authenticationsObject instanceof List)) {
            throw new ParseException("Reponse doesn't contain a valid authentications list", 0);
        }
        List<Object> authenticationsObjectList = (List<Object>) authenticationsObject;

        List<Authentication> authentications = new ArrayList<Authentication>();

        for(Object authenticationObject : authenticationsObjectList) {
            if(authenticationObject == null || !(authenticationObject instanceof Map)) {
                throw new ParseException("Response contains an invalid authenticaion node", 0);
            }
            Map<String, Object> authenticationMap = (Map<String, Object>) authenticationObject;

            authentications.add(m_authParser.parseAuthentication(authenticationMap));
        }

        return new VerificationResponse().setAuthentications(authentications).setVerified(verified);
    }

    /**
     * Same as parseVerificationResponse but also checks for:
     * <code>online: list of string UUIDs of teamspeak accounts that are online</code>
     * @param map the map to parse
     * @return A parsed OnlineVerificationResponse
     * @throws ParseException
     */
    public OnlineVerificationResponse parseOnlineVerificationResponse(HashMap<String, Object> map) throws ParseException {
        OnlineVerificationResponse response = new OnlineVerificationResponse(parseVerificationResponse(map));

        Object onlineUUIDsObject = map.get("online");
        if(null == onlineUUIDsObject || !(onlineUUIDsObject instanceof List)) {
            throw new ParseException("Reponse doesn't contain a valid online UUIDs list", 0);
        }
        List onlineUUIDsList = (List) onlineUUIDsObject;

        List<String> onlineUUIDs = new ArrayList<String>();

        for(Object onlineUUIDObject : onlineUUIDsList) {
            if(onlineUUIDObject == null || !(onlineUUIDObject instanceof String)) {
                throw new ParseException("Online UUID list contains an invalid UUID string", 0);
            }
            onlineUUIDs.add((String) onlineUUIDObject);
        }

        return response.setOnlineUUIDs(onlineUUIDs);
    }
}
