package adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import activity.Activity_Members;
import activity.Activity_MyActivity;
import activity.Activity_Other_Activity;
import activity.Activity_Other_Group;
import activity.Activity_Rank_Report;
import api.HTTP_API;
import hoanvusolution.pikapaka.R;
import image.lib_image_save_original;
import internet.CheckWifi3G;
import item.item_chat;
import item.item_my_activity;
import item.item_search_activity;
import item.item_user_group;
import lib_horizontall_listview.HorizontalListView;
import util.Activity_Result;
public class adapter_myactivity extends BaseAdapter {
    private String TAG_STATUS = "";
    private String TAG_MESSAGE = "";
    private LayoutInflater mInflater;
    private ArrayList<item_my_activity> arItem;
    public   static  Activity_MyActivity activity;
    //View vi;
    private ProgressDialog progressDialog1;
    private boolean click = false;
    private RelativeLayout.LayoutParams lparam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
    private ArrayList<item_search_activity> arr_search = new ArrayList<item_search_activity>();
    //static  ListView list_chat_view4 =null;
    public static String TAG_ID = "";
    public static String TAG_IDTO = "";
    private String TAG_ID_SINGLE = "";
    private String TAG_TYPE = "";
    private String TAG_COLER_VIEW_CHAT="";
    private String TAG_CONTENT_CHAT="";
    private String TAG_CONVERSATION="";
    private static   ArrayList<item_chat>arr_chat= new ArrayList<item_chat>();
    private static adapater_chat adapter_ch;
    //
    //private static  LinearLayout view1, view2, view3, view4;
//    //----------------- view 2
//    protected ImageView img_view2 ;
//    protected TextView tv_old_view2 ;
//    protected TextView tv_plan_view2;
//    protected TextView tv_name_view2;
//    protected TextView tv_age_gender_view2;
//    protected TextView tv_level_view2 ;
//    protected TextView tv_venue;
//    protected LinearLayout ll_back_view ;
//    protected LinearLayout ll_prifile_view2;
//    protected LinearLayout ll_chat_view2;
//    protected LinearLayout ll_join_view2;
    //---------------------------
    //-------------------------
    //public static LinearLayout view1, view2, view3, view4;
    //public static View convertView;
    public adapter_myactivity(Activity_MyActivity activity,
                              ArrayList<item_my_activity> arItem) {
        mInflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.arItem = arItem;
        this.activity = activity;
    }
    public int getCount() {
        return arItem.size();
    }
    public item_my_activity getItem(int position) {
        return arItem.get(position);
    }
    public long getItemId(int position) {
        return position;
    }
    public int getItemViewType(int position) {
        return 1;
    }
    public int getViewTypeCount() {
        return 1;
    }
    @SuppressLint({"InflateParams", "CutPasteId"})
    public View getView(int position, View convertView, final ViewGroup parent) {
        final int pos = position;
       // convertView =conver_view;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.listview_widget_my_activity,
                        null);
                String activityType = arItem.get(pos).activityType;
                ImageView img_cate = (ImageView) convertView.findViewById(R.id.img_cate);
                TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                TextView tv_status = (TextView) convertView.findViewById(R.id.tv_status);
                ImageView img_search = (ImageView) convertView.findViewById(R.id.img_search);
                TextView tv_count_frend = (TextView) convertView.findViewById(R.id.tv_count_frend);
                tv_count_frend.setVisibility(View.GONE);
                tv_name.setText(arItem.get(pos).activityTypeName.toUpperCase());
                tv_status.setText(arItem.get(pos).status.toUpperCase());
                String activityTypeIcon =arItem.get(pos).activityTypeIcon;
                if(activityTypeIcon.length()>0){
                    activityTypeIcon=HTTP_API.url_image+activityTypeIcon;
                    new lib_image_save_original(activity,activityTypeIcon,img_cate);
                }
                //--------
            final LinearLayout view1, view2, view3, view4;
                view1 = (LinearLayout) convertView.findViewById(R.id.view1);
                view2 = (LinearLayout) convertView.findViewById(R.id.view2);
                view3 = (LinearLayout) convertView.findViewById(R.id.view3);
                view4 = (LinearLayout) convertView.findViewById(R.id.view4);
                // ***************************************
                view1.setVisibility(View.GONE);
                view2.setVisibility(View.GONE);
                view3.setVisibility(View.GONE);
                view4.setVisibility(View.GONE);
                final LinearLayout ll_backgroud = (LinearLayout) convertView.findViewById(R.id.ll_backgroud);
                final LinearLayout ll_click = (LinearLayout) convertView.findViewById(R.id.ll_click);
                ll_backgroud.setBackgroundColor(Color.parseColor(arItem.get(pos).activityTypeColor.toString()));
                //View1 item**********************************************
                final HorizontalListView mHlvCustomList_ = (HorizontalListView)convertView. findViewById(R.id.hlvCustomListWithDividerAndFadingEdge);
                //*********************************************************
                // View2 item
                final ImageView img_view2 = (ImageView) convertView.findViewById(R.id.img_view2);
                final TextView tv_old_view2 = (TextView) convertView.findViewById(R.id.tv_old_view2);
                final TextView tv_plan_view2 = (TextView) convertView.findViewById(R.id.tv_plan_view2);
                final TextView tv_name_view2 = (TextView) convertView.findViewById(R.id.tv_name_view2);
                final TextView tv_age_gender_view2 = (TextView) convertView.findViewById(R.id.tv_age_gender_view2);
                final TextView tv_level_view2 = (TextView) convertView.findViewById(R.id.tv_level_view2);
                final TextView tv_venue = (TextView) convertView.findViewById(R.id.tv_venue);
                final LinearLayout ll_back_view = (LinearLayout) convertView.findViewById(R.id.ll_back_view2);
                final LinearLayout ll_prifile_view2 = (LinearLayout) convertView.findViewById(R.id.ll_prifile_view2);
                final LinearLayout ll_chat_view2 = (LinearLayout) convertView.findViewById(R.id.ll_chat_view2);
                final LinearLayout ll_join_view2 = (LinearLayout) convertView.findViewById(R.id.ll_join_view2);


                //*********************************
                //View3 item
                final ImageView img1_view3 = (ImageView) convertView.findViewById(R.id.img1_view3);
                final ImageView img2_view3 = (ImageView) convertView.findViewById(R.id.img2_view3);
                final  LinearLayout ll_user2_view3=(LinearLayout)convertView.findViewById(R.id.ll_user2_view3);
                final TextView tv_name1_view3 = (TextView) convertView.findViewById(R.id.tv_name1_view3);
                final TextView tv_name2_view3 = (TextView) convertView.findViewById(R.id.tv_name2_view3);
                final TextView tv_old1_view3 = (TextView) convertView.findViewById(R.id.tv_old1_view3);
                final TextView tv_old2_view3 = (TextView) convertView.findViewById(R.id.tv_old2_view3);
                final TextView tv_plan_view3 = (TextView) convertView.findViewById(R.id.tv_plan_view3);
                final TextView tv_age_gender_view3 = (TextView) convertView.findViewById(R.id.tv_age_gender_view3);
                final LinearLayout ll_back_view3 = (LinearLayout) convertView.findViewById(R.id.ll_back_view3);
                final LinearLayout ll_member_view3 = (LinearLayout) convertView.findViewById(R.id.ll_member_view3);
                final LinearLayout ll_join_view3 = (LinearLayout) convertView.findViewById(R.id.ll_join_view3);
                final LinearLayout ll_chat_view3 = (LinearLayout) convertView.findViewById(R.id.ll_chat_view3);
                //***************************
                //---------------------
                // view4 item
                final LinearLayout ll_leave_view4 = (LinearLayout) convertView.findViewById(R.id.ll_leave_view4);
                final LinearLayout ll_policy_view4 = (LinearLayout) convertView.findViewById(R.id.ll_policy_view4);
                final LinearLayout ll_member_view4 = (LinearLayout) convertView.findViewById(R.id.ll_member_view4);
                final LinearLayout ll_more_view4 = (LinearLayout) convertView.findViewById(R.id.ll_more_view4);

                final EditText ed_input_chat_view4 = (EditText) convertView.findViewById(R.id.ed_input_chat_view4);
                final TextView tv_send_view4 = (TextView) convertView.findViewById(R.id.tv_send_view4);
                final  ListView list_chat_view4 = (ListView) convertView.findViewById(R.id.list_chat_view4);
                final ProgressBar probar=(ProgressBar) convertView.findViewById(R.id.probar);
                probar.setVisibility(View.GONE);
                // VIEW 5
                // view4 item
                adapter_ch = new adapater_chat(activity, arr_chat);
                list_chat_view4.setAdapter(adapter_ch);
                        list_chat_view4.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
                list_chat_view4.setStackFromBottom(true);
//                Scroll_Listview();
                //******************
                class Loading extends AsyncTask<String, String, String> {

