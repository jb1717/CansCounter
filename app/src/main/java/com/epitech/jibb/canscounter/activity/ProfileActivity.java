package com.epitech.jibb.canscounter.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.epitech.jibb.canscounter.R;
import com.epitech.jibb.canscounter.api.DodoApi;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ProfileActivity extends AppCompatActivity {

    private String TAG = "INFO_ProfileActivity";
    private JsonHttpResponseHandler getUserHandler = new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            try {
                ListView history;
                TextView text_cans = (TextView) findViewById(R.id.nb_cans_textView);
                JSONObject data = response.getJSONObject("data");
                TextView username = (TextView) findViewById(R.id.username);
                username.setText(data.getString("name"));
                JSONArray cans = data.getJSONArray("cans");
                ArrayList<String> cans_list = new ArrayList<>();
                history = (ListView) findViewById(R.id.cans_history);
                text_cans.setText(String.valueOf(cans.length()));
                for (int i = 0; i < cans.length(); ++i) {
                    cans_list.add(cans.getJSONObject(i).getString("date"));
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(ProfileActivity.this, android.R.layout.simple_list_item_1, cans_list);
                history.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            Log.i(TAG, "Can't get the list of users: " + statusCode + " - " + String.valueOf(errorResponse));
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Bundle params = getIntent().getExtras();
        final String id = params.getString("id");

        DodoApi.getUser(id, getUserHandler);

        FloatingActionButton add_button = (FloatingActionButton) findViewById(R.id.add_cans_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DodoApi.addOneCan(id, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        Context context = getApplicationContext();
                        CharSequence text = "One more can...";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        DodoApi.getUser(id, getUserHandler);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Log.i(TAG, "Can't get the list of users: " + statusCode + " - " + String.valueOf(errorResponse));
                    }

                });
            }
        });

        Button back_button = (Button) findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}