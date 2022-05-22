package com.example.actmysql.app;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class AppController extends Application {
    public static final String TAG = AppController.class.getSimpleName();

    private RequestQueue mRequeastQueue;
    private static AppController mInstance;

    @Override
    public void onCreate(){
        super.onCreate();
        mInstance = this;
    }
    public static synchronized AppController getInstance(){return mInstance;}

    public RequestQueue getmRequeastQueue(){
        if (mRequeastQueue == null)
        {
            mRequeastQueue = Volley.newRequestQueue(getApplicationContext());
        }return mRequeastQueue;

    }
    public <T> void addToRequestQueue(Request<T> req, String tag){
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getmRequeastQueue().add(req);
    }
    public <T> void addToRequestQueue(Request <T> req){
        req.setTag(TAG);
        getmRequeastQueue().add(req);
    }
    public void cancelPendingRequest (Object tag){
        if (mRequeastQueue != null){
            mRequeastQueue.cancelAll(tag);
        }
    }
}
