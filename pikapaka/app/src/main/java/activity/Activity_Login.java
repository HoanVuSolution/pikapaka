package activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import activity_register.Activity_Register;
import api.HTTP_API;
import check_email_pass.check_mail;
import hoanvusolution.pikapaka.MainActivity;
import hoanvusolution.pikapaka.R;
import internet.CheckWifi3G;
import loading.lib_dialog;
import loading.lib_loading;
import loginsocial.LoginFragment;
import loginsocial.LoginGoogle;
import loginsocial.Login_Twitter;
import shared_prefs.Commit_Sha;
import util.Activity_Result;

public class Activity_Login extends AppCompatActivity {

    public static String TAG_EMAIL = "";
    public static String TAG_PASSWORD = "";
    public static String TAG_STATUS = "";
    public static String TAG_MESSENG = "";

    private String TAG_USERID = "";
    private String TAG_AUTH_TOKEN = "";
    private ProgressDialog progressDialog;

    private AppCompatActivity activity;
    private ImageView img_facebook, img_switter, img_googleplus;

    private RelativeLayout rl_register, rl_login;

    private ImageView ic_profile;
    private TextView tv_forgot_pass;
    private EditText ed_email, ed_password;
    JSONArray array = null;

    //------------------------------------------
    //server key : AIzaSyDOs3otEtv96BUdckzdoRgbI9YV2TM9I_c
    private String TAG_TOKEN_GOOGLE;
    private String regid; // regis google
    private String d_id; // get device ID
    public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    String SENDER_ID = "613376380859";

    static final String TAG = "GCMD_pikapaka";
    GoogleCloudMessaging gcm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() throws Exception {
        get_resource();
        Onclik();
        get_shapreference();

        if(checkPlayServices()){
            gcm = GoogleCloudMessaging.getInstance(this);
            regid = getRegistrationId(activity);
            TAG_TOKEN_GOOGLE = getRegistrationId(activity);

            if(regid.isEmpty()){
                new RegisterBackground().execute();
                //new_key_Rig();

            }
        }
        Log.e("TAG_TOKEN_GOOGLE---", TAG_TOKEN_GOOGLE);
    }

    private void get_resource() throws Exception {
        activity = this;
        ic_profile = (ImageView) findViewById(R.id.ic_profile);
        img_facebook = (ImageView) findViewById(R.id.img_facebook);
        img_switter = (ImageView) findViewById(R.id.img_switter);
        img_googleplus = (ImageView) findViewById(R.id.img_googleplus);
        tv_forgot_pass = (TextView) findViewById(R.id.tv_forgot_pass);
        rl_register = (RelativeLayout) findViewById(R.id.rl_register);
        rl_login = (RelativeLayout) findViewById(R.id.rl_login);
        //  EditText ed_email,ed_password;
        ed_email = (EditText) findViewById(R.id.ed_email);
        ed_email.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        ed_password = (EditText) findViewById(R.id.ed_password);
    }

