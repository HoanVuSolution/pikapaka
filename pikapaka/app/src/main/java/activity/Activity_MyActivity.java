package activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
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

import adapter.adapter_myactivity;
import api.HTTP_API;
import hoanvusolution.pikapaka.MainActivity;
import hoanvusolution.pikapaka.PikaPakaApplication;
import hoanvusolution.pikapaka.R;
import internet.CheckWifi3G;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import item.item_chat;
import item.item_my_activity;
import loading.lib_loading;
import location_gps.GPSTracker;

/**
 * Created by MrThanhPhong on 2/18/2016.
 */
public class Activity_MyActivity extends AppCompatActivity implements
        View.OnFocusChangeListener {
    public  static  String TAG_USERID = "";
    public static String TAG_TOKEN = "";
    private String TAG_ID="";

    private AppCompatActivity activity;
    private ListView list;
    public ArrayList<item_my_activity> arItem = new ArrayList<item_my_activity>() ;
    private ProgressDialog progressDialog;
    private ProgressDialog progressDialog1;

    private String TAG_STATUS="";
    private String TAG_MESSAGE="";

    private ImageView img_home;
    private TextView tv_count_msg,tv_count_f;

    public static String TAG_LATITUDE="0";
    public static String TAG_LONGITUDE="0";
    private GPSTracker gps;
    public Socket mSocket;
private PikaPakaApplication application;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myactivity);
        application =(PikaPakaApplication)this.getApplication();
        mSocket = application.getSocket();
        mSocket.connect();
        mSocket.on("message:new",onMessage);
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init()throws Exception{
        get_resource();
        get_shapreference();

        Get_GPS();

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mSocket.disconnect();

    }


    private void get_resource()throws Exception{

        activity =this;
        //private TextView tv_count_msg,tv_count_f;
        tv_count_msg=(TextView) findViewById(R.id.tv_count_msg);
        tv_count_msg.setVisibility(View.GONE);

        tv_count_f=(TextView) findViewById(R.id.tv_count_f);
        tv_count_f.setVisibility(View.GONE);

        img_home=(ImageView)findViewById(R.id.img_home);
        list=(ListView)findViewById(R.id.list);
        list.setItemsCanFocus(true);
        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(activity, MainActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(in);
                finish();
            }
        });
    }
    private void get_shapreference()throws Exception{

        SharedPreferences sha_IDuser;
        SharedPreferences sha_Token;

        sha_IDuser = activity.getSharedPreferences("ID_user", 0);
        sha_Token = activity.getSharedPreferences("auth_token", 0);

        TAG_USERID = sha_IDuser.getString(
                "ID_user", "");
        TAG_TOKEN = sha_Token.getString(
                "auth_token", "");
        get_myactivity();
    }

