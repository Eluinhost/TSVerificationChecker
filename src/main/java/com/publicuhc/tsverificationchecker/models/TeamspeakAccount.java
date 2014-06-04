package com.publicuhc.tsverificationchecker.models;

public class TeamspeakAccount extends APIModel {

    private String m_uuid;

    /**
     * @return the teamspeak UUID of the account
     */
    public String getUUID() {
        return m_uuid;
    }

    public TeamspeakAccount setUUID(String uuid) {
        m_uuid = uuid;
        return this;
    }
}
