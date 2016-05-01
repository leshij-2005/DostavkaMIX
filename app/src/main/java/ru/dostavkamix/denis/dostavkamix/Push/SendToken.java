package ru.dostavkamix.denis.dostavkamix.Push;

import android.os.AsyncTask;
import android.util.Log;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

import ru.dostavkamix.denis.dostavkamix.AppController;

/**
 * Created by d.tkachenko on 30.04.2016.
 */
public class SendToken extends AsyncTask<String, Void, Void> {

    private final String LOG = this.getClass().getSimpleName();
    private final String SENDER_ID = "795256844466";
    private final String request = "http://chaihanamix.ru/server/add-token";

    @Override
    protected Void doInBackground(String... params) {

        try {

            if (AppController.getInstance().preferences.getString("token", "") == "") {
                Log.d(LOG, "token not found. generation token and send to server...");

                // Получаем deviceID
                InstanceID instanceID = InstanceID.getInstance(AppController.getInstance().getMainActivity());
                String token = instanceID.getToken(SENDER_ID,
                        GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                Log.d(LOG, "token: " + token);

                // Сохроняем его
                AppController.getInstance().editPref.putString("token", token);
                AppController.getInstance().editPref.commit();

                // Отправляем его
                String urlParameters = "token=" + token + "&vender=" + "android";
                byte[] postData = urlParameters.getBytes(Charset.forName("UTF-8"));
                int postDataLength = postData.length;
                URL url = new URL(request);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setInstanceFollowRedirects(false);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("charset", "utf-8");
                conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
                conn.setUseCaches(false);

                DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
                wr.write(postData);
                wr.flush();


                int responseCode = conn.getResponseCode();
                Log.d(LOG, "send response code = " + responseCode);


                if (responseCode == 200) //HTTP 200: Response OK
                {
                    String result = "";
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String output;
                    while ((output = br.readLine()) != null) {
                        result += output;
                    }
                    Log.d(LOG, "Response message: " + result);
                }

            } else Log.d(LOG, "token found: " + AppController.getInstance().preferences.getString("token", ""));


        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
