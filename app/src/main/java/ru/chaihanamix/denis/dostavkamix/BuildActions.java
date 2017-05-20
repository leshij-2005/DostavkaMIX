package ru.chaihanamix.denis.dostavkamix;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by d.tkachenko on 01.05.2016.
 */
public class BuildActions extends AsyncTask<Void, Void, String>{

    final static String LOG = "action";


    HttpURLConnection urlConnection = null;
    BufferedReader reader = null;
    String jsonAction = "";

    @Override
    protected String doInBackground(Void... params) {
        try {
            Log.d(LOG, "export actions");
            URL url_action = new URL(Constants.getBase_url() + "server/actionsnews");
            urlConnection = (HttpURLConnection) url_action.openConnection();
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            jsonAction = buffer.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonAction;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        Log.d(LOG, "parse actions");

        try {
            JSONArray actions = new JSONArray(jsonAction);
            for (int i = 0; i < actions.length(); i++) {
                JSONObject action = actions.getJSONObject(i);
                AppController.getInstance().getMainActivity().actions.add(new Action(action.getString("title"), action.getString("img"), action.getString("desc")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
