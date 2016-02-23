package com.epitech.jibb.canscounter.api;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.util.Log;

public class DodoApi {

    private static AsyncHttpClient _client = new AsyncHttpClient();
    private static String _url = "http://10.10.252.167:7070/";

    public static void getUsers(JsonHttpResponseHandler callback) {
        _client.get(_url + "user", callback);
    }

    public static void getUser(String userid, JsonHttpResponseHandler callback) {
        _client.get(_url + "user/" + userid, callback);
    }

    public static void addUser(String username, JsonHttpResponseHandler callback) {
        RequestParams params = new RequestParams();
        params.put("params", username);
        _client.post(_url + "user", params, callback);
    }

    public static void addOneCan(String userid, JsonHttpResponseHandler callback) {
        _client.post(_url + "cans/" + userid, callback);
    }
}
