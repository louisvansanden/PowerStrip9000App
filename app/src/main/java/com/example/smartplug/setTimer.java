package com.example.smartplug;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class setTimer extends AppCompatActivity {

    String id;

    Button setTimer;

    Button btnHourDecPlus;
    Button btnHourUnitPlus;
    Button btnMinutesDecPlus;
    Button btnMinutesUnitPlus;

    Button btnHourDecMin;
    Button btnHourUnitMin;
    Button btnMinutesDecMin;
    Button btnMinutesUnitMin;

    TextView txtHourDec;
    TextView txtHourUnit;
    TextView txtMinutesDec;
    TextView txtMinutesUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_timer);

        Intent intent = getIntent();
        id = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        setTimer = findViewById(R.id.setTimer);

        /*
        Adding buttons
         */
        btnHourDecPlus = findViewById(R.id.btnHourDecPlus);
        btnHourUnitPlus = findViewById(R.id.btnHourUnitPlus);
        btnMinutesDecPlus = findViewById(R.id.btnMinutesDecPlus);
        btnMinutesUnitPlus = findViewById(R.id.btnMinutesUnitPlus);

        /*
        Subtracting buttons
         */
        btnHourDecMin = findViewById(R.id.btnHourDecMin);
        btnHourUnitMin = findViewById(R.id.btnHourUnitMin);
        btnMinutesDecMin = findViewById(R.id.btnMinutesDecMin);
        btnMinutesUnitMin = findViewById(R.id.btnMinutesUnitMin);

        /*
        Text Views
         */
        txtHourDec = findViewById(R.id.txtHourDec);
        txtHourUnit = findViewById(R.id.txtHourUnit);
        txtMinutesDec = findViewById(R.id.txtMinutesDec);
        txtMinutesUnit = findViewById(R.id.txtMinutesUnit);

        setTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTimerFor(id, txtHourDec.getText().toString(), txtHourUnit.getText().toString(), txtMinutesDec.getText().toString(), txtMinutesUnit.getText().toString());
            }
        });

        btnHourDecPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add("x000");
            }
        });
        btnHourUnitPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add("0x00");
            }
        });
        btnMinutesDecPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add("00x0");
            }
        });
        btnMinutesUnitPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add("000x");
            }
        });

        btnHourDecMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sub("x000");
            }
        });
        btnHourUnitMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sub("0x00");
            }
        });
        btnMinutesDecMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sub("00x0");
            }
        });
        btnMinutesUnitMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sub("000x");
            }
        });

    }

    private void add(String pos) {
        int nextInt;
        switch (pos){
            case "x000":
                nextInt = Integer.parseInt(txtHourDec.getText().toString()) + 1;
                if (nextInt > 9) {nextInt = 0;}
                txtHourDec.setText(String.valueOf(nextInt));
                break;
            case "0x00":
                nextInt = Integer.parseInt(txtHourUnit.getText().toString()) + 1;
                if (nextInt > 9) {nextInt = 0;}
                txtHourUnit.setText(String.valueOf(nextInt));
                break;
            case "00x0":
                nextInt = Integer.parseInt(txtMinutesDec.getText().toString()) + 1;
                if (nextInt > 9) {nextInt = 0;}
                txtMinutesDec.setText(String.valueOf(nextInt));
                break;
            case "000x":
                nextInt = Integer.parseInt(txtMinutesUnit.getText().toString()) + 1;
                if (nextInt > 9) {nextInt = 0;}
                txtMinutesUnit.setText(String.valueOf(nextInt));
                break;
        }

    }

    private void sub(String pos) {
        int prevInt;
        switch (pos){
            case "x000":
                prevInt = Integer.parseInt(txtHourDec.getText().toString()) - 1;
                if (prevInt < 0) {prevInt = 9;}
                txtHourDec.setText(String.valueOf(prevInt));
                break;
            case "0x00":
                prevInt = Integer.parseInt(txtHourUnit.getText().toString()) - 1;
                if (prevInt < 0) {prevInt = 9;}
                txtHourUnit.setText(String.valueOf(prevInt));
                break;
            case "00x0":
                prevInt = Integer.parseInt(txtMinutesDec.getText().toString()) - 1;
                if (prevInt < 0) {prevInt = 9;}
                txtMinutesDec.setText(String.valueOf(prevInt));
                break;
            case "000x":
                prevInt = Integer.parseInt(txtMinutesUnit.getText().toString()) - 1;
                if (prevInt < 0) {prevInt = 9;}
                txtMinutesUnit.setText(String.valueOf(prevInt));
                break;
        }
    }


    private void setTimerFor(String id, String xooo, String oxoo, String ooxo, String ooox) {

        final String deviceId = id;
        final String XOOO = xooo;
        final String OXOO = oxoo;
        final String OOXO = ooxo;
        final String OOOX = ooox;

        RequestQueue requestQueue = Volley.newRequestQueue(setTimer.this);
        String url = Secrets.IP + "database/toggleState.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getMessage());
                Toast.makeText(setTimer.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){

            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<>();
                params.put("id", deviceId);
                params.put("timer", "1");
                params.put("xooo", XOOO);
                params.put("oxoo", OXOO);
                params.put("ooxo", OOXO);
                params.put("ooox", OOOX);
                return params;
            }


            @Override
            public Map<String,String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };

        requestQueue.add(stringRequest);

        goBack();

    }

    private void goBack(){

        String hours = txtHourDec.getText().toString() + txtHourUnit.getText().toString();
        String minutes = txtMinutesDec.getText().toString() + txtMinutesUnit.getText().toString();

        String message = "Timer set for " + hours + " hours and " + minutes + " minutes.";

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
