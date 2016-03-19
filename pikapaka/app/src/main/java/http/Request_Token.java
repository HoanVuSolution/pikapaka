package http;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Looper;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import api.HTTP_API;
import internet.CheckWifi3G;
import shared_prefs.Commit_Sha;
import util.dataString;

/**
 * Created by MrThanhPhong on 2/23/2016.
 */
public class Request_Token {

    private String TAG_STATUS;
    private ProgressDialog progressDialog;


    public void send_token_social(final Context context,  final String token) {
        Thread t = new Thread() {

            public void run() {
                Looper.prepare(); //For Preparing Message Pool for the child Thread
                HttpClient client = new DefaultHttpClient();
                HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
                HttpResponse response;

                try {
                    HttpGet post = new HttpGet(HTTP_API.Request_Token_SOC + "/" +token);
                    //post.addHeader("X-User-Id", idUser);
                   // Log.i("idUser-------", idUser);
                    Log.i("token--------", token);
                    Log.i("URL--------", post.getAllHeaders().toString());

                    response = client.execute(post);

                    /*Checking response */
                    if (response != null) {

                        HttpEntity resEntity = response.getEntity();
                        if (resEntity != null) {
                            String msg = EntityUtils.toString(resEntity);
                            Log.e("msg-facebook-",msg);

                            JSONObject jsonObject = new JSONObject(msg);
                            String status =jsonObject.getString("status");
                            Log.e("status--",status);

                            JSONObject object =jsonObject.getJSONObject("data");
                            String userID =object.getString("userId");
                            String authToken =object.getString("authToken");
                            Log.d("userID",userID);
                            Log.d("authToken",authToken);
                            if(userID.length()>0&& authToken.length()>0){
                                new Commit_Sha().Write_TokenID(context,userID,authToken,"user","pass");

                            }

                            //parseJSON(msg);
                        }
                        if (resEntity != null) {
                            resEntity.consumeContent();
                        }



                    }
                    client.getConnectionManager().shutdown();
                } catch (Exception e) {
                    e.printStackTrace();
                    //createDialog("Error", "Cannot Estabilish Connection");
                    Log.e("Error", "Error");

                }

                Looper.loop(); //Loop in the message queue
            }
        };

        t.start();

    }


    public void Get_Profile(final Context context,final String TAG_USERID,final String TAG_AUTH_TOKEN)throws Exception {
        class Request extends AsyncTask<String, String, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected String doInBackground(String... args) {
                // Looper.prepare(); //For Preparing Message Pool for the child Thread
                HttpClient client = new DefaultHttpClient();
                //HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
                HttpResponse response;
                try {
                    HttpGet post = new HttpGet(HTTP_API.Request_Token);
                    post.addHeader("X-User-Id", TAG_USERID);
                    post.addHeader("X-Auth-Token", TAG_AUTH_TOKEN);
//                    Log.i("idUser-------", idUser);
//                    Log.i("token--------", token);
                    Log.i("URL--------", post.getAllHeaders().toString());
                    response = client.execute(post);
                    if (response != null) {


                        HttpEntity resEntity = response.getEntity();
                        if (resEntity != null) {
                            String msg = EntityUtils.toString(resEntity);
                            Log.e("msg--", msg);

                            JSONObject jsonObject = new JSONObject(msg);
                            TAG_STATUS = jsonObject.getString("status");

                            if (TAG_STATUS.equals("success")) {
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

                    Log.e("Error", "Error");

                } catch (Throwable t) {

                    Log.e("Error1", "Error1");

                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {

                try {

                    if (TAG_STATUS.equals("success")) {
                        Log.e("get_profile","success");
                    } else {
                        Log.e("get_profile","fail");
                    }


                } catch (Exception e) {

                } catch (Throwable t) {

                }

            }

        }

        if (CheckWifi3G.isConnected(context)) {
            new Request().execute();
        }
    }
}
