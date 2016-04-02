package adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import activity.Activity_MyActivity;
import activity.Activity_Other_Activity;
import activity.Activity_Other_Group;
import api.HTTP_API;
import hoanvusolution.pikapaka.R;
import image.lib_image_save_original;
import internet.CheckWifi3G;
import item.item_search_activity;
import util.Activity_Result;

/**
 * Created by MrThanhPhong on 3/15/2016.
 */
public class adapter_activity_request extends BaseAdapter {
    private LayoutInflater mInflater;
    private ArrayList<item_search_activity> arItem;
    private Activity activity;

    public adapter_activity_request(Activity activity,
                                    ArrayList<item_search_activity> arItem2) {
        mInflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.arItem = arItem2;
        this.activity = activity;
    }

    public int getCount() {
        return arItem.size();
    }

    public item_search_activity getItem(int position) {
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

    //@SuppressLint("ResourceAsColor")
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        if (convertView == null) {
          convertView = mInflater.inflate(R.layout.gridview_widget_request, parent, false);

                        final RelativeLayout rl_img1 = (RelativeLayout) convertView.findViewById(R.id.rl_img1);
            final ImageView img_profile = (ImageView) convertView.findViewById(R.id.img_profile);
            final ImageView img_profile1 = (ImageView) convertView.findViewById(R.id.img_profile1);
            final ImageView icon_hasRequest = (ImageView) convertView.findViewById(R.id.icon_hasRequest);
            final TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);

    //-----
                        if (arItem.get(pos).hasRequest.equals("true")) {
                icon_hasRequest.setImageResource(R.drawable.ic_like);
                icon_hasRequest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter_myactivity.TAG_IDTO =arItem.get(pos)._id;
                        Action_Accept(arItem.get(pos)._id);
                    }
                });
            } else {
                icon_hasRequest.setImageResource(R.drawable.ic_add);
                icon_hasRequest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        adapter_myactivity.TAG_IDTO =arItem.get(pos)._id;

                        Action_Send(arItem.get(pos)._id);
                    }
                });
            }
            //---
            if (arItem.get(pos).type.equals("request")) {
                rl_img1.setVisibility(View.GONE);
                tv_name.setText(arItem.get(pos).firstName);
                String img = arItem.get(pos).imageUrl;
                if (img.length() > 5) {
                    String url_ = img.substring(0, 3);
                    if (url_.equals("http")) {
                        new lib_image_save_original(activity, img, img_profile);
                    } else {
                        img = HTTP_API.url_image + img;
                        new lib_image_save_original(activity, img, img_profile);
                    }
                }

                img_profile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //  Toast.makeText(activity,arItem.get(pos).type,Toast.LENGTH_LONG).show();
                        // adapter_myactivity.o

                           // adapter_myactivity.get_single(arItem.get(pos)._id);

                            String color = arItem.get(pos).activityTypeColor;
                            String ac_name = arItem.get(pos).activityName;
                            String name = arItem.get(pos).displayName;
                            String age = arItem.get(pos).age;
                            String gender = arItem.get(pos).gender_;
                            String imageUrl = arItem.get(pos).imageUrl;
                            String rank = arItem.get(pos).rank;
                            Intent in = new Intent(activity, Activity_Other_Activity.class);
                            in.putExtra("ac_name",ac_name);
                            in.putExtra("color",color);
                            in.putExtra("name",name);
                            in.putExtra("age",age);
                            in.putExtra("gender",gender);
                            in.putExtra("imageUrl",imageUrl);
                            in.putExtra("rank",rank);
                            activity.startActivityForResult(in, Activity_Result.REQUEST_CODE_ACT);



                    }
                });

            }//----
            else{
                JSONArray jar_user = arItem.get(pos).arr_group;
                       try {
                           if (jar_user.length() == 1) {
                               String name = jar_user.getJSONObject(0).getString("firstName");
                               if (name.length() > 10) {
                                   tv_name.setText(name.substring(0, 10) + "...");
                               } else {
                                   tv_name.setText(name);
                               }

                               String img = arItem.get(pos).imageUrl;
                               if (img.length() > 0) {
                                   String url_ = img.substring(0, 3);
                                   if (url_.equals("http")) {
                                       new lib_image_save_original(activity, img, img_profile);
                                   } else {
                                       img = HTTP_API.url_image + img;
                                       new lib_image_save_original(activity, img, img_profile);
                                   }
                               }

                           } else if (jar_user.length() > 1) {
                               rl_img1.setVisibility(View.VISIBLE);
                               String name = jar_user.getJSONObject(0).getString("firstName") + " and " + jar_user.getJSONObject(1).getString("firstName");
                               if (name.length() > 10) {
                                   tv_name.setText(name.substring(0, 10) + "...");
                               } else {
                                   tv_name.setText(name);
                               }
                               String img = jar_user.getJSONObject(0).getString("imageUrl");
                               // String img = ac_type.imageUrl;
                               if (img.length() > 0) {
                                   String url_ = img.substring(0, 3);
                                   if (url_.equals("http")) {
                                       new lib_image_save_original(activity, img, img_profile);
                                   } else {
                                       img = HTTP_API.url_image + img;
                                       new lib_image_save_original(activity, img, img_profile);
                                   }
                               }
                               String img1 = jar_user.getJSONObject(1).getString("imageUrl");
                               if (img1.length() > 0) {
                                   String url_ = img1.substring(0, 3);
                                   if (url_.equals("http")) {
                                       new lib_image_save_original(activity, img1, img_profile1);
                                   } else {
                                       img1 = HTTP_API.url_image + img1;
                                       new lib_image_save_original(activity, img1, img_profile1);
                                   }
                               }
                           }

                           img_profile.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View v) {
                                 //  Toast.makeText(activity,arItem.get(pos).type,Toast.LENGTH_LONG).show();
                                  // adapter_myactivity.o


                                       Activity_Other_Group.TAG_ID =arItem.get(pos)._id;
                                       Intent in = new Intent(activity,Activity_Other_Group.class);
                                            String color = arItem.get(pos).activityTypeColor;
                                            String ac_name = arItem.get(pos).activityName;
                                            in.putExtra("ac_name",ac_name);
                                            in.putExtra("color",color);
                                    activity.startActivityForResult(in,Activity_Result.REQUEST_CODE_ACT);
                                     //  adapter_myactivity.get_group(arItem.get(pos)._id);


                               }
                           });

                       }catch (JSONException e){
                           e.getStackTrace();
                       }
            }



