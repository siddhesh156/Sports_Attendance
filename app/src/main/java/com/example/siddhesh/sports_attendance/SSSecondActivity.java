package com.example.siddhesh.sports_attendance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SSSecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sssecond);

        //if (InternetConnection.checkConnection(getApplicationContext())) {
            Intent intent = new Intent(SSSecondActivity.this, ReadAllData.class);
            startActivity(intent);

        //} else {
          //  Toast.makeText(getApplicationContext(), "Check your internet connection", Toast.LENGTH_LONG).show();
        //}
    }
}
