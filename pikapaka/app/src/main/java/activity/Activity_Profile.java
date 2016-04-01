package activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import api.HTTP_API;
import hoanvusolution.pikapaka.R;
import image.lib_image_save_original;
import internet.CheckWifi3G;
import loading.lib_dialog;
import loading.lib_loading;
import multipart.AndroidMultiPartEntity;

/**
 * Created by MrThanhPhong on 2/19/2016.
 */
public class Activity_Profile extends AppCompatActivity{
    private String TAG_STATUS="";
    private String TAG_MESSAGE="";

    private String TAG_IMAGE_URL="";

    private String TAG_USERID="";
    private String TAG_TOKEN="";
    private String TAG_FIRSTNAME="";
    private String TAG_LASTNAME="";
    private String TAG_DOB="";
    private String TAG_PROVIDER="";
    private String TAG_DISPLAYNAME="";
    private String TAG_GENDER="";
    private String TAG_USERNAME="";
    private String TAG_SUMMARY="";
    private String TAG_SHARECONTACT="";
    private String TAG_MINNUMOF="";
    private String TAG_MAXNUMOF="";
    private String TAG_AGEFROM="";
    private String TAG_AGETO="";
    private String TAG_GENDER_PARTNER="";
    private String TAG_DISTANCE="";
    private String TAG_TYPE_DISTANCE="";
    private String TAG_EXPRIEDHOURS="";


    private AppCompatActivity activity;

    private LinearLayout ll_back;
    private LinearLayout ll_save;
    private LinearLayout ll_share;
    private TextView tv_share;

    private LinearLayout ll_max;
    private TextView tv_max;
    private LinearLayout ll_min;
    private TextView tv_min;
    private LinearLayout ll_par_gender;
    private TextView tv_part_gender;
    private LinearLayout ll_distance;
    private LinearLayout ll_type_distance;
    private TextView tv_km;
    private TextView tv_type_dis;
    private LinearLayout ll_experss;
    private TextView tv_hours;

    private LinearLayout age1;
    private TextView tv_minage;
    private LinearLayout age2;
    private TextView tv_maxage;


    private int Age_User;

    private List<String> list_age = new ArrayList<String>();
    private int select =1;
    private ProgressDialog progressDialog;
    private TextView tv_name,tv_old;
    double hours = 2;

    private ImageView img_profile;
    private Button bt_comfirm;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private  ImageView ic_img;
    private int serverResponseCode = 0;
    private String file_path="";
    private String TAG_PHONE="";
    private File TAG_FILE;
    long totalSize=1;
    private boolean img_avata=false;
    private String path_avatar="";
    private TextView tv_count_friend,tv_count_mail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init()throws Exception{
        get_resource();
        get_shapreference();
        OnClick();

    }

