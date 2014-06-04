package com.publicuhc.tsverificationchecker.models;

import java.util.List;

/**
 * Same as a verification response but includes a list of online UUIDs
 */
public class OnlineVerificationResponse extends VerificationResponse {

    private List<String> m_online;

    public OnlineVerificationResponse() {}

    public OnlineVerificationResponse(VerificationResponse response) {
        setAuthentications(response.getAuthentications());
        setVerified(response.isVerified());
    }

    /**
     * @return A list of teamspeak UUIDs that are online
     */
    public List<String> getOnlineUUIDs() {
        return m_online;
    }

    public OnlineVerificationResponse setOnlineUUIDs(List<String> uuids) {
        m_online = uuids;
        return this;
    }
}
