package com.epitech.jibb.canscounter.activity;

import android.graphics.Color;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import android.util.Log;
import android.widget.ListView;
import android.widget.SearchView;

import com.epitech.jibb.canscounter.R;
import com.epitech.jibb.canscounter.adapter.UsersAdapter;
import com.epitech.jibb.canscounter.api.DodoApi;
import com.epitech.jibb.canscounter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private JSONArray _users = new JSONArray();
    private ListView _userListView;

    // LOG
    private final String TAG = "INFO_MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        final SearchView searchView = (SearchView) findViewById(R.id.usernameInput);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });

        DodoApi.getUsers(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response.getString("status").equals("ok")) {
                        _users = response.getJSONArray("data");
                        _userListView = (ListView) findViewById(R.id.users_listView);
                        List<User> users = new ArrayList<>();
                        for (int i = 0; i < _users.length(); ++i) {
                            users.add(new User(_users.getJSONObject(i).getString("name"), Color.GRAY, _users.getJSONObject(i).getJSONArray("cans").length()));
                        }
                        UsersAdapter adapter = new UsersAdapter(MainActivity.this, users);
                        _userListView.setAdapter(adapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.i(TAG, "Can't get the list of users: " + statusCode + " - " + String.valueOf(errorResponse));
            }
        });
    }
}
