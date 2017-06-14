package ru.chaihanamix.denis.dostavkamix;

import android.os.AsyncTask;
import android.util.Log;


import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by den on 07.02.2016.
 */
public class Buy extends AsyncTask<Buyer, Void, JSONObject>{

    private static final String buyUrl = Constants.getBase_url() + "server/order/";

    public interface OnUpdateListener {
        public void onUpdate(JSONObject response);
    }

    OnUpdateListener listener;

    public void setUpdateListener(OnUpdateListener listener) {
        this.listener = listener;
    }

    @Override
    protected JSONObject doInBackground(Buyer... params) {

        ObjectMapper mapper = new ObjectMapper();
        JSONObject result = null;

        try {
            String temp = mapper.writeValueAsString(params);
            String json = temp.substring(1, temp.length() - 1);

            URL url = new URL(buyUrl);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes());
            os.flush();

            //int responseCode = conn.getResponseCode();

            String response = "";
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while((line = br.readLine()) != null) {
                response += line;
            }

            try {
                result = new JSONObject(response);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    protected void onPostExecute(JSONObject result) {
        if (listener != null) {
            listener.onUpdate(result);
        }
    }
}
