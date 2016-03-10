package adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import org.json.JSONObject;

import java.util.ArrayList;

import activity.Activity_MyActivity;
import activity.Activity_Rank_Report;
import api.HTTP_API;
import hoanvusolution.pikapaka.R;
import internet.CheckWifi3G;
import item.item_chat;
import item.item_my_activity;
import item.item_search_activity;
import item.item_user_group;
import lib_resize_listview.Resize_Auto;
import util.Activity_Result;
import util.dataString;

/**
 * Created by MrThanhPhong on 2/23/2016.
 */
public class adapter_myactivity extends BaseAdapter {
    private String TAG_STATUS = "";
    private String TAG_MESSAGE = "";

    private LayoutInflater mInflater;
    private ArrayList<item_my_activity> arItem;
    public static Activity_MyActivity activity;

    //View vi;
    private ProgressDialog progressDialog1;

    private boolean click = false;

    private RelativeLayout.LayoutParams lparam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);

    private ArrayList<item_search_activity> arr_search = new ArrayList<item_search_activity>();

//    private String id = "";
//    private String userID = Activity_MyActivity.TAG_USERID;
//    private String Token = Activity_MyActivity.TAG_TOKEN;

    public static String TAG_ID = "";
    public static String TAG_IDTO = "";

    private String TAG_ID_SINGLE = "";
    private String TAG_TYPE = "";


    private String TAG_COLER_VIEW_CHAT="";
    private String TAG_CONTENT_CHAT="";
    private String TAG_CONVERSATION="";

   public   ArrayList<item_chat>arr_chat= new ArrayList<item_chat>();
     adapater_chat adapter_ch;

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
        //vi = convertView;

        //try {
            if (convertView == null) {

                convertView = mInflater.inflate(R.layout.listview_widget_activityreview,
                        null);


                String activityType = arItem.get(pos).activityType;


                ImageView img_cate = (ImageView) convertView.findViewById(R.id.img_cate);

                TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                TextView tv_status = (TextView) convertView.findViewById(R.id.tv_status);
                ImageView img_search = (ImageView) convertView.findViewById(R.id.img_search);
                TextView tv_frend = (TextView) convertView.findViewById(R.id.tv_frend);
                tv_name.setText(arItem.get(pos).activityTypeName.toUpperCase());
                tv_status.setText(arItem.get(pos).status.toUpperCase());

                //--------

                // parent1 = (FrameLayout) vi.findViewById(R.id.parent);
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

                LinearLayout ll_backgroud = (LinearLayout) convertView.findViewById(R.id.ll_backgroud);

                ll_backgroud.setBackgroundColor(Color.parseColor(arItem.get(pos).activityTypeColor.toString()));
                //View1 item**********************************************
                final LinearLayout ll_view1_user1 = (LinearLayout) convertView.findViewById(R.id.ll_view1_user1);
                final LinearLayout ll_view1_user2 = (LinearLayout) convertView.findViewById(R.id.ll_view1_user2);
                final LinearLayout ll_view1_user3 = (LinearLayout) convertView.findViewById(R.id.ll_view1_user3);
                final TextView tv_name1_view1 = (TextView) convertView.findViewById(R.id.tv_name1_view1);
                final TextView tv_name2_view1 = (TextView) convertView.findViewById(R.id.tv_name2_view1);
                final TextView tv_name3_view1 = (TextView) convertView.findViewById(R.id.tv_name3_view1);
                final ImageView icon_hasRequest1_view1 = (ImageView) convertView.findViewById(R.id.icon_hasRequest1_view1);
                final ImageView icon_hasRequest2_view1 = (ImageView) convertView.findViewById(R.id.icon_hasRequest2_view1);
                final ImageView icon_hasRequest3_view1 = (ImageView) convertView.findViewById(R.id.icon_hasRequest3_view1);
                final ImageView img1_view1 = (ImageView) convertView.findViewById(R.id.img1_view1);
                final ImageView img2_view1 = (ImageView) convertView.findViewById(R.id.img2_view1);
                final ImageView img3_view1 = (ImageView) convertView.findViewById(R.id.img3_view1);
                final ImageView img4_view1 = (ImageView) convertView.findViewById(R.id.img4_view1);
                img4_view1.setVisibility(View.GONE);
                final RelativeLayout rl_img4_view1 = (RelativeLayout) convertView.findViewById(R.id.rl_img4_view1);
                rl_img4_view1.setVisibility(View.GONE);

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
                final ListView list_chat_view4 = (ListView) convertView.findViewById(R.id.list_chat_view4);

                adapter_ch = new adapater_chat(activity, arr_chat);
                list_chat_view4.setAdapter(adapter_ch);
                //******************

                class Loading extends AsyncTask<String, String, String> {


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
                            // Looper.prepare(); //For Preparing Message Pool for the child Thread
                            HttpClient client = new DefaultHttpClient();
                            arr_search.clear();
                            //JSONObject json = new JSONObject();

                            HttpPost post = new HttpPost(HTTP_API.SEARCH_ACTIVITY + "/" + TAG_ID);
                            post.addHeader("X-User-Id", Activity_MyActivity.TAG_USERID);
                            post.addHeader("X-Auth-Token", Activity_MyActivity.TAG_TOKEN);

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
                                    if (TAG_STATUS.equals("success")) {
                                        //arr_search.clear();
                                        JSONArray jsonarray = jsonObject.getJSONArray("data");

                                        if (jsonarray.length() > 0) {
                                            for (int i = 0; i < jsonarray.length(); i++) {

                                                String _id = jsonarray.getJSONObject(i).getString("_id");
                                                String minNumOfParticipants = jsonarray.getJSONObject(i).getString("minNumOfParticipants");
                                                String ageTo = jsonarray.getJSONObject(i).getString("ageTo");
                                                String maxNumOfParticipants = jsonarray.getJSONObject(i).getString("maxNumOfParticipants");
                                                String distance = jsonarray.getJSONObject(i).getString("distance");
                                                String ageFrom = jsonarray.getJSONObject(i).getString("ageFrom");
                                                String plan = jsonarray.getJSONObject(i).getString("plan");
                                                String publishToSocial = jsonarray.getJSONObject(i).getString("publishToSocial");
                                                String expiredHours = jsonarray.getJSONObject(i).getString("expiredHours");
                                                String meetConfirm = jsonarray.getJSONObject(i).getString("meetConfirm");
                                                String gender = jsonarray.getJSONObject(i).getString("gender");
                                                String activityType = jsonarray.getJSONObject(i).getString("activityType");
                                                String userId = jsonarray.getJSONObject(i).getString("userId");
                                                String status = jsonarray.getJSONObject(i).getString("status");
                                                String type = jsonarray.getJSONObject(i).getString("type");
                                                String activityTypeName = jsonarray.getJSONObject(i).getString("activityTypeName");
                                                String activityTypeColor = jsonarray.getJSONObject(i).getString("activityTypeColor");
                                                String createdAt = jsonarray.getJSONObject(i).getString("createdAt");
                                                String active = jsonarray.getJSONObject(i).getString("active");

                                                JSONObject j_user = jsonarray.getJSONObject(i);
                                                JSONObject jo = j_user.getJSONObject("user");
                                                String firstName = jo.getString("firstName");
                                                String gender_ = jo.getString("gender");
                                                String lastName = jo.getString("lastName");
                                                String dob = "dob";
                                                String displayName = jo.getString("displayName");
                                                String age = "age";                   //
//
                                                String hasRequest = jsonarray.getJSONObject(i).getString("hasRequest");
//
                                                item_search_activity item = new item_search_activity(
                                                        _id,
                                                        minNumOfParticipants,
                                                        ageTo,
                                                        maxNumOfParticipants,
                                                        distance,
                                                        ageFrom,
                                                        plan,
                                                        publishToSocial,
                                                        expiredHours,
                                                        meetConfirm,
                                                        gender,
                                                        activityType,
                                                        userId,
                                                        status,
                                                        type,
                                                        activityTypeName,
                                                        activityTypeColor,
                                                        createdAt,
                                                        active,
                                                        firstName,
                                                        gender_,
                                                        lastName,
                                                        dob,
                                                        displayName,
                                                        age,
                                                        hasRequest
                                                );

                                                arr_search.add(item);

                                            }
                                            Log.i("arr_search", arr_search.size() + "");
                                        }


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
                            Log.e("TAG_STATUS----", TAG_STATUS);
                            Log.e("TAG_MESSAGE---", TAG_MESSAGE);

                            if (arr_search.size() > 0) {
                                view1.setVisibility(View.VISIBLE);
                                // parent.addView(view1);
                                view1.setBackgroundColor(Color.parseColor(arr_search.get(0).activityTypeColor.toString()));

                                if (arr_search.size() == 1) {
                                    ll_view1_user1.setVisibility(View.VISIBLE);
                                    ll_view1_user2.setVisibility(View.GONE);
                                    ll_view1_user3.setVisibility(View.GONE);

                                    tv_name1_view1.setText(arr_search.get(0).firstName);
                                    if (arr_search.get(0).hasRequest.equals("true")) {
                                        icon_hasRequest1_view1.setImageResource(R.drawable.ic_like);
                                    } else {
                                        icon_hasRequest1_view1.setImageResource(R.drawable.ic_add);

                                    }

                                    if (arr_search.get(0).gender_.equals("Woman")) {
                                        img1_view1.setImageResource(R.drawable.female);

                                    } else {
                                        img1_view1.setImageResource(R.drawable.male);
                                    }
                                } else if (arr_search.size() == 2) {
                                    ll_view1_user1.setVisibility(View.VISIBLE);
                                    ll_view1_user2.setVisibility(View.VISIBLE);
                                    ll_view1_user3.setVisibility(View.GONE);
                                    tv_name1_view1.setText(arr_search.get(0).firstName);
                                    tv_name2_view1.setText(arr_search.get(1).firstName);

                                    if (arr_search.get(0).hasRequest.equals("true")) {
                                        icon_hasRequest1_view1.setImageResource(R.drawable.ic_like);
                                    } else {
                                        icon_hasRequest1_view1.setImageResource(R.drawable.ic_add);

                                    }

                                    if (arr_search.get(1).hasRequest.equals("true")) {
                                        icon_hasRequest2_view1.setImageResource(R.drawable.ic_like);
                                    } else {
                                        icon_hasRequest2_view1.setImageResource(R.drawable.ic_add);

                                    }

                                    //-------------
                                    if (arr_search.get(0).gender.equals("Woman")) {
                                        img1_view1.setImageResource(R.drawable.female);

                                    } else {
                                        img1_view1.setImageResource(R.drawable.male);
                                    }
                                    if (arr_search.get(1).gender.equals("Woman")) {
                                        img2_view1.setImageResource(R.drawable.female);

                                    } else {
                                        img2_view1.setImageResource(R.drawable.male);
                                    }


                                } else if (arr_search.size() == 3) {
                                    ll_view1_user1.setVisibility(View.VISIBLE);
                                    ll_view1_user2.setVisibility(View.VISIBLE);
                                    ll_view1_user3.setVisibility(View.VISIBLE);
                                    tv_name1_view1.setText(arr_search.get(0).firstName);
                                    tv_name2_view1.setText(arr_search.get(1).firstName);
                                    tv_name3_view1.setText(arr_search.get(2).firstName);


                                    if (arr_search.get(0).hasRequest.equals("true")) {
                                        icon_hasRequest1_view1.setImageResource(R.drawable.ic_like);
                                    } else {
                                        icon_hasRequest1_view1.setImageResource(R.drawable.ic_add);

                                    }

                                    if (arr_search.get(1).hasRequest.equals("true")) {
                                        icon_hasRequest2_view1.setImageResource(R.drawable.ic_like);
                                    } else {
                                        icon_hasRequest2_view1.setImageResource(R.drawable.ic_add);

                                    }
                                    if (arr_search.get(2).hasRequest.equals("true")) {
                                        icon_hasRequest3_view1.setImageResource(R.drawable.ic_like);
                                    } else {
                                        icon_hasRequest3_view1.setImageResource(R.drawable.ic_add);

                                    }
                                    //----

                                    if (arr_search.get(0).gender.equals("Woman")) {
                                        img1_view1.setImageResource(R.drawable.female);

                                    } else {
                                        img1_view1.setImageResource(R.drawable.male);
                                    }
                                    if (arr_search.get(1).gender.equals("Woman")) {
                                        img2_view1.setImageResource(R.drawable.female);

                                    } else {
                                        img2_view1.setImageResource(R.drawable.male);
                                    }
                                    if (arr_search.get(2).gender.equals("Woman")) {
                                        img3_view1.setImageResource(R.drawable.female);

                                    } else {
                                        img3_view1.setImageResource(R.drawable.male);
                                    }
                                } else if (arr_search.size() > 3) {

                                    rl_img4_view1.setVisibility(View.VISIBLE);
                                    img4_view1.setVisibility(View.VISIBLE);

                                    ll_view1_user1.setVisibility(View.VISIBLE);
                                    ll_view1_user2.setVisibility(View.VISIBLE);
                                    ll_view1_user3.setVisibility(View.VISIBLE);
                                    tv_name1_view1.setText(arr_search.get(0).firstName);
                                    tv_name2_view1.setText(arr_search.get(1).firstName);
                                    tv_name3_view1.setText(arr_search.get(2).firstName + " & " + arr_search.get(3).lastName);

                                    if (arr_search.get(0).hasRequest.equals("true")) {
                                        icon_hasRequest1_view1.setImageResource(R.drawable.ic_like);
                                    } else {
                                        icon_hasRequest1_view1.setImageResource(R.drawable.ic_add);

                                    }

                                    if (arr_search.get(1).hasRequest.equals("true")) {
                                        icon_hasRequest2_view1.setImageResource(R.drawable.ic_like);
                                    } else {
                                        icon_hasRequest2_view1.setImageResource(R.drawable.ic_add);

                                    }
                                    if (arr_search.get(2).hasRequest.equals("true")) {
                                        icon_hasRequest3_view1.setImageResource(R.drawable.ic_like);
                                    } else {
                                        icon_hasRequest3_view1.setImageResource(R.drawable.ic_add);

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
                            // Looper.prepare(); //For Preparing Message Pool for the child Thread
                            HttpClient client = new DefaultHttpClient();

                            //JSONObject json = new JSONObject();

                            HttpGet post = new HttpGet(HTTP_API.GET_SINGLE_ACTIVITY + "/" + TAG_ID_SINGLE);
                            post.addHeader("X-User-Id", Activity_MyActivity.TAG_USERID);
                            post.addHeader("X-Auth-Token", Activity_MyActivity.TAG_TOKEN);

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
                                        userId = data.getString("userId");
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
                                        age = jo.getString("age");                   //
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
                            Log.e("TAG_STATUS----", TAG_STATUS);
                            Log.e("TAG_MESSAGE---", TAG_MESSAGE);

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
                    String type;
                    String maxNumOfParticipants;
                    String minNumOfParticipants;
                    String distance;
                    String ageFrom;
                    String ageTo;
                    String meetConfirm;
                    String publishToSocial;
                    String status;
                    String createdAt;


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
                            // Looper.prepare(); //For Preparing Message Pool for the child Thread
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
                                    Log.d("msg-- cate", msg);
                                    JSONObject jsonObject = new JSONObject(msg);
                                    TAG_STATUS = jsonObject.getString("status");
                                    TAG_MESSAGE = jsonObject.getString("message");
                                    if (TAG_STATUS.equals("success")) {

                                        JSONObject data = jsonObject.getJSONObject("data");
                                        _id = data.getString("_id");
                                        minNumOfParticipants = data.getString("minNumOfParticipants");
                                        ageTo = data.getString("ageTo");
                                        maxNumOfParticipants = data.getString("maxNumOfParticipants");
                                        distance = data.getString("distance");
                                        ageFrom = data.getString("ageFrom");
                                        publishToSocial = data.getString("publishToSocial");
                                        meetConfirm = data.getString("meetConfirm");
                                        activityType = data.getString("activityType");
                                        status = data.getString("status");
                                        type = data.getString("type");
                                        activityTypeName = data.getString("activityTypeName");
                                        activityTypeColor = data.getString("activityTypeColor");
                                        createdAt = data.getString("createdAt");
                                        active = data.getString("active");

                                        //JSONObject jo = data.getJSONObject("users");
                                        JSONArray jarr = data.getJSONArray("users");
                                        for (int i = 0; i < jarr.length(); i++) {

                                            String id = jarr.getJSONObject(i).getString("_id");
                                            //JSONObject profile =jsonObject.getJSONObject("profile");
                                            JSONObject profile = jarr.getJSONObject(i).getJSONObject("profile");

                                            String firstName = profile.getString("firstName");

                                            String gender = profile.getString("gender");

                                            String lastName = profile.getString("lastName");

                                            String dob = profile.getString("dob");

                                            String displayName = profile.getString("displayName");

                                            String age = profile.getString("age");

                                            item_user_group item = new item_user_group(
                                                    id,
                                                    firstName,
                                                    gender,
                                                    lastName,
                                                    dob,
                                                    displayName,
                                                    age
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
                            Log.e("TAG_STATUS----", TAG_STATUS);
                            Log.e("TAG_MESSAGE---", TAG_MESSAGE);


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
                                tv_age_gender_view3.setText("Demography: " + "Ages " + ageFrom + " to " + ageTo);
                                if (arr_user.size() >= 2) {
                                    tv_name1_view3.setText(arr_user.get(0).lastName);
                                    tv_name2_view3.setText(arr_user.get(1).lastName);
                                    tv_old1_view3.setText(arr_user.get(0).age + " years old, " + arr_user.get(0).gender);
                                    tv_old2_view3.setText(arr_user.get(1).age + " years old, " + arr_user.get(1).gender);
                                    ll_user2_view3.setVisibility(View.VISIBLE);

                                } else {
                                    tv_name1_view3.setText(arr_user.get(0).lastName);
                                    tv_old1_view3.setText(arr_user.get(0).age + " years old, " + arr_user.get(0).gender);
                                    ll_user2_view3.setVisibility(View.INVISIBLE);
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

                                    Intent in = new Intent(activity, Activity_Rank_Report.class);
                                    in.putExtra("id_user",id_user);
                                    in.putExtra("fullname",fullname);
                                    in.putExtra("age",age);
                                    in.putExtra("gender",gender);
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

                                    Intent in = new Intent(activity, Activity_Rank_Report.class);
                                    in.putExtra("id_user",id_user);
                                    in.putExtra("fullname",fullname);
                                    in.putExtra("age",age);
                                    in.putExtra("gender",gender);
                                    activity.startActivityForResult(in, Activity_Result.REQUEST_CODE_ACT);
                                }
                            });


                        } catch (Exception e) {

                        } catch (Throwable t) {

                        }

                    }

                }
                class Send_Activity extends AsyncTask<String, String, String> {

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

                            // HttpPost post = new HttpPost(HTTP_API.GET_SEND_ACTIVITY + "/" + TAG_ID_SINGLE);
                            //HttpPost post = new HttpPost(HTTP_API.GET_SEND_ACTIVITY + "/" + TAG_IDTO);
                            HttpPost post = new HttpPost(HTTP_API.GET_SEND_ACTIVITY);
                            post.addHeader("X-User-Id", Activity_MyActivity.TAG_USERID);
                            post.addHeader("X-Auth-Token", Activity_MyActivity.TAG_TOKEN);

                            json.put("fromId", TAG_ID);
                            json.put("toId", TAG_IDTO);

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
                            Log.e("TAG_STATUS----", TAG_STATUS);
                            Log.e("TAG_MESSAGE---", TAG_MESSAGE);

                            if (TAG_STATUS.equals("success")) {
                                Toast.makeText(activity, TAG_MESSAGE, Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(activity, TAG_MESSAGE, Toast.LENGTH_SHORT).show();
                            }


                        } catch (Exception e) {

                        } catch (Throwable t) {

                        }

                    }

                }

                class Accept_Activity extends AsyncTask<String, String, String> {
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

                            // HttpPost post = new HttpPost(HTTP_API.GET_ACCEPT_ACTIVITY + "/" + TAG_ID_SINGLE);
                            HttpPost post = new HttpPost(HTTP_API.GET_ACCEPT_ACTIVITY);
                            post.addHeader("X-User-Id", Activity_MyActivity.TAG_USERID);
                            post.addHeader("X-Auth-Token", Activity_MyActivity.TAG_TOKEN);

                            json.put("fromId", TAG_ID);
                            json.put("toId", TAG_IDTO);
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
                            Log.e("TAG_STATUS----", TAG_STATUS);
                            Log.e("TAG_MESSAGE---", TAG_MESSAGE);

                            if (TAG_STATUS.equals("success")) {
                                Toast.makeText(activity, TAG_MESSAGE, Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(activity, TAG_MESSAGE, Toast.LENGTH_SHORT).show();
                            }


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
                            Log.e("TAG_STATUS----", TAG_STATUS);
                            Log.e("TAG_MESSAGE---", TAG_MESSAGE);

                            if (TAG_STATUS.equals("success")) {
                                Toast.makeText(activity, TAG_MESSAGE, Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(activity, TAG_MESSAGE, Toast.LENGTH_SHORT).show();
                            }


                        } catch (Exception e) {

                        } catch (Throwable t) {

                        }

                    }

                }

                class Chat_Group extends AsyncTask<String, String, String> {

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
                            Log.e("TAG_STATUS----", TAG_STATUS);
                            Log.e("TAG_MESSAGE---", TAG_MESSAGE);

                            if (TAG_STATUS.equals("success")) {
                                //Toast.makeText(activity, TAG_MESSAGE, Toast.LENGTH_SHORT).show();
                                arr_chat.add(new item_chat("", "", dataString.TAG_FIRSTNAME, "", "", TAG_CONTENT_CHAT
                                ));

                                // adapter_ch.notifyDataSetChanged();
                                adapter_ch = new adapater_chat(activity, arr_chat);
                                list_chat_view4.setAdapter(adapter_ch);
                                TAG_CONTENT_CHAT = "";
                                ed_input_chat_view4.setText("");
                            } else {
                                Toast.makeText(activity, TAG_MESSAGE, Toast.LENGTH_SHORT).show();
                            }


                        } catch (Exception e) {

                        } catch (Throwable t) {

                        }

                    }

                }

                class Load_ListChat extends AsyncTask<String, String, String> {

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

                            HttpGet post = new HttpGet(HTTP_API.CHAT_LOADLIST + TAG_CONVERSATION);
                            post.addHeader("X-User-Id", Activity_MyActivity.TAG_USERID);
                            post.addHeader("X-Auth-Token", Activity_MyActivity.TAG_TOKEN);

//                        json.put("toGroup", TAG_ID);
//                        json.put("content", TAG_CONTENT_CHAT);
//                        StringEntity se = new StringEntity(json.toString());
//                        se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
//                        post.setEntity(se);

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

                                    JSONObject jdata = jsonObject.getJSONObject("data");

                                    JSONArray message = jdata.getJSONArray("messages");
                                    arr_chat.clear();
                                    for (int i = 0; i < message.length(); i++) {
                                        String _id = message.getJSONObject(i).getString("_id");
                                        String conversationId = message.getJSONObject(i).getString("conversationId");
//                                    String fromUser =message.getJSONObject(i).getString("fromUser");
//
                                        JSONObject fromUser = message.getJSONObject(i).getJSONObject("fromUser");
                                        JSONObject profile = fromUser.getJSONObject("profile");
                                        String firstName = profile.getString("firstName");
                                        String gender = profile.getString("gender");
                                        String lastName = profile.getString("lastName");
                                        String content = message.getJSONObject(i).getString("content");
                                        item_chat item = new item_chat(_id, conversationId, firstName, gender, lastName, content);
                                        arr_chat.add(item);
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
                            Log.e("TAG_STATUS----", TAG_STATUS);
                            Log.e("TAG_MESSAGE---", TAG_MESSAGE);

                            if (arr_chat.size() > 0) {
                                //  Toast.makeText(activity, TAG_MESSAGE, Toast.LENGTH_SHORT).show();
                                Log.e("arr_chat size", arr_chat.size() + "");
                                adapater_chat adapter = new adapater_chat(activity, arr_chat);


                                list_chat_view4.setAdapter(adapter);
                                Resize_Auto resize = new Resize_Auto();
                                resize.getListViewSize(list_chat_view4);

                            } else {
                                Toast.makeText(activity, TAG_MESSAGE, Toast.LENGTH_SHORT).show();
                            }


                        } catch (Exception e) {

                        } catch (Throwable t) {

                        }

                    }

                }

                class Get_Conversations extends AsyncTask<String, String, String> {

                    ProgressDialog progressDialog;
                    String _id;
                    String type;
                    String groupId;
                    String users;
                    String mutedUsers;
                    String createdAt;
                    String updatedAt;
                    String lastMessage;
                    String _id_;
                    String conversationId;
                    String fromUser;
                    String content;
                    String read;
                    String createdAt_;

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

                            HttpGet post = new HttpGet(HTTP_API.CHAT_GET_CONVERSATION + TAG_ID);
                            post.addHeader("X-User-Id", Activity_MyActivity.TAG_USERID);
                            post.addHeader("X-Auth-Token", Activity_MyActivity.TAG_TOKEN);

//                        json.put("toGroup", TAG_ID);
//                        json.put("content", TAG_CONTENT_CHAT);
//                        StringEntity se = new StringEntity(json.toString());
//                        se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
//                        post.setEntity(se);

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

                                    JSONArray jarr = jsonObject.getJSONArray("data");

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
                            Log.e("TAG_STATUS----", TAG_STATUS);
                            Log.e("TAG_MESSAGE---", TAG_MESSAGE);

                            if (TAG_STATUS.equals("success")) {

                                if (TAG_CONVERSATION.length() > 0) {
                                    new Load_ListChat().execute();
                                }

                            } else {
                                Toast.makeText(activity, TAG_MESSAGE, Toast.LENGTH_SHORT).show();
                            }


                        } catch (Exception e) {

                        } catch (Throwable t) {

                        }

                    }

                }

                ll_backgroud.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TAG_ID = arItem.get(pos)._id;
                        TAG_TYPE = arItem.get(pos).type;
                        TAG_COLER_VIEW_CHAT = arItem.get(pos).activityTypeColor;
                        Log.i("TAG_ID   ---------", TAG_ID);
                        //M4fE8ZXJpqkYePCKH
                        //eyGZGCGr48Ljec7a2


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

                            click = false;

                        }
                    }
                });


                //--********************************
                //*********************

