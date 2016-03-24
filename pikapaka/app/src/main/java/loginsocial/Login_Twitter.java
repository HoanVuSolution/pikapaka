package loginsocial;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;
import com.twitter.sdk.android.core.services.AccountService;

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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import api.HTTP_API;
import check_email_pass.check_mail;
import hoanvusolution.pikapaka.MainActivity;
import hoanvusolution.pikapaka.R;
import internet.CheckWifi3G;
import io.fabric.sdk.android.Fabric;
import loading.lib_loading;
import shared_prefs.Commit_Sha;

public class Login_Twitter extends AppCompatActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "yQ1HxtQxDZRQuuzK55zVfaX6k";
    private static final String TWITTER_SECRET = "fbbyvfaupbzaJwxwxbZebHT8VbFB4KB0YdqlgHfOgRdBNayVOP";

    Long userid;
    TwitterSession session;

    private TwitterLoginButton loginButton;
    TextView textView;
    private RelativeLayout rl_back;
    final Calendar myCalendar = Calendar.getInstance();
    private String TAG_ID="";
    private String TAG_TOKEN="";
    private String TAG_SECRET="";
    private String TAG_EMAIL="";
    private String TAG_FIRSTNAM="";
    private String TAG_LASTNAME="";
    private String TAG_GENDER="";
    private String TAG_DOB="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.login_twitter);

        textView = (TextView) findViewById(R.id.tv_username);

        loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);

                loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                AccountService ac = Twitter.getApiClient(result.data).getAccountService();
                Log.e("Twitter ", "Login sucessfull");
                session = result.data;

                String username = session.getUserName();
                userid = session.getUserId();


                textView.setText("Hi " + username);
                TwitterAuthToken authToken = session.getAuthToken();
                String token = authToken.token;
                String secret = authToken.secret;
                // TODO: Remove toast and use the TwitterSession's userID
                // with your app's user model
