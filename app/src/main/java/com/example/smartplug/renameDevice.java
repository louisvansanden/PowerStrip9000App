package com.example.smartplug;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class renameDevice extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner devicesSpinner;
    TextView currentName;
    Button renameBtn;
    EditText newNameInput;

    String[] devices = {"ESP001-A", "ESP001-B", "ESP001-C"};
    String selectedDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rename_device);

        currentName = findViewById(R.id.currentName);
        renameBtn = findViewById(R.id.renameBtn);
        renameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rename(selectedDevice);
            }
        });

        newNameInput = findViewById(R.id.newNameInput);

        devicesSpinner = findViewById(R.id.devicesSpinner);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, devices);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        devicesSpinner.setAdapter(adapter);
        devicesSpinner.setOnItemSelectedListener(this);

    }

    private void rename(String id) {

        final String newName = newNameInput.getText().toString();
        final String selectedDeviceId = id;

        if (selectedDevice == null) {
            Toast.makeText(this, "Select a device to rename.", Toast.LENGTH_SHORT).show();
        } else {

            RequestQueue requestQueue = VolleySingleton.getInstance(this).getRequestQueue();
            String url = Secrets.IP + "database/renameDevice.php";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println(error.getMessage());
                    Toast.makeText(renameDevice.this, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }){

                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params=new HashMap<>();
                    params.put("id", selectedDeviceId);
                    params.put("name", newName);
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
            goBack();

        }

    }

    private void goBack() {

        String message = "Device renamed!";
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String deviceId = parent.getItemAtPosition(position).toString();
        selectedDevice = deviceId;
        getCurrentName(deviceId);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        selectedDevice = null;
    }

    private void getCurrentName(String id) {
        final String deviceId = id;

        RequestQueue requestQueue = VolleySingleton.getInstance(this).getRequestQueue();
        String url = Secrets.IP + "database/getName.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                currentName.setText(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getMessage());
                Toast.makeText(renameDevice.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){

            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<>();
                params.put("id", deviceId);
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
    }

}