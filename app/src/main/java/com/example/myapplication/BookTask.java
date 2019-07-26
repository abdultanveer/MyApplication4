package com.example.myapplication;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class BookTask extends AsyncTask<String, Void, String> {
    String contentAsString = "";
    public static String TAG = BookTask.class.getSimpleName();

    @Override
    protected String doInBackground(String... strings) {
        HttpURLConnection conn = null;
        InputStream is = null;
        try {
            URL requestURL = new URL(strings[0]);
            conn =
                    (HttpURLConnection) requestURL.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            int response = conn.getResponseCode();

            is = conn.getInputStream();
            int len = conn.getContentLength();
            contentAsString = convertIsToString(is, len);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return contentAsString;
        }
    }

    public String convertIsToString(InputStream inputStream, int len)
            throws IOException, UnsupportedEncodingException {
        StringBuffer buffer = new StringBuffer();
        String bookJSONString = "";

        if (inputStream == null) {
// Nothing to do.
            return null;
        }
        BufferedReader reader = null;

        reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null) {
/* Since it's JSON, adding a newline isn't necessary (it won't affect
parsing) but it does make debugging a *lot* easier if you print out the
completed buffer for debugging. */
            buffer.append(line + "\n");
        }
        if (buffer.length() == 0) {
// Stream was empty. No point in parsing.
            return null;
        }
        bookJSONString = buffer.toString();
        Log.d(TAG, bookJSONString);
        return bookJSONString;

    }


    @Override
    protected void onPostExecute(String json) {
        super.onPostExecute(json);
        parseJson(json);
    }

    private void parseJson(String response) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
            JSONArray itemsArray = jsonObject.getJSONArray("items");

            for(int i = 0; i<itemsArray.length(); i++){
                JSONObject book = itemsArray.getJSONObject(i); //Get the current item
                String title=null;
                String authors=null;
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");
                try {
                    title = volumeInfo.getString("title");
                    authors = volumeInfo.getString("authors");
                } catch (Exception e){
                    e.printStackTrace();
                }
//If both a title and author exist, update the TextViews and return
                if (title != null && authors != null){

                    return;
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
