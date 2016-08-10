package ru.dostavkamix.denis.dostavkamix;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOError;
import java.util.HashMap;
import java.util.Map;

import me.drakeet.materialdialog.MaterialDialog;
import ru.dostavkamix.denis.dostavkamix.Objects.User;

import static com.android.volley.Request.Method.GET;

/**
 * Created by Денис on 08.08.2016.
 */

public class UserHelper {

    private static final String TAG = "UserHelper";

    private static final String TAG_SIGNIN = "signin";
    private static final String TAG_SIGNUP = "signup";

    private static final String base_url = "http://cabinet.chaihanamix.ru/api/v1";
    private static final String _USER = "/user";
    public static final String _TOKEN = "/token";
    private static final String APP_KEY = "123456";

    private static final String TAG_USER_ID = "user_id";
    private static final String TAG_TOKEN = "access_token";
    private static final String TAG_AUTH = "Authorization";
    private static final String TAG_STATUS = "status";
    private static final String TAG_ERRORS = "errors";
    private static final String TAG_MSG = "msg";
    private static final String TAG_NAME = "name";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_PHONE = "phone";
    private static final String TAG_BIRTHDAY = "birthday";
    private static final String TAG_PASS = "password";
    private static final String TAG_APP_KEY = "app_key";
    private static final String TAG_POINTS = "points";
    private static final String TAG_ADDRESSES = "addresses";
    private static final String TAG_CRE_AT = "created_at";
    private static final String TAG_UPD_AT = "updated_at";

    static MaterialDialog progressDialog;
    static MaterialDialog msgDialog;

    public static void signUp(String name, final String email, String birthday, final String pass, final Context ctx, final SignCallback callback) {
        progressDialog = new MaterialDialog(ctx);
        msgDialog = new MaterialDialog(ctx);

        View viewProgress = LayoutInflater.from(ctx).inflate(R.layout.layout_progress_dialog, null);
        ((TextView) (viewProgress.findViewById(R.id.msg))).setText(R.string.msg_signup);
        progressDialog.setContentView(viewProgress);
        progressDialog.show();


        final Map<String, String> params = new HashMap<>();

        params.put(TAG_NAME, name);
        params.put(TAG_BIRTHDAY, birthday);
        params.put(TAG_EMAIL, email);
        params.put(TAG_PHONE, "123456");
        params.put(TAG_PASS, pass);

        Log.d(TAG, "signUp: request: " + new JSONObject(params).toString());

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                base_url + _USER,
                new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "onResponse: " + response);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                    }
                }, 300);

                try {
                    if (response.getBoolean(TAG_STATUS)) {
                        callback.onSuccess();
                    } else {
                        JSONObject errors = response.getJSONObject(TAG_ERRORS);

                        msgDialog.setMessage(errors.getJSONArray(errors.keys().next()).getString(0))
                                .setPositiveButton("OK", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        msgDialog.dismiss();
                                    }
                                }).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        Volley.newRequestQueue(ctx).add(request);


    }

    public static void signIn(String email, String pass, final Context ctx, final SignCallback callback) {
        progressDialog = new MaterialDialog(ctx);
        msgDialog = new MaterialDialog(ctx);

        View viewProgress = LayoutInflater.from(ctx).inflate(R.layout.layout_progress_dialog, null);
        ((TextView) (viewProgress.findViewById(R.id.msg))).setText(R.string.msg_signin);
        progressDialog.setContentView(viewProgress);
        progressDialog.show();

        final Map<String, String> params = new HashMap<>();
        params.put(TAG_EMAIL, email);
        params.put(TAG_PASS, pass);
        params.put(TAG_APP_KEY, APP_KEY);

        Log.d(TAG, "signIn: " + new JSONObject(params));

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                base_url + _TOKEN,
                new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "onResponse: " + response);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                    }
                }, 300);
                try {
                    AppController.getInstance().setUser(new User(response.getString(TAG_TOKEN)));
                    callback.onSuccess();
                    Log.d(TAG, "onResponse: signin ok");

                } catch (JSONException e) {
                    try {
                        msgDialog.
                                setMessage(response.getString(TAG_MSG))
                                .setPositiveButton("ОК", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        msgDialog.dismiss();
                                    }
                                })
                                .show();
                        Log.d(TAG, "onResponse: signin fail");
                    } catch (JSONException e1) {
                        e.printStackTrace();
                        e1.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: " + error.getMessage());
            }
        });

        Volley.newRequestQueue(ctx).add(request);
    }

    public static void getUser(final String token, Context ctx, final UserCallback callback) {

        JsonObjectRequest request = new JsonObjectRequest(
                GET,
                base_url + _USER,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response1) {
                        Log.d(TAG, "onResponse: response: " + response1);
                        User result = new User(token);
                        try {
                            JSONObject response = response1.getJSONObject("user");
                            result.setName(response.getString(TAG_NAME));
                            Log.d(TAG, "onResponse: user: " + result.getName());
                            result.setEmail(response.getString(TAG_EMAIL));
                            result.setPhone(response.getString(TAG_PHONE));
                            result.setBirthday(response.getString(TAG_BIRTHDAY));
                            result.setCreated_at(response.getString(TAG_CRE_AT));
                            result.setUpdate_at(response.getString(TAG_UPD_AT));
                            result.setPoints(response.getInt(TAG_POINTS));
                            result.setAddresses(response.getString(TAG_ADDRESSES));
                            callback.onSuccess(result);
                            Log.d(TAG, "onResponse: success parse json");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            callback.onError(e.getMessage());
                            Log.e(TAG, "onResponse: error parse json");
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error.getMessage());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put(TAG_AUTH, token);
                return headers;
            }
        };

        Volley.newRequestQueue(ctx).add(request);
    }

}
