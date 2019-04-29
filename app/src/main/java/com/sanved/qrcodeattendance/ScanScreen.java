package com.sanved.qrcodeattendance;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.blikoon.qrcodescanner.QrCodeActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ScanScreen extends AppCompatActivity {

    Button scan;
    TextView tvWel;
    String name,day,month,year;
    String url = "http://wiseassenterprises.com/attn/regAtt.php";

    private static final int REQUEST_CODE_QR_SCAN = 101;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_screen);

        if(savedInstanceState == null){

            Bundle extras = getIntent().getExtras();

            name = extras.getString("name");

        }else{

            name = (String) savedInstanceState.getSerializable("name");

        }

        tvWel = findViewById(R.id.tvWel);
        tvWel.setText("Welcome " + name + " !");

        scan = findViewById(R.id.buttonScan);

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ScanScreen.this, QrCodeActivity.class);
                startActivityForResult( i,REQUEST_CODE_QR_SCAN);
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode != Activity.RESULT_OK)
        {
            Log.d("Scan","Bad QR Result");
            if(data==null)
                return;
            //Getting the passed result
            String result = data.getStringExtra("com.blikoon.qrcodescanner.error_decoding_image");
            if( result!=null)
            {
                Toast.makeText(this, "Bad result", Toast.LENGTH_SHORT).show();
            }
            return;

        }
        if(requestCode == REQUEST_CODE_QR_SCAN)
        {
            if(data==null)
                return;
            //Getting the passed result
            String result = data.getStringExtra("com.blikoon.qrcodescanner.got_qr_scan_relult");
            Log.d("Scanner","Have scan result in your app activity :"+ result);
            Toast.makeText(this, ""+result, Toast.LENGTH_SHORT).show();

            // String token
            String tokens[] = result.split("-");

            month = tokens[0];
            day = tokens[1];
            year = tokens[2];
            sendData();


        }
    }

    public void sendData(){

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i("Scan",""+response);
                if(response.contains("error")){
                    Toast.makeText(ScanScreen.this, "Upload failed", Toast.LENGTH_SHORT).show();
                }else{
                    Intent i = new Intent(ScanScreen.this, Success.class);
                    i.putExtra("day",day);
                    i.putExtra("month",month);
                    i.putExtra("year",year);
                    startActivity(i);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(ScanScreen.this, "my error :"+error, Toast.LENGTH_LONG).show();
                Log.i("Something went wrong",""+error);
            }
        }){
            @Override
            protected Map<String, String> getParams() {


                Map<String,String> map = new HashMap<>();
                map.put("sid", name);

                map.put("month", month);
                map.put("day", day);
                map.put("year", year);

                return map;
            }
        };
        queue.add(request);
    }
}
