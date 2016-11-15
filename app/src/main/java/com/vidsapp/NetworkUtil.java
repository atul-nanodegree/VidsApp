package com.vidsapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by atul on 12/06/16.
 */
public class NetworkUtil {

    public static boolean isConnected(Context ctx) {

        boolean flag = false;

        ConnectivityManager connectivityManager = (ConnectivityManager) ctx.getSystemService(ctx.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            flag = true;
        }
        return flag;
    }
}
