package activity_register;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import api.HTTP_API;
import check_email_pass.check_mail;
import hoanvusolution.pikapaka.R;
import internet.CheckWifi3G;
import json.JSONParser;
import loading.lib_dialog;
import loading.lib_loading;

/**
 * Created by MrThanhPhong on 2/17/2016.
 */
public class Activity_Register extends AppCompatActivity {

    private AppCompatActivity activity;
    private TextView tv_gander, tv_bithday;
    private ImageView img_gander, img_birthday;
    final Calendar myCalendar = Calendar.getInstance();
   // private RelativeLayout rl_back;
    private TextView tv_back;
    private TextView tv_register;
    private EditText ed_email,ed_password,ed_repassword;

    private GoogleApiClient client;

    private EditText ed_fname,ed_lname;

    public String TAG_FNAME="";
    public String TAG_LNAME="";
    public String TAG_EMAIL="";
    public String TAG_PASSWORD="";
    public String TAG_GENDER="";
    public String TAG_BIRTHDAY="";

    private String TAG_STATUS="";
    private String profile[]=null;
    JSONParser jsonpaser;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
       // client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void init() throws Exception {

        get_resource();
        Onclick();
    }

    private void get_resource() throws Exception {
        activity = this;

        ed_fname=(EditText)findViewById(R.id.ed_fname);
        ed_lname=(EditText)findViewById(R.id.ed_lname);
        tv_gander = (TextView) findViewById(R.id.tv_gander);
        tv_bithday = (TextView) findViewById(R.id.tv_bithday);
        img_gander = (ImageView) findViewById(R.id.img_gander);
        img_birthday = (ImageView) findViewById(R.id.img_birthday);

        tv_back = (TextView) findViewById(R.id.tv_back);
        ed_email = (EditText) findViewById(R.id.ed_email);
        ed_password= (EditText) findViewById(R.id.ed_password);
        ed_repassword= (EditText) findViewById(R.id.ed_repassword);
        tv_register = (TextView) findViewById(R.id.tv_register);
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
           // String myFormat = "dd/MM/yyyy";
            String myFormat = "yyyy-MM-dd";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            tv_bithday.setText(sdf.format(myCalendar.getTime()));

            // birth_ = 1;

        }

    };

    private void Onclick() throws Exception {
        tv_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();

            }
        });
        img_gander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] item = new String[]{"Man", "Woman", "Anything goes"};
                // String[] item = new String[] { getString(R.string.male), getString(R.string.female) };

                new AlertDialog.Builder(activity).setTitle("Gender?")
                        .setItems(item, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                switch (which) {
                                    case 0:
                                        tv_gander.setText("Man");
                                        ///gend_ = 1;
                                        break;
                                    case 1:
                                        tv_gander.setText("Woman");
                                        // gend_ = 1;
                                        break;
                                    case 2:
                                        tv_gander.setText("Anything goes");
                                        // gend_ = 1;
                                        break;

                                }
                                dialog.dismiss();
                            }
                        }).show();
            }
        });


        img_birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.YEAR, -14);
                Date maxDate = calendar.getTime();

                calendar.add(Calendar.YEAR, -100);
                Date minDate = calendar.getTime();

                DatePickerDialog datePicker = new DatePickerDialog(
                        activity, date, myCalendar
                        .get(Calendar.YEAR), myCalendar
                        .get(Calendar.MONTH), myCalendar
                        .get(Calendar.DAY_OF_MONTH));
                datePicker.getDatePicker().setMinDate(minDate.getTime());
                datePicker.getDatePicker().setMaxDate(maxDate.getTime());
                datePicker.show();
            }
        });


        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(Activity_Register.this, "Processing...", Toast.LENGTH_SHORT).show();

                check_mail check = new check_mail();

                TAG_FNAME =ed_fname.getText().toString();
                TAG_LNAME =ed_lname.getText().toString();
                TAG_EMAIL =ed_email.getText().toString();
                TAG_PASSWORD =ed_password.getText().toString();
                String re_password =ed_repassword.getText().toString();
                TAG_GENDER=tv_gander.getText().toString();
                TAG_BIRTHDAY=tv_bithday.getText().toString();

