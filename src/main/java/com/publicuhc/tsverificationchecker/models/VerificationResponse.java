package com.publicuhc.tsverificationchecker.models;

import java.util.List;

/**
 * Response from the API for verified checks for minecraft UUIDs
 */
public class VerificationResponse {

    private boolean m_verified;
    private List<Authentication> m_authentications;

    public VerificationResponse(boolean verified, List<Authentication> authentications) {
        m_verified = verified;
        m_authentications = authentications;
    }

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
}
