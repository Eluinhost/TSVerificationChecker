package com.publicuhc.tsverificationchecker.models;

import java.util.List;

/**
 * Same as a verification response but includes a list of online UUIDs
 */
public class OnlineVerificationResponse extends VerificationResponse {

    private final List<String> m_online;

    public OnlineVerificationResponse(boolean verified, List<Authentication> authentications, List<String> onlineUUIDs) {
        super(verified, authentications);
        m_online = onlineUUIDs;
    }

    /**
     * @return A list of teamspeak UUIDs that are online
     */
    public List<String> getOnlineUUIDs() {
        return m_online;
    }
}
