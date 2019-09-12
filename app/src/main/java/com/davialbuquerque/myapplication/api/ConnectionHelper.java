package com.davialbuquerque.myapplication.api;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class ConnectionHelper {

    public static JSONObject loadJson(String stringUrl) {
        JSONObject result = null;
        HttpsURLConnection connection = null;
        try {
            StringBuilder builder = new StringBuilder();
            URL url = new URL(stringUrl);
            connection = (HttpsURLConnection) url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while((line = in.readLine()) != null) {
                builder.append(line);
            }
            result = new JSONObject(builder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null)
                connection.disconnect();
        }
        return result;
    }

}
