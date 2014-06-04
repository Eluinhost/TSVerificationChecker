package com.publicuhc.tsverificationchecker.models;

public class Authentication extends APIModel {

    private MinecraftAccount m_mcAccount;
    private TeamspeakAccount m_tsAccount;

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

    public Authentication setMinecraftAccount(MinecraftAccount account) {
        m_mcAccount = account;
        return this;
    }

    public Authentication setTeamspeakAccount(TeamspeakAccount account) {
        m_tsAccount = account;
        return this;
    }
}
