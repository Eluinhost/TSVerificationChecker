package com.publicuhc.tsverificationchecker.models;

import java.util.UUID;

/**
 * Represents a minecraftaccount in the database, API returns just the UUID
 */
public class MinecraftAccount extends APIModel {

    private UUID m_mcUUID;
    private String m_lastName;

    /**
     * @return the UUID for the account
     */
    public UUID getUUID() {
        return m_mcUUID;
    }

    public MinecraftAccount setUUID(UUID uuid) {
        m_mcUUID = uuid;
        return this;
    }

    public String getLastName() {
        return m_lastName;
    }

    public MinecraftAccount setLastName(String lastName) {
        m_lastName = lastName;
        return this;
    }
}
