package com.publicuhc.tsverificationchecker.models;

import java.util.List;

/**
 * Response from the API for verified checks for minecraft UUIDs
 */
public class VerificationResponse {

    private boolean m_verified;
    private List<Authentication> m_authentications;

    /**
     * @return if the minecraft account is verified
     */
    public boolean isVerified() {
        return m_verified;
    }

    /**
     * @return all the authentications attributed to the account
     */
    public List<Authentication> getAuthentications() {
        return m_authentications;
    }

    public VerificationResponse setVerified(boolean verified) {
        m_verified = verified;
        return this;
    }

    public VerificationResponse setAuthentications(List<Authentication> authentications) {
        m_authentications = authentications;
        return this;
    }
}
