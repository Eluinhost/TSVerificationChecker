package com.publicuhc.tsverificationchecker.fetchers;

import com.publicuhc.tsverificationchecker.exceptions.FetchException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class URLFetcher {

    /**
     * Fetch the raw string from the URL
     * @return String version of the contents
     * @throws FetchException
     */
    public String fetch(String urlString) throws FetchException {
        InputStream inputStream = null;
        try {
            URL url = new URL(urlString);
            inputStream = url.openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder jsonStringBuilder = new StringBuilder();
            String tempLine;
            while ((tempLine = reader.readLine()) != null) {
                jsonStringBuilder.append(tempLine);
            }
            return jsonStringBuilder.toString();
        } catch (IOException ioe) {
            throw new FetchException();
        } finally {
            try {
                if(inputStream != null)
                    inputStream.close();
            } catch (IOException ignored) {}
        }
    }
}
