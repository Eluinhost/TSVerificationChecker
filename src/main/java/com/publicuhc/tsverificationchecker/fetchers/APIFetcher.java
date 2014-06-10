package com.publicuhc.tsverificationchecker.fetchers;

import com.publicuhc.tsverificationchecker.exceptions.FetchException;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class APIFetcher {

    private final URLFetcher m_fetcher;
    private final String m_baseURL;

    public APIFetcher(URLFetcher fetcher, String baseURL) {
        m_fetcher = fetcher;
        m_baseURL = baseURL;
    }

    public String fetchVerified(List<UUID> uuids, boolean checkOnline) throws FetchException {
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        for(UUID uuid : uuids) {
            parameters.add(new BasicNameValuePair("uuids[]", uuid.toString().replaceAll("-", "")));
        }
        return m_fetcher.fetch(m_baseURL + "/api/" + (checkOnline ? "online" : "verified"), parameters);
    }
}
