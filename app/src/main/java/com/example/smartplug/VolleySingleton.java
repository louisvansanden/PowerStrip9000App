package com.example.smartplug;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

class VolleySingleton {

    private static VolleySingleton singletonInstance;
    private RequestQueue requestQueue;

    private VolleySingleton(Context context){
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    static synchronized VolleySingleton getInstance(Context context) {
        if (singletonInstance == null){
            singletonInstance = new VolleySingleton(context);
        }
        return singletonInstance;
    }

    RequestQueue getRequestQueue(){
        return requestQueue;
    }

}
