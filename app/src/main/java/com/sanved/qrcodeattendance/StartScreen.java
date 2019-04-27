package com.sanved.qrcodeattendance;

import android.Manifest;
import android.content.Intent;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Map;

public class StartScreen extends AppCompatActivity {

    EditText user, pass;
    Button login;
    String url = "http://www.wiseassenterprises.com/attn/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_screen);

        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 111);

        user = findViewById(R.id.etUser);
        pass = findViewById(R.id.etPass);

        login = findViewById(R.id.bLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
            }
        });


    }

    public void sendData(){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //Toast.makeText(StartScreen.this, ""+response, Toast.LENGTH_SHORT).show();
                Log.i("Login Successful",""+response);
                if(response.contains("[]")){
                    Toast.makeText(StartScreen.this, "Login failed", Toast.LENGTH_SHORT).show();
                }else{

                    //JSONObject jsnobject = new JSONObject(response);
                    //Toast.makeText(StartScreen.this, "nagdi bai !!!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(StartScreen.this, ScanScreen.class);
                    startActivity(i);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(StartScreen.this, "my error :"+error, Toast.LENGTH_LONG).show();
                Log.i("Something went wrong",""+error);
            }
        }){
            @Override
            protected Map<String, String> getParams() {

                //String sid = "sanved";
                String sid = "" + user.getText().toString();
                String passw = "" + pass.getText().toString();
                //String passw = "sanved123";
                //String time = ""+ SimpleDateFormat.getInstance().format(Calendar.getInstance().getTime());

                Map<String,String> map = new HashMap<>();
                map.put("sid", sid);
                map.put("pass", passw);

                return map;
            }
        };
        queue.add(request);
    }

}
