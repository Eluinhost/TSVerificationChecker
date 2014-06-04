package com.publicuhc.tsverificationchecker.fetchers;

import com.publicuhc.tsverificationchecker.exceptions.FetchException;

import java.util.UUID;

public class APIFetcher {

    private final URLFetcher m_fetcher;
    private final String m_baseURL;

    public APIFetcher(URLFetcher fetcher, String baseURL) {
        m_fetcher = fetcher;
        m_baseURL = baseURL;
    }

    public String fetchVerified(UUID uuid) throws FetchException {
        return m_fetcher.fetch(m_baseURL + "/api/verified/" + uuid.toString().replaceAll("-", ""));
    }

    public String fetchOnline(UUID uuid) throws FetchException {
        return m_fetcher.fetch(m_baseURL + "/api/online/" + uuid.toString().replaceAll("-", ""));
    }
}