//---------------
        }
            return convertView;
        }


    private   void Action_Send(final String id_into){
        class Send_Activity extends AsyncTask<String, String, String> {
            String  TAG_STATUS ="";
            String TAG_MESSAGE = "";
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
                    HttpClient client = new DefaultHttpClient();
                    JSONObject json = new JSONObject();
                    HttpPost post = new HttpPost(HTTP_API.GET_SEND_ACTIVITY);
                    post.addHeader("X-User-Id", Activity_MyActivity.TAG_USERID);
                    post.addHeader("X-Auth-Token", Activity_MyActivity.TAG_TOKEN);

                    json.put("fromId", adapter_myactivity.TAG_ID);
                    json.put("toId", id_into);

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
                    Toast.makeText(activity, TAG_MESSAGE, Toast.LENGTH_SHORT).show();


                } catch (Exception e) {

                } catch (Throwable t) {

                }

            }

        }
        if(CheckWifi3G.isConnected(activity)){
            new Send_Activity().execute();
        }
        else{

        }
    }

    private    void Action_Accept(final String id_into){
        class Accept_Activity extends AsyncTask<String, String, String> {
            ProgressDialog progressDialog;
            String  TAG_STATUS ="";
            String TAG_MESSAGE = "";

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
                    HttpClient client = new DefaultHttpClient();
                    JSONObject json = new JSONObject();
                    // HttpPost post = new HttpPost(HTTP_API.GET_ACCEPT_ACTIVITY + "/" + TAG_ID_SINGLE);
                    HttpPost post = new HttpPost(HTTP_API.GET_ACCEPT_ACTIVITY);
                    post.addHeader("X-User-Id", Activity_MyActivity.TAG_USERID);
                    post.addHeader("X-Auth-Token", Activity_MyActivity.TAG_TOKEN);
                    json.put("fromId", adapter_myactivity.TAG_ID);
                    json.put("toId", id_into);
                    StringEntity se = new StringEntity(json.toString());
                    se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                    post.setEntity(se);
                    HttpResponse response;
                    response = client.execute(post);

                    if (response != null) {
                        HttpEntity resEntity = response.getEntity();
                        if (resEntity != null) {
                            String msg = EntityUtils.toString(resEntity);
                            // Log.v("msg-- cate", msg);
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
                    Toast.makeText(activity, TAG_MESSAGE, Toast.LENGTH_SHORT).show();

                    if(TAG_STATUS.equals("success")){
                    adapter_myactivity.activity.get_myactivity();

                    }


                } catch (Exception e) {

                } catch (Throwable t) {

                }

            }

        }
        if(CheckWifi3G.isConnected(activity)){
            new Accept_Activity().execute();
        }
        else{

        }
    }
}





