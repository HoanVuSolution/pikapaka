package activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import adapter.adapter_user_group_other;
import api.HTTP_API;
import fragment.Fragment_MyActivity;
import hoanvusolution.pikapaka.R;
import internet.CheckWifi3G;
import item.item_user_group;
import util.Activity_Result;

/**
 * Created by MrThanhPhong on 4/2/2016.
 */
public class Activity_Other_Group extends AppCompatActivity {
    private AppCompatActivity activity;
    public  static String TAG_ID="";
    private  String TAG_STATUS="";
    private  String TAG_MESSAGE="";
    private ProgressDialog progressDialog;
    private String TAG_ACNAME="";
    private String TAG_COLOR="";

    private View ll_back;
    private ListView list;
    public ArrayList<item_user_group> arr_user = new ArrayList<item_user_group>();
    private TextView tv_ac_name;
    private RelativeLayout bg_toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_group);
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TAG_ID="";
    }

    private void init()throws Exception{;
        get_resource();
        get_Intent();
        get_members();
        onClick();
    }
    private  void get_resource()throws Exception{
        activity=this;
        ll_back =(LinearLayout)findViewById(R.id.ll_back);
        bg_toolbar =(RelativeLayout)findViewById(R.id.bg_toolbar);
        list=(ListView)findViewById(R.id.list);
        tv_ac_name=(TextView) findViewById(R.id.tv_ac_name);
    }
    private void onClick(){
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent in = new Intent(activity,Activity_Other_Activity.class);
                String name = arr_user.get(position).displayName;
                String age = arr_user.get(position).age;
                String gender = arr_user.get(position).gender;
                String imageUrl = arr_user.get(position).imageUrl;
                String rank = arr_user.get(position).rank;
                in.putExtra("ac_name",TAG_ACNAME);
                in.putExtra("color",TAG_COLOR);
                in.putExtra("name",name);
                in.putExtra("age",age);
                in.putExtra("gender",gender);
                in.putExtra("imageUrl",imageUrl);
                in.putExtra("rank",rank);
                startActivityForResult(in, Activity_Result.REQUEST_CODE_ACT);
            }
        });
    }
    private void get_members()throws Exception    {
        class Get_Group extends AsyncTask<String, String, String> {
            String _id;
            String activityType;
            String activityTypeName;
            String activityTypeColor;
            String active;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(activity, "",
                        "", true);
            }
            @Override
            protected String doInBackground(String... args) {
                try {
                    HttpClient client = new DefaultHttpClient();
                    HttpGet post = new HttpGet(HTTP_API.GET_SINGLE_ACTIVITY + "/" + TAG_ID);
                    post.addHeader("X-User-Id", Fragment_MyActivity.TAG_USERID);
                    post.addHeader("X-Auth-Token", Fragment_MyActivity.TAG_TOKEN);
                    HttpResponse response;
                    response = client.execute(post);
                    if (response != null) {
                        HttpEntity resEntity = response.getEntity();
                        if (resEntity != null) {
                            String msg = EntityUtils.toString(resEntity);
                             Log.e("group - cate", msg);
                            JSONObject jsonObject = new JSONObject(msg);
                            TAG_STATUS = jsonObject.getString("status");
                            TAG_MESSAGE = jsonObject.getString("message");
                            if (TAG_STATUS.equals("success")) {
                                JSONObject data = jsonObject.getJSONObject("data");
                                _id = data.getString("_id");
                                activityType = data.getString("activityType");
                                activityTypeName = data.getString("activityTypeName");
                                activityTypeColor = data.getString("activityTypeColor");
                                active = data.getString("active");
                                JSONArray jarr = data.getJSONArray("users");
                                for (int i = 0; i < jarr.length(); i++) {
                                    String id = jarr.getJSONObject(i).getString("_id");
                                    // JSONObject profile = jarr.getJSONObject(i).getJSONObject("profile");

                                    String firstName =  jarr.getJSONObject(i).getString("firstName");

                                    String gender =  jarr.getJSONObject(i).getString("gender");

                                    String lastName =  jarr.getJSONObject(i).getString("lastName");

                                    String dob =  jarr.getJSONObject(i).getString("dob");

                                    String displayName =  jarr.getJSONObject(i).getString("displayName");

                                    String age =  jarr.getJSONObject(i).getString("age");
                                    String rank =  jarr.getJSONObject(i).getString("rank");

                                    String imageUrl="";
                                    try {
                                        imageUrl = jarr.getJSONObject(i).getString("imageUrl");
                                    }catch(JSONException e){
                                        imageUrl="";
                                        // Log.e("Error,---","TAG_IMAGE_URL");
                                        e.getStackTrace();
                                    }
                                    item_user_group item = new item_user_group(
                                            id,
                                            firstName,
                                            gender,
                                            lastName,
                                            dob,
                                            displayName,
                                            age,
                                            imageUrl,
                                            rank
                                    );
                                    arr_user.add(item);
                                }

                            }
                        }

                        if (resEntity != null) {
                            resEntity.consumeContent();
                        }

                        client.getConnectionManager().shutdown();

                    }
                } catch (Exception e) {
                    Log.e("Error :","get members");
                    progressDialog.dismiss();

                } catch (Throwable t) {
                    Log.e("Error1 :","get members");
                    progressDialog.dismiss();

                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                progressDialog.dismiss();
                try {
                    // Log.e("group---", arr_user.size()+"");
                    if (arr_user.size()>0) {
                        adapter_user_group_other adapter = new adapter_user_group_other(activity,arr_user);

                        list.setAdapter(adapter);
                    } else {
                        Toast.makeText(activity, TAG_MESSAGE, Toast.LENGTH_SHORT).show();
                    }




                } catch (Exception e) {

                } catch (Throwable t) {

                }

            }



        }
        if(CheckWifi3G.isConnected(activity)){
            new Get_Group().execute();
        }
    }
    private void get_Intent()throws Exception {

        Bundle b =getIntent().getExtras();
        if(b!=null){
             TAG_ACNAME =b.getString("ac_name");
             TAG_COLOR =b.getString("color");
            tv_ac_name.setText(TAG_ACNAME.toUpperCase());
            if (TAG_COLOR!=null){
                bg_toolbar.setBackgroundColor(Color.parseColor(TAG_COLOR.toString()));
            }
        }
    }
}
