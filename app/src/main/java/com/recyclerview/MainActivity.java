package com.recyclerview;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int CONNECTION_TIMEOUT = 19000;
    public static final int READ_TIMEOUT = 19000;
    private RecyclerView mRV;
    private ReviewAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new GetContacts().execute();
    }

    private class GetContacts extends AsyncTask<String, Integer, String> {

        ProgressDialog pDialog = new ProgressDialog(MainActivity.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                url = new URL("https://api.androidhive.info/contacts/");

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                Log.e("Tag2",e.toString());
                e.printStackTrace();
                return e.toString();
            }

            try {
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");

                conn.setDoOutput(true);

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                Log.e("Tag11",e1.toString());
                e1.printStackTrace();
                return e1.toString();
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server

                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return (result.toString());

                } else {

                    return ("unsuccessful");
                }

            } catch (IOException e) {
                Log.e("Tag",e.toString());
                e.printStackTrace();
                return e.toString();
            } finally {
                conn.disconnect();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            pDialog.dismiss();
            List<Contact> data=new ArrayList<>();
            Log.e("Tag","even comin in?");

            try {
                Log.e("Tag1","came in");
                JSONObject jObj = new JSONObject(result);
                JSONArray jArray = jObj.getJSONArray("contacts");

                // Extract data from json and store into ArrayList as class objects
                for(int i=0;i<jArray.length();i++){
                    JSONObject json_data = jArray.getJSONObject(i);
                    Contact cont = new Contact();
                    cont.id= json_data.getString("id");
                    Log.e("Tag1",cont.id);
                    cont.name= json_data.getString("name");
                    cont.email= json_data.getString("email");
                    cont.address= json_data.getString("address");
                    cont.gender= json_data.getString("gender");
                    data.add(cont);
                }

                Log.e("Tag","adapter going to attach");

                mRV = (RecyclerView)findViewById(R.id.movie);
                mAdapter = new ReviewAdapter(MainActivity.this, data);
                mRV.setAdapter(mAdapter);
                mRV.setLayoutManager(new LinearLayoutManager(MainActivity.this));

            } catch (JSONException e) {
                Log.e("Tag1",e.toString());
                Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
            }

        }


    }
}



