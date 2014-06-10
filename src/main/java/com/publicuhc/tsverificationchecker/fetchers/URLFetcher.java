package com.publicuhc.tsverificationchecker.fetchers;

import com.publicuhc.tsverificationchecker.exceptions.FetchException;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.List;

public class URLFetcher {

    /**
     * Fetch the raw string from the URL using POST with the given parameters
     * @return String version of the contents
     * @throws FetchException
     */
    public String fetch(String urlString, List<NameValuePair> parameters) throws FetchException {
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(urlString);
            httpPost.setEntity(new UrlEncodedFormEntity(parameters));
            CloseableHttpResponse response = client.execute(httpPost);
            try {
                if (response.getStatusLine().getStatusCode() != 200) {
                    throw new FetchException();
                }
                HttpEntity entity = response.getEntity();
                String contents = EntityUtils.toString(entity);
                EntityUtils.consumeQuietly(entity);
                return contents;
            } finally {
                response.close();
            }
        } catch (Exception ex) {
            throw new FetchException();
        }
    }
}
