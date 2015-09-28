package ru.wifiauth;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    start();
                } catch (Exception e) {
                    final Exception ex = e;
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {Toast.makeText(MainActivity.this, ex.toString(), Toast.LENGTH_LONG).show();}});
                }
            }
        });
        thread.start();
    }

    private void start() throws IOException {
        String urlParameters = "buttonClicked=4";
        byte[] postData = urlParameters.getBytes("UTF-8");
        int postDataLength = postData.length;
        String request = "http://example.com";

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
        try {
            wr.write(postData);
            wr.flush();
        } finally {
            wr.close();
        }
        conn.disconnect();
    }
}
