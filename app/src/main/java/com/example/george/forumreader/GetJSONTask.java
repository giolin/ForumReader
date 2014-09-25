package com.example.george.forumreader;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by george on 2014/9/24.
 *
 * GetJSONTask is running in the background and will callback to
 * ForumReaderActivity with the interface, AsyncTaskResponse.
 */

public class GetJSONTask extends AsyncTask<String, Void, String> {

    public AsyncTaskResponse callback = null;

    private final String NAME_TAG = "mmpud";

    static InputStream is = null;
    static String json = "";

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        callback.asyncTaskFinish(result);
        Log.d(NAME_TAG, "GetJSONTask Done! result: " + result);
    }

    @Override
    protected String doInBackground(String... urls) {
        // get JSON from URL
        Log.d(NAME_TAG, urls[0]);
        String json = getJSONFromUrl(urls[0]);
        return json;
    }

    public String getJSONFromUrl(String url) {
        // Making HTTP request
        try {
            // defaultHttpClient
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            is.close();
            json = sb.toString();

        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        return json;
    }
}