package http;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import activity.Activity_MyActivity;
import activity.Activity_YourActivity;
import api.HTTP_API;
import internet.CheckWifi3G;
import loading.lib_dialog;
import loading.lib_loading;

/**
 * Created by MrThanhPhong on 2/27/2016.
 */
public class create_activities {
    private ProgressDialog progressDialog;
    private String TAG_MESSAGE="";
    Activity_YourActivity activity;
    public void SEND(
            final Context context, final String idUser, final String token, final String activityType, final String plan, final int minNumOfParticipants
            , final int maxNumOfParticipants, final int ageFrom, final int ageTo, final String gender,
            final float distance, final double expiredHours, final boolean meetConfirm, final boolean publishToSocial,
            final boolean test,final String latitude,final String longitude

    ) {

        class Loading extends AsyncTask<String, String, String> {
            String TAG_STATUS;
            String _id;
            String _activityTypes;
            String _plan;
            String _minNumOfParticipans;
            String _maxNumOfParticipans;
            String _ageFrom;
            String _ageTo;
            String _distance;
            String _expiredHours;
            boolean _meetConfirm;
            boolean _publicSocial;
            String _userId;
            String _status;
            boolean _activie;
            String _activityTypeName;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = lib_loading.f_init(context);
            }

            @Override
            protected String doInBackground(String... args) {
                try {
                   // Looper.prepare(); //For Preparing Message Pool for the child Thread
                    HttpClient client = new DefaultHttpClient();
                    HttpResponse response;
                    JSONObject json = new JSONObject();

                    HttpPost post = new HttpPost(HTTP_API.CREATE_ACTIVITIES);
                    post.addHeader("X-User-Id", idUser);
                    post.addHeader("X-Auth-Token", token);
                    json.put("activityType", activityType);
                    json.put("plan", plan);
                    json.put("minNumOfParticipants", minNumOfParticipants);
                    json.put("maxNumOfParticipants", maxNumOfParticipants);
                    json.put("ageFrom", ageFrom);
                    json.put("ageTo", ageTo);
                    json.put("gender", gender);
                    json.put("distance", distance);
                    json.put("expiredHours", expiredHours);
                    json.put("meetConfirm", meetConfirm);
                    json.put("publishToSocial", publishToSocial);
                    JSONObject location = new JSONObject();
                    location.put("lat",latitude);
                    location.put("lng",longitude);
                    json.put("location",location);

                    StringEntity se = new StringEntity(json.toString());
                    se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                    post.setEntity(se);
                    response = client.execute(post);
                    Log.i("se----", se.toString());
                    if (response != null) {
                        HttpEntity resEntity = response.getEntity();
                        if (resEntity != null) {
                            String msg = EntityUtils.toString(resEntity);
                            Log.e("create  cate", msg);

                            JSONObject jsonObject = new JSONObject(msg);
                            TAG_STATUS = jsonObject.getString("status");
                            TAG_MESSAGE= jsonObject.getString("message");

                            JSONObject data = jsonObject.getJSONObject("data");
                            _id = data.getString("_id");
                            _activityTypes = data.getString("activityTypes");
                            _plan = data.getString("plan");
                            _minNumOfParticipans = data.getString("minNumOfParticipans");
                            _maxNumOfParticipans = data.getString("maxNumOfParticipans");
                            _ageFrom = data.getString("ageFrom");
                            _ageTo = data.getString("ageTo");
                            _distance = data.getString("distance");
                            _expiredHours = data.getString("expiredHours");
                            _meetConfirm = data.getBoolean("meetConfirm");
                            _publicSocial = data.getBoolean("publicSocial");
                            _userId = data.getString("userId");
                            _status = data.getString("status");
                            _activie = data.getBoolean("activie");
                            _activityTypeName = data.getString("activityTypeName");


                        }

                        if (resEntity != null) {
                            resEntity.consumeContent();
                        }

                        client.getConnectionManager().shutdown();

                    }
                } catch (Exception e) {
                    progressDialog.dismiss();

                } catch (Throwable t) {
                    progressDialog.dismiss();

                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                progressDialog.dismiss();
                try {
                    if (TAG_STATUS.equals("success")) {
                        Toast.makeText(context,TAG_MESSAGE,Toast.LENGTH_SHORT).show();

                        Intent in = new Intent(context, Activity_MyActivity.class);
                        context.startActivity(in);


                    } else {

                        new lib_dialog().f_dialog_msg(context,TAG_MESSAGE);


                    }

                } catch (Exception e) {

                } catch (Throwable t) {

                }

            }

        }

        if (CheckWifi3G.isConnected(context)) {
            new Loading().execute();
        } else {
            Toast.makeText(context, "Error Connect Internet!", Toast.LENGTH_SHORT).show();
        }


//        progressDialog = lib_loading.f_init(context);
//        Thread t = new Thread() {
//
//            public void run() {
//                Looper.prepare(); //For Preparing Message Pool for the child Thread
//                HttpClient client = new DefaultHttpClient();
//                HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
//                HttpResponse response;
//                JSONObject json = new JSONObject();
//
//                try {
//                    HttpPost post = new HttpPost(HTTP_API.CREATE_ACTIVITIES);
//                    post.addHeader("X-User-Id", idUser);
//                    post.addHeader("X-Auth-Token", token);
//                    Log.i("idUser-------", idUser);
//                    Log.i("token--------", token);
//
//                    json.put("activityType", activityType);
//                    json.put("plan", plan);
//                    json.put("minNumOfParticipants", minNumOfParticipants);
//                    json.put("maxNumOfParticipants", maxNumOfParticipants);
//                    json.put("ageFrom", ageFrom);
//                    json.put("ageTo", ageTo);
//                    json.put("distance", distance);
//                    json.put("gender", gender);
//                    json.put("expiredHours", expiredHours);
//                    json.put("meetConfirm", meetConfirm);
//                    json.put("publishToSocial", publishToSocial);
//
//                    StringEntity se = new StringEntity( json.toString());
//                    se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
//                    post.setEntity(se);
//                    response = client.execute(post);
//
//                    /*Checking response */
//                    if(response!=null){
//                        StatusLine statusLine = response.getStatusLine();
//                        int statusCode = statusLine.getStatusCode();
//                        InputStream in = response.getEntity().getContent(); //Get the data in the entity
//                    ConvetInputStream con = new ConvetInputStream();
//                        String _res = convertInputStreamToString(in);
//
//                        Log.i("_res---", _res);
//                        Log.i("statusCode---", statusCode+"");
//                        if (statusCode == 200) {
//                            progressDialog.dismiss();
//
//                            new lib_dialog().f_dialog_msg(context,"Congratulation! You have successfully registered; please check your email to confirm registration!");
//
//                        }
//                        else{
//                            Toast.makeText(context, "fail", Toast.LENGTH_SHORT).show();
//                            progressDialog.dismiss();
//                        }
//
//
//
//
//
//                    }
//
//                } catch(Exception e) {
//                    e.printStackTrace();
//                    //createDialog("Error", "Cannot Estabilish Connection");
//                    Log.e("Error","Error");
//                    progressDialog.dismiss();
//
//                }
//
//                Looper.loop(); //Loop in the message queue
//            }
//        };
//
//        t.start();
    }

    private static String convertInputStreamToString(InputStream inputStream) {
        String result = "";
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
