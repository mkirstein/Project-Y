package com.teamlimo.project_y.core;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Project0rion on 27.03.2016.
 */
public class HttpDatabaseManager implements IDatabaseManager {

    private String baseAddress;
    private boolean lastOperationSucceeded;

    private static final String TAG_ITEMS = "items";

    public HttpDatabaseManager(String baseAddress) {
        this.baseAddress = baseAddress;
    }

    public <T extends IEntity> ArrayList<T> queryMany(Class<T> entityType, String operationName) {

        InputStream inputStream = null;

        try {
            // request method is GET
            DefaultHttpClient httpClient = new DefaultHttpClient();
            String url = buildOperationUrl(operationName);
            HttpGet httpGet = new HttpGet(url);

            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject jsonResult = getJSONfromStream(inputStream);
        ArrayList<T> result = new ArrayList<T>();

        try {
            JSONArray jsonItems = jsonResult.getJSONArray(TAG_ITEMS);

            // looping through all items
            for (int i = 0; i < jsonItems.length(); i++) {
                JSONObject jsonItem = jsonItems.getJSONObject(i);

                T queriedItem = entityType.newInstance();
                queriedItem.createFromJSON(jsonItem);
                result.add(queriedItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return result;
    }

    public Object insertOrUpdate(String operationName, List<NameValuePair> params) {

        InputStream inputStream = null;

        try {
            // request method is POST
            // defaultHttpClient
            DefaultHttpClient httpClient = new DefaultHttpClient();
            String url = buildOperationUrl(operationName);
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(params));

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject result = getJSONfromStream(inputStream);

        return result;
    }

    private String buildOperationUrl(String operationName) {
        return baseAddress + operationName + ".php";
    }

    private JSONObject getJSONfromStream(InputStream inputStream) {

        String json = "";

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            inputStream.close();
            json = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        // try parse the string to a JSON object
        JSONObject jObj = null;

        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        // return JSON String
        return jObj;
    }
}
