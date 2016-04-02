package adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import activity.Activity_MyActivity;
import api.HTTP_API;
import hoanvusolution.pikapaka.R;
import image.lib_image_save_original;
import item.item_my_activity;

/**
 * Created by MrThanhPhong on 4/2/2016.
 */
public class adapter_my_activity extends ArrayAdapter<item_my_activity> {
    private Activity_MyActivity activity;
    private ArrayList<item_my_activity> arItem;
    private static int layoutID = R.layout.listview_widget_my_activity;
    private boolean click = false;
    private View view;
    public adapter_my_activity(Activity_MyActivity context, ArrayList<item_my_activity> arItem) {
        super(context, layoutID,arItem);
        this.activity =context;
        this.arItem =arItem;
    }

    public int getCount() {
        if (null == arItem) {
            return 0;
        }
        return arItem.size();
    }

    @Override
    public item_my_activity getItem(int position) {
        if (arItem == null) {
            return null;
        }
        if (position < 0 || position >= getCount()) {
            return null;
        }
        return arItem.get(position);
    }
    public void setListArray(List<item_my_activity> arrayList, int itemIdSelected) {
        if (arrayList == null) {
            return;
        }
        if (this.arItem == null) {
            this.arItem = new ArrayList<>();
        }
//
//        this.mItemSelected = itemIdSelected;
//        this.arrayData.clear();
//        this.arrayData.addAll(arrayList);
//        notifyDataSetChanged();
    }
    public View getView(final int position, View convertView, ViewGroup parent) {
        //View view = convertView;
         view = convertView;
        ViewWrapper     viewWrapper = null;
        if (view == null) {
            view = LayoutInflater.from(activity).inflate(layoutID, parent, false);
            viewWrapper = new ViewWrapper(activity, view);
            view.setTag(viewWrapper);
        } else {
            viewWrapper = (ViewWrapper) view.getTag();
        }
        item_my_activity item = arItem.get(position);
        viewWrapper.tv_name.setText(item.activityTypeName.toUpperCase());
        viewWrapper.tv_status.setText(item.status.toUpperCase());
        viewWrapper.tv_count_frend.setVisibility(View.GONE);
        String activityTypeIcon =item.activityTypeIcon;
        viewWrapper.ll_backgroud.setBackgroundColor(Color.parseColor(item.activityTypeColor.toString()));
        if(activityTypeIcon.length()>0){
            activityTypeIcon= HTTP_API.url_image+activityTypeIcon;
            new lib_image_save_original(activity,activityTypeIcon,viewWrapper.img_cate);
        }
        //************************
        viewWrapper.ll_backgroud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity,"ssac0",Toast.LENGTH_SHORT).show();
                if (click == false) {
                    inLoading();
                    //----------
                    click = true;
                } else {
                    ViewWrapper    viewWrapper  = new ViewWrapper(activity,view);
                    viewWrapper.view1.setVisibility(View.GONE);
                    viewWrapper.view2.setVisibility(View.GONE);
                    viewWrapper.view3.setVisibility(View.GONE);
                    viewWrapper.view4.setVisibility(View.GONE);
                    click = false;

                }
            }
        });

        //***************
        return view;
    }

  public  static class ViewWrapper {
        // Item defauld
        ImageView img_cate = null;
        TextView tv_name= null;
        TextView tv_status = null;
        ImageView img_search = null;
        TextView tv_count_frend = null;
         LinearLayout ll_backgroud= null;
        //
         LinearLayout view1, view2, view3, view4;
        //-- view 1

        //
        ViewWrapper(Context context, View view) {
            img_cate = (ImageView) view.findViewById(R.id.img_cate);
            img_search = (ImageView) view.findViewById(R.id.img_search);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_status = (TextView) view.findViewById(R.id.tv_status);
            tv_count_frend = (TextView) view.findViewById(R.id.tv_count_frend);
            ll_backgroud = (LinearLayout) view.findViewById(R.id.ll_backgroud);
            //----
            view1 = (LinearLayout) view.findViewById(R.id.view1);
            view2 = (LinearLayout) view.findViewById(R.id.view2);
            view3 = (LinearLayout) view.findViewById(R.id.view3);
            view4 = (LinearLayout) view.findViewById(R.id.view4);
            view1.setVisibility(View.GONE);
            view2.setVisibility(View.GONE);
            view3.setVisibility(View.GONE);
            view4.setVisibility(View.GONE);
        }
    }
