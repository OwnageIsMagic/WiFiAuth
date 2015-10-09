package ru.wifiauth;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WifiReceiver extends BroadcastReceiver {
    public WifiReceiver() {
    }
	
	boolean authorized;
	NetworkInfo info;
	WifiManager wifiManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        /** This method is called when the BroadcastReceiver is receiving an Intent broadcast.**/
       // String ssid = "";
	   authorized = false;
        info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
        if (info != null) {
            if (info.isConnected()) {
                wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                //ssid = wifiInfo.getSSID();
                if (wifiInfo.getSSID().equals("\"MGUPI-WiFi\"")) {
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                auth("http://1.1.1.10/login.html", "buttonClicked=4");
                            } catch (IOException e) {
                                Log.e("WiFiAuth", "IOError", e);
                            }
                        }
                    });
                    thread.start();
					authorized = true;
					//wifiManager.reconnect();
                }
            }
		//if (info.getDetailedState() == NetworkInfo.DetailedState.DISCONNECTED) wifiManager.reconnect();
		}
		if (authorized)
        Toast.makeText(context, "Authorized", Toast.LENGTH_LONG).show();
		Toast.makeText(context, info.toString(), Toast.LENGTH_LONG).show();
    }

    private void auth(String request, String urlParameters) throws IOException {
		Log.d("WiFiAuth", "auth()");
        
        byte[] postData = urlParameters.getBytes("UTF-8");
        URL url = new URL(request);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setInstanceFollowRedirects(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("charset", "utf-8");
        conn.setRequestProperty("Content-Length", Integer.toString(postData.length));
        conn.setUseCaches(false);

        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        try {
            wr.write(postData);
            wr.flush();
        } finally {
            wr.close();
        }
        conn.connect();

        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        // StringBuilder str = new StringBuilder();
        // for ( int c = in.read(); c != -1; c = in.read() ) str.append((char) c);
        // System.out.print(str.toString());
        //System.out.print((char)c);

        in.close();
        conn.disconnect();
    }
}
