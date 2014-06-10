package com.publicuhc.tsverificationchecker.models;

import java.util.List;

public class UUIDResponse {

    private MinecraftAccount m_minecraftAccount;
    private List<Authentication> m_authentications;
    private boolean m_isVerified;

    public MinecraftAccount getMinecraftAccount() {
        return m_minecraftAccount;
    }

    public UUIDResponse setMinecraftAccount(MinecraftAccount account) {
        m_minecraftAccount = account;
        return this;
    }

    public List<Authentication> getAuthentications() {
        return m_authentications;
    }

    public UUIDResponse setAuthentications(List<Authentication> authentications) {
        m_authentications = authentications;
        return this;
    }

    public boolean isVerified() {
        return m_isVerified;
    }

    public UUIDResponse setIsVerified(boolean verified) {
        m_isVerified = verified;
        return this;
    }
}
