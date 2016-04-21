package com.teamlimo.project_y.core;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Project0rion on 27.03.2016.
 */
public class HttpDatabaseManager implements IDatabaseManager {

    private String baseAddress;

    private static final String TAG_ITEMS = "items";
    private static final String TAG_VALUE = "value";

    public HttpDatabaseManager(String baseAddress) {
        this.baseAddress = baseAddress;
    }

    public <T> T queryPrimitive(Class<T> primitiveType, String operationName) {
        return queryPrimitive(primitiveType, operationName, null);
    }

    public <T> T queryPrimitive(Class<T> primitiveType, String operationName, IEntity input) {

        String url = buildOperationUrl(operationName);
        HttpPost httpPost = new HttpPost(url);

        if (input != null) {
            List<NameValuePair> entityPropertyValues = getEntityPropertyValues(input);

            try {
                httpPost.setEntity(new UrlEncodedFormEntity(entityPropertyValues));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        JSONObject requestResult = executeHttpRequest(httpPost);

        if(requestResult == null)
            return null;

        try {
            if (primitiveType == Integer.class)
                return primitiveType.cast(requestResult.getInt(TAG_VALUE));
            if (primitiveType == Long.class)
                return primitiveType.cast(requestResult.getLong(TAG_VALUE));
            if (primitiveType == String.class)
                return primitiveType.cast(requestResult.getString(TAG_VALUE));
            if (primitiveType == Boolean.class)
                return primitiveType.cast(requestResult.getBoolean(TAG_VALUE));
            if (primitiveType == Double.class)
                return primitiveType.cast(requestResult.getDouble(TAG_VALUE));
            else
                throw new IllegalArgumentException("primitive type is not supported");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public <T extends IEntity> ArrayList<T> queryMany(Class<T> entityType, String operationName) {

        String url = buildOperationUrl(operationName);
        HttpGet httpGet = new HttpGet(url);
        JSONObject requestResult = executeHttpRequest(httpGet);

        // Return null if server connection failed
        if(requestResult == null) {
            return null;
        }

        ArrayList<T> result = new ArrayList<T>();

        try {
            JSONArray jsonItems = requestResult.getJSONArray(TAG_ITEMS);

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

    public void insertOrUpdate(String operationName, IEntity entity) {

        List<NameValuePair> entityPropertyValues = getEntityPropertyValues(entity);

        JSONObject requestResult = null;

        String url = buildOperationUrl(operationName);
        HttpPost httpPost = new HttpPost(url);

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(entityPropertyValues));
            requestResult = executeHttpRequest(httpPost);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private JSONObject executeHttpRequest(HttpUriRequest httpRequest) {

        InputStream requestResult = null;

        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpResponse = httpClient.execute(httpRequest);
            HttpEntity httpEntity = httpResponse.getEntity();
            requestResult = httpEntity.getContent();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return getJSONfromStream(requestResult);
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
            return null;
        }

        // try parse the string to a JSON object
        JSONObject jObj = null;

        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
            return null;
        }

        // return JSON String
        return jObj;
    }

    private List<NameValuePair> getEntityPropertyValues(IEntity entity) {
        Map<String, String> convertedEntity = entity.saveToMap();
        Iterator iterator = convertedEntity.entrySet().iterator();

        List<NameValuePair> entityPropertyValues = new ArrayList<>();
        while (iterator.hasNext()) {
            Map.Entry<String, String> propertyValue = (Map.Entry<String, String>) iterator.next();
            entityPropertyValues.add(new BasicNameValuePair(propertyValue.getKey(), propertyValue.getValue()));
        }

        return entityPropertyValues;
    }
}
