package activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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

import api.HTTP_API;
import hoanvusolution.pikapaka.R;
import internet.CheckWifi3G;
import loading.lib_loading;

/**
 * Created by MrThanhPhong on 3/8/2016.
 */
public class Activity_Rank_Report extends AppCompatActivity {
    private AppCompatActivity activity;
    private TextView tv_fullname,tv_age,tv_gender,tv_rank,tv_report;
    ProgressDialog   progressDialog1;
    private String SCORE="0";
    private String TAG_USERID_RANK="";
    private String TAG_STATUS;
    private String TAG_MESSAGE;

    private String   TAG_USERID ="";
    private String   TAG_TOKEN ="";

    private LinearLayout ll_back;

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
    }
    private void get_resource()throws Exception{
        activity=this;
        ll_back=(LinearLayout)findViewById(R.id.ll_back);
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
            String age = b.getString("age");
            String gender = b.getString("gender");
            tv_fullname.setText(fullname);
            tv_age.setText(age);
            tv_gender.setText(gender);
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
                Toast.makeText(Activity_Rank_Report.this, "processing....", Toast.LENGTH_SHORT).show();
            }
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
                            Log.d("msg-- cate", msg);
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

        Log.d("TAG_USERID-----", TAG_USERID);
        Log.d("token-----", TAG_TOKEN);



    }
}
