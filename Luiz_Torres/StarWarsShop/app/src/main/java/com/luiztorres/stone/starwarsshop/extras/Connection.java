package com.luiztorres.stone.starwarsshop.extras;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class Connection
{
    private static Connection mInstance;
    private RequestQueue mRequestQueue;
    private static Context ctx;

    private Connection(Context context) {
        ctx = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized Connection getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new Connection(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