                    String message;
                    String status;
                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        progressDialog1 = ProgressDialog.show(activity, "",
                                "", true);
                    }

                    @Override
                    protected String doInBackground(String... args) {
                        try {
                            HttpClient client = new DefaultHttpClient();
                            arr_search.clear();
                            JSONObject json = new JSONObject();
                            HttpPost post = new HttpPost(HTTP_API.SEARCH_ACTIVITY + "/" + TAG_ID);
                            post.addHeader("X-User-Id", Activity_MyActivity.TAG_USERID);
                            post.addHeader("X-Auth-Token", Activity_MyActivity.TAG_TOKEN);
                            JSONObject jlocation = new JSONObject();
                            jlocation.put("lat",Activity_MyActivity.TAG_LATITUDE);
                            jlocation.put("lng",Activity_MyActivity.TAG_LONGITUDE);
                            json.put("location",jlocation);
                            HttpResponse response;
                            StringEntity se = new StringEntity( json.toString());
                            se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                            post.setEntity(se);
                            response = client.execute(post);
                            if (response != null) {
                                HttpEntity resEntity = response.getEntity();
                                if (resEntity != null) {
                                    String msg = EntityUtils.toString(resEntity);
                                    JSONObject jsonObject = new JSONObject(msg);
                                    status = jsonObject.getString("status");
                                    message = jsonObject.getString("message");
                              //    Log.e("loadding-- ",msg);
                                    if (status.equals("success")) {
                                        //arr_search.clear();
                                        JSONArray jsonarray = jsonObject.getJSONArray("data");

                                        if (jsonarray.length() > 0) {
                                            for (int i = 0; i < jsonarray.length(); i++) {
                                                String _id=null;
                                                String activityType=null;
                                                String activityName=null;
                                                String type=null;
                                                String activityTypeColor=null;
                                                String active=null;
                                                String id_=null;
                                                String firstName=null;
                                                String gender_=null;
                                                String lastName=null;
                                                String dob=null;
                                                String displayName=null;
                                                String age=null;
                                                String imageUrl = "";
                                                String rank = "";
                                                String hasRequest="false";
                                                try {
                                                    _id = jsonarray.getJSONObject(i).getString("_id");
                                                     activityType = jsonarray.getJSONObject(i).getString("activityType");
                                                    activityName = jsonarray.getJSONObject(i).getString("activityTypeName");
                                                    type = jsonarray.getJSONObject(i).getString("type");
                                                     activityTypeColor = jsonarray.getJSONObject(i).getString("activityTypeColor");
                                                     active = jsonarray.getJSONObject(i).getString("active");

                                                    try {
                                                        if(!jsonarray.getJSONObject(i).isNull("hasRequest")){
                                                            hasRequest=jsonarray.getJSONObject(i).getString("hasRequest");
                                                        }
                                                    }catch (JSONException e){
                                                         hasRequest="false";
                                                    }
                                                    if(type.equals("request")){
                                                        JSONObject ob_user =jsonarray.getJSONObject(i).getJSONObject("user");
                                                        if(!ob_user.isNull("_id")){
                                                            id_= ob_user.getString("_id");
                                                        }
                                                        if(!ob_user.isNull("firstName")){
                                                            firstName = ob_user.getString("firstName");
                                                        }
                                                        if(!ob_user.isNull("gender")){
                                                            gender_ = ob_user.getString("gender");
                                                        }
                                                        if(!ob_user.isNull("lastName")){
                                                            lastName = ob_user.getString("lastName");
                                                        }
                                                        if(!ob_user.isNull("dob")){
                                                            dob = ob_user.getString("dob");
                                                        }
                                                        if(!ob_user.isNull("displayName")){
                                                            displayName = ob_user.getString("displayName");
                                                        }
                                                        if(!ob_user.isNull("age")){
                                                            age = ob_user.getString("age");
                                                        }
                                                        if(!ob_user.isNull("rank")){
                                                            rank=ob_user.getString("rank");
                                                        }
                                                        if(!ob_user.isNull("rank")){
                                                            imageUrl=ob_user.getString("rank");
                                                        }

                                                        item_search_activity item = new item_search_activity(
                                                                _id,
                                                                activityType,
                                                                activityName,
                                                                type,
                                                                activityTypeColor,
                                                                active,
                                                                id_,
                                                                firstName,
                                                                gender_,
                                                                lastName,
                                                                dob,
                                                                displayName,
                                                                age,
                                                                imageUrl,
                                                                rank,
                                                                hasRequest,
                                                                null
                                                        );
                                                        arr_search.add(item);
                                                    }
                                                    else{
                                                        JSONArray jar_user = jsonarray.getJSONObject(i).getJSONArray("users");
                                                                                                               item_search_activity item = new item_search_activity(
                                                                _id,
                                                                activityType,
                                                                  activityName,
                                                                type,
                                                                activityTypeColor,
                                                                active,
                                                                "",
                                                                "",
                                                                "",
                                                                "",
                                                                "",
                                                                "",
                                                                "",
                                                                "",
                                                                "",
                                                                hasRequest,
                                                                jar_user
                                                        );
                                                        arr_search.add(item);


                                                    }


                                                }catch (JSONException e){
                                                    Log.e("erre---",e.toString());
                                                }

                                            }

                                        }

                                    }
                                }

                                if (resEntity != null) {
                                    resEntity.consumeContent();
                                }
                                client.getConnectionManager().shutdown();

                            }
                        } catch (Exception e) {
                            Log.e("Error loading", "error");
                            progressDialog1.dismiss();

                        } catch (Throwable t) {
                            Log.e("Error loading", "error1");
                            progressDialog1.dismiss();

                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(String result) {
                        progressDialog1.dismiss();
                        try {
                            //Log.e("arr_search size",arr_search.size()+"");
                            if(arr_search.size()>0) {
                                view1.setVisibility(View.VISIBLE);
                            view1.setBackgroundColor(Color.parseColor(arr_search.get(0).activityTypeColor.toString()));
                             // load_slider();
                            
                                adapter_activity_request adapter = new adapter_activity_request(activity,arr_search);
                                mHlvCustomList_.setAdapter(adapter);

                            }
                            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Log.e("gridFragments---","Error");
                        } catch (Throwable t) {

                        }

                    }

                }

                 class Get_Single extends AsyncTask<String, String, String> {
                    // view2
                    String _id;
                    String minNumOfParticipants;
                    String ageTo;
                    String maxNumOfParticipants;
                    String distance;
                    String ageFrom;
                    String plan;
                    String publishToSocial;
                    String expiredHours;
                    String meetConfirm;
                    String gender;
                    String activityType;
                    String userId;
                    String status;
                    String type;
                    String activityTypeName;
                    String activityTypeColor;
                    String createdAt;
                    String active;

                    String firstName;
                    String gender_;
                    String lastName;
                    String dob;
                    String displayName;
                    String age;                   //
                    String imageUrl;                   //
                    String hasRequest;

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        // progressDialog = lib_loading.f_init(activity);
                        progressDialog1 = ProgressDialog.show(activity, "",
                                "", true);
                    }

                    @Override
                    protected String doInBackground(String... args) {
                        try {
                            HttpClient client = new DefaultHttpClient();

                            JSONObject json = new JSONObject();

                            HttpGet post = new HttpGet(HTTP_API.GET_SINGLE_ACTIVITY + "/" + TAG_ID_SINGLE);
                            post.addHeader("X-User-Id", Activity_MyActivity.TAG_USERID);
                            post.addHeader("X-Auth-Token", Activity_MyActivity.TAG_TOKEN);

                            JSONObject jlocation = new JSONObject();

                            HttpResponse response;

                            response = client.execute(post);

                            if (response != null) {
                                HttpEntity resEntity = response.getEntity();
                                if (resEntity != null) {
                                    String msg = EntityUtils.toString(resEntity);
                                    Log.e("single-- cate", msg);
                                    JSONObject jsonObject = new JSONObject(msg);
                                    TAG_STATUS = jsonObject.getString("status");
                                    TAG_MESSAGE = jsonObject.getString("message");
                                    if (TAG_STATUS.equals("success")) {
                                        //arr_search.clear();
                                        // JSONArray jsonarray = jsonObject.getJSONArray("data");
                                        JSONObject data = jsonObject.getJSONObject("data");
                                        _id = data.getString("_id");
                                        minNumOfParticipants = data.getString("minNumOfParticipants");
                                        ageTo = data.getString("ageTo");
                                        maxNumOfParticipants = data.getString("maxNumOfParticipants");
                                        distance = data.getString("distance");
                                        ageFrom = data.getString("ageFrom");
                                        plan = data.getString("plan");
                                        publishToSocial = data.getString("publishToSocial");
                                        expiredHours = data.getString("expiredHours");
                                        meetConfirm = data.getString("meetConfirm");
                                        gender = data.getString("gender");
                                        activityType = data.getString("activityType");
                                      //  userId = data.getString("userId");
                                        status = data.getString("status");
                                        type = data.getString("type");
                                        activityTypeName = data.getString("activityTypeName");
                                        activityTypeColor = data.getString("activityTypeColor");
                                        createdAt = data.getString("createdAt");
                                        active = data.getString("active");
                                        JSONObject jo = data.getJSONObject("user");
                                        firstName = jo.getString("firstName");
                                        gender_ = jo.getString("gender");
                                        lastName = jo.getString("lastName");
                                        dob = jo.getString("dob");
                                        displayName = jo.getString("displayName");
                                        age = jo.getString("age");
                                        if(jo.isNull("imageUrl")){
                                            imageUrl= jo.getString("imageUrl");
                                        }


                                        hasRequest = "hasRequest";

                                    }
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

                            if (TAG_STATUS.equals("success")) {
                                view1.setVisibility(View.GONE);
                                view2.setVisibility(View.VISIBLE);
                                view2.setBackgroundColor(Color.parseColor(activityTypeColor));
                                ll_back_view.setBackgroundColor(Color.parseColor(activityTypeColor));
                                ll_prifile_view2.setBackgroundColor(Color.parseColor(activityTypeColor));
                                ll_chat_view2.setBackgroundColor(Color.parseColor(activityTypeColor));
                                ll_join_view2.setBackgroundColor(Color.parseColor(activityTypeColor));

                                view3.setVisibility(View.GONE);
                                view4.setVisibility(View.GONE);


                                tv_name_view2.setText(firstName);
                                tv_plan_view2.setText(plan);
                                tv_old_view2.setText(age + " years old" + "," + gender_);
                                tv_age_gender_view2.setText("Demography:" + " Ages " + ageFrom + " to " + ageTo + " " + gender);


                                    if(imageUrl.length()>0){
//                                        new lib_image_save_original(activity,imageUrl,img_view2);
                                        String check = imageUrl.substring(0,3);
                                        if(check.equals("http")){
                                            new lib_image_save_original(activity,imageUrl,img_view2);

                                        }
                                        else{
                                            imageUrl=HTTP_API.url_image+imageUrl;
                                            new lib_image_save_original(activity,imageUrl,img_view2);

                                        }
                                    }


                            } else {
                                Toast.makeText(activity, TAG_MESSAGE, Toast.LENGTH_SHORT).show();
                            }


                        } catch (Exception e) {

                        } catch (Throwable t) {

                        }

                    }

                }

                class Get_Group extends AsyncTask<String, String, String> {
                    // view3
                    ArrayList<item_user_group> arr_user = new ArrayList<item_user_group>();

                    String _id;
                    String activityType;
                    String activityTypeName;
                    String activityTypeColor;
                    String active;


                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();

                        progressDialog1 = ProgressDialog.show(activity, "",
                                "", true);
                    }

                    @Override
                    protected String doInBackground(String... args) {
                        try {
                            HttpClient client = new DefaultHttpClient();

                            //JSONObject json = new JSONObject();

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
                                            //JSONObject profile = jarr.getJSONObject(i).getJSONObject("profile");
                                            String firstName =  jarr.getJSONObject(i).getString("firstName");
                                            String gender =  jarr.getJSONObject(i).getString("gender");
                                            String lastName =  jarr.getJSONObject(i).getString("lastName");
                                            String dob =  jarr.getJSONObject(i).getString("dob");
                                            String displayName =  jarr.getJSONObject(i).getString("displayName");
                                            String age =  jarr.getJSONObject(i).getString("age");
                                            String rank =  jarr.getJSONObject(i).getString("rank");
                                            String imageUrl="";
                                            if(!jarr.getJSONObject(i).isNull("imageUrl")){
                                                imageUrl = jarr.getJSONObject(i).getString("imageUrl");
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
                            Log.e("Error---","1");
                            progressDialog1.dismiss();

                        } catch (Throwable t) {
                            progressDialog1.dismiss();
                            Log.e("Error--","2");
                        }
                        return null;
                    }
                    @Override
                    protected void onPostExecute(String result) {
                        progressDialog1.dismiss();
                        try {
                            Log.e("group---", arr_user.size()+"");
                            if (TAG_STATUS.equals("success")) {
                                view1.setVisibility(View.GONE);
                                view2.setVisibility(View.GONE);
                                view4.setVisibility(View.GONE);
                                view3.setVisibility(View.VISIBLE);
                                view3.setBackgroundColor(Color.parseColor(activityTypeColor));
                                ll_back_view3.setBackgroundColor(Color.parseColor(activityTypeColor));
                                ll_member_view3.setBackgroundColor(Color.parseColor(activityTypeColor));
                                ll_join_view3.setBackgroundColor(Color.parseColor(activityTypeColor));
                                ll_chat_view3.setBackgroundColor(Color.parseColor(activityTypeColor));
                                tv_plan_view3.setText("");
                                if (arr_user.size() >= 2) {
                                    tv_name1_view3.setText(arr_user.get(0).lastName);
                                    tv_name2_view3.setText(arr_user.get(1).lastName);
                                    tv_old1_view3.setText(arr_user.get(0).age + " years old, " + arr_user.get(0).gender);
                                    tv_old2_view3.setText(arr_user.get(1).age + " years old, " + arr_user.get(1).gender);
                                    ll_user2_view3.setVisibility(View.VISIBLE);
                                    //img1_view3
                                    String urlImage1 =arr_user.get(0).imageUrl;

                                    String urlImage2 =arr_user.get(1).imageUrl;

                                    if(urlImage1.length()>0){
//                                        new lib_image_save_original(activity,urlImage1,img1_view3);
                                        String check = urlImage1.substring(0,3);
                                        if(check.equals("http")){
                                            new lib_image_save_original(activity,urlImage1,img1_view3);

                                        }
                                        else{
                                            urlImage1=HTTP_API.url_image+urlImage1;
                                            new lib_image_save_original(activity,urlImage1,img1_view3);

                                        }
                                    }
                                    if(urlImage2.length()>0){
//                                        new lib_image_save_original(activity,urlImage2,img2_view3);
                                        String check = urlImage2.substring(0,3);
                                        if(check.equals("http")){
                                            new lib_image_save_original(activity,urlImage2,img2_view3);

                                        }
                                        else{
                                            urlImage2=HTTP_API.url_image+urlImage2;
                                            new lib_image_save_original(activity,urlImage2,img2_view3);

                                        }
                                    }

                                } else {
                                    tv_name1_view3.setText(arr_user.get(0).lastName);
                                    tv_old1_view3.setText(arr_user.get(0).age + " years old, " + arr_user.get(0).gender);
                                    ll_user2_view3.setVisibility(View.INVISIBLE);

                                    String urlImage1 =arr_user.get(0).imageUrl;
                                    if(urlImage1.length()>0){
                                       // new lib_image_save_original(activity,urlImage1,img1_view3);
                                        String check = urlImage1.substring(0,3);
                                        if(check.equals("http")){
                                            new lib_image_save_original(activity,urlImage1,img1_view3);

                                        }
                                        else{
                                            urlImage1=HTTP_API.url_image+urlImage1;
                                            new lib_image_save_original(activity,urlImage1,img1_view3);

                                        }
                                    }
                                }
                            } else {
                                Toast.makeText(activity, TAG_MESSAGE, Toast.LENGTH_SHORT).show();
                            }

                            img1_view3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String id_user =arr_user.get(0)._id;
                                    String fullname = arr_user.get(0).displayName;
                                    String age =arr_user.get(0).age;
                                    String gender =arr_user.get(0).gender;
                                    String imageUrl =arr_user.get(0).imageUrl;

                                    Intent in = new Intent(activity, Activity_Rank_Report.class);
                                    in.putExtra("id_user",id_user);
                                    in.putExtra("fullname",fullname);
                                    in.putExtra("age",age);
                                    in.putExtra("gender",gender);
                                    in.putExtra("imageUrl",imageUrl);
                                    activity.startActivityForResult(in, Activity_Result.REQUEST_CODE_ACT);
                                }
                            });
                            img2_view3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String id_user =arr_user.get(1)._id;
                                    String fullname = arr_user.get(1).displayName;
                                    String age =arr_user.get(1).age;
                                    String gender =arr_user.get(1).gender;
                                    String imageUrl =arr_user.get(1).imageUrl;

                                    Intent in = new Intent(activity, Activity_Rank_Report.class);
                                    in.putExtra("id_user",id_user);
                                    in.putExtra("fullname",fullname);
                                    in.putExtra("age",age);
                                    in.putExtra("gender",gender);
                                    in.putExtra("imageUrl",imageUrl);
                                    activity.startActivityForResult(in, Activity_Result.REQUEST_CODE_ACT);
                                }
                            });


                        } catch (Exception e) {

                        } catch (Throwable t) {

                        }

                    }

                }

                class Leave_Group extends AsyncTask<String, String, String> {
                    ProgressDialog progressDialog;

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        // progressDialog = lib_loading.f_init(activity);
                        progressDialog = ProgressDialog.show(activity, "",
                                "", true);
                    }

                    @Override
                    protected String doInBackground(String... args) {
                        try {
                            // Looper.prepare(); //For Preparing Message Pool for the child Thread
                            HttpClient client = new DefaultHttpClient();

                            JSONObject json = new JSONObject();

                            HttpPost post = new HttpPost(HTTP_API.LEAVE_GROUP);
                            post.addHeader("X-User-Id", Activity_MyActivity.TAG_USERID);
                            post.addHeader("X-Auth-Token", Activity_MyActivity.TAG_TOKEN);

                            json.put("groupId", TAG_ID);
                            json.put("userId", Activity_MyActivity.TAG_USERID);
                            StringEntity se = new StringEntity(json.toString());
                            se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                            post.setEntity(se);

                            HttpResponse response;
                            response = client.execute(post);

                            if (response != null) {
                                HttpEntity resEntity = response.getEntity();
                                if (resEntity != null) {
                                    String msg = EntityUtils.toString(resEntity);
                                    Log.v("msg-- cate", msg);
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
//                            Log.e("TAG_STATUS----", TAG_STATUS);
//                            Log.e("TAG_MESSAGE---", TAG_MESSAGE);

                            if (TAG_STATUS.equals("success")) {
                                Toast.makeText(activity, TAG_MESSAGE, Toast.LENGTH_SHORT).show();
                                activity.get_myactivity();
                            } else {
                                Toast.makeText(activity, TAG_MESSAGE, Toast.LENGTH_SHORT).show();
                            }


                        } catch (Exception e) {

                        } catch (Throwable t) {

                        }

                    }

                }

                class Chat_Group extends AsyncTask<String, String, String> {

                    //ProgressDialog progressDialog;

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
//                        progressDialog = ProgressDialog.show(activity, "",
//                                "", true);
                    }

                    @Override
                    protected String doInBackground(String... args) {
                        try {
                            HttpClient client = new DefaultHttpClient();

                            JSONObject json = new JSONObject();

                            HttpPost post = new HttpPost(HTTP_API.CHAT_GROUP);
                            post.addHeader("X-User-Id", Activity_MyActivity.TAG_USERID);
                            post.addHeader("X-Auth-Token", Activity_MyActivity.TAG_TOKEN);

                            json.put("toGroup", TAG_ID);
                            json.put("content", TAG_CONTENT_CHAT);
                            StringEntity se = new StringEntity(json.toString());
                            se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                            post.setEntity(se);

                            HttpResponse response;
                            response = client.execute(post);

                            if (response != null) {
                                HttpEntity resEntity = response.getEntity();
                                if (resEntity != null) {
                                    String msg = EntityUtils.toString(resEntity);
                                    Log.e("msg-- chat", msg);
                                    JSONObject jsonObject = new JSONObject(msg);
                                    TAG_STATUS = jsonObject.getString("status");
                                    TAG_MESSAGE = jsonObject.getString("message");
                                    JSONObject jdata = jsonObject.getJSONObject("data");
                                    JSONObject jlastmsg = jdata.getJSONObject("lastMessage");
                                    TAG_CONVERSATION =jlastmsg.getString("conversationId");
                                }

                                if (resEntity != null) {
                                    resEntity.consumeContent();
                                }

                                client.getConnectionManager().shutdown();

                            }
                        } catch (Exception e) {
                         //   progressDialog.dismiss();

                        } catch (Throwable t) {
                          //  progressDialog.dismiss();

                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(String result) {
                        //progressDialog.dismiss();
                        try {

                            if (TAG_STATUS.equals("success")) {
                                TAG_CONTENT_CHAT = "";
                                ed_input_chat_view4.setText("");
                                activity.mSocket.emit("join",TAG_CONVERSATION);
                            } else {
                                Toast.makeText(activity, TAG_MESSAGE, Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {

                        } catch (Throwable t) {

                        }

                    }

                }


                class Load_ListChat extends AsyncTask<String, String, String> {

                   // ProgressDialog progressDialog;


                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        // progressDialog = lib_loading.f_init(activity);
//                        progressDialog = ProgressDialog.show(activity, "",
//                                "", true);
                        probar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    protected String doInBackground(String... args) {
                        try {
                            arr_chat.clear();
                            adapter_ch.notifyDataSetChanged();
                            HttpClient client = new DefaultHttpClient();
                            JSONObject json = new JSONObject();
                            HttpGet post = new HttpGet(HTTP_API.CHAT_LOADLIST + TAG_CONVERSATION);
                            post.addHeader("X-User-Id", Activity_MyActivity.TAG_USERID);
                            post.addHeader("X-Auth-Token", Activity_MyActivity.TAG_TOKEN);
                            HttpResponse response;
                            response = client.execute(post);
                            if (response != null) {
                                HttpEntity resEntity = response.getEntity();
                                if (resEntity != null) {
                                    String msg = EntityUtils.toString(resEntity);
                                   /// Log.e("msg-- listchat", msg);
                                    JSONObject jsonObject = new JSONObject(msg);
                                    TAG_STATUS = jsonObject.getString("status");
                                    TAG_MESSAGE = jsonObject.getString("message");
//                                    activity.mSocket.emit("join",TAG_CONVERSATION);
                                    JSONObject jdata = jsonObject.getJSONObject("data");
                                    JSONArray message = jdata.getJSONArray("messages");

                                    for (int i = 0; i < message.length(); i++) {
                                        String _id = message.getJSONObject(i).getString("_id");
                                        String conversationId = message.getJSONObject(i).getString("conversationId");
                                        JSONObject fromUser = message.getJSONObject(i).getJSONObject("fromUser");
                                        String id_user = fromUser.getString("_id");
                                       // JSONObject profile = fromUser.getJSONObject("profile");
                                        String firstName = fromUser.getString("firstName");
                                        String gender = fromUser.getString("gender");
                                        String lastName = fromUser.getString("lastName");
                                        String content = message.getJSONObject(i).getString("content");
                                        String imageUrl="";
                                        try {
                                            imageUrl = fromUser.getString("imageUrl");
                                        }catch (JSONException e){
                                            imageUrl="";
                                        }
                                        item_chat item = new item_chat(_id, conversationId,id_user, firstName, gender, lastName,imageUrl, content);
                                        arr_chat.add(item);
                                    }

                                }

                                if (resEntity != null) {
                                    resEntity.consumeContent();
                                }

                                client.getConnectionManager().shutdown();

                            }
                        } catch (Exception e) {
                            //progressDialog.dismiss();
                            Log.e("Error","get list chat");

                        } catch (Throwable t) {
                          //  progressDialog.dismiss();

                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(String result) {
                        probar.setVisibility(View.GONE);
                        try {
                            if (arr_chat.size() > 0) {
                              //  Toast.makeText(activity,arr_chat.size()+"",Toast.LENGTH_SHORT).show();
                                adapter_ch = new adapater_chat(activity, arr_chat);
                                list_chat_view4.setAdapter(adapter_ch);
                                //adapter_ch.notifyDataSetChanged();
//                                Scroll_Listview();

                            }
//                            else {
//                                Toast.makeText(activity, TAG_MESSAGE, Toast.LENGTH_SHORT).show();
//                            }


                        } catch (Exception e) {

                        } catch (Throwable t) {

                        }

                    }

                }
                class Get_Conversations extends AsyncTask<String, String, String> {
                   // ProgressDialog progressDialog;

                    String _id;
                    String type;
                    String groupId;
                    String users;
                    String mutedUsers;
                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
//                        progressDialog = ProgressDialog.show(activity, "",
//                                "", true);
                        probar.setVisibility(View.VISIBLE);
                    }
                    @Override
                    protected String doInBackground(String... args) {
                        try {
                            HttpClient client = new DefaultHttpClient();
                            JSONObject json = new JSONObject();
                            HttpGet post = new HttpGet(HTTP_API.CHAT_GET_CONVERSATIONS + TAG_ID);
                            post.addHeader("X-User-Id", Activity_MyActivity.TAG_USERID);
                            post.addHeader("X-Auth-Token", Activity_MyActivity.TAG_TOKEN);
                            HttpResponse response;
                            response = client.execute(post);
                            if (response != null) {
                                HttpEntity resEntity = response.getEntity();
                                if (resEntity != null) {
                                    String msg = EntityUtils.toString(resEntity);
                                    JSONObject jsonObject = new JSONObject(msg);
                                    TAG_STATUS = jsonObject.getString("status");
                                    TAG_MESSAGE = jsonObject.getString("message");
                                    JSONArray jarr = jsonObject.getJSONArray("data");
                                   // Log.e("TAG_CONVERSATION group",jarr.toString());
                                    for (int i = 0; i < jarr.length(); i++) {
                                        _id = jarr.getJSONObject(i).getString("_id");
                                        type = jarr.getJSONObject(i).getString("type");
                                        groupId = jarr.getJSONObject(i).getString("groupId");
                                        users = "";
                                        mutedUsers = "";
                                        JSONObject last_msg = jarr.getJSONObject(i).getJSONObject("lastMessage");
                                        TAG_CONVERSATION = last_msg.getString("conversationId");
                                    }

                                }

                                if (resEntity != null) {
                                    resEntity.consumeContent();
                                }

                                client.getConnectionManager().shutdown();

                            }
                        } catch (Exception e) {

                            Log.e("Error","get conversation");
                        } catch (Throwable t) {

                        }
                        return null;
                    }
                    @Override
                    protected void onPostExecute(String result) {
                        probar.setVisibility(View.GONE);
                        try {
                            if (TAG_STATUS.equals("success")) {
                                Log.e("TAG_CONVERSATION",TAG_CONVERSATION);
                                if (TAG_CONVERSATION.length() > 0) {
                                    activity.mSocket.emit("join",TAG_CONVERSATION);
                                    new Load_ListChat().execute();
                                }

                            }
//                            else {
//                                Toast.makeText(activity, TAG_MESSAGE, Toast.LENGTH_SHORT).show();
//                            }


                        } catch (Exception e) {

                        } catch (Throwable t) {

                        }
                    }

                }
                ll_click.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TAG_ID = arItem.get(pos)._id;
                        TAG_TYPE = arItem.get(pos).type;
                        Log.e("TAG_ID----",TAG_ID);
                       // Toast.makeText(activity, TAG_TYPE, Toast.LENGTH_SHORT).show();
                        TAG_COLER_VIEW_CHAT = arItem.get(pos).activityTypeColor;
                        if (click == false) {
                            if (TAG_TYPE.equals("request")) {
                                if (CheckWifi3G.isConnected(activity)) {
                                    new Loading().execute();
                                } else {
                                    Toast.makeText(activity, "Error Connect Internet!", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                if (CheckWifi3G.isConnected(activity)) {
                                    new Get_Group().execute();
                                } else {
                                    Toast.makeText(activity, "Error Connect Internet!", Toast.LENGTH_SHORT).show();
                                }
                            }


                            //----------


                            click = true;
                        } else {
                            view1.setVisibility(View.GONE);
                            view2.setVisibility(View.GONE);
                            view3.setVisibility(View.GONE);
                            view4.setVisibility(View.GONE);
                            arr_chat.clear();
                            adapter_ch.notifyDataSetChanged();
                            click = false;

                        }
                    }
                });



                //--********************************


                //*********************

 //-------------nview1 click
//                img1_view1.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        TAG_ID=arItem.get(pos)._id;
//                        TAG_ID_SINGLE = arr_search.get(0)._id;
//                        Log.e("TAG_ID_SINGLE---",TAG_ID_SINGLE);
//                        TAG_COLER_VIEW_CHAT = arItem.get(pos).activityTypeColor;
//                        TAG_ID_USER_CHAT_PPRITE= arr_search.get(0).id_user;
//
//                        String type = arr_search.get(0).type;
//                        Log.e("type",type);
//                        if (CheckWifi3G.isConnected(activity)) {
//                            if(type.equals("request")){
//                                new Get_Single().execute();
//                            }
//                            else{
//                                TAG_ID=arr_search.get(0)._id;
//                                new Get_Group().execute();
//                            }
//
//                        } else {
//                            Toast.makeText(activity, "Error Connect Internet!", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//                img2_view1.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        TAG_ID=arItem.get(pos)._id;
//                        //TAG_ID_SINGLE = arr_search.get(1).id_user;
//                        TAG_ID_SINGLE = arr_search.get(1)._id;
//                        TAG_COLER_VIEW_CHAT = arItem.get(pos).activityTypeColor;
//                        TAG_ID_USER_CHAT_PPRITE= arr_search.get(1).id_user;
//                        if (CheckWifi3G.isConnected(activity)) {
//
//                            new Get_Single().execute();
//                        } else {
//                            Toast.makeText(activity, "Error Connect Internet!", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//                img3_view1.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        TAG_ID=arItem.get(pos)._id;
//                       // TAG_ID_SINGLE = arr_search.get(2).id_user;
//                        TAG_ID_SINGLE = arr_search.get(2)._id;
//                        TAG_COLER_VIEW_CHAT = arItem.get(pos).activityTypeColor;
//                        TAG_ID_USER_CHAT_PPRITE= arr_search.get(2).id_user;
//                        if (CheckWifi3G.isConnected(activity)) {
//
//                            new Get_Single().execute();
//                        } else {
//                            Toast.makeText(activity, "Error Connect Internet!", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//                icon_hasRequest1_view1.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        TAG_ID=arItem.get(pos)._id;
//                        //TAG_IDTO = arr_search.get(0).id_user;
//                        TAG_IDTO = arr_search.get(0)._id;
//                        TAG_COLER_VIEW_CHAT = arItem.get(pos).activityTypeColor;
//                        String check = arr_search.get(0).hasRequest;
//                        Log.i("TAG_IDTO", TAG_IDTO + "");
//                        if (check.equals("false")) {
//                            Log.i("Send_Activity", "Send_Activity");
//                            if (CheckWifi3G.isConnected(activity)) {
//
//                                new Send_Activity().execute();
//                            } else {
//                                Toast.makeText(activity, "Error Connect Internet!", Toast.LENGTH_SHORT).show();
//                            }
//                        } else {
//                            Log.i("Accept_Activity", "Accept_Activity");
//                            if (CheckWifi3G.isConnected(activity)) {
//
//                                new Accept_Activity().execute();
//                            } else {
//                                Toast.makeText(activity, "Error Connect Internet!", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    }
//                });
//                icon_hasRequest2_view1.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        TAG_ID=arItem.get(pos)._id;
//                        TAG_IDTO = arr_search.get(1)._id;
//                        TAG_COLER_VIEW_CHAT = arItem.get(pos).activityTypeColor;
//                        //boolean check = Boolean.getBoolean(arr_search.get(1).hasRequest);
//                        String check = arr_search.get(1).hasRequest;
//                        if (check.equals("false")) {
//                            if (CheckWifi3G.isConnected(activity)) {
//
//                                new Send_Activity().execute();
//                            } else {
//                                Toast.makeText(activity, "Error Connect Internet!", Toast.LENGTH_SHORT).show();
//                            }
//                        } else {
//                            if (CheckWifi3G.isConnected(activity)) {
//
//                                new Accept_Activity().execute();
//                            } else {
//                                Toast.makeText(activity, "Error Connect Internet!", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    }
//                });
//                icon_hasRequest3_view1.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        TAG_ID=arItem.get(pos)._id;
//                        TAG_IDTO = arr_search.get(2)._id;
//                        String check = arr_search.get(1).hasRequest;
//                        TAG_COLER_VIEW_CHAT = arItem.get(pos).activityTypeColor;
//                        if (check.equals("false")) {
//                            if (CheckWifi3G.isConnected(activity)) {
//
//                                new Send_Activity().execute();
//                            } else {
//                                Toast.makeText(activity, "Error Connect Internet!", Toast.LENGTH_SHORT).show();
//                            }
//                        } else {
//                            if (CheckWifi3G.isConnected(activity)) {
//
//                                new Accept_Activity().execute();
//                            } else {
//                                Toast.makeText(activity, "Error Connect Internet!", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    }
//                });
                //---------------------------------------------------------------------
                // view2 click-------------------
                ll_back_view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        view1.setVisibility(View.VISIBLE);
                        view2.setVisibility(View.GONE);
                        view3.setVisibility(View.GONE);
                        view4.setVisibility(View.GONE);
                    }

                });

                // view 3 click----------------------------------------
                ll_back_view3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        view1.setVisibility(View.GONE);
                        view2.setVisibility(View.GONE);
                        view3.setVisibility(View.GONE);
                        view4.setVisibility(View.GONE);
                    }

                });
                ll_chat_view3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TAG_COLER_VIEW_CHAT = arItem.get(pos).activityTypeColor;
                        TAG_ID=arItem.get(pos)._id;
                        view1.setVisibility(View.GONE);
                        view2.setVisibility(View.GONE);
                        view3.setVisibility(View.GONE);
                        view4.setVisibility(View.VISIBLE);
                        view4.setBackgroundColor(Color.parseColor(TAG_COLER_VIEW_CHAT.toString()));
                        ll_leave_view4.setBackgroundColor(Color.parseColor(TAG_COLER_VIEW_CHAT.toString()));
                        ll_policy_view4.setBackgroundColor(Color.parseColor(TAG_COLER_VIEW_CHAT.toString()));
                        ll_member_view4.setBackgroundColor(Color.parseColor(TAG_COLER_VIEW_CHAT.toString()));
                        ll_more_view4.setBackgroundColor(Color.parseColor(TAG_COLER_VIEW_CHAT.toString()));
                        Log.e("TAG_ID--",TAG_ID);

                        new Get_Conversations().execute();
                    }
                });

                ll_member_view3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Activity_Members.TAG_ID =arItem.get(pos)._id;
                        Intent in = new Intent(activity, Activity_Members.class);
                        activity.startActivityForResult(in,Activity_Result.REQUEST_CODE_ACT);
                    }
                });


                // view 4 click---------------------------------------

                tv_send_view4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TAG_CONTENT_CHAT = ed_input_chat_view4.getText().toString();
