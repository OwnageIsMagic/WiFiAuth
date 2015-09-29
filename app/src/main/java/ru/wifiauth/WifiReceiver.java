package ru.wifiauth;

import android.content.*;
import android.net.*;
import android.net.wifi.*;
import android.util.*;
import android.widget.*;
import java.io.*;
import java.net.*;

public class WifiReceiver extends BroadcastReceiver
	{
		public WifiReceiver( )
			{
			}

		@Override
		public void onReceive( Context context, Intent intent )
			{
			// TODO: This method is called when the BroadcastReceiver is receiving
			// an Intent broadcast.
			// android.os.Debug.waitForDebugger();
			//String ssid="";
			NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
			if ( info != null )
				{
				if ( info.isConnected() )
					{
					// Do your work.
					// e.g. To check the Network Name or other info:
					//WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
					//WifiInfo wifiInfo = wifiManager.getConnectionInfo();
					//ssid = wifiInfo.getSSID();
					//Log.w("MyTAG", "124");

					//if ( wifiInfo.getSSID().toString() == "MGUPI-WiFi" )
						//{
						//Log.w("MyTAG", "qwe");

						Thread thread = new Thread(new Runnable() {
									@Override
									public void run( )
										{
										try
											{
											start();
											}
										catch (IOException e)
											{ Log.e("MyTAG", "IOError", e); }

										}
								});
						thread.start();
						//Log.w("MyTAG", wifiInfo.toString());
						}}//}
			//Log.d("MyTAG", "onReceive()");
			//Toast.makeText(context, ssid + "\n" + info.toString(), Toast.LENGTH_LONG).show();
			// throw new UnsupportedOperationException("Not yet implemented");
			}


		private void start( ) throws IOException
			{
			//Log.w("MyTAG", "start()");
			String urlParameters = "buttonClicked=4";
			byte[] postData = urlParameters.getBytes("UTF-8");
			int postDataLength = postData.length;
			String request = "http://1.1.1.10/login.html";

			URL url = new URL(request);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setInstanceFollowRedirects(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("charset", "utf-8");
			conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
			conn.setUseCaches(false);

			DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
			try
				{
				wr.write(postData);
				//Log.w("MyTAG", "write()");
				wr.flush();
				}
			finally
				{
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
			//Log.w("MyTAG", "disconn()");
			}

	}