public void get_myactivity()throws Exception{
    class Loading extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = lib_loading.f_init(Activity_MyActivity.this);

        }

        @Override
        protected String doInBackground(String... args) {
            try {
                //Looper.prepare(); //For Preparing Message Pool for the child Thread
                HttpClient client = new DefaultHttpClient();
                HttpResponse response;


                HttpGet post = new HttpGet(HTTP_API.MY_ACTIVITY);
                post.addHeader("X-User-Id", TAG_USERID);
                post.addHeader("X-Auth-Token", TAG_TOKEN);

                response = client.execute(post);

                if (response != null) {
                    HttpEntity resEntity = response.getEntity();
                    if (resEntity != null) {
                        String msg = EntityUtils.toString(resEntity);
                        Log.e("activity created--", msg);
                        JSONObject jsonObject = new JSONObject(msg);
                        TAG_STATUS = jsonObject.getString("status");
                        TAG_MESSAGE = jsonObject.getString("message");
                        arItem.clear();
                        if(TAG_STATUS.equals("success")){
                            JSONArray jsonarray = jsonObject.getJSONArray("data");
                            for(int i=0;i<jsonarray.length();i++){
                                String _id = jsonarray.getJSONObject(i).getString("_id");
                                String activityTypeIcon = jsonarray.getJSONObject(i).getString("activityTypeIcon");
                                String activityType = jsonarray.getJSONObject(i).getString("activityType");
                                String plan = "";
//                                if (!jsonarray.getJSONObject(i).getString("plan").isEmpty()) {
//                                    plan = jsonarray.getJSONObject(i).getString("plan");
//                                }
                                String minNumOfParticipants = jsonarray.getJSONObject(i).getString("minNumOfParticipants");
                                String maxNumOfParticipants = jsonarray.getJSONObject(i).getString("maxNumOfParticipants");
                                String ageFrom = jsonarray.getJSONObject(i).getString("ageFrom");
                                String ageTo = jsonarray.getJSONObject(i).getString("ageTo");
                                String gender = jsonarray.getJSONObject(i).getString("gender");
                                String distance = jsonarray.getJSONObject(i).getString("distance");
                                String expiredHours = "";
                                Log.i("expiredHours",expiredHours);
//                                if (jsonarray.getJSONObject(i).getString("expiredHours")!=null) {
//                                    expiredHours = jsonarray.getJSONObject(i).getString("expiredHours");
//                                }
                                Log.i("expiredHours1",expiredHours);
                                String meetConfirm = jsonarray.getJSONObject(i).getString("meetConfirm");
                                String publishToSocial = jsonarray.getJSONObject(i).getString("publishToSocial");
                                String userId ="";
//                                if (!jsonarray.getJSONObject(i).getString("userId").isEmpty()) {
//                                    expiredHours = jsonarray.getJSONObject(i).getString("userId");
//                                }
                                String status = jsonarray.getJSONObject(i).getString("status");
                                String type = jsonarray.getJSONObject(i).getString("type");
                                String activityTypeName = jsonarray.getJSONObject(i).getString("activityTypeName");
                                String activityTypeColor = jsonarray.getJSONObject(i).getString("activityTypeColor");
                                String createdAt = jsonarray.getJSONObject(i).getString("createdAt");

                            item_my_activity item = new item_my_activity(
                                     _id,
                                    activityTypeIcon,
                                     activityType,
                                     plan,
                                     minNumOfParticipants,
                                     maxNumOfParticipants,
                                     ageFrom,
                                     ageTo,
                                     gender,
                                     distance,
                                     expiredHours,
                                     meetConfirm,
                                     publishToSocial,
                                     userId,
                                     status,
                                     type,
                                     activityTypeName,
                                    activityTypeColor,
                                     createdAt);

                                arItem.add(item);
                            }
                        }
                    }

                    if (resEntity != null) {
                        resEntity.consumeContent();
                    }

                    client.getConnectionManager().shutdown();

                }
            } catch (Exception e) {
                progressDialog.dismiss();
                Log.i("ERROR ", "ERROR1");


            } catch (Throwable t) {
                progressDialog.dismiss();
                Log.i("ERROR ", "ERROR2");

            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            try {
                Log.e("arItem create size =",arItem.size()+"");
                if (arItem.size()>0){

                    adapter_myactivity adapter = new adapter_myactivity(Activity_MyActivity.this,arItem);
               //MyAdapter adapter = new MyAdapter(Activity_MyActivity.this,arItem);
                    list.setAdapter(adapter);
                } else {
                    Log.i("ERROR ","GET DATA");
                }

            } catch (Exception e) {

            } catch (Throwable t) {

            }

        }

    }

    if (CheckWifi3G.isConnected(activity)) {
        new Loading().execute();
    } else {
        Toast.makeText(activity, "Error Connect Internet!", Toast.LENGTH_SHORT).show();
    }
}

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

    }
// _________________________________


private Emitter.Listener onMessage = new Emitter.Listener() {

@Override
public void call(final Object... args) {
    Activity_MyActivity.this.runOnUiThread(new Runnable() {
        @Override
        public void run() {
                        String message = args[0].toString();
            Log.e("onMessage-----", message);

            JSONObject data =(JSONObject) args[0];

            String _id=null;
            String conversationId=null;
            String id_user=null;
            String firstName=null;
            String gender=null;
            String lastName=null;
            String imageUrl=null;
            String content=null;
            try {
                _id =data.getString("_id");
                conversationId =data.getString("conversationId");

                content =data.getString("content");
                JSONObject from = data.getJSONObject("fromUser");
                id_user =from.getString("_id");
                firstName=from.getString("firstName");
                gender=from.getString("gender");
                lastName=from.getString("lastName");
                imageUrl=from.getString("imageUrl");
            }catch (JSONException e){
                e.getStackTrace();
            }

            item_chat item = new item_chat(_id,conversationId,id_user,firstName,gender,lastName,imageUrl,content);


            adapter_myactivity.add_message(item);


        }
    });
}

    };
//    private void addImage(Bitmap bmp){
//        mMessages.add(new Message.Builder(Message.TYPE_MESSAGE)
//                .image(bmp).build());
//        mAdapter = new MessageAdapter( mMessages);
//        mAdapter.notifyItemInserted(0);
//        scrollToBottom();
//    }
//    private Emitter.Listener handleIncomingMessages = new Emitter.Listener(){
//        @Override
//        public void call(final Object... args){
//            activity.runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    JSONObject data = (JSONObject) args[0];
//                    String message;
//                    String imageText;
//                    try {
//                        message = data.getString("text").toString();
//                        addMessage(message);
//
//                    } catch (JSONException e) {
//                        // return;
//                    }
////                    try {
////                        imageText = data.getString("image");
////                        addImage(decodeImage(imageText));
////                    } catch (JSONException e) {
////                        //retur
////                    }
//
//                }
//            });
//        }
//    };

    private void Get_GPS()throws Exception{


        gps = new GPSTracker(activity);

        if (gps.canGetLocation())
        {
            double latitude_ = gps.getLatitude();
            double longitude_ = gps.getLongitude();

            TAG_LATITUDE = String.valueOf(latitude_);
            TAG_LONGITUDE = String.valueOf(longitude_);
            Log.e("location----","lat:"+ TAG_LATITUDE +" - lng:"+ TAG_LONGITUDE);


        }
        else{

            gps.showSettingsAlert();
        }


    }

}
