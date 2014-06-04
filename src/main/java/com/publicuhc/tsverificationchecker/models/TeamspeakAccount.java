package com.publicuhc.tsverificationchecker.models;

import java.util.Date;

public class TeamspeakAccount extends APIModel {

    private final String m_uuid;

    public TeamspeakAccount(String uuid, Date createdAt, Date updatedAt) {
        super(createdAt, updatedAt);
        m_uuid = uuid;
    }

    /**
     * @return the teamspeak UUID of the account
     */
    public String getUUID() {
        return m_uuid;
    }
}
