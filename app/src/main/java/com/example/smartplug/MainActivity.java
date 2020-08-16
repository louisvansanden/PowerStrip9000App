package com.example.smartplug;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;
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
    
    Button next;

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
        
        next = findViewById(R.id.next);

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

        btnA.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                turnOff("ESP001");
                return true;
            }
        });
        btnB.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                turnOff("ESP001");
                return true;
            }
        });
        btnC.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                turnOff("ESP001");
                return true;
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshView();
            }
        });
        
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextSong();
            }
        });

    }


    private void nextSong() {

        final String token = "Bearer BQC_L_n9TYWTz5s3G1CKtymL8B9kUpSc-WDCtorpGRX4_lU816TLVeAB0KgOvVmEXqOEvFpaPYQPdnnuj-LRUK3rdvYA5ZN79AjP_FKuJlT_yfMcx51XdPMw1fN2exP0RHV3KFlEUSMQfyb7D-Jt68mMDiC8rDxashoWzbKAePuHO50VKOL3QfvlL5jLjTVtC9X7Yrr2V0DfshAi795OFR0XJNY3d9n0C0555XByjX7-GRuzwtp-A4Uip6qiSl_cHb3X3qFimWzuiXjG4rbAOXnJmnk";

        RequestQueue requestQueue = VolleySingleton.getInstance(this).getRequestQueue();
        String url = "https://api.spotify.com/v1/me/player/play?device_id=a508fe7138f3947d91c06b0e63e85e5dc7bded30";
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MainActivity.this, response, Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getMessage());
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            public Map<String,String> getHeaders() throws AuthFailureError{
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type","application/json");
                params.put("Accept", "application/json");
                params.put("Authorization", token);
                return params;
            }
        };

        requestQueue.add(stringRequest);

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

        if (id.equals("ESP001")){
            String[] ids = new String[]{"ESP001-A", "ESP001-B", "ESP001-C"};
            for (String thisId : ids){
                toggleState(thisId);
            }

        } else {

            final String deviceId = id;

            RequestQueue requestQueue = VolleySingleton.getInstance(this).getRequestQueue();
            String url = Secrets.IP + "database/toggleState.php";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equals("on")) {
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
            }) {

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("id", deviceId);
                    return params;
                }


                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("Content-Type", "application/x-www-form-urlencoded");
                    return params;
                }
            };

            requestQueue.add(stringRequest);
        }
    }

    private void turnOff(String id) {
        if (id.equals("ESP001")){
            String[] ids = new String[]{"ESP001-A", "ESP001-B", "ESP001-C"};
            for (String thisId : ids){
                turnOff(thisId);
            }

        } else {

            final String deviceId = id;

            RequestQueue requestQueue = VolleySingleton.getInstance(this).getRequestQueue();
            String url = Secrets.IP + "database/toggleState.php";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equals("on")) {
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
            }) {

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("id", deviceId);
                    params.put("io", "0");
                    return params;
                }


                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("Content-Type", "application/x-www-form-urlencoded");
                    return params;
                }
            };

            requestQueue.add(stringRequest);
        }
    }

    private void setTimer(String id) {

        Intent intent = new Intent(this, setTimer.class);
        intent.putExtra(EXTRA_MESSAGE, id);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }


}