// -------------nview1 click
                img1_view1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        TAG_ID=arItem.get(pos)._id;
                        TAG_ID_SINGLE = arr_search.get(0)._id;
                        TAG_COLER_VIEW_CHAT = arItem.get(pos).activityTypeColor;

                        if (CheckWifi3G.isConnected(activity)) {

                            new Get_Single().execute();
                        } else {
                            Toast.makeText(activity, "Error Connect Internet!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                img2_view1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        TAG_ID=arItem.get(pos)._id;
                        TAG_ID_SINGLE = arr_search.get(1)._id;
                        TAG_COLER_VIEW_CHAT = arItem.get(pos).activityTypeColor;
                        if (CheckWifi3G.isConnected(activity)) {

                            new Get_Single().execute();
                        } else {
                            Toast.makeText(activity, "Error Connect Internet!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                img3_view1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TAG_ID=arItem.get(pos)._id;
                        TAG_ID_SINGLE = arr_search.get(2)._id;
                        TAG_COLER_VIEW_CHAT = arItem.get(pos).activityTypeColor;
                        if (CheckWifi3G.isConnected(activity)) {

                            new Get_Single().execute();
                        } else {
                            Toast.makeText(activity, "Error Connect Internet!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                icon_hasRequest1_view1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TAG_ID=arItem.get(pos)._id;
                        TAG_IDTO = arr_search.get(0)._id;
                        TAG_COLER_VIEW_CHAT = arItem.get(pos).activityTypeColor;
                        String check = arr_search.get(0).hasRequest;
                        Log.i("TAG_IDTO", TAG_IDTO + "");
                        if (check.equals("false")) {
                            Log.i("Send_Activity", "Send_Activity");
                            if (CheckWifi3G.isConnected(activity)) {

                                new Send_Activity().execute();
                            } else {
                                Toast.makeText(activity, "Error Connect Internet!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.i("Accept_Activity", "Accept_Activity");
                            if (CheckWifi3G.isConnected(activity)) {

                                new Accept_Activity().execute();
                            } else {
                                Toast.makeText(activity, "Error Connect Internet!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                icon_hasRequest2_view1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TAG_ID=arItem.get(pos)._id;
                        TAG_IDTO = arr_search.get(1)._id;
                        TAG_COLER_VIEW_CHAT = arItem.get(pos).activityTypeColor;
                        //boolean check = Boolean.getBoolean(arr_search.get(1).hasRequest);
                        String check = arr_search.get(1).hasRequest;
                        if (check.equals("false")) {
                            if (CheckWifi3G.isConnected(activity)) {

                                new Send_Activity().execute();
                            } else {
                                Toast.makeText(activity, "Error Connect Internet!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            if (CheckWifi3G.isConnected(activity)) {

                                new Accept_Activity().execute();
                            } else {
                                Toast.makeText(activity, "Error Connect Internet!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                icon_hasRequest3_view1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TAG_ID=arItem.get(pos)._id;
                        TAG_IDTO = arr_search.get(2)._id;
                        String check = arr_search.get(1).hasRequest;
                        TAG_COLER_VIEW_CHAT = arItem.get(pos).activityTypeColor;
                        if (check.equals("false")) {
                            if (CheckWifi3G.isConnected(activity)) {

                                new Send_Activity().execute();
                            } else {
                                Toast.makeText(activity, "Error Connect Internet!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            if (CheckWifi3G.isConnected(activity)) {

                                new Accept_Activity().execute();
                            } else {
                                Toast.makeText(activity, "Error Connect Internet!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
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


                        new Get_Conversations().execute();
                    }
                });


                // view 4 click---------------------------------------

                tv_send_view4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TAG_CONTENT_CHAT = ed_input_chat_view4.getText().toString();
                        if (TAG_CONTENT_CHAT.length() == 0) {
                            Toast.makeText(activity, "Please input content", Toast.LENGTH_SHORT).show();
                        } else {
                            new Chat_Group().execute();
                        }
                    }
                });

                ll_leave_view4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new Leave_Group().execute();
                    }
                });
                //***************************************************************
//        } catch (Exception e) {
//            // TODO: handle exception
//            e.printStackTrace();
//        } catch (OutOfMemoryError e) {
//            System.gc();
//        } catch (Throwable t) {
//
//        }

//        if (convertView == null) {
//            convertView = new View(activity);
//        }}
            }
        return convertView;
    }

}
