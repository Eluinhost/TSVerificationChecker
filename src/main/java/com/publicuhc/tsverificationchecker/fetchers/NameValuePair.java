package com.publicuhc.tsverificationchecker.fetchers;

public class NameValuePair {

    private final String m_key;
    private final String m_value;

    public NameValuePair(String key, String value) {
        m_key = key;
        m_value = value;
    }

    public String getKey() {
        return m_key;
    }

    public String getValue() {
        return m_value;
    }
}
