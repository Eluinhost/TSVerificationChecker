package com.publicuhc.tsverificationchecker.models;

import java.util.Date;
import java.util.UUID;

/**
 * Represents a minecraftaccount in the database, API returns just the UUID
 */
public class MinecraftAccount extends APIModel {

    private final UUID m_mcUUID;

    public MinecraftAccount(UUID mcUUID, Date createdAt, Date updatedAt) {
        super(createdAt, updatedAt);
        m_mcUUID = mcUUID;
    }

    /**
     * @return the UUID for the account
     */
    public UUID getUUID() {
        return m_mcUUID;
    }
}
