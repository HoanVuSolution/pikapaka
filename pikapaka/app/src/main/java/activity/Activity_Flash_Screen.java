package activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import api.HTTP_API;
import hoanvusolution.pikapaka.MainActivity;
import hoanvusolution.pikapaka.R;
import internet.CheckWifi3G;
import loading.lib_dialog;
import loading.lib_loading;
import util.dataString;

/**
 * Created by MrThanhPhong on 3/6/2016.
 */
public class Activity_Flash_Screen extends Activity {
    private ProgressDialog progressDialog;

    public static String TAG_STATUS = "";


    private String TAG_USERID = "";
    private String TAG_AUTH_TOKEN = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_flash_screen);

        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init()throws Exception{
        get_shapreference();

    }
    private void get_shapreference() throws Exception {
        try{
            SharedPreferences sha_IDuser;
            SharedPreferences sha_Token;

            sha_IDuser = Activity_Flash_Screen.this.getSharedPreferences("ID_user", 0);
            sha_Token = Activity_Flash_Screen.this.getSharedPreferences("auth_token", 0);

            TAG_USERID = sha_IDuser.getString(
                    "ID_user", "");
            TAG_AUTH_TOKEN = sha_Token.getString(
                    "auth_token", "");


            if (TAG_USERID.length() > 0 && TAG_AUTH_TOKEN.length() > 0) {
                Request_Token();
            } else {
                Intent in = new Intent(Activity_Flash_Screen.this,Activity_Login.class);
                startActivity(in);
                finish();

            }
        }catch(Exception e){
            Intent in = new Intent(Activity_Flash_Screen.this,Activity_Login.class);
            startActivity(in);
            finish();
        }




    }
    private void Request_Token()throws Exception {
        class Request extends AsyncTask<String, String, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = lib_loading.f_init(Activity_Flash_Screen.this);
            }

            @Override
            protected String doInBackground(String... args) {

                HttpClient client = new DefaultHttpClient();
                HttpResponse response;
                try {
                    HttpGet post = new HttpGet(HTTP_API.Request_Token);
                    post.addHeader("X-User-Id", TAG_USERID);
                    post.addHeader("X-Auth-Token", TAG_AUTH_TOKEN);
//
                    response = client.execute(post);
                    if (response != null) {


                        HttpEntity resEntity = response.getEntity();
                        if (resEntity != null) {
                            String msg = EntityUtils.toString(resEntity);
                            Log.e("msg--", msg);

                            JSONObject jsonObject = new JSONObject(msg);
                            TAG_STATUS = jsonObject.getString("status");
                            Log.e("status--", TAG_STATUS);
                            if (TAG_STATUS.equals("success")) {
//                                dataString.TAG_USERID=idUser;
//                                dataString.TAG_AUTH_TOKEN=token;

                                JSONObject data = jsonObject.getJSONObject("data");

                                dataString.TAG_FIRSTNAME = data.getString("firstName");
                                dataString.TAG_LASTNAME = data.getString("lastName");
                                dataString.TAG_DISPLAYNAME = data.getString("displayName");
                                dataString.TAG_GENDER = data.getString("gender");

                                dataString.TAG_EMAIL = data.getString("email");


                            }

                        }
                        if (resEntity != null) {
                            resEntity.consumeContent();
                        }

                        client.getConnectionManager().shutdown();

                    }


                } catch (Exception e) {
                    progressDialog.dismiss();
                    Log.e("Error", "Error");

                } catch (Throwable t) {
                    progressDialog.dismiss();
                    Log.e("Error1", "Error1");

                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                progressDialog.dismiss();
                try {

                    if (TAG_STATUS.equals("success")) {
                        Intent in = new Intent(Activity_Flash_Screen.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                                | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(in);
                        finish();
                    } else {
                        Intent in = new Intent(Activity_Flash_Screen.this,Activity_Login.class);
                        startActivity(in);
                        finish();

                    }


                } catch (Exception e) {

                } catch (Throwable t) {

                }

            }

        }

        if (CheckWifi3G.isConnected(Activity_Flash_Screen.this)) {
            new Request().execute();
        } else {
            new lib_dialog().f_dialog_msg(Activity_Flash_Screen.this, "Can't connect Internet ");

        }
    }


}
