package com.sanved.qrcodeattendance;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Success extends AppCompatActivity {

    String day,month, year;

    TextView wel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.success);

        if(savedInstanceState == null){

            Bundle extras = getIntent().getExtras();

            day = extras.getString("day");
            month = extras.getString("month");
            year = extras.getString("year");

        }else{

            day = (String) savedInstanceState.getSerializable("day");
            month = (String) savedInstanceState.getSerializable("month");
            year = (String) savedInstanceState.getSerializable("name");

        }

        wel = findViewById(R.id.tvWel2);

        wel.setText(month + "/" + day + "/" + year);

    }
}
