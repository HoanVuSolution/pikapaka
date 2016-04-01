package activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
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
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import adapter.adapter_reason;
import api.HTTP_API;
import hoanvusolution.pikapaka.R;
import image.lib_image_save_original;
import internet.CheckWifi3G;
import item.item_reason;
import loading.lib_loading;


public class Activity_Rank_Report extends AppCompatActivity {
    private AppCompatActivity activity;
    private ImageView img_avatar;
    private TextView tv_fullname,tv_age,tv_gender,tv_rank,tv_report;
    ProgressDialog   progressDialog1;
    private String SCORE="0";
    private String TAG_USERID_RANK="";
    private String TAG_FULLNAME="";
    private String TAG_IMAGE="";
    private String TAG_STATUS;
    private String TAG_MESSAGE;

    private String   TAG_USERID ="";
    private String   TAG_TOKEN ="";

    private LinearLayout ll_back;
    private String TAG_REASON="";
    private String TAG_DESCRIPTION="";

    private   EditText ed_input;
    private  TextView tv_reason;
    private Dialog dialog;
    private ProgressDialog progressDialog;
    private ArrayList<item_reason>arr_reason = new ArrayList<item_reason>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank_user);
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init()throws Exception{
        get_resource();
        get_shapreference();
        get_Intent();
        onClick();
        get_reason();
    }
    private void get_resource()throws Exception{
        activity=this;
        ll_back=(LinearLayout)findViewById(R.id.ll_back);
        img_avatar=(ImageView)findViewById(R.id.img_avatar);
        tv_fullname=(TextView)findViewById(R.id.tv_fullname);
        tv_age=(TextView)findViewById(R.id.tv_age);
        tv_gender=(TextView)findViewById(R.id.tv_gender);
        tv_rank=(TextView)findViewById(R.id.tv_rank);
        tv_report=(TextView)findViewById(R.id.tv_report);

    }
    private void get_Intent()throws  Exception{
        Bundle b =getIntent().getExtras();

        if(b!=null){
            TAG_USERID_RANK= b.getString("id_user");
            String fullname = b.getString("fullname");
            TAG_FULLNAME = b.getString("fullname");
            TAG_IMAGE= b.getString("imageUrl");
            String age = b.getString("age");
            String gender = b.getString("gender");
            tv_fullname.setText(fullname);
            tv_age.setText(age);
            tv_gender.setText(gender);
       // Toast.makeText(activity,TAG_IMAGE,Toast.LENGTH_SHORT).show();
            if(TAG_IMAGE.length()>0){

                String check = TAG_IMAGE.substring(0,3);
                if(check.equals("http")){
                    new lib_image_save_original(activity,TAG_IMAGE,img_avatar);

                }
                else{
                    TAG_IMAGE=HTTP_API.url_image+TAG_IMAGE;
                    new lib_image_save_original(activity,TAG_IMAGE,img_avatar);

                }

            }
        }
    }

    private void onClick()throws  Exception{
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_rank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dilog_rank();
            }
        });
        tv_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dilog_report() ;           }
        });
    }
    private void dilog_rank(){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(dialog.getWindow().FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_rank);

        final ImageView star1=(ImageView)dialog.findViewById(R.id.star1);
        final ImageView star2=(ImageView)dialog.findViewById(R.id.star2);
        final ImageView star3=(ImageView)dialog.findViewById(R.id.star3);
        final ImageView star4=(ImageView)dialog.findViewById(R.id.star4);
        final ImageView star5=(ImageView)dialog.findViewById(R.id.star5);
        final TextView tv_cancel =(TextView)dialog.findViewById(R.id.tv_cancel);
        final TextView tv_send =(TextView)dialog.findViewById(R.id.tv_send);



        star1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star1.setImageResource(R.drawable.star);
                star2.setImageResource(R.drawable.star_nau);
                star3.setImageResource(R.drawable.star_nau);
                star4.setImageResource(R.drawable.star_nau);
                star5.setImageResource(R.drawable.star_nau);
                SCORE="20";
            }
        });
        star2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star1.setImageResource(R.drawable.star);
                star2.setImageResource(R.drawable.star);
                star3.setImageResource(R.drawable.star_nau);
                star4.setImageResource(R.drawable.star_nau);
                star5.setImageResource(R.drawable.star_nau);
                SCORE="40";
            }
        });
        star3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star1.setImageResource(R.drawable.star);
                star2.setImageResource(R.drawable.star);
                star3.setImageResource(R.drawable.star);
                star4.setImageResource(R.drawable.star_nau);
                star5.setImageResource(R.drawable.star_nau);
                SCORE="60";
            }
        });
        star4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star1.setImageResource(R.drawable.star);
                star2.setImageResource(R.drawable.star);
                star3.setImageResource(R.drawable.star);
                star4.setImageResource(R.drawable.star);
                star5.setImageResource(R.drawable.star_nau);
                SCORE="80";
            }
        });
        star5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star1.setImageResource(R.drawable.star);
                star2.setImageResource(R.drawable.star);
                star3.setImageResource(R.drawable.star);
                star4.setImageResource(R.drawable.star);
                star5.setImageResource(R.drawable.star);
                SCORE="100";
            }
        });
        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rank_User();
                dialog.dismiss();
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    private void dilog_report(){
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(dialog.getWindow().FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_report);


        final ImageView img_select=(ImageView)dialog.findViewById(R.id.img_select);
        tv_reason =(TextView)dialog.findViewById(R.id.tv_reason);
        final TextView tv_name =(TextView)dialog.findViewById(R.id.tv_name);
        tv_name.setText(TAG_FULLNAME);
         ed_input =(EditText)dialog.findViewById(R.id.ed_input);
        final TextView tv_cancel =(TextView)dialog.findViewById(R.id.tv_cancel);
        final TextView tv_send =(TextView)dialog.findViewById(R.id.tv_send);




        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ed_input.length()>0){
                    TAG_DESCRIPTION =ed_input.getText().toString();
                   // Rank_User();
                    send_report();

                }

            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        img_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    dialogReason();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        dialog.show();
    }
    private void Rank_User(){

        class Rank extends AsyncTask<String, String, String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog1 = lib_loading.f_init(activity);

            }

            @Override
            protected String doInBackground(String... args) {
                try {
                    // Looper.prepare(); //For Preparing Message Pool for the child Thread
                    HttpClient client = new DefaultHttpClient();

                    JSONObject json = new JSONObject();

                    HttpPost post = new HttpPost(HTTP_API.RANK_USER + TAG_USERID_RANK);
                    post.addHeader("X-User-Id", TAG_USERID);
                    post.addHeader("X-Auth-Token", TAG_TOKEN);
                    json.put("score", SCORE);
                    StringEntity se = new StringEntity(json.toString());
                    se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                    post.setEntity(se);
                    HttpResponse response;
                    response = client.execute(post);

                    if (response != null) {
                        HttpEntity resEntity = response.getEntity();
                        if (resEntity != null) {
                            String msg = EntityUtils.toString(resEntity);
                        //    Log.d("msg-- cate", msg);
                            JSONObject jsonObject = new JSONObject(msg);
                            TAG_STATUS = jsonObject.getString("status");
                            TAG_MESSAGE = jsonObject.getString("message");

                        }

                        if (resEntity != null) {
                            resEntity.consumeContent();
                        }

                        client.getConnectionManager().shutdown();

                    }
                } catch (Exception e) {
                    progressDialog1.dismiss();

                } catch (Throwable t) {
                    progressDialog1.dismiss();

                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                progressDialog1.dismiss();
                try {

                    Toast.makeText(activity, TAG_MESSAGE, Toast.LENGTH_SHORT).show();
                    TAG_MESSAGE="";


                } catch (Exception e) {

                } catch (Throwable t) {

                }

            }

        }
        if(CheckWifi3G.isConnected(activity)){
            new Rank().execute();
        }
        else{
            Toast.makeText(activity, "Error : Check connect internet!", Toast.LENGTH_SHORT).show();

        }
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


    }

    private void get_reason()throws Exception{

        TAG_STATUS="";
        TAG_MESSAGE="";
        class Loading extends AsyncTask<String, String, String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
               // progressDialog = lib_loading.f_init(Activity_Rank_Report.this);
            }

            @Override
            protected String doInBackground(String... args) {
                try {

                    HttpClient client = new DefaultHttpClient();
                    HttpResponse response;

                    HttpGet post = new HttpGet(HTTP_API.GET_ALL_REASON);
                    post.addHeader("X-User-Id", TAG_USERID);
                    post.addHeader("X-Auth-Token", TAG_TOKEN);

                    response = client.execute(post);
                    if (response != null) {
                        HttpEntity resEntity = response.getEntity();
                        if (resEntity != null) {
                            String msg = EntityUtils.toString(resEntity);
                        //    Log.i("msg-- cate", msg);

                            JSONObject jsonObject = new JSONObject(msg);
                            TAG_STATUS = jsonObject.getString("status");
                            TAG_MESSAGE=jsonObject.getString("message");

                            if(TAG_STATUS.equals("success")){
                                JSONArray jsonarray = jsonObject.getJSONArray("data");
                                if(jsonarray!=null){

                                    for(int i=0;i<jsonarray.length();i++){
                                        String _id = jsonarray.getJSONObject(i).getString("_id");
                                        String name = jsonarray.getJSONObject(i).getString("name");

                                        item_reason item = new item_reason(_id,name);
                                        arr_reason.add(item);
                                    }
                                }

                            }
//                            else{
//                                Log.e("TAG_MESSAGE",TAG_MESSAGE);
//                            }


                        }

                        if (resEntity != null) {
                            resEntity.consumeContent();
                        }

                        client.getConnectionManager().shutdown();

                    }
                } catch (Exception e) {
                  //  progressDialog.dismiss();

                } catch (Throwable t) {
                   // progressDialog.dismiss();

                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
             //   progressDialog.dismiss();
                try {
                    if(TAG_STATUS.equals("success")){

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

    private void send_report(){
        TAG_STATUS="";
        TAG_MESSAGE="";
        class Loading extends AsyncTask<String, String, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = lib_loading.f_init(Activity_Rank_Report.this);
            }

            @Override
            protected String doInBackground(String... args) {
                try {

                    HttpClient client = new DefaultHttpClient();
                    HttpResponse response;
                    JSONObject json = new JSONObject();

                    HttpPost post = new HttpPost(HTTP_API.REPORT_USER+TAG_USERID_RANK);
                    post.addHeader("X-User-Id", TAG_USERID);
                    post.addHeader("X-Auth-Token", TAG_TOKEN);
                    json.put("reason",TAG_REASON);
                    json.put("description",TAG_DESCRIPTION);
                    StringEntity se = new StringEntity(json.toString());
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

                            if(TAG_STATUS.equals("success")){
                               // JSONObject data =jsonObject.getJSONObject("data");
                               // Log.e("TAG_MESSAGE",TAG_MESSAGE);
                            }
                            else{
                             //   Log.e("TAG_MESSAGE",TAG_MESSAGE);
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
                        //Log.e("TAG_MESSAGE--",TAG_MESSAGE);
                        ed_input.setText("");
                        dialog.dismiss();
                    }
                    else{

                        Toast.makeText(Activity_Rank_Report.this, TAG_MESSAGE , Toast.LENGTH_SHORT).show();
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



    private void dialogReason()throws Exception{
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(dialog.getWindow().FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_reason);

        ListView list = (ListView) dialog.findViewById(R.id.list);
        adapter_reason adapter  = new adapter_reason(activity, arr_reason);

        list.setAdapter(adapter);

        if(arr_reason.size()>0){
            dialog.show();
        }
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TAG_REASON =arr_reason.get(position)._id;
                tv_reason.setText(arr_reason.get(position).name);
                dialog.dismiss();
            }
        });



    }
}
