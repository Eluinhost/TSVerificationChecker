package com.publicuhc.tsverificationchecker.models;

import java.util.HashMap;
import java.util.UUID;

/**
 * Response from the API for verified checks for minecraft UUIDs
 */
public class VerificationResponse {

    private HashMap<UUID, UUIDResponse> m_responses;

    public HashMap<UUID, UUIDResponse> getResponseMap() {
        return m_responses;
    }

    public VerificationResponse setResponseMap(HashMap<UUID, UUIDResponse> map) {
        m_responses = map;
        return this;
    }
}
