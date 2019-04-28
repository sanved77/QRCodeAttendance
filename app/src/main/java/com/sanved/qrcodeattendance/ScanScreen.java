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

import com.blikoon.qrcodescanner.QrCodeActivity;

public class ScanScreen extends AppCompatActivity {

    Button scan;
    TextView tvWel;
    String name;

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
            Log.d("Scan","COULD NOT GET A GOOD RESULT.");
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

            Intent i = new Intent(ScanScreen.this, Success.class);
            i.putExtra("day", tokens[0]);
            i.putExtra("month", tokens[1]);
            i.putExtra("year", tokens[2]);
            startActivity(i);

        }
    }
}