    private void Onclik() throws Exception {
        img_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(activity, LoginFragment.class);
                startActivityForResult(in, 100);
            }
        });
        img_switter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(activity, Login_Twitter.class);
                startActivityForResult(in, Activity_Result.REQUEST_CODE_ACT);            }
        });
        img_googleplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Activity_Login.this, "Processing...", Toast.LENGTH_SHORT).show();
                Intent in = new Intent(activity, LoginGoogle.class);
                startActivityForResult(in, Activity_Result.REQUEST_CODE_ACT);

            }
        });
        rl_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "Processing...", Toast.LENGTH_SHORT).show();


                TAG_EMAIL = ed_email.getText().toString();
                TAG_PASSWORD = ed_password.getText().toString();
                if (TAG_EMAIL.length() == 0 || TAG_PASSWORD.length() == 0) {
                    Toast.makeText(Activity_Login.this, "please input email and password", Toast.LENGTH_SHORT).show();
                } else {
                    // LOGIN();
                    LogOn();
                    // log();
                }

            }
        });
        rl_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(activity, Activity_Register.class);
                startActivityForResult(in, 100);
            }
        });


        tv_forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_forgot_pass();

            }
        });
    }

    private void dialog_forgot_pass() {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(dialog.getWindow().FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_forgot_pass);

        dialog.show();
        final EditText ed_email = (EditText) dialog.findViewById(R.id.ed_email);

        ed_email.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        final TextView tv_back = (TextView) dialog.findViewById(R.id.tv_back);
        final TextView tv_send = (TextView) dialog.findViewById(R.id.tv_send);

        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_mail check = new check_mail();
                String email = ed_email.getText().toString();
                if (email.length() == 0) {
                    Toast.makeText(Activity_Login.this, "Please input your email...", Toast.LENGTH_SHORT).show();
                } else if (!check.isValidEmail(email)) {
                    Toast.makeText(activity, "Incorrect Email", Toast.LENGTH_SHORT).show();
                    ed_email.setError("Incorrect Email");
                    ed_email.requestFocus();
                } else {
                    Toast.makeText(Activity_Login.this, "Processing....", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });
    }





    private void LogOn() {
        class Log_In extends AsyncTask<String, String, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = lib_loading.f_init(activity);
            }

            @Override
            protected String doInBackground(String... args) {
                try {


                    HttpClient client = new DefaultHttpClient();
                    HttpResponse response;
                    JSONObject json = new JSONObject();
                    HttpPost post = new HttpPost(HTTP_API.LOGIN);
                    json.put("user", TAG_EMAIL);
                    json.put("password", TAG_PASSWORD);
                    json.put("deviceToken", TAG_TOKEN_GOOGLE);
                    StringEntity se = new StringEntity(json.toString());
                    se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                    post.setEntity(se);
                    response = client.execute(post);

                    if (response != null) {
                        HttpEntity resEntity = response.getEntity();
                        if (resEntity != null) {
                            String msg = EntityUtils.toString(resEntity);
                            Log.i("msg-- cate", msg);


                            JSONObject jsonObject = new JSONObject(msg);
                            TAG_STATUS = jsonObject.getString("status");
                            TAG_MESSENG = jsonObject.getString("message");

                            JSONObject data = jsonObject.getJSONObject("data");
                            TAG_USERID = data.getString("userId");
                            TAG_AUTH_TOKEN = data.getString("authToken");
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
                        MainActivity.get_profile =true;
                       // new Request_Token().Get_Profile(activity,TAG_USERID,TAG_AUTH_TOKEN);
                      //  new MainActivity().Get_Profile(activity,TAG_USERID,TAG_AUTH_TOKEN);

                        new Commit_Sha().Write_TokenID(Activity_Login.this, TAG_USERID, TAG_AUTH_TOKEN,TAG_EMAIL,TAG_PASSWORD);
                        Toast.makeText(Activity_Login.this, "You have successfully logged in", Toast.LENGTH_SHORT).show();

                        Log.e("TAG_USERID", TAG_USERID);
                        Log.e("TAG_AUTH_TOKEN", TAG_AUTH_TOKEN);

                        Intent in = new Intent(activity, MainActivity.class);
                        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                | Intent.FLAG_ACTIVITY_CLEAR_TASK
                                | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(in);
                        finish();
                    } else {
                        Toast.makeText(Activity_Login.this, TAG_MESSENG, Toast.LENGTH_SHORT).show();

                    }


                } catch (Exception e) {

                } catch (Throwable t) {

                }

            }

        }

        if (CheckWifi3G.isConnected(activity)) {
            new Log_In().execute();
        } else {
            new lib_dialog().f_dialog_msg(activity, "Can't connect Internet");

        }
    }

    private void get_shapreference() {

        SharedPreferences sha_IDuser;
        SharedPreferences sha_Token;

        sha_IDuser = activity.getSharedPreferences("ID_user", 0);
        sha_Token = activity.getSharedPreferences("auth_token", 0);

        TAG_USERID = sha_IDuser.getString(
                "ID_user", "");
        TAG_AUTH_TOKEN = sha_Token.getString(
                "auth_token", "");

        Log.d("id_user-----", TAG_USERID);
        Log.d("token-----", TAG_AUTH_TOKEN);



    }

//**********************************************
class RegisterBackground extends AsyncTask<String,String,String>{

    @Override
    protected String doInBackground(String... arg0) {
        // TODO Auto-generated method stub
        String msg = "";

        try {
            if (gcm == null) {
                gcm = GoogleCloudMessaging.getInstance(activity);
            }
            regid = gcm.register(SENDER_ID);
            TAG_TOKEN_GOOGLE = gcm.register(SENDER_ID);
            msg = "Dvice registered, registration ID=" + regid;
            Log.e("111", msg);

            // Persist the regID - no need to register again.
            storeRegistrationId(activity, regid);
        } catch (IOException ex) {
            msg = "Error :" + ex.getMessage();
        }
        return msg;
    }

    @Override
    protected void onPostExecute(String msg) {
       // mDisplay.append(msg + "\n");
        Log.d("msg-----",msg);

    }



}
private void storeRegistrationId(Context context, String regId) {
    final SharedPreferences prefs = getGCMPreferences(context);
    int appVersion = getAppVersion(context);
    Log.i(TAG, "Saving regId on app version " + appVersion);
    SharedPreferences.Editor editor = prefs.edit();
    editor.putString(PROPERTY_REG_ID, regId);
    editor.putInt(PROPERTY_APP_VERSION, appVersion);
    editor.commit();
}

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }
    private String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGCMPreferences(context);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return "";
        }

        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return "";
        }
        return registrationId;
    }

    private SharedPreferences getGCMPreferences(Context context) {

        return getSharedPreferences(MainActivity.class.getSimpleName(),
                Context.MODE_PRIVATE);
    }

    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

}