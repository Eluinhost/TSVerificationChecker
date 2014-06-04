package com.publicuhc.tsverificationchecker.models;

import java.util.Date;

public class Authentication extends APIModel {

    private final MinecraftAccount m_mcAccount;
    private final TeamspeakAccount m_tsAccount;

    public Authentication(MinecraftAccount mcAccount, TeamspeakAccount tsAccount, Date createdAt, Date updatedAt) {
        super(createdAt, updatedAt);
        m_mcAccount = mcAccount;
        m_tsAccount = tsAccount;
    }

    /**
     * @return the minecraft account for this authentication
     */
    public MinecraftAccount getMinecraftAccount() {
        return m_mcAccount;
    }

    /**
     * @return the teamspeak account for this authentication
     */
    public TeamspeakAccount getTeamspeakAccount() {
        return m_tsAccount;
    }
}
