package com.example.myapplicationc1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class MyReceiver extends BroadcastReceiver {



    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving

        if (checkNetwork(context)) {

            Toast.makeText(context, "اتصال برقرار است", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, " اتصال برقرار نیست ", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(context, "تست ورود به رسیور", Toast.LENGTH_SHORT).show();
//        throw new UnsupportedOperationException("Not yet implemented");
    }

    private boolean checkNetwork(Context mContext) {
        try {
            ConnectivityManager conManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = conManager.getActiveNetworkInfo();
            return (nInfo != null && nInfo.isConnected());
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }

    }

}