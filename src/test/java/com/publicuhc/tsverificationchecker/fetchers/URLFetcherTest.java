package com.publicuhc.tsverificationchecker.fetchers;

import com.publicuhc.tsverificationchecker.exceptions.FetchException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(PowerMockRunner.class)
public class URLFetcherTest {

    @Test
    public void testFetchValid() throws FetchException {
        URLFetcher fetcher = new URLFetcher(getClass().getResource("/simpleJSON.json"));

        assertThat(fetcher.fetch()).isEqualTo("{\"key\":\"value\", \"array\":[\"value\",\"value\"]}");
    }

    @Test(expected = FetchException.class)
    public void testFetchInvalid() throws FetchException, MalformedURLException {
        URL fileURL = new URL("http://nonvaliddomain.publicuhc.com/skfshdiuyfwieufyhskjfhjkdjfhiue"); //doesn't exist
        URLFetcher fetcher = new URLFetcher(fileURL);
        fetcher.fetch();
    }
}
