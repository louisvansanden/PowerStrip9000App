package com.example.smartplug;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "timer.deviceId";

    Button btnA;
    Button btnB;
    Button btnC;

    ImageButton btnTimerA;
    ImageButton btnTimerB;
    ImageButton btnTimerC;

    Button statusA;
    Button statusB;
    Button statusC;

    ImageButton refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnA = findViewById(R.id.ESP001A);
        btnB = findViewById(R.id.ESP001B);
        btnC = findViewById(R.id.ESP001C);

        btnTimerA = findViewById(R.id.timerA);
        btnTimerB = findViewById(R.id.timerB);
        btnTimerC = findViewById(R.id.timerC);

        statusA = findViewById(R.id.statusA);
        statusB = findViewById(R.id.statusB);
        statusC = findViewById(R.id.statusC);

        refresh = findViewById(R.id.refresh);

        checkStatusDevices();

        getNames();

        btnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleState("ESP001-A");
            }
        });

        btnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleState("ESP001-B");
            }
        });

        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleState("ESP001-C");
            }
        });

        btnTimerA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTimer("ESP001-A");
            }
        });

        btnTimerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTimer("ESP001-B");
            }
        });

        btnTimerC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTimer("ESP001-C");
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshView();
            }
        });

    }

    private void refreshView() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void getNames() {

        getName("ESP001-A");
        getName("ESP001-B");
        getName("ESP001-C");
    }

    private void getName(String id) {

        final String deviceId = id;

        RequestQueue requestQueue = VolleySingleton.getInstance(this).getRequestQueue();
        String url = Secrets.IP + "database/getName.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (!response.equals("")) {

                    switch (deviceId) {
                        case "ESP001-A":
                            ((Button) findViewById(R.id.ESP001A)).setText(response);
                            break;
                        case "ESP001-B":
                            ((Button) findViewById(R.id.ESP001B)).setText(response);
                            break;
                        case "ESP001-C":
                            ((Button) findViewById(R.id.ESP001C)).setText(response);
                            break;
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getMessage());
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){

            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<>();
                params.put("id", deviceId);
                return params;
            }


            @Override
            public Map<String,String> getHeaders() throws AuthFailureError{
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };

        requestQueue.add(stringRequest);

    }

    private void checkStatusDevices() {

        checkStatusDevice("ESP001-A");
        checkStatusDevice("ESP001-B");
        checkStatusDevice("ESP001-C");

    }

    private void checkStatusDevice(String id) {
        final String deviceId = id;

        RequestQueue requestQueue = VolleySingleton.getInstance(this).getRequestQueue();
        String url = Secrets.IP + "database/getCurrentStatus.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("1")){
                    switch (deviceId) {
                        case "ESP001-A":
                            findViewById(R.id.statusA).setBackgroundColor(Color.parseColor("#00FF00"));
                            break;
                        case "ESP001-B":
                            findViewById(R.id.statusB).setBackgroundColor(Color.parseColor("#00FF00"));
                            break;
                        case "ESP001-C":
                            findViewById(R.id.statusC).setBackgroundColor(Color.parseColor("#00FF00"));
                            break;
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getMessage());
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){

            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<>();
                params.put("id", deviceId);
                return params;
            }


            @Override
            public Map<String,String> getHeaders() throws AuthFailureError{
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void toggleState(String id) {

        final String deviceId = id;

        RequestQueue requestQueue = VolleySingleton.getInstance(this).getRequestQueue();
        String url = Secrets.IP + "database/toggleState.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("on")){
                    switch (deviceId) {
                        case "ESP001-A":
                            findViewById(R.id.statusA).setBackgroundColor(Color.GREEN);
                            break;
                        case "ESP001-B":
                            findViewById(R.id.statusB).setBackgroundColor(Color.GREEN);
                            break;
                        case "ESP001-C":
                            findViewById(R.id.statusC).setBackgroundColor(Color.GREEN);
                            break;
                    }

                } else {
                    switch (deviceId) {
                        case "ESP001-A":
                            findViewById(R.id.statusA).setBackgroundColor(Color.RED);
                            break;
                        case "ESP001-B":
                            findViewById(R.id.statusB).setBackgroundColor(Color.RED);
                            break;
                        case "ESP001-C":
                            findViewById(R.id.statusC).setBackgroundColor(Color.RED);
                            break;
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getMessage());
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){

            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<String, String>();
                params.put("id", deviceId);
                return params;
            }


            @Override
            public Map<String,String> getHeaders() throws AuthFailureError{
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void setTimer(String id) {

        Intent intent = new Intent(this, setTimer.class);
        intent.putExtra(EXTRA_MESSAGE, id);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

}
