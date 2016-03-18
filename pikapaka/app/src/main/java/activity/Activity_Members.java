package activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
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

import adapter.adapter_members;
import api.HTTP_API;
import hoanvusolution.pikapaka.R;
import internet.CheckWifi3G;
import item.item_user_group;

/**
 * Created by MrThanhPhong on 3/18/2016.
 */
public class Activity_Members extends AppCompatActivity {
    private AppCompatActivity activity;
    public  static String TAG_ID="";
    private  String TAG_STATUS="";
    private  String TAG_MESSAGE="";
    private ProgressDialog progressDialog;

    private LinearLayout ll_back;
    private ListView list_members;
    public ArrayList<item_user_group> arr_user = new ArrayList<item_user_group>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members);
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init()throws Exception{;
        get_resource();
        get_members();
        onClick();
    }
    private  void get_resource()throws Exception{
        activity=this;
        ll_back =(LinearLayout)findViewById(R.id.ll_back);
        list_members=(ListView)findViewById(R.id.list_members);
    }
    private void onClick(){
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
                    post.addHeader("X-User-Id", Activity_MyActivity.TAG_USERID);
                    post.addHeader("X-Auth-Token", Activity_MyActivity.TAG_TOKEN);

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
                                    JSONObject profile = jarr.getJSONObject(i).getJSONObject("profile");

                                    String firstName = profile.getString("firstName");

                                    String gender = profile.getString("gender");

                                    String lastName = profile.getString("lastName");

                                    String dob = profile.getString("dob");

                                    String displayName = profile.getString("displayName");

                                    String age = profile.getString("age");

                                    String imageUrl="";
                                    try {
                                        imageUrl =profile.getString("imageUrl");
                                    }catch(JSONException e){
                                        imageUrl="";
                                        Log.e("Error,---","TAG_IMAGE_URL");
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
                                            imageUrl
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
                    Log.e("group---", arr_user.size()+"");


                    if (arr_user.size()>0) {
                        adapter_members adapter = new adapter_members(activity,arr_user);

                        list_members.setAdapter(adapter);
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
}

