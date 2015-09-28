package ru.wifiauth;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

public class WifiReceiver extends BroadcastReceiver {
    public WifiReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        android.os.Debug.waitForDebugger();

        NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
        if(info != null) {
            if(info.isConnected()) {
                // Do your work.

                // e.g. To check the Network Name or other info:
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ssid = wifiInfo.getSSID();

                Toast.makeText(context, "TEST TEST TEST\n" + ssid, Toast.LENGTH_LONG).show();
            }}
        Log.w("MyTAG", "ENTERED");
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
