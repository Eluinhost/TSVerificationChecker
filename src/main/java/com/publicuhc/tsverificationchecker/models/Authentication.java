package com.publicuhc.tsverificationchecker.models;

public class Authentication extends APIModel {

    private TeamspeakAccount m_tsAccount;

    /**
     * @return the teamspeak account for this authentication
     */
    public TeamspeakAccount getTeamspeakAccount() {
        return m_tsAccount;
    }

    public Authentication setTeamspeakAccount(TeamspeakAccount account) {
        m_tsAccount = account;
        return this;
    }
}
