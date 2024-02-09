package com.sp.madproject;

import java.util.HashMap;

public class EventVolleyHelper {
    static String region = "https://b3375405-7090-4847-ac1f-9ed3a6891df8-asia-south1.apps.astra.datastax.com/api/rest";
    static String url = region + "/v2/keyspaces/event-db/eventhelper/";
    static String Cassandra_Token = "AstraCS:OSsamFBYvtErPwpomSTFOQGf:5c0f97d0beb32e3bc2d19353bbc89dbae5ef67e67f0ac3daa5731513a7f2c7f8";
    static int lastID = 0;
    static HashMap getHeader(){
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Cassandra-Token", Cassandra_Token);
        headers.put("Accept", "application/json");
        return headers;
    }
}
