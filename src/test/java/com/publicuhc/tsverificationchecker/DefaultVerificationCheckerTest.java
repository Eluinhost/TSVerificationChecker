package com.publicuhc.tsverificationchecker;

import com.publicuhc.tsverificationchecker.exceptions.FetchException;
import com.publicuhc.tsverificationchecker.fetchers.APIFetcher;
import com.publicuhc.tsverificationchecker.fetchers.URLFetcher;
import com.publicuhc.tsverificationchecker.models.parsers.*;
import org.json.simple.parser.JSONParser;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RunWith(PowerMockRunner.class)
@Ignore
public class DefaultVerificationCheckerTest {

    private List<UUID> unverifiedUUIDs = new ArrayList<UUID>();
    private List<UUID> verifiedNotOnlineUUIDs = new ArrayList<UUID>();
    private List<UUID> verifiedOnlineUUIDs = new ArrayList<UUID>();

    private NumberFormat nf = NumberFormat.getInstance();

    public DefaultVerificationCheckerTest()
    {
        nf.setMaximumFractionDigits(2);
        for(int i = 0; i<100; i++) {
            unverifiedUUIDs.add(UUID.fromString("000000000000-0000-0000-0000-00000000"));
            verifiedOnlineUUIDs.add(UUID.fromString("111111111111-1111-1111-1111-11111111"));
            verifiedNotOnlineUUIDs.add(UUID.fromString("222222222222-2222-2222-2222-22222222"));
        }
    }

    private VerificationChecker getNewChecker()
    {
        APIFetcher apiFetcher = new APIFetcher(new URLFetcher(), "http://test.publicuhc.com");

        //set up all the parsers
        APIModelParser modelParser = new APIModelParser();
        TeamspeakAccountParser tsAccountParser = new TeamspeakAccountParser(modelParser);
        AuthenticationParser authenticationParser = new AuthenticationParser(modelParser, tsAccountParser);
        MinecraftUUIDParser uuidParser = new MinecraftUUIDParser();
        UUIDResponseParser uuidResponseParser = new UUIDResponseParser(new MinecraftAccountParser(modelParser, uuidParser), authenticationParser);
        VerificationResponseParser verificationResponseParser = new VerificationResponseParser(uuidResponseParser, uuidParser);
        JSONParser jsonParser = new JSONParser();
        return new DefaultVerificationChecker(apiFetcher, verificationResponseParser, jsonParser);
    }

    @Test
    public void testTimings() throws ParseException, FetchException {
        int size = unverifiedUUIDs.size();
        System.out.println("BATCH REQUESTS - "+size+" REQUESTS OF "+size+" UUIDS");
        System.out.println("SINGLE REQUESTS - "+size+" REQUESTS OF 1 UUID EACH");
        System.out.println("");
        System.out.println("ALL UNVERIFIED ACCOUNTS");
        System.out.println("-----------------------");
        checkUUIDs(unverifiedUUIDs);
        System.out.println("");

        System.out.println("ALL VERIFIED AND NOT ONLINE ACCOUNTS");
        System.out.println("------------------------------------");
        checkUUIDs(verifiedNotOnlineUUIDs);
        System.out.println("");

        System.out.println("ALL VERIFIED AND ONLINE ACCOUNTS");
        System.out.println("--------------------------------");
        checkUUIDs(verifiedOnlineUUIDs);
    }

    private void checkUUIDs(List<UUID> uuids) throws ParseException, FetchException
    {
        VerificationChecker checker = getNewChecker();
        long amount = uuids.size();

        long startTime = System.currentTimeMillis();
        for(int i = 0; i<amount; i++) {
            checker.getVerificationResponseForUUIDs(uuids, false);
        }
        long time = System.currentTimeMillis() - startTime;
        System.out.println("VERIFY TIME BATCH REQUEST: " +  time + "ms total / " + nf.format((double)time/(double)amount/(double) amount) + "ms per uuid");

        startTime = System.currentTimeMillis();
        for(int i = 0; i<amount; i++) {
            checker.getVerificationResponseForUUIDs(uuids, true);
        }
        time = System.currentTimeMillis() - startTime;
        System.out.println("ONLINE TIME BATCH REQUEST: " +  time + "ms total / " + nf.format((double)time/(double)amount/(double)amount) + "ms per uuid");

        startTime = System.currentTimeMillis();
        for(UUID uuid : uuids) {
            checker.getVerificationResponseForUUIDs(Arrays.asList(uuid), false);
        }
        time = System.currentTimeMillis() - startTime;
        System.out.println("VERIFY TIME SINGLE REQUESTS: " +  time + "ms total / " + nf.format((double)time/(double)amount) + "ms per uuid");

        startTime = System.currentTimeMillis();
        for(UUID uuid : uuids) {
            checker.getVerificationResponseForUUIDs(Arrays.asList(uuid), true);
        }
        time = System.currentTimeMillis() - startTime;
        System.out.println("ONLINE TIME SINGLE REQUESTS: " +  time + "ms total / " + nf.format((double)time/(double)amount) + "ms per uuid");

    }
}