    private void get_resource()throws Exception{
        activity=this;
        ll_back=(LinearLayout)findViewById(R.id.ll_back);
        ll_save=(LinearLayout)findViewById(R.id.ll_save);
        tv_name=(TextView)findViewById(R.id.tv_name);
        tv_old=(TextView)findViewById(R.id.tv_old);

        tv_name.setText("");
        tv_old.setText("");
        tv_count_friend=(TextView)findViewById(R.id.tv_count_friend);
        tv_count_mail=(TextView)findViewById(R.id.tv_count_mail);
        tv_count_mail.setVisibility(View.GONE);
        tv_count_friend.setVisibility(View.GONE);

        //------

        ll_share=(LinearLayout)findViewById(R.id.ll_share);
        tv_share=(TextView)findViewById(R.id.tv_share);
        tv_share.setText("");
        ll_max=(LinearLayout)findViewById(R.id.ll_max);
        tv_max=(TextView)findViewById(R.id.tv_max);
        tv_max.setText("4");
        ll_min=(LinearLayout)findViewById(R.id.ll_min);
        tv_min=(TextView)findViewById(R.id.tv_min);
        tv_min.setText("2");
        ll_par_gender=(LinearLayout)findViewById(R.id.ll_par_gender);
        tv_part_gender=(TextView)findViewById(R.id.tv_part_gender);
        tv_part_gender.setText("");
        ll_distance=(LinearLayout)findViewById(R.id.ll_distance);
        ll_type_distance=(LinearLayout)findViewById(R.id.ll_type_distance);
        tv_km=(TextView)findViewById(R.id.tv_km);
        tv_type_dis=(TextView)findViewById(R.id.tv_type_dis);
        tv_km.setText("");
        ll_experss=(LinearLayout)findViewById(R.id.ll_experss);
        tv_hours=(TextView)findViewById(R.id.tv_hours);
        tv_hours.setText("");
        age1=(LinearLayout)findViewById(R.id.age1);
        tv_minage=(TextView)findViewById(R.id.tv_minage);
        tv_minage.setText("");
        age2=(LinearLayout)findViewById(R.id.age2);
        tv_maxage=(TextView)findViewById(R.id.tv_maxage);
        tv_maxage.setText("");

        img_profile=(ImageView)findViewById(R.id.img_profile);
        bt_comfirm=(Button)findViewById(R.id.bt_comfirm);
    }
    private void OnClick()throws Exception{
        ll_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        img_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_avata=true;
                selectImage();
            }
        });
        //-------------
        ll_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] item = new String[]{"Never", "Phone", "Email", "Both"};
                // String[] item = new String[] { getString(R.string.male), getString(R.string.female) };

                new AlertDialog.Builder(activity).setTitle("Your contacts")
                        .setItems(item, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                switch (which) {
                                    case 0:
                                        tv_share.setText("Never");

                                        break;
                                    case 1:
                                        tv_share.setText("Phone");

                                        break;
                                    case 2:
                                        tv_share.setText("Email");

                                        break;
                                    case 3:
                                        tv_share.setText("Both");

                                        break;


                                }

                                dialog.dismiss();
                            }
                        }).show();
            }
        });
        ll_max.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    String[] item = new String[]{"2", "3", "4", "5", "10", "20"};
                    // String[] item = new String[] { getString(R.string.male), getString(R.string.female) };

                    new AlertDialog.Builder(activity).setTitle("Maximal")
                            .setItems(item, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    switch (which) {
                                        case 0:
                                            tv_max.setText("2");
                                            ///gend_ = 1;
                                            break;
                                        case 1:
                                            tv_max.setText("3");
                                            // gend_ = 1;
                                            break;
                                        case 2:
                                            tv_max.setText("4");
                                            // gend_ = 1;
                                            break;
                                        case 3:
                                            tv_max.setText("5");
                                            // gend_ = 1;
                                            break;
                                        case 4:
                                            tv_max.setText("10");
                                            // gend_ = 1;
                                            break;
                                        case 5:
                                            tv_max.setText("20");
                                            // gend_ = 1;
                                            break;

                                    }

                                    int max = Integer.parseInt(tv_max.getText().toString());
                                    int min = Integer.parseInt(tv_min.getText().toString());

                                    if (max < min) {
                                        Toast.makeText(activity, "Max participants > Min participants", Toast.LENGTH_SHORT).show();
                                    }
                                    dialog.dismiss();
                                }
                            }).show();
                }
            }
        });
        ll_min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    String[] item = new String[]{"2", "3", "4", "5", "10", "20"};
                    // String[] item = new String[] { getString(R.string.male), getString(R.string.female) };

                    new AlertDialog.Builder(activity).setTitle("Minimal")
                            .setItems(item, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    switch (which) {
                                        case 0:
                                            tv_min.setText("2");
                                            ///gend_ = 1;
                                            break;
                                        case 1:
                                            tv_min.setText("3");
                                            // gend_ = 1;
                                            break;
                                        case 2:
                                            tv_min.setText("4");
                                            // gend_ = 1;
                                            break;
                                        case 3:
                                            tv_min.setText("5");
                                            // gend_ = 1;
                                            break;
                                        case 4:
                                            tv_min.setText("10");
                                            // gend_ = 1;
                                            break;
                                        case 5:
                                            tv_min.setText("20");
                                            // gend_ = 1;
                                            break;

                                    }


                                    int max = Integer.parseInt(tv_max.getText().toString());
                                    int min = Integer.parseInt(tv_min.getText().toString());

                                    if (max < min) {
                                        Toast.makeText(activity, "Max participants > Min participants", Toast.LENGTH_SHORT).show();
                                    }
                                    dialog.dismiss();
                                }
                            }).show();
                }
            }
        });

        age1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select = 1;

                int a1 = Integer.parseInt(tv_minage.getText().toString());
                int a2 = Integer.parseInt(tv_maxage.getText().toString());
                list_age.clear();
                for (int i = 12; i <= a2; i++) {
                    list_age.add(i + "");
                }
                try {
                    dialog();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        age2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select = 2;
                int a1 = Integer.parseInt(tv_minage.getText().toString());
                int a2 = Integer.parseInt(tv_maxage.getText().toString());
                list_age.clear();
                for (int i = a1; i <= 120; i++) {
                    list_age.add(i + "");
                }
                try {
                    dialog();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        ll_par_gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    String[] item = new String[]{"Man", "Woman", "Anything goes"};
                    // String[] item = new String[] { getString(R.string.male), getString(R.string.female) };

                    new AlertDialog.Builder(activity).setTitle("Partnet gender")
                            .setItems(item, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    switch (which) {
                                        case 0:
                                            tv_part_gender.setText("Man");
                                            ///gend_ = 1;
                                            break;
                                        case 1:
                                            tv_part_gender.setText("Woman");
                                            // gend_ = 1;
                                            break;
                                        case 2:
                                            tv_part_gender.setText("Anything gose");
                                            // gend_ = 1;
                                            break;

                                    }
                                    dialog.dismiss();
                                }
                            }).show();
                }
            }
        });

        ll_distance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] item = new String[]{"10", "20", "30", "40", "50", "60", "70", "80", "90", "100"};
                // String[] item = new String[] { getString(R.string.male), getString(R.string.female) };

                new AlertDialog.Builder(activity).setTitle("Distance")
                        .setItems(item, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                switch (which) {
                                    case 0:
                                        tv_km.setText("10");
                                        ///gend_ = 1;
                                        break;
                                    case 1:
                                        tv_km.setText("20");
                                        // gend_ = 1;
                                        break;
                                    case 2:
                                        tv_km.setText("30");
                                        // gend_ = 1;
                                        break;
                                    case 3:
                                        tv_km.setText("40");
                                        // gend_ = 1;
                                        break;
                                    case 4:
                                        tv_km.setText("50");
                                        // gend_ = 1;
                                        break;
                                    case 5:
                                        tv_km.setText("60");
                                        // gend_ = 1;
                                        break;
                                    case 6:
                                        tv_km.setText("70");
                                        // gend_ = 1;
                                        break;
                                    case 7:
                                        tv_km.setText("80");
                                        // gend_ = 1;
                                        break;
                                    case 8:
                                        tv_km.setText("90");
                                        // gend_ = 1;
                                        break;
                                    case 9:
                                        tv_km.setText("100");
                                        // gend_ = 1;
                                        break;

                                }

                                dialog.dismiss();
                            }
                        }).show();
            }

        });

        ll_type_distance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] item = new String[]{"kilometers", "miles"};
                // String[] item = new String[] { getString(R.string.male), getString(R.string.female) };

                new AlertDialog.Builder(activity).setTitle("Unit")
                        .setItems(item, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                switch (which) {
                                    case 0:
                                        tv_type_dis.setText("kilometers");
                                        ///gend_ = 1;
                                        break;
                                    case 1:
                                        tv_type_dis.setText("miles");
                                        // gend_ = 1;
                                        break;


                                }

                                dialog.dismiss();
                            }
                        }).show();
            }

        });

        ll_experss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    String[] item = new String[]{"30 Minutes", "1 Hour", "2 Hours", "4 Hours", "12 Hours", "1 Week", "2 Weeks"};

                    new AlertDialog.Builder(activity).setTitle("Activity expires?")
                            .setItems(item, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    switch (which) {
                                        case 0:
                                            tv_hours.setText("30 Minutes");
                                            hours = 0.5;

                                            break;
                                        case 1:
                                            tv_hours.setText("1 Hour");
                                            hours = 1;

                                            break;
                                        case 2:
                                            tv_hours.setText("2 Hours");
                                            hours = 2;
                                            break;
                                        case 3:
                                            tv_hours.setText("4 Hours");
                                            hours = 4;
                                            break;
                                        case 4:
                                            tv_hours.setText("12 Hours");
                                            hours = 12;
                                            break;
                                        case 5:
                                            tv_hours.setText("1 Week");
                                            hours = 168;
                                            break;
                                        case 6:
                                            tv_hours.setText("2 Weeks");
                                            hours = 336;
                                            break;

                                    }


                                    dialog.dismiss();
                                }
                            }).show();

            }
        });

        ///
        ll_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TAG_GENDER_PARTNER = tv_part_gender.getText().toString();
                TAG_MINNUMOF = tv_min.getText().toString();
                TAG_MAXNUMOF = tv_max.getText().toString();
                TAG_SHARECONTACT = tv_share.getText().toString();
                TAG_EXPRIEDHOURS = hours + "";
                TAG_TYPE_DISTANCE = tv_type_dis.getText().toString();

                if (tv_type_dis.getText().toString().equals("kilometers")) {
                    TAG_DISTANCE = tv_km.getText().toString();
                } else {
                    float km = Float.parseFloat(tv_km.getText().toString());
                    double miles = km / 1.6;
                    TAG_DISTANCE = miles + "";
                 //   Log.e("TAG_DISTANCE", TAG_DISTANCE);
                }

                int max = Integer.parseInt(tv_max.getText().toString());
                int min = Integer.parseInt(tv_min.getText().toString());

                if (max < min) {
                    Toast.makeText(activity, "Max participants > Min participants", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        Save_Change();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        bt_comfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    dialog1();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void  dialog(){
        final AlertDialog.Builder Select = new AlertDialog.Builder(
                activity);
        Select.setTitle("Age");

        final ListView List_age = new ListView(activity);
        ViewGroup.LayoutParams dialogTxt_idLayoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        List_age.setLayoutParams(dialogTxt_idLayoutParams);

        ArrayAdapter adapter_region = adapter_region = new ArrayAdapter(
                activity, R.layout.item_list, list_age);
        adapter_region
                .setDropDownViewResource(R.layout.item_list);
        List_age.setAdapter(adapter_region);

        LinearLayout layout = new LinearLayout(activity);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(List_age);

        Select.setView(layout);

        final AlertDialog alert = Select.create();

        alert.show();

        List_age.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (select == 1) {
                    tv_minage.setText(list_age.get(position).toString());
                    TAG_AGETO = list_age.get(position).toString();

                }
                if (select == 2) {
                    tv_maxage.setText(list_age.get(position).toString());
                    TAG_AGEFROM = list_age.get(position).toString();
                }
                alert.dismiss();
            }
        });
    }

    private void get_shapreference(){

        SharedPreferences sha_IDuser;
        SharedPreferences sha_Token;

        sha_IDuser = activity.getSharedPreferences("ID_user", 0);
        sha_Token = activity.getSharedPreferences("auth_token", 0);

        TAG_USERID = sha_IDuser.getString(
                "ID_user", "");
        TAG_TOKEN = sha_Token.getString(
                "auth_token", "");


        if(TAG_TOKEN.length()>0&& TAG_TOKEN.length()>0){
            try {
                get_info();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
         //   Log.i("Token +UserID = ", "NULL");
        }


    }
    private void get_info()throws Exception{
        class Loading extends AsyncTask<String, String, String> {

            String age_user;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = lib_loading.f_init(activity);
            }

            @Override
            protected String doInBackground(String... args) {
                try {
                   // Looper.prepare(); //For Preparing Message Pool for the child Thread
                    HttpClient client = new DefaultHttpClient();
                    // HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
                    HttpResponse response;

                    HttpGet post = new HttpGet(HTTP_API.GET_PROFILE+"/"+TAG_USERID);
                    post.addHeader("X-User-Id", TAG_USERID);
                    post.addHeader("X-Auth-Token", TAG_TOKEN);
//                    Log.i("idUser-------", TAG_USERID);
//                    Log.i("token--------", TAG_TOKEN);
                    response = client.execute(post);
                    if (response != null) {
                        HttpEntity resEntity = response.getEntity();
                        if (resEntity != null) {
                            String msg = EntityUtils.toString(resEntity);
                         //   Log.i("msg-- cate", msg);

                            JSONObject jsonObject = new JSONObject(msg);
                            TAG_STATUS = jsonObject.getString("status");
                            TAG_MESSAGE=jsonObject.getString("message");
                          JSONObject data =jsonObject.getJSONObject("data");
                            TAG_FIRSTNAME =data.getString("firstName");
                            TAG_LASTNAME =data.getString("lastName");
                            TAG_DOB =data.getString("dob");
                            age_user=data.getString("age");
                            //TAG_PROVIDER =data.getString("provider");
                            TAG_DISPLAYNAME =data.getString("displayName");
                            TAG_GENDER =data.getString("gender");
                            //TAG_USERNAME =data.getString("username");
                           // TAG_SUMMARY =data.getString("summary");
                            TAG_SHARECONTACT =data.getString("shareContact");

                            JSONObject activityPreferences =data.getJSONObject("activityPreferences");
                            TAG_MINNUMOF =activityPreferences.getString("minNumOfParticipants");
                            TAG_MAXNUMOF =activityPreferences.getString("maxNumOfParticipants");
                            TAG_AGEFROM =activityPreferences.getString("ageFrom");
                            TAG_AGETO =activityPreferences.getString("ageTo");
                            TAG_GENDER_PARTNER =activityPreferences.getString("gender");
                            TAG_DISTANCE =activityPreferences.getString("distance");
                            TAG_EXPRIEDHOURS =activityPreferences.getString("expiredHours");


                            try {
                                TAG_IMAGE_URL =data.getString("imageUrl");
                            }catch(JSONException e){
                                TAG_IMAGE_URL="";
                                Log.e("Error,---","TAG_IMAGE_URL");
                                e.getStackTrace();
                            }


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
                    if(TAG_STATUS.equals("success")){
                        tv_name.setText(TAG_DISPLAYNAME.toUpperCase());
                        tv_old.setText(age_user+"years old "+TAG_GENDER);
                        tv_share.setText(TAG_SHARECONTACT);
                        tv_max.setText(TAG_MAXNUMOF);
                        tv_min.setText(TAG_MINNUMOF);
                        tv_minage.setText(TAG_AGEFROM);
                        tv_maxage.setText(TAG_AGETO);
                        tv_part_gender.setText(TAG_GENDER_PARTNER);
                       // tv_km.setText(TAG_DISTANCE);
                        double distan =Double.parseDouble(TAG_DISTANCE);
                       // Log.e("distan", distan + "");
                        if(distan%2==0){
                            tv_km.setText(TAG_DISTANCE);
                            tv_type_dis.setText("kilometers");
                        }
                        else{
                            distan*=1.6;
                            int mile =(int)distan;
                          //  Log.e("distan--", "miles");
                            tv_km.setText(mile + "");
                            tv_type_dis.setText("miles");
                        }
                        tv_hours.setText(TAG_EXPRIEDHOURS);
                        TAG_MESSAGE="";

                      //  Log.e("TAG_IMAGE_URL,---",TAG_IMAGE_URL);
                        if(TAG_IMAGE_URL.length()>0){
                            String check = TAG_IMAGE_URL.substring(0,3);
                            if(check.equals("http")){
                                new lib_image_save_original(activity,TAG_IMAGE_URL,img_profile);

                            }
                            else{
                                TAG_IMAGE_URL=HTTP_API.url_image+TAG_IMAGE_URL;
                                new lib_image_save_original(activity,TAG_IMAGE_URL,img_profile);

                            }
                        }
                    }
                    else{
                        Toast.makeText(activity, TAG_MESSAGE, Toast.LENGTH_SHORT).show();
                        TAG_MESSAGE="";
                    }

                } catch (Exception e) {

                } catch (Throwable t) {

                }

            }

        }

        if (CheckWifi3G.isConnected(activity)) {

            new Loading().execute();
        }
        else{
            new lib_dialog().f_dialog_msg(activity, "Error Connect Internet!");
        }
    }


private void Save_Change(){
   // Log.i("msg-- cate", "cate");
    class Update extends AsyncTask<String, String, String> {
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
                HttpPut post = new HttpPut(HTTP_API.UPDATE_PROFILE);

                post.addHeader("X-User-Id", TAG_USERID);
                post.addHeader("X-Auth-Token", TAG_TOKEN);
                json.put("firstName", TAG_FIRSTNAME);
                json.put("lastName", TAG_LASTNAME);
                json.put("gender",TAG_GENDER);
                json.put("dob",TAG_DOB);
                json.put("displayName",TAG_DISPLAYNAME);
                json.put("shareContact",TAG_SHARECONTACT);
                JSONObject Preferences = new JSONObject();
                Preferences.put("minNumOfParticipants",TAG_MINNUMOF);
                Preferences.put("maxNumOfParticipants",TAG_MAXNUMOF);
                Preferences.put("ageFrom",TAG_AGEFROM);
                Preferences.put("ageTo",TAG_AGETO);
                Preferences.put("gender",TAG_GENDER_PARTNER);
                Preferences.put("distance", TAG_DISTANCE);
                Preferences.put("expiredHours", TAG_EXPRIEDHOURS);
                json.put("activityPreferences", Preferences);

                StringEntity se = new StringEntity( json.toString());
                se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

                post.setEntity(se);
                response = client.execute(post);

                if (response != null) {
                    HttpEntity resEntity = response.getEntity();
                    if (resEntity != null) {
                        String msg = EntityUtils.toString(resEntity);
                     //   Log.i("msg-- cate", msg);

                        JSONObject jsonObject = new JSONObject(msg);
                        TAG_STATUS = jsonObject.getString("status");
                        TAG_MESSAGE=jsonObject.getString("message");

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
                if(TAG_STATUS.equals("success")){
                    Toast.makeText(activity, TAG_MESSAGE, Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(activity, TAG_MESSAGE, Toast.LENGTH_SHORT).show();

                }

            } catch (Exception e) {

            } catch (Throwable t) {

            }

        }

    }

    if (CheckWifi3G.isConnected(activity)) {
        new Update().execute();
    }
    else{
        new lib_dialog().f_dialog_msg(activity, "Error Connect Internet!");
    }
}


    private void dialog1()throws Exception{
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(dialog.getWindow().FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_comfirm);


       final EditText ed_phome=(EditText)dialog.findViewById(R.id.ed_phome);
       final TextView tv_cancel=(TextView)dialog.findViewById(R.id.tv_cancel);
       final TextView tv_ok=(TextView)dialog.findViewById(R.id.tv_ok);
         ic_img =(ImageView)dialog.findViewById(R.id.ic_img);

        ic_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TAG_PHONE=ed_phome.getText().toString();
               // uploadProfilePhoto(TAG_PHONE, TAG_FILE);
              //  doFileUpload();
                //uploadProfilePhoto(TAG_PHONE, TAG_FILE);
                if(TAG_PHONE.length()==0){
                    Toast.makeText(Activity_Profile.this, "Input your phone number!", Toast.LENGTH_SHORT).show();
                }
                else if(file_path.length()==0){
                    Toast.makeText(Activity_Profile.this, "please select the picture!", Toast.LENGTH_SHORT).show();

                }
                else{
                    upload_identity(TAG_PHONE, file_path);
                    dialog.dismiss();
                }

                //uploadFile(file_path);
            }
        });




        dialog.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity_Profile.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    //----------------------------------
    private void selectImage() {
        final CharSequence[] items = { "Camera", "Gallery",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(
                activity);
        builder.setTitle("Select");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Camera")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Gallery")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "select"),
                            SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String url =destination.getAbsolutePath();
        Log.e("url", url);
        if(img_avata=true){
            img_profile.setImageBitmap(thumbnail);
            path_avatar =url;
            //update_avatar(path_avatar);
            File file = new File(path_avatar);
            Upload1(file);
            img_avata=false;
        }
        else {
            ic_img.setImageBitmap(thumbnail);

            file_path=url;
            TAG_FILE = new File(url);
        }

    }
    private void onSelectFromGalleryResult(Intent data) {



        Uri selectedImageUri = data.getData();
        String imagepath = getPath(selectedImageUri);
        Bitmap bitmap= BitmapFactory.decodeFile(imagepath);


        //---- avata

        if(img_avata==true){
            path_avatar =imagepath;
              img_profile.setImageBitmap(bitmap);
            //update_avatar(path_avatar);
           //upload_(path_avatar);
            File file = new File(path_avatar);
            Upload1(file);
            //uploadProfilePhoto(file);
            img_avata=false;
        }
        else{
            ic_img.setImageBitmap(bitmap);

            Log.e("path", imagepath);
            file_path=imagepath;
            TAG_FILE= new File(imagepath);
            Log.e("TAG_FILE", TAG_FILE.toString());
        }

    }
    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }



    private void upload_identity(final String phoneNumber, final String file_){

        class UploadFileToServer extends AsyncTask<Void, Integer, String> {
            ProgressDialog progressDialog;
            String STATUS="";
            String MESSAGE="";
            @Override
            protected void onPreExecute() {


                progressDialog = lib_loading.f_init(activity);
            }

            @Override
            protected void onProgressUpdate(Integer... progress) {
                // Making progress bar visible
                //progressBar.setVisibility(View.VISIBLE);

                // updating progress bar value
                //i progressBar.setProgress(progress[0]);

                // updating percentage value
                //  txtPercentage.setText(String.valueOf(progress[0]) + "%");
            }

            @Override
            protected String doInBackground(Void... params) {
                return uploadFile();
            }

            @SuppressWarnings("deprecation")
            private String uploadFile() {
                String responseString = "";

                try {

                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost(HTTP_API.USER_IDENTITY);

                    AndroidMultiPartEntity entity;
                    entity = new AndroidMultiPartEntity(
                            new AndroidMultiPartEntity.ProgressListener() {

                                @Override
                                public void transferred(long num) {
                                    publishProgress((int) ((num / (float) totalSize) * 100));
                                }
                            });

                    File sourceFile;
                    sourceFile = new File(file_);

                    // Log.i("UploadApp", "file path: " + filePath);

                    // Adding file data to http body
                    entity.addPart("file", new FileBody(sourceFile));

                    // Extra parameters if you want to pass to server
                    entity.addPart("phoneNumber",new StringBody(phoneNumber));

                    totalSize = entity.getContentLength();
                    httppost.addHeader("X-User-Id", TAG_USERID);
                    httppost.addHeader("X-Auth-Token", TAG_TOKEN);
                    httppost.setEntity(entity);
                    //Log.e("sourceFile  ----",sourceFile.getPath());
                    // Making server call
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity r_entity = response.getEntity();

                    int statusCode = response.getStatusLine().getStatusCode();
                    if (statusCode == 200) {
                        // Server response
                        responseString = EntityUtils.toString(r_entity);
                      //  String msg =EntityUtils.toString(r_entity);
                        JSONObject jsonObject = new JSONObject(responseString);
                        STATUS=jsonObject.getString("status");
                        MESSAGE=jsonObject.getString("message");
                    } else {
                        responseString = "Error occurred! Http Status Code: "
                                + statusCode;
                    }

                } catch (Exception e){
                    progressDialog.dismiss();
                    Log.e("Error", "Error");
                }catch (Throwable t) {
                    progressDialog.dismiss();
                    Log.e("Error1", "Error1");

                }
//              catch (ClientProtocolException e) {
//                    responseString = e.toString();
//                    Log.e("UploadApp", "exception: " + responseString);
//                    progressDialog.dismiss();
//                } catch (IOException e) {
//                    responseString = e.toString();
//                    Log.e("UploadApp", "exception: " + responseString);
//                    progressDialog.dismiss();
//                }

                return responseString;

            }

            @Override
            protected void onPostExecute(String result) {
              //  Log.e("---", "Response from server: " + result);
                progressDialog.dismiss();
                // showing the server response in an alert dialog
                // showAlert(result);

                Toast.makeText(Activity_Profile.this, MESSAGE, Toast.LENGTH_SHORT).show();
              //  Log.e("STATUS---------------",STATUS);
                file_path="";
                super.onPostExecute(result);

            }

        }
        if(CheckWifi3G.isConnected(activity)){
            new UploadFileToServer().execute();
        }
        else{
            Toast.makeText(Activity_Profile.this, "Error: Check Connect internet!", Toast.LENGTH_SHORT).show();
        }

    }




    //------------------


    private void Upload1(final File file){
        class Loading extends AsyncTask<String, String, String> {
            String mess = "";
            String status="";
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

                    HttpPost post = new HttpPost(HTTP_API.USER_AVATAR);
                    post.addHeader("X-User-Id", TAG_USERID);
                    post.addHeader("X-Auth-Token", TAG_TOKEN);
                    MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                    entity.addPart("file", new FileBody(file));
                    post.setEntity(entity);
                    response = client.execute(post);
                    if (response != null) {
                        HttpEntity resEntity = response.getEntity();
                        if (resEntity != null) {
                            String msg = EntityUtils.toString(resEntity);
                        //   Log.e("msg-- respost-------", msg);
                            JSONObject json = new JSONObject(msg);
                            mess = json.getString("message");
                            status = json.getString("status");


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
                    Toast.makeText(Activity_Profile.this, status+" : "+mess, Toast.LENGTH_SHORT).show();


                } catch (Exception e) {

                } catch (Throwable t) {

                }

            }

        }

        if (CheckWifi3G.isConnected(activity)) {

            new Loading().execute();
        }
        else{
            new lib_dialog().f_dialog_msg(activity, "Error Connect Internet!");
        }
    }
}
