package com.am.store.starwars.helper;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Log;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by Augusto on 13/01/2017.
 */

public final class AndroidLogger implements Serializable {

    private static AndroidLogger logger;

    private boolean logOn = false;

    private AndroidLogger() {

    }

    private static void createInstance() {
        if (logger == null) {
            logger = new AndroidLogger();
        }
    }

    public static AndroidLogger getInstance() {
        createInstance();
        return logger;
    }

    public static void configurationLog(Context context) {
        createInstance();
        logger.logOn = ( 0 != ( context.getApplicationInfo().flags &= ApplicationInfo.FLAG_DEBUGGABLE ) );
    }

    public void info(String constante, Object... message) {
        createInstance();

        if (logger.logOn) {
            Log.i(constante, Arrays.toString(message));
        }
    }

    public void info(String constante, Exception e, Object... message) {
        createInstance();

        if (logger.logOn) {
            Log.i(constante, Arrays.toString(message), e);
        }
    }

    public void error(String constante, Object... message) {
        createInstance();

        if (logger.logOn) {
            Log.e(constante, Arrays.toString(message));
        }
    }

    public void error(String constante, Exception e, Object... message) {
        createInstance();

        if (logger.logOn) {
            Log.e(constante, Arrays.toString(message), e);
        }
    }
    
    
    public void debug(String constante, Object... message) {
        createInstance();

        if (logger.logOn) {
            Log.d(constante, Arrays.toString(message));
        }
    }

    public void debug(String constante, Exception e, Object... message) {
        createInstance();

        if (logger.logOn) {
            Log.d(constante, Arrays.toString(message), e);
        }
    }
    
    public void warn(String constante, Object... message) {
        createInstance();

        if (logger.logOn) {
            Log.w(constante, Arrays.toString(message));
        }
    }

    public void warn(String constante, Exception e, Object... message) {
        createInstance();

        if (logger.logOn) {
            Log.w(constante, Arrays.toString(message), e);
        }
    }
}