package http;

import android.content.Context;
import android.os.Looper;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import api.HTTP_API;
import item.item_activity_types;
import item.item_categories;

/**
 * Created by MrThanhPhong on 2/25/2016.
 */
public class get_all_categories {
    public static ArrayList<item_categories> arItem_cat = new ArrayList<item_categories>();
    public static ArrayList<item_activity_types> arItem_activity = new ArrayList<item_activity_types>();
    public void GET(final Context context, final String idUser, final String token) {

        Thread t = new Thread() {

            public void run() {
                Looper.prepare(); //For Preparing Message Pool for the child Thread
                HttpClient client = new DefaultHttpClient();
               // HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
                HttpResponse response;

                try {
                    HttpGet post = new HttpGet(HTTP_API.GET_ALL_CATEGORIES);
                    post.addHeader("X-User-Id", idUser);
                    post.addHeader("X-Auth-Token", token);
                    Log.i("idUser-------", idUser);
                    Log.i("token--------", token);
                    Log.i("URL--------", post.getAllHeaders().toString());

//                        StringEntity se = new StringEntity( json.toString());
//                        se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
//                        post.setEntity(se);
                    response = client.execute(post);

                    /*Checking response */
                    if (response != null) {
                        HttpEntity resEntity = response.getEntity();
                        if (resEntity != null) {
                            String msg = EntityUtils.toString(resEntity);
                            Log.i("msg-- cate",msg);

                            JSONObject jsonObject = new JSONObject(msg);
                            String status =jsonObject.getString("status");

                            Log.e("status--", status);
                             String _id="";
                             String name="";
                             String description="";
                             String color="";
                             String activityTypes="";

                            JSONArray jsonaTypes=null ;
                            JSONObject object=null;
                            JSONArray jsonarray = jsonObject.getJSONArray("data");
                            for(int i=0;i<jsonarray.length();i++){

                                _id = jsonarray.getJSONObject(i).getString("_id");
                                name = jsonarray.getJSONObject(i).getString("name");
                                description = jsonarray.getJSONObject(i).getString("description");
                                color = jsonarray.getJSONObject(i).getString("color");
                                activityTypes = jsonarray.getJSONObject(i).getString("activityTypes");

                                item_categories item = new item_categories(_id,name,description,color,activityTypes);
                                arItem_cat.add(item);

                                jsonaTypes = jsonarray.getJSONObject(i).getJSONArray("activityTypes");
                                if(jsonaTypes.length()>0){
                                    for(int j=0;j<jsonaTypes.length();j++){
                                        //JSONObject  object1 = jsonaTypes.getJSONObject(j);
                                        String _id1 =jsonaTypes.getJSONObject(j).getString("_id");
                                        String nane =jsonaTypes.getJSONObject(j).getString("name");
                                        String description1 =jsonaTypes.getJSONObject(j).getString("description");
                                        String categoryId =jsonaTypes.getJSONObject(j).getString("categoryId");

//                                        item_activity_types item1 = new item_activity_types(_id1,nane,description1,categoryId);
//                                    arItem_activity.add(item1);
                                    }
                                }
                            }
                            Log.e("arr_activty ----1",arItem_activity.size()+"");



                            //parseJSON(msg);

                        }
                        if (resEntity != null) {
                            resEntity.consumeContent();
                        }

                        client.getConnectionManager().shutdown();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    //createDialog("Error", "Cannot Estabilish Connection");
                    Log.e("Error", "Error");

                }

                Looper.loop(); //Loop in the message queue
            }
        };

        t.start();

    }
}
