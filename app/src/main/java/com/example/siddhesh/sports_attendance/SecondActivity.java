package com.example.siddhesh.sports_attendance;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

public class SecondActivity extends AppCompatActivity {
    ListView listView;
    EditText eUID;
    EditText eName;
    ArrayList<String> listItems;
    ArrayAdapter<String> adapter;
    JSONObject postDataParams;
    BufferedWriter writer;
    OutputStream os;
    HttpURLConnection conn;
    URL url;

    String sportsname;
    String date;
    String stime;
    String etime;
    int i=0;
    int q=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        listView = (ListView) findViewById(R.id.lstNames);
        eUID = (EditText)findViewById(R.id.uid);
        eName = (EditText)findViewById(R.id.name);
        listItems = new ArrayList<String>();
        //listItems.add("First Item - added on Activity Create");
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, listItems);
        listView.setAdapter(adapter);
        //listView.setOnItemClickListener(MshowforItem);

        Intent intent = getIntent();

        sportsname = intent.getStringExtra("sportsname");
        date = intent.getStringExtra("date");
        stime = intent.getStringExtra("stime");
        etime = intent.getStringExtra("etime");



    }

    /*private AdapterView.OnItemClickListener MshowforItem = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String uid = eUID.getText().toString();
            String name = eName.getText().toString();
            String add1 = ""+uid+" : "+name;

            listItems.set(position,add1);

            adapter.notifyDataSetChanged();
        }
    };*/

    public void edit(View view) {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position,
                                    long id) {
                String uid = eUID.getText().toString();
                String name = eName.getText().toString();
                String add1 = ""+uid+" : "+name;

                listItems.set(position,add1);

                adapter.notifyDataSetChanged();


            }
        });
    }


    public void add(View view) {
        String uid = eUID.getText().toString();
        String name = eName.getText().toString();
        String add1 = ""+uid+" : "+name;
        listItems.add(add1);
        adapter.notifyDataSetChanged();

       /* */

        eUID.setText("");
        eName.setText("");
    }

    public void back(View view) {
        Intent in = new Intent(SecondActivity.this, FirstActivity.class);
        startActivity(in);

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void submit(View view) {
        for(i=0;i<adapter.getCount();i++) {

            new SendRequest().execute();

        }
    }


    public class SendRequest extends AsyncTask<String, Void, String> {

        protected void onPreExecute(){}

        protected String doInBackground(String... arg0) {



                try {

                        url = new URL("https://script.google.com/macros/s/AKfycbwEHl1NydHVUfHdEMiCd7Qq6yhQqjahaIEFB1Lyp77c6ekGHxM/exec");

                        postDataParams = new JSONObject();

                        String id = "1iAuAhnTLoESqHPFhvQDQSzKvIm0Sa_8q32vzyM1QOW4";

                        postDataParams.put("sportsname", sportsname);
                        postDataParams.put("date", date);
                        postDataParams.put("stime", stime);
                        postDataParams.put("etime", etime);
                        postDataParams.put("uids_names", adapter.getItem(q));
                        postDataParams.put("id", id);
                    q=q+1;


                        Log.e("params", postDataParams.toString());

                        conn = (HttpURLConnection) url.openConnection();
                        conn.setReadTimeout(15000 /* milliseconds */);
                        conn.setConnectTimeout(15000 /* milliseconds */);
                        conn.setRequestMethod("POST");
                        conn.setDoInput(true);
                        conn.setDoOutput(true);

                        os = conn.getOutputStream();
                        writer = new BufferedWriter(
                                new OutputStreamWriter(os, "UTF-8"));
                        writer.write(getPostDataString(postDataParams));

                        writer.flush();


                        writer.close();
                        os.close();


                        int responseCode = conn.getResponseCode();

                        if (responseCode == HttpsURLConnection.HTTP_OK) {

                            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                            StringBuffer sb = new StringBuffer("");
                            String line = "";

                            while ((line = in.readLine()) != null) {

                                sb.append(line);
                                break;
                            }

                            in.close();
                            return sb.toString();


                        } else {
                            return new String("false : " + responseCode);
                        }

                    }




                catch (Exception e) {
                    return new String("Exception: " + e.getMessage());
                }

            }



        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(), result,
                    Toast.LENGTH_LONG).show();



        }
    }


    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }



}