//                        if (TAG_CONTENT_CHAT.length() == 0) {
//                            Toast.makeText(activity, "Please input content", Toast.LENGTH_SHORT).show();
//                        } else {
//
//
//
//                        }
                        if(TAG_CONTENT_CHAT.length() > 0){
                            new Chat_Group().execute();
                        }
                    }
                });

                ll_leave_view4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog dialog = new Dialog(activity);
                        dialog.requestWindowFeature(dialog.getWindow().FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.dialog_notify_leave);


                        final TextView tv_no =(TextView)dialog.findViewById(R.id.tv_no);
                        final TextView tv_yes =(TextView)dialog.findViewById(R.id.tv_yes);

                        tv_yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(CheckWifi3G.isConnected(activity)){
                                    new Leave_Group().execute();
                                    dialog.dismiss();
                                }
                                else{
                                    Toast.makeText(activity, "Error connect internet!", Toast.LENGTH_SHORT).show();
                                }


                            }
                        });
                        tv_no.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

                        dialog.show();
                    }
                });
//                list_chat_view4.setOnTouchListener(new View.OnTouchListener() {
//                    @Override
//                    public boolean onTouch(View v, MotionEvent event) {
//                        return false;
//                    }
//                });
                ll_member_view4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Activity_Members.TAG_ID =arItem.get(pos)._id;
                        Intent in = new Intent(activity, Activity_Members.class);
                        activity.startActivityForResult(in,Activity_Result.REQUEST_CODE_ACT);
                    }
                });

                /// view 5 click

                //***************************************************************

            }
        return convertView;
    }
    // Socket
    public static void add_message(item_chat item){
        arr_chat.add(item);
       adapter_ch.notifyDataSetChanged();
        //Log.e("new item =",item.firstName+"- "+item.content);
        //Scroll_Listview();
    }

    public static  void get_single(final String id_single){
        class Get_Single extends AsyncTask<String, String, String> {
            ProgressDialog progressDialog1;
         String   TAG_STATUS = "";
            String  TAG_MESSAGE = "";
            // view2
            String _id;
            String minNumOfParticipants;
            String ageTo;
            String maxNumOfParticipants;
            String distance;
            String ageFrom;
            String plan;
            String publishToSocial;
            String expiredHours;
            String meetConfirm;
            String gender;
            String activityType;
            String userId;
            String status;
            String type;
            String activityTypeName;
            String activityTypeColor;
            String createdAt;
            String active;

            String firstName;
            String gender_;
            String lastName;
            String dob;
            String displayName;
            String age;                   //
            String imageUrl;                   //
            String rank;                   //
            String hasRequest;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // progressDialog = lib_loading.f_init(activity);
                progressDialog1 = ProgressDialog.show(activity, "",
                        "", true);
            }

            @Override
            protected String doInBackground(String... args) {
                try {
                    HttpClient client = new DefaultHttpClient();

                    JSONObject json = new JSONObject();

                    //HttpGet post = new HttpGet(HTTP_API.GET_SINGLE_ACTIVITY + "/" + TAG_ID_SINGLE);
                    HttpGet post = new HttpGet(HTTP_API.GET_SINGLE_ACTIVITY + "/" + id_single);
                    post.addHeader("X-User-Id", Activity_MyActivity.TAG_USERID);
                    post.addHeader("X-Auth-Token", Activity_MyActivity.TAG_TOKEN);

                    JSONObject jlocation = new JSONObject();

                    HttpResponse response;

                    response = client.execute(post);

                    if (response != null) {
                        HttpEntity resEntity = response.getEntity();
                        if (resEntity != null) {
                            String msg = EntityUtils.toString(resEntity);
                            Log.e("single-- cate", msg);
                            JSONObject jsonObject = new JSONObject(msg);
                            TAG_STATUS = jsonObject.getString("status");
                            TAG_MESSAGE = jsonObject.getString("message");
                            if (TAG_STATUS.equals("success")) {
                                //arr_search.clear();
                                // JSONArray jsonarray = jsonObject.getJSONArray("data");
                                JSONObject data = jsonObject.getJSONObject("data");
                                _id = data.getString("_id");
                                minNumOfParticipants = data.getString("minNumOfParticipants");
                                ageTo = data.getString("ageTo");
                                maxNumOfParticipants = data.getString("maxNumOfParticipants");
                                distance = data.getString("distance");
                                ageFrom = data.getString("ageFrom");
                                plan = data.getString("plan");
                                publishToSocial = data.getString("publishToSocial");
                                expiredHours = data.getString("expiredHours");
                                meetConfirm = data.getString("meetConfirm");
                                gender = data.getString("gender");
                                activityType = data.getString("activityType");
                                //  userId = data.getString("userId");
                                status = data.getString("status");
                                type = data.getString("type");
                                activityTypeName = data.getString("activityTypeName");
                                activityTypeColor = data.getString("activityTypeColor");
                                createdAt = data.getString("createdAt");
                                active = data.getString("active");
                                JSONObject jo = data.getJSONObject("user");
                                firstName = jo.getString("firstName");
                                gender_ = jo.getString("gender");
                                lastName = jo.getString("lastName");
                                dob = jo.getString("dob");
                                displayName = jo.getString("displayName");
                                rank = jo.getString("rank");
                                age = jo.getString("age");
                                if(jo.isNull("imageUrl")){
                                    imageUrl= jo.getString("imageUrl");
                                }


                                hasRequest = "hasRequest";

                            }
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
                    if (TAG_STATUS.equals("success")) {
                        dialog_activity_request(imageUrl,firstName,age,gender,plan,ageFrom,ageTo,gender_,rank,activityTypeColor,activityTypeName,displayName);
//                        view1.setVisibility(View.GONE);
//                        view2.setVisibility(View.VISIBLE);
//                        view2.setBackgroundColor(Color.parseColor(activityTypeColor));
//                        ll_back_view.setBackgroundColor(Color.parseColor(activityTypeColor));
//                        ll_prifile_view2.setBackgroundColor(Color.parseColor(activityTypeColor));
//                        ll_chat_view2.setBackgroundColor(Color.parseColor(activityTypeColor));
//                        ll_join_view2.setBackgroundColor(Color.parseColor(activityTypeColor));
//
//                        view3.setVisibility(View.GONE);
//                        view4.setVisibility(View.GONE);
//
//
//                        tv_name_view2.setText(firstName);
//                        tv_plan_view2.setText(plan);
//                        tv_old_view2.setText(age + " years old" + "," + gender_);
//                        tv_age_gender_view2.setText("Demography:" + " Ages " + ageFrom + " to " + ageTo + " " + gender);
//
//
//                        if(imageUrl.length()>0){
////                                        new lib_image_save_original(activity,imageUrl,img_view2);
//                            String check = imageUrl.substring(0,3);
//                            if(check.equals("http")){
//                                new lib_image_save_original(activity,imageUrl,img_view2);
//
//                            }
//                            else{
//                                imageUrl=HTTP_API.url_image+imageUrl;
//                                new lib_image_save_original(activity,imageUrl,img_view2);
//
//                            }
//                        }
//
//
//                    } else {
//                        Toast.makeText(activity, TAG_MESSAGE, Toast.LENGTH_SHORT).show();
//                    }

                    }
                } catch (Exception e) {

                } catch (Throwable t) {

                }

            }

        }
        if(CheckWifi3G.isConnected(activity)){
            new Get_Single().execute();
        }
    }
    public static void get_group(final String id_group){
        class Get_Group extends AsyncTask<String, String, String> {
            ProgressDialog progressDialog1;
            String   TAG_STATUS = "";
            String  TAG_MESSAGE = "";
            // view3
            ArrayList<item_user_group> arr_user = new ArrayList<item_user_group>();

            String _id;
            String activityType;
            String activityTypeName;
            String activityTypeColor;
            String active;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog1 = ProgressDialog.show(activity, "",
                        "", true);
            }

            @Override
            protected String doInBackground(String... args) {
                try {
                    HttpClient client = new DefaultHttpClient();
                    arr_user.clear();
                    //JSONObject json = new JSONObject();

                    HttpGet post = new HttpGet(HTTP_API.GET_SINGLE_ACTIVITY + "/" + id_group);
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
                                //JSONArray jarr = data.getJSONArray("users");
                                JSONArray jarr = data.getJSONArray("users");
                                for (int i = 0; i < jarr.length(); i++) {
                                    String id = jarr.getJSONObject(i).getString("_id");
                                    //JSONObject profile = jarr.getJSONObject(i).getJSONObject("profile");
                                    String firstName =  jarr.getJSONObject(i).getString("firstName");
                                    String gender =  jarr.getJSONObject(i).getString("gender");
                                    String lastName =  jarr.getJSONObject(i).getString("lastName");
                                    String dob =  jarr.getJSONObject(i).getString("dob");
                                    String displayName =  jarr.getJSONObject(i).getString("displayName");
                                    String age =  jarr.getJSONObject(i).getString("age");
                                    String rank =  jarr.getJSONObject(i).getString("rank");
                                    String imageUrl="";
                                    if(!jarr.getJSONObject(i).isNull("imageUrl")){
                                        imageUrl = jarr.getJSONObject(i).getString("imageUrl");
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
                    Log.e("Error---","1");
                    progressDialog1.dismiss();

                } catch (Throwable t) {
                    progressDialog1.dismiss();
                    Log.e("Error--","2");

                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                progressDialog1.dismiss();
                try {
                    Log.e("group---", arr_user.size()+"");
                 //   Log.e("TAG_STATUS---",TAG_STATUS);
                    if (arr_user.size()>0) {
//                        final  LinearLayout view1 = (LinearLayout) conver_view.findViewById(R.id.view1);
//                        final LinearLayout view2 = (LinearLayout) conver_view.findViewById(R.id.view2);
//                        final LinearLayout view3 = (LinearLayout) conver_view.findViewById(R.id.view3);
//                        final LinearLayout view4 = (LinearLayout) conver_view.findViewById(R.id.view4);

//                        view1.setVisibility(View.GONE);
//                        view2.setVisibility(View.GONE);
//                        view4.setVisibility(View.GONE);
//
//                        view3.setVisibility(View.VISIBLE);
//                        view3.setBackgroundColor(Color.parseColor(activityTypeColor));
//                        ll_back_view3.setBackgroundColor(Color.parseColor(activityTypeColor));
//                        ll_member_view3.setBackgroundColor(Color.parseColor(activityTypeColor));
//                        ll_join_view3.setBackgroundColor(Color.parseColor(activityTypeColor));
//                        ll_chat_view3.setBackgroundColor(Color.parseColor(activityTypeColor));
//
//                        tv_plan_view3.setText("");

//                        if (arr_user.size() >= 2) {
//                            tv_name1_view3.setText(arr_user.get(0).lastName);
//                            tv_name2_view3.setText(arr_user.get(1).lastName);
//                            tv_old1_view3.setText(arr_user.get(0).age + " years old, " + arr_user.get(0).gender);
//                            tv_old2_view3.setText(arr_user.get(1).age + " years old, " + arr_user.get(1).gender);
//                            ll_user2_view3.setVisibility(View.VISIBLE);
//                            //img1_view3
//                            String urlImage1 =arr_user.get(0).imageUrl;
//
//                            String urlImage2 =arr_user.get(1).imageUrl;
//
//                            if(urlImage1.length()>0){
////                                        new lib_image_save_original(activity,urlImage1,img1_view3);
//                                String check = urlImage1.substring(0,3);
//                                if(check.equals("http")){
//                                    new lib_image_save_original(activity,urlImage1,img1_view3);
//
//                                }
//                                else{
//                                    urlImage1=HTTP_API.url_image+urlImage1;
//                                    new lib_image_save_original(activity,urlImage1,img1_view3);
//
//                                }
//                            }
//                            if(urlImage2.length()>0){
////                                        new lib_image_save_original(activity,urlImage2,img2_view3);
//                                String check = urlImage2.substring(0,3);
//                                if(check.equals("http")){
//                                    new lib_image_save_original(activity,urlImage2,img2_view3);
//
//                                }
//                                else{
//                                    urlImage2=HTTP_API.url_image+urlImage2;
//                                    new lib_image_save_original(activity,urlImage2,img2_view3);
//
//                                }
//                            }
//
//                        } else {
//                            tv_name1_view3.setText(arr_user.get(0).lastName);
//                            tv_old1_view3.setText(arr_user.get(0).age + " years old, " + arr_user.get(0).gender);
//                            ll_user2_view3.setVisibility(View.INVISIBLE);
//
//                            String urlImage1 =arr_user.get(0).imageUrl;
//                            if(urlImage1.length()>0){
//                                // new lib_image_save_original(activity,urlImage1,img1_view3);
//                                String check = urlImage1.substring(0,3);
//                                if(check.equals("http")){
//                                    new lib_image_save_original(activity,urlImage1,img1_view3);
//
//                                }
//                                else{
//                                    urlImage1=HTTP_API.url_image+urlImage1;
//                                    new lib_image_save_original(activity,urlImage1,img1_view3);
//
//                                }
//                            }
//                        }
//                    } else {
//                        Toast.makeText(activity, TAG_MESSAGE, Toast.LENGTH_SHORT).show();
//                    }
//
//                    img1_view3.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            String id_user =arr_user.get(0)._id;
//                            String fullname = arr_user.get(0).displayName;
//                            String age =arr_user.get(0).age;
//                            String gender =arr_user.get(0).gender;
//                            String imageUrl =arr_user.get(0).imageUrl;
//
//                            Intent in = new Intent(activity, Activity_Rank_Report.class);
//                            in.putExtra("id_user",id_user);
//                            in.putExtra("fullname",fullname);
//                            in.putExtra("age",age);
//                            in.putExtra("gender",gender);
//                            in.putExtra("imageUrl",imageUrl);
//                            activity.startActivityForResult(in, Activity_Result.REQUEST_CODE_ACT);
//                        }
//                    });
//                    img2_view3.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            String id_user =arr_user.get(1)._id;
//                            String fullname = arr_user.get(1).displayName;
//                            String age =arr_user.get(1).age;
//                            String gender =arr_user.get(1).gender;
//                            String imageUrl =arr_user.get(1).imageUrl;
//
//                            Intent in = new Intent(activity, Activity_Rank_Report.class);
//                            in.putExtra("id_user",id_user);
//                            in.putExtra("fullname",fullname);
//                            in.putExtra("age",age);
//                            in.putExtra("gender",gender);
//                            in.putExtra("imageUrl",imageUrl);
//                            activity.startActivityForResult(in, Activity_Result.REQUEST_CODE_ACT);
//                        }
//                    });
                        dialog_group_request(arr_user,activityTypeColor,id_group,activityTypeName);

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

    private static void dialog_activity_request(final String imageUrl,final String firstname,final String old,final String gender,final String place,final String age_f,final String age_to,final String gender_,final String rank,final String color,final String ac_name,final String fullname){
        //final Dialog dialog = new Dialog(activity);
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(dialog.getWindow().FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_activity_request);

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
       final  LinearLayout view2=(LinearLayout)dialog.findViewById(R.id.view2);
        final ImageView img_view2=(ImageView)dialog.findViewById(R.id.img_view2);
        final TextView tv_name_view2=(TextView)dialog.findViewById(R.id.tv_name_view2);
        final TextView tv_old_view2=(TextView)dialog.findViewById(R.id.tv_old_view2);
        final TextView tv_rank_view2=(TextView)dialog.findViewById(R.id.tv_rank_view2);
        final TextView tv_plan_view2=(TextView)dialog.findViewById(R.id.tv_plan_view2);
        final TextView tv_age_gender_view2=(TextView)dialog.findViewById(R.id.tv_age_gender_view2);
        final LinearLayout ll_back_view2=(LinearLayout)dialog.findViewById(R.id.ll_back_view2);
        final LinearLayout ll_prifile_view2=(LinearLayout)dialog.findViewById(R.id.ll_prifile_view2);
        final LinearLayout ll_chat_view2=(LinearLayout)dialog.findViewById(R.id.ll_chat_view2);
        final LinearLayout ll_join_view2=(LinearLayout)dialog.findViewById(R.id.ll_join_view2);
        dialog.show();
        view2.setBackgroundColor(Color.parseColor(color));
        ll_back_view2.setBackgroundColor(Color.parseColor(color));
        ll_prifile_view2.setBackgroundColor(Color.parseColor(color));
        ll_chat_view2.setBackgroundColor(Color.parseColor(color));
        ll_join_view2.setBackgroundColor(Color.parseColor(color));
//        if(imageUrl.length()>4){
//            String  k =imageUrl.substring(0,3);
//            if(k.equals("http")){
//              new lib_image_save_original(activity,imageUrl,img_view2);
//
//            }
//            else{
//                String ulr =HTTP_API.url_image+imageUrl;
//                new lib_image_save_original(activity,ulr,img_view2);
//            }
//        }
        tv_name_view2.setText(firstname);
        tv_old_view2.setText(old+" years old, "+gender);
        tv_rank_view2.setText(rank+"%"+"\nRANK");
        tv_plan_view2.setText(place);
        tv_age_gender_view2.setText(place);
    tv_age_gender_view2.setText("Demography:" + " Ages " + age_f + " to " + age_to + " " + gender_);


        ll_back_view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ll_prifile_view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                                            Intent in = new Intent(activity, Activity_Other_Activity.class);
                            in.putExtra("ac_name",ac_name);
                            in.putExtra("color",color);
                            in.putExtra("name",fullname);
                            in.putExtra("age",old);
                            in.putExtra("gender",gender);
                            in.putExtra("imageUrl",imageUrl);
                            in.putExtra("rank",rank);
                            activity.startActivityForResult(in, Activity_Result.REQUEST_CODE_ACT);
            }
        });
        ll_chat_view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "You need sent request to this user! ", Toast.LENGTH_SHORT).show();
            }
        });
        ll_join_view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "You need sent request to this user! ", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private static void dialog_group_request(final  ArrayList<item_user_group>arr,final String color,final String id_group,final String ac_name){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(dialog.getWindow().FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_group_request);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    final LinearLayout view3=(LinearLayout)dialog.findViewById(R.id.view3);
    final LinearLayout ll_back_view3=(LinearLayout)dialog.findViewById(R.id.ll_back_view3);
    final LinearLayout ll_member_view3=(LinearLayout)dialog.findViewById(R.id.ll_member_view3);
    final LinearLayout ll_join_view3=(LinearLayout)dialog.findViewById(R.id.ll_join_view3);
    final LinearLayout ll_chat_view3=(LinearLayout)dialog.findViewById(R.id.ll_chat_view3);
    final ImageView img1_view3=(ImageView)dialog.findViewById(R.id.img1_view3);
    final ImageView img2_view3=(ImageView)dialog.findViewById(R.id.img2_view3);
    final LinearLayout ll_user2_view3=(LinearLayout)dialog.findViewById(R.id.ll_user2_view3);
    final TextView tv_name1_view3=(TextView)dialog.findViewById(R.id.tv_name1_view3);
    final TextView tv_name2_view3=(TextView)dialog.findViewById(R.id.tv_name2_view3);
    final TextView tv_old1_view3=(TextView)dialog.findViewById(R.id.tv_old1_view3);
    final TextView tv_old2_view3=(TextView)dialog.findViewById(R.id.tv_old2_view3);
    final TextView tv_plan_view3=(TextView)dialog.findViewById(R.id.tv_plan_view3);
    final TextView tv_rank_view3=(TextView)dialog.findViewById(R.id.tv_rank_view3);
        tv_rank_view3.setText("0%"+"\nRANK");
                    view3.setBackgroundColor(Color.parseColor(color));
                        ll_member_view3.setBackgroundColor(Color.parseColor(color));
            ll_back_view3.setBackgroundColor(Color.parseColor(color));
                        ll_join_view3.setBackgroundColor(Color.parseColor(color));
                        ll_chat_view3.setBackgroundColor(Color.parseColor(color));
                        tv_plan_view3.setText("");
                                if (arr.size() >= 2) {
                            tv_name1_view3.setText(arr.get(0).lastName);
                            tv_name2_view3.setText(arr.get(1).lastName);
                            tv_old1_view3.setText(arr.get(0).age + " years old, " + arr.get(0).gender);
                            tv_old2_view3.setText(arr.get(1).age + " years old, " + arr.get(1).gender);
                            ll_user2_view3.setVisibility(View.VISIBLE);
                            //img1_view3
                            String urlImage1 =arr.get(0).imageUrl;

                            String urlImage2 =arr.get(1).imageUrl;

                            if(urlImage1.length()>0){
//                                        new lib_image_save_original(activity,urlImage1,img1_view3);
                                String check = urlImage1.substring(0,3);
                                if(check.equals("http")){
                                    new lib_image_save_original(activity,urlImage1,img1_view3);

                                }
                                else{
                                    urlImage1=HTTP_API.url_image+urlImage1;
                                    new lib_image_save_original(activity,urlImage1,img1_view3);

                                }
                            }
                            if(urlImage2.length()>0){
//                                        new lib_image_save_original(activity,urlImage2,img2_view3);
                                String check = urlImage2.substring(0,3);
                                if(check.equals("http")){
                                    new lib_image_save_original(activity,urlImage2,img2_view3);

                                }
                                else{
                                    urlImage2=HTTP_API.url_image+urlImage2;
                                    new lib_image_save_original(activity,urlImage2,img2_view3);

                                }
                            }

                        } else {
                            tv_name1_view3.setText(arr.get(0).lastName);
                            tv_old1_view3.setText(arr.get(0).age + " years old, " + arr.get(0).gender);
                            ll_user2_view3.setVisibility(View.INVISIBLE);

                            String urlImage1 =arr.get(0).imageUrl;
                            if(urlImage1.length()>0){
                                // new lib_image_save_original(activity,urlImage1,img1_view3);
                                String check = urlImage1.substring(0,3);
                                if(check.equals("http")){
                                    new lib_image_save_original(activity,urlImage1,img1_view3);

                                }
                                else{
                                    urlImage1=HTTP_API.url_image+urlImage1;
                                    new lib_image_save_original(activity,urlImage1,img1_view3);

                                }
                            }
                        }

        ll_member_view3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        Activity_Other_Group.TAG_ID =id_group;
                        Intent in = new Intent(activity,Activity_Other_Group.class);

                        in.putExtra("ac_name",ac_name);
                        in.putExtra("color",color);
                        activity.startActivityForResult(in, Activity_Result.REQUEST_CODE_ACT);

            }
        });
        ll_chat_view3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(activity, "You need sent request to Group", Toast.LENGTH_SHORT).show();
            }
        });
        ll_join_view3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(activity, "You need sent request to Group", Toast.LENGTH_SHORT).show();
            }
        });
        ll_back_view3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        dialog.show();
                    }








}