//                String msg = "@token + session.getUserName() + " logged in! (#" + session.getUserId() + ")";
//                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();


                /*TweetComposer.Builder builder = new TweetComposer.Builder(MainActivity.this).text("Just setting up my Fabric!");
                builder.show();*/
                getUserData();
            }

            @Override
            public void failure(TwitterException exception) {
                Log.e("TwitterKit", "Login with Twitter failure", exception);
            }
        });


        rl_back =(RelativeLayout)findViewById(R.id.rl_back);
        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginButton.onActivityResult(requestCode, resultCode, data);

    }

    void getUserData() {
        Twitter.getApiClient(session).getAccountService()
                .verifyCredentials(true, false, new Callback<User>() {

                    @Override
                    public void failure(TwitterException e) {

                    }

                    @Override
                    public void success(Result<User> userResult) {

                        User user = userResult.data;
                        String twitterImage = user.profileImageUrl;
                        TwitterAuthToken authToken = session.getAuthToken();
                        String token = authToken.token;
                        String secret = authToken.secret;
                        String id=null;


                        try {
                            Log.e("imageurl", user.profileImageUrl);
                            Log.e("name", user.name);
                            Log.e("name_id", user.getId()+"");
                             id=user.getId()+"";
                            Log.e("secret", secret);

                            Log.e("des", user.description);
                            Log.e("followers ", String.valueOf(user.followersCount));
                            Log.e("createdAt", user.createdAt);
                            Log.e("token---", token);
                           // Log.e("email",user.email);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //---------
                        TAG_ID =user.getId()+"";
                        TAG_TOKEN =token;
                        TAG_SECRET =secret;
                        if(TAG_ID.length()>0&&token.length()>0){
                            Check_Info(TAG_ID,TAG_TOKEN,TAG_SECRET);
                        }

                    }

                });
// Can also use Twitter directly: Twitter.getApiClient()

    }

    //---------
    private void Check_Info(final String id,final String token,final String secret){
        class Request extends AsyncTask<String, String, String> {
            ProgressDialog progressDialog;
            String status;
            String message;
            String user_id;
            String auth_token;
            int responseCode;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = lib_loading.f_init(Login_Twitter.this);
            }
            @Override
            protected String doInBackground(String... args) {
                HttpClient client = new DefaultHttpClient();
                HttpResponse response;
                try {
                    HttpPost post = new HttpPost(HTTP_API.LOGIN_Twitter_verify);
                    JSONObject json = new JSONObject();
                    json.put("id", id);
                    json.put("token", token);
                    json.put("secret", secret);
                    StringEntity se = new StringEntity( json.toString());
                    se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                    post.setEntity(se);

                    response = client.execute(post);
                    if (response != null) {

                        HttpEntity resEntity = response.getEntity();
                        if (resEntity != null) {
                             responseCode = response.getStatusLine().getStatusCode();
                            Log.e("responseCode---",responseCode+"");
                            String msg = EntityUtils.toString(resEntity);
                            Log.e("msg-twitter-",msg);

                            JSONObject jsonObject = new JSONObject(msg);
                            status =jsonObject.getString("status");
                            message =jsonObject.getString("message");
                            Log.e("status--",status);

                            JSONObject object =jsonObject.getJSONObject("data");
                            if(object.length()>0){
                                user_id =object.getString("userId");
                                auth_token =object.getString("authToken");

                            }
                            //parseJSON(msg);
                        }
                        if (resEntity != null) {
                            resEntity.consumeContent();
                        }
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
                    //Toast.makeText(Login_Twitter.this, message, Toast.LENGTH_SHORT).show();

                    if(responseCode==200){
                        if(user_id.length()>0&& auth_token.length()>0){
                            new Commit_Sha().Write_TokenID(Login_Twitter.this,user_id,auth_token,"user","pass");

                            Intent in = new Intent(Login_Twitter.this, MainActivity.class);
                            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                    | Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(in);
                            finish();
                        }
                        else{
                            dialog_profile();
                        }
                    }

                    else{
                        dialog_profile();
                    }

                } catch (Exception e) {

                } catch (Throwable t) {

                }

            }

        }

        if (CheckWifi3G.isConnected(Login_Twitter.this)) {
            new Request().execute();
        }
    }

    private void add_info(final String id,final String token,final String secret,final String firstName,final String lastName,final String gender,final String email,final String dob){
        class Request extends AsyncTask<String, String, String> {
            ProgressDialog progressDialog;
            String status;
            String message;
            String user_id;
            String auth_token;
            int responseCode;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = lib_loading.f_init(Login_Twitter.this);
            }
            @Override
            protected String doInBackground(String... args) {
                HttpClient client = new DefaultHttpClient();
                HttpResponse response;
                try {
                    HttpPost post = new HttpPost(HTTP_API.LOGIN_Twitter_add_email);
                    JSONObject json = new JSONObject();
                    json.put("id", id);
                    json.put("token", token);
                    json.put("secret", secret);
                    json.put("email", email);
                    json.put("firstName", firstName);
                    json.put("lastName", lastName);
                    json.put("gender", gender);
                    json.put("dob", dob);
                    StringEntity se = new StringEntity( json.toString());
                    se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                    post.setEntity(se);

                    response = client.execute(post);
                    if (response != null) {
                        HttpEntity resEntity = response.getEntity();
                        if (resEntity != null) {
                            responseCode = response.getStatusLine().getStatusCode();
                            Log.e("responseCode---",responseCode+"");
                            String msg = EntityUtils.toString(resEntity);
                            Log.e("msg-twitter-",msg);

                            JSONObject jsonObject = new JSONObject(msg);
                            status =jsonObject.getString("status");
                            message =jsonObject.getString("message");
                            Log.e("status--",status);

                            JSONObject object =jsonObject.getJSONObject("data");
                            if(object.length()>0){
                                user_id =object.getString("userId");
                                auth_token =object.getString("authToken");

                            }
                            //parseJSON(msg);
                        }
                        if (resEntity != null) {
                            resEntity.consumeContent();
                        }
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
                    //Toast.makeText(Login_Twitter.this, message, Toast.LENGTH_SHORT).show();

                    //if(responseCode==200){
                        if(user_id.length()>0&& auth_token.length()>0){
                            new Commit_Sha().Write_TokenID(Login_Twitter.this,user_id,auth_token,"user","pass");

                            Intent in = new Intent(Login_Twitter.this, MainActivity.class);
                            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                    | Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(in);
                            finish();
                        }


                    else{
                            Toast.makeText(Login_Twitter.this,message, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {

                } catch (Throwable t) {

                }

            }

        }

        if (CheckWifi3G.isConnected(Login_Twitter.this)) {
            new Request().execute();
        }
    }

    private void dialog_profile(){
        final Dialog dialog = new Dialog(Login_Twitter.this);
        dialog.requestWindowFeature(dialog.getWindow().FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_twitter);


        final EditText ed_firstname=(EditText)dialog.findViewById(R.id.ed_firstname);
        final EditText ed_lastname=(EditText)dialog.findViewById(R.id.ed_lastname);
        final EditText ed_email=(EditText)dialog.findViewById(R.id.ed_email);
        final TextView tv_gender=(TextView)dialog.findViewById(R.id.tv_gender);
        final TextView tv_birthday=(TextView)dialog.findViewById(R.id.tv_birthday);
        final TextView tv_cancel=(TextView)dialog.findViewById(R.id.tv_cancel);
        final TextView tv_send=(TextView)dialog.findViewById(R.id.tv_send);
        final ImageView img_select1=(ImageView)dialog.findViewById(R.id.img_select1);
        final ImageView img_birthday=(ImageView)dialog.findViewById(R.id.img_birthday);
        //---
        //--
      final  DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                // String myFormat = "dd/MM/yyyy";
                String myFormat = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                tv_birthday.setText(sdf.format(myCalendar.getTime()));

                // birth_ = 1;

            }

        };
        //----
        img_birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.YEAR, -14);
                Date maxDate = calendar.getTime();
                calendar.add(Calendar.YEAR, -100);
                Date minDate = calendar.getTime();

                DatePickerDialog datePicker = new DatePickerDialog(
                        Login_Twitter.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar
                        .get(Calendar.MONTH), myCalendar
                        .get(Calendar.DAY_OF_MONTH));
                datePicker.getDatePicker().setMinDate(minDate.getTime());
                datePicker.getDatePicker().setMaxDate(maxDate.getTime());
                datePicker.show();
            }
        });

        img_select1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] item = new String[]{"Man", "Woman", "Anything goes"};
                // String[] item = new String[] { getString(R.string.male), getString(R.string.female) };

                new AlertDialog.Builder(Login_Twitter.this).setTitle("Gender?")
                        .setItems(item, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                switch (which) {
                                    case 0:
                                        tv_gender.setText("Man");
                                        ///gend_ = 1;
                                        break;
                                    case 1:
                                        tv_gender.setText("Woman");
                                        // gend_ = 1;
                                        break;
                                    case 2:
                                        tv_gender.setText("Anything goes");
                                        // gend_ = 1;
                                        break;

                                }
                                dialog.dismiss();
                            }
                        }).show();
            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_mail check = new check_mail();
                if(ed_firstname.getText().toString().length()==0){
                    Toast.makeText(Login_Twitter.this,"Input First name",Toast.LENGTH_SHORT).show();
                }
                else  if(ed_lastname.getText().toString().length()==0){
                    Toast.makeText(Login_Twitter.this,"Input Last name",Toast.LENGTH_SHORT).show();
                }
               else if(tv_gender.getText().toString().length()==0){
                    Toast.makeText(Login_Twitter.this,"Select Gender",Toast.LENGTH_SHORT).show();
                }
               else if(tv_birthday.getText().toString().length()==0){
                    Toast.makeText(Login_Twitter.this,"Select Birthday",Toast.LENGTH_SHORT).show();
                }
                else  if(ed_email.getText().toString().length()==0){
                    Toast.makeText(Login_Twitter.this,"Input your email",Toast.LENGTH_SHORT).show();
                }
                else if(!check.isValidEmail(ed_email.getText().toString())){
                    Toast.makeText(Login_Twitter.this, "Incorrect Email", Toast.LENGTH_SHORT).show();
                    ed_email.setError("Incorrect Email");
                    ed_email.requestFocus();
                }
                else{
                    TAG_EMAIL =ed_email.getText().toString();
                    TAG_FIRSTNAM =ed_firstname.getText().toString();
                    TAG_LASTNAME =ed_lastname.getText().toString();
                    TAG_GENDER =tv_gender.getText().toString();
                    TAG_DOB =tv_birthday.getText().toString();
                    add_info(TAG_ID,TAG_TOKEN,TAG_SECRET,TAG_FIRSTNAM,TAG_LASTNAME,TAG_GENDER,TAG_EMAIL,TAG_DOB);
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }



}

