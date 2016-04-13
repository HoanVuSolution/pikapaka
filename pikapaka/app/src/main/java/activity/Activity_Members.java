package activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import fragment.Fragment_MyActivity;
import hoanvusolution.pikapaka.R;
import internet.CheckWifi3G;
import item.item_user_group;

/**
 * Created by MrThanhPhong on 3/18/2016.
 */
public class Activity_Members extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private AppCompatActivity activity;
    public  static String TAG_ID="";
    private  String TAG_STATUS="";
    private  String TAG_MESSAGE="";
    private ProgressDialog progressDialog;
    private RelativeLayout ll_back;
    private ListView list_members;
    public ArrayList<item_user_group> arr_user = new ArrayList<item_user_group>();
    private SwipeRefreshLayout mSwipeRefreshLayout = null;
    private adapter_members adapter=null;
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
        callRefresh();
        onClick();
    }
    private  void get_resource()throws Exception{
        activity=this;
        ll_back =(RelativeLayout)findViewById(R.id.ll_back);
        list_members=(ListView)findViewById(R.id.list_members);
        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeRefresh1);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        adapter = new adapter_members(activity,arr_user);
        list_members.setAdapter(adapter);
        mSwipeRefreshLayout.setColorSchemeColors(R.color.colorPrimary,R.color.colorPrimaryDark,R.color.colorAccent);
    }
    private void onClick(){
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void callRefresh() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                try {
                    get_members();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    @Override
    public void onRefresh() {
        if(arr_user==null){
            try {
                get_members();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            loadRefreshComplete();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        arr_user.clear();
        adapter.notifyDataSetChanged();
    }

    private void loadRefreshComplete() {
        mSwipeRefreshLayout.setRefreshing(false);
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

//                progressDialog = ProgressDialog.show(activity, "",
//                        "", true);
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
                        // Log.e("member - cate", msg);
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
//                    progressDialog.dismiss();
                    loadRefreshComplete();

                } catch (Throwable t) {
                    Log.e("Error1 :","get members");
//                    progressDialog.dismiss();
                    loadRefreshComplete();

                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
//                progressDialog.dismiss();
                loadRefreshComplete();
                try {
                    // Log.e("group---", arr_user.size()+"");
                    if (arr_user.size()>0) {
//                        adapter_members adapter = new adapter_members(activity,arr_user);
//
//                        list_members.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

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
        else {
            loadRefreshComplete();
        }
    }
}