//    private void onClick(){
//
//    }
    private void inLoading(){
//        ViewWrapper    viewWrapper  = new ViewWrapper(activity,view);
//        viewWrapper.view1.setVisibility(View.VISIBLE);
//        ViewWrapper     viewWrapper = null;
//        if (view == null) {
//            view = LayoutInflater.from(activity).inflate(layoutID, parent, false);
//            viewWrapper = new ViewWrapper(activity, view);
//            view.setTag(viewWrapper);
//        } else {
//            viewWrapper = (ViewWrapper) view.getTag();
//        }
//        class Loading extends AsyncTask<String, String, String> {
//            final ArrayList<item_search_activity> users_group = new ArrayList<item_search_activity>();
//            final ArrayList<item_group_request> j_group = new ArrayList<item_group_request>();
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//                progressDialog1 = ProgressDialog.show(activity, "",
//                        "", true);
//            }
//
//            @Override
//            protected String doInBackground(String... args) {
//                try {
//                    HttpClient client = new DefaultHttpClient();
//                    arr_search.clear();
//                    JSONObject json = new JSONObject();
//                    HttpPost post = new HttpPost(HTTP_API.SEARCH_ACTIVITY + "/" + TAG_ID);
//                    post.addHeader("X-User-Id", Activity_MyActivity.TAG_USERID);
//                    post.addHeader("X-Auth-Token", Activity_MyActivity.TAG_TOKEN);
//                    JSONObject jlocation = new JSONObject();
//                    jlocation.put("lat",Activity_MyActivity.TAG_LATITUDE);
//                    jlocation.put("lng",Activity_MyActivity.TAG_LONGITUDE);
//                    json.put("location",jlocation);
//                    HttpResponse response;
//                    StringEntity se = new StringEntity( json.toString());
//                    se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
//                    post.setEntity(se);
//                    response = client.execute(post);
//                    if (response != null) {
//                        HttpEntity resEntity = response.getEntity();
//                        if (resEntity != null) {
//                            String msg = EntityUtils.toString(resEntity);
//                            JSONObject jsonObject = new JSONObject(msg);
//                            TAG_STATUS = jsonObject.getString("status");
//                            TAG_MESSAGE = jsonObject.getString("message");
//                            // Log.e("loadding-- ",msg);
//                            if (TAG_STATUS.equals("success")) {
//                                //arr_search.clear();
//                                JSONArray jsonarray = jsonObject.getJSONArray("data");
//
//                                if (jsonarray.length() > 0) {
//                                    for (int i = 0; i < jsonarray.length(); i++) {
//                                        String _id=null;
//                                        String activityType=null;
//                                        String type=null;
//                                        String activityTypeColor=null;
//                                        String active=null;
//                                        String id_=null;
//                                        String firstName=null;
//                                        String gender_=null;
//                                        String lastName=null;
//                                        String dob=null;
//                                        String displayName=null;
//                                        String age=null;
//                                        String imageUrl = "";
//                                        String hasRequest="false";
//                                        try {
//                                            _id = jsonarray.getJSONObject(i).getString("_id");
//                                            activityType = jsonarray.getJSONObject(i).getString("activityType");
//                                            type = jsonarray.getJSONObject(i).getString("type");
//                                            activityTypeColor = jsonarray.getJSONObject(i).getString("activityTypeColor");
//                                            active = jsonarray.getJSONObject(i).getString("active");
//
//                                            try {
//                                                if(!jsonarray.getJSONObject(i).isNull("hasRequest")){
//                                                    hasRequest=jsonarray.getJSONObject(i).getString("hasRequest");
//                                                }
//                                            }catch (JSONException e){
//                                                hasRequest="false";
//                                            }
//                                            if(type.equals("request")){
//                                                JSONObject ob_user =jsonarray.getJSONObject(i).getJSONObject("user");
//                                                if(!ob_user.isNull("_id")){
//                                                    id_= ob_user.getString("_id");
//                                                }
//                                                if(!ob_user.isNull("firstName")){
//                                                    firstName = ob_user.getString("firstName");
//                                                }
//                                                if(!ob_user.isNull("gender")){
//                                                    gender_ = ob_user.getString("gender");
//                                                }
//                                                if(!ob_user.isNull("lastName")){
//                                                    lastName = ob_user.getString("lastName");
//                                                }
//                                                if(!ob_user.isNull("dob")){
//                                                    dob = ob_user.getString("dob");
//                                                }
//                                                if(!ob_user.isNull("displayName")){
//                                                    displayName = ob_user.getString("displayName");
//                                                }
//                                                if(!ob_user.isNull("age")){
//                                                    age = ob_user.getString("age");
//                                                }
//                                                if(!ob_user.isNull("imageUrl")){
//                                                    imageUrl=ob_user.getString("imageUrl");
//                                                }
//                                                item_search_activity item = new item_search_activity(
//                                                        _id,
//                                                        activityType,
//                                                        type,
//                                                        activityTypeColor,
//                                                        active,
//                                                        id_,
//                                                        firstName,
//                                                        gender_,
//                                                        lastName,
//                                                        dob,
//                                                        displayName,
//                                                        age,
//                                                        imageUrl,
//                                                        hasRequest,
//                                                        null
//                                                );
//                                                arr_search.add(item);
//                                            }
//                                            else{
//                                                JSONArray jar_user = jsonarray.getJSONObject(i).getJSONArray("users");
//                                                item_search_activity item = new item_search_activity(
//                                                        _id,
//                                                        activityType,
//                                                        type,
//                                                        activityTypeColor,
//                                                        active,
//                                                        "",
//                                                        "",
//                                                        "",
//                                                        "",
//                                                        "",
//                                                        "",
//                                                        "",
//                                                        "",
//                                                        hasRequest,
//                                                        jar_user
//                                                );
//                                                arr_search.add(item);
//
//
//                                            }
//
//
//                                        }catch (JSONException e){
//                                            Log.e("erre---",e.toString());
//                                        }
//
//                                    }
//
//                                }
//
//
//                            }
//                        }
//
//                        if (resEntity != null) {
//                            resEntity.consumeContent();
//                        }
//
//                        client.getConnectionManager().shutdown();
//
//                    }
//                } catch (Exception e) {
//                    Log.e("Error loading", "error");
//                    progressDialog1.dismiss();
//
//                } catch (Throwable t) {
//                    Log.e("Error loading", "error1");
//                    progressDialog1.dismiss();
//
//                }
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(String result) {
//                progressDialog1.dismiss();
//                try {
//                    Log.e("arr_search size",arr_search.size()+"");
//                    if(arr_search.size()>0) {
//                        view1.setVisibility(View.VISIBLE);
//                        view1.setBackgroundColor(Color.parseColor(arr_search.get(0).activityTypeColor.toString()));
//                        // load_slider();
//
//                        adapter_activity_request adapter = new adapter_activity_request(activity,arr_search);
//                        mHlvCustomList_.setAdapter(adapter);
//
//                    }
//                } catch (Exception e) {
//                    Log.e("gridFragments---","Error");
//                } catch (Throwable t) {
//
//                }
//
//            }
//
//        }
    }
}
