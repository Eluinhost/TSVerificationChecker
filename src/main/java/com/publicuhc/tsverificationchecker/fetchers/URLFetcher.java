package com.publicuhc.tsverificationchecker.fetchers;

import com.publicuhc.tsverificationchecker.exceptions.FetchException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;

public class URLFetcher {

    private String buildParameterString(List<NameValuePair> pairs) {
        try {
            StringBuilder params = new StringBuilder();
            Iterator<NameValuePair> pairIterator = pairs.iterator();
            while (pairIterator.hasNext()) {
                NameValuePair pair = pairIterator.next();
                params.append(pair.getKey())
                        .append("=")
                        .append(URLEncoder.encode(pair.getValue(), "utf-8"));
                if(pairIterator.hasNext()) {
                    params.append("&");
                }
            }
            return params.toString();
        }catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
            return "";
        }
    }

    /**
     * Fetch the raw string from the URL using POST with the given parameters
     * @return String version of the contents
     * @throws FetchException
     */
    public String fetch(String urlString, List<NameValuePair> parameters) throws FetchException {
        URL url;
        HttpURLConnection connection = null;
        try {
            url = new URL(urlString);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            String urlParameters = buildParameterString(parameters);

            connection.setRequestProperty("Content-Length", Integer.toString(urlParameters.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes (urlParameters);
            wr.flush();
            wr.close();

            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuilder response = new StringBuilder();
            while((line = rd.readLine()) != null) {
                response.append(line);
            }
            rd.close();
            return response.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new FetchException();
        } finally {
            if(connection != null) {
                connection.disconnect();
            }
        }
    }
}