//                if(TAG_FNAME.length()==0||
//                        TAG_LNAME.length()==0||
//                        TAG_EMAIL.length()==0||
//                        TAG_PASSWORD.length()==0||
//                        re_password.length()==0||
//                        TAG_GENDER.length()==0||
//                        TAG_BIRTHDAY.length()==0
//                        ){
//                    new lib_dialog().f_dialog_msg(activity, "please enter all the fields");
//
//
//                }
                if(TAG_FNAME.length()==0){
                    ed_fname.setError("First name must not be balnk");
                }
                else if(TAG_LNAME.length()==0){
                    ed_lname.setError("Last name must not be balnk");
                }
                else if(TAG_EMAIL.length()==0){
                    ed_email.setError("E-mail must not be balnk");
                }
                else if(TAG_PASSWORD.length()==0){
                    ed_password.setError("Password must not be blank");
                }
                else if(ed_repassword.length()==0){
                    ed_repassword.setError("Confirm password must not be blank");
                }
                else if(TAG_GENDER.length()==0){
                    tv_gander.setError("Gender must not be balnk");
                    Toast.makeText(activity,"Gender must not be blank",Toast.LENGTH_SHORT).show();
                }
                else if(TAG_BIRTHDAY.length()==0){
                    tv_bithday.setError("Birthdate must not be blank");
                    Toast.makeText(activity,"Birthdate must not be blank",Toast.LENGTH_SHORT).show();

                }

               else if (!check.isValidEmail(TAG_EMAIL)) {
                    Toast.makeText(activity, "Incorrect Email", Toast.LENGTH_SHORT).show();
                    ed_email.setError("Incorrect Email");
                    ed_email.requestFocus();
                }
                else if(TAG_PASSWORD.length()<8){
                    Toast.makeText(activity,"Password must be at least 8 characters but no more than 16 characters",Toast.LENGTH_SHORT).show();

                }
//                else if(!check.isValidPassword(ed_password.getText().toString())){
//                    Toast.makeText(activity, "Invalid Password", Toast.LENGTH_SHORT).show();
//                    ed_password.setError("Invalid Password");
//                    ed_password.requestFocus();
//                }
                else if(!ed_password.getText().toString().equals(ed_repassword.getText().toString())){
                    Toast.makeText(activity, "Incorrect Password combination", Toast.LENGTH_SHORT).show();
                    ed_password.setError("Incorrect Password combination");
                    ed_password.requestFocus();
                    ed_repassword.setError("Incorrect Password combination");
                    ed_repassword.requestFocus();
                }

                else {
                    //RE();

                    if(CheckWifi3G.isConnected(activity)){
                         sendJson();
                    }
                    else{
                        new lib_dialog().f_dialog_msg(activity,"Can't connect Internet");
                    }

                }

            }
        });
    }

    private String get_datetime_current(){
        //TimeUnit timeUnit = new TimeUnit().;
       // String time = timeUnit.toString();
        String date="";
        //DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy:HH-mm-ss");
        Calendar cal = Calendar.getInstance();

        date=dateFormat.format(cal.getTime());
        return date;

    }


    protected void sendJson() {
        class Loading extends AsyncTask<String, String, String> {
            String status="";
             String message="";
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = lib_loading.f_init(activity);
            }

            @Override
            protected String doInBackground(String... args) {
                try {

                    HttpClient client = new DefaultHttpClient();
               HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
               HttpResponse response;
               JSONObject json = new JSONObject();
                    HttpPost post = new HttpPost(HTTP_API.REGISTER);
                    json.put("email", TAG_EMAIL);
                    json.put("password", TAG_PASSWORD);
                    JSONObject joPro =new JSONObject();
                    joPro.put("firstName", TAG_FNAME);
                    joPro.put("lastName", TAG_LNAME);
                    joPro.put("dob", TAG_BIRTHDAY);
                    joPro.put("gender", TAG_GENDER);

                    json.put("profile", joPro);
                  StringEntity se = new StringEntity( json.toString());
                    se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                    post.setEntity(se);
                    response = client.execute(post);
                    if (response != null) {
                        HttpEntity resEntity = response.getEntity();
                        if (resEntity != null) {
                            String msg = EntityUtils.toString(resEntity);
//                            Log.i("msg-- cate", msg);
//                            Log.i("msg-- cate", msg);
                            JSONObject jsonObject = new JSONObject(msg);
                             status = jsonObject.getString("status");
                             message = jsonObject.getString("message");

                        }

                        if (resEntity != null) {
                            resEntity.consumeContent();
                        }

                        client.getConnectionManager().shutdown();

                    }
                } catch (Exception e) {
                    progressDialog.dismiss();
                    Log.i("ERROR ", "ERROR1");

                } catch (Throwable t) {
                    progressDialog.dismiss();
                    Log.i("ERROR ", "ERROR2");

                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                progressDialog.dismiss();
                try {

                    if(status.equals("success")){

                        Toast.makeText(activity,"\"Congratulation! You have successfully registered; please check your email to confirm registration!",Toast.LENGTH_SHORT).show();
                        finish();
                        // new lib_dialog().f_dialog_msg(activity,"Congratulation! You have successfully registered; please check your email to confirm registration!");
                    }
                    else{
                        Toast.makeText(activity,message,Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {

                } catch (Throwable t) {

                }

            }

        }

        if (CheckWifi3G.isConnected(activity)) {
            new Loading().execute();
        }
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


