package ru.dostavkamix.denis.dostavkamix.Tasks;

import android.os.AsyncTask;
import android.util.Log;


import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import ru.dostavkamix.denis.dostavkamix.Objects.Buyer;

/**
 * Created by den on 07.02.2016.
 */
public class Buy extends AsyncTask<Buyer, Void, Void>{

    private static final String buyUrl = "http://chaihanamix.ru/server/order/";


    @Override
    protected Void doInBackground(Buyer... params) {

        ObjectMapper mapper = new ObjectMapper();
        try {
            String temp = mapper.writeValueAsString(params);
            String json = temp.substring(1, temp.length() - 1);
            Log.d("json", json);


            URL url = new URL(buyUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes());
            os.flush();
            int responseCode = conn.getResponseCode();

            if(responseCode == 200) //HTTP 200: Response OK
            {
                String result = "";
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String output;
                while((output = br.readLine()) != null)
                {
                    result += output;
                }
                Log.d("json", "Response message: " + result);
            }


        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
