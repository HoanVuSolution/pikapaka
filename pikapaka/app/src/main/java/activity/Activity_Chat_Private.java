package activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

import java.net.URISyntaxException;
import java.util.ArrayList;

import adapter.adapter_chat_pri;
import api.HTTP_API;
import hoanvusolution.pikapaka.R;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import item.item_chat;

public class Activity_Chat_Private extends AppCompatActivity implements
        View.OnFocusChangeListener{
    private AppCompatActivity activity;
    private RelativeLayout ll_back;
    private TextView tv_user;
    private String TAG_ID_GROUP;
    private String TAG_ID_RECEIVER;
    private String TAG_NAME_USER;
    private String TAG_CONVERSATION="";
    private String TAG_STATUS="";
    private String TAG_MESSAGE="";
    public static String TAG_USERID="";
    public static String TAG_TOKEN="";

    private   ArrayList<item_chat> arr_chat= new ArrayList<item_chat>();
    private  adapter_chat_pri adapter_ch;
    private EditText ed_content;
    private Button bt_send;
    private ListView list_chat;
    private String TAG_MSG="";
    //---
    public Socket mSocket;
    {
        try {
            mSocket = IO.socket(HTTP_API.SOCKET);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //application =(PikaPakaApplication)this.getApplication();
        //mSocket = application.getSocket();
        mSocket.connect();
        mSocket.on("message:new",onMessage);
        setContentView(R.layout.activity_chat_private);
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void init()throws Exception{
        get_resource();
        get_inten();
        get_shapreference();
        OnClick();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        arr_chat.clear();
        adapter_ch.notifyDataSetChanged();
    }

    private void get_resource()throws Exception{
        activity =this;
        ll_back =(RelativeLayout)findViewById(R.id.ll_back);
        tv_user=(TextView)findViewById(R.id.tv_user);
        ed_content=(EditText) findViewById(R.id.ed_content);
        bt_send=(Button) findViewById(R.id.bt_send);
        list_chat=(ListView) findViewById(R.id.list_chat);
        adapter_ch = new adapter_chat_pri(activity,arr_chat);
        list_chat.setAdapter(adapter_ch);
        list_chat.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        list_chat.setStackFromBottom(true);
    }
    private void get_inten()throws Exception{
        Bundle b =getIntent().getExtras();
        if(b!=null){
            TAG_ID_GROUP =b.getString("TAG_ID_GROUP");
            TAG_ID_RECEIVER =b.getString("TAG_ID_RECEIVER");
            TAG_NAME_USER =b.getString("TAG_NAME_USER");
        }
        tv_user.setText(TAG_NAME_USER);

    }
    private void OnClick(){
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        bt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TAG_MSG =ed_content.getText().toString();
                if(TAG_MSG.length()>0){
                    send_msg(TAG_MSG);
                }
                ed_content.setText("");
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
        Get_Conversation();
    }
    private void Get_Conversation()throws Exception{

        Log.e("id_user",TAG_ID_RECEIVER);
        Log.e("id_group",TAG_ID_GROUP);
        class Get_Conversations extends AsyncTask<String, String, String> {
            ProgressDialog progressDialog;
            String _id;
            String type;
            String groupId;
            String users;
            String mutedUsers;
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
                    JSONObject json = new JSONObject();
                   HttpGet post = new HttpGet(HTTP_API.CHAT_GET_CONVERSATION_BYUSER+TAG_ID_RECEIVER+"/" + TAG_ID_GROUP);
                    //HttpGet post = new HttpGet(HTTP_API.CHAT_GET_CONVERSATION + TAG_ID_RECEIVER);
                    post.addHeader("X-User-Id", Activity_MyActivity.TAG_USERID);
                    post.addHeader("X-Auth-Token", Activity_MyActivity.TAG_TOKEN);
                    HttpResponse response;
                    response = client.execute(post);
                    arr_chat.clear();
                    if (response != null) {
                        HttpEntity resEntity = response.getEntity();
                        if (resEntity != null) {
                            String msg = EntityUtils.toString(resEntity);
                     // Log.e("conversation by user",msg);
                            JSONObject jsonObject = new JSONObject(msg);
                            TAG_STATUS = jsonObject.getString("status");
                            TAG_MESSAGE = jsonObject.getString("message");
                            JSONObject jarr = jsonObject.getJSONObject("data");
                            if(jarr!=null){
                                for (int i = 0; i < jarr.length(); i++) {
                                    JSONArray jmessage = jarr.getJSONArray("messages");
                                    String _id = jmessage.getJSONObject(i).getString("_id");
                                    String conversationId = jmessage.getJSONObject(i).getString("conversationId");
//                                    JSONObject fromUser = jmessage.getJSONObject(i).getJSONObject("fromUser");
//                                    String id_user = fromUser.getString("_id");
//                                    String firstName = fromUser.getString("firstName");
//                                    String gender = fromUser.getString("gender");
//                                    String lastName = fromUser.getString("lastName");
//                                    String imageUrl="";
//                                    try {
//                                        imageUrl = fromUser.getString("imageUrl");
//                                    }catch (JSONException e){
//                                        imageUrl="";
//                                    }

                                    TAG_CONVERSATION=conversationId;

                                }
                            }
                        }
                        if (resEntity != null) {
                            resEntity.consumeContent();
                        }

                        client.getConnectionManager().shutdown();

                    }
                } catch (Exception e) {
                    Log.e("Error conversation","Error");
                    progressDialog.dismiss();

                } catch (Throwable t) {
                    progressDialog.dismiss();

                }
                return null;
            }
                    // 7cY97D2LLrQEsdirA
            @Override
            protected void onPostExecute(String result) {
                progressDialog.dismiss();
                try {
//                    if(arr_chat.size()>0){
//                        adapter_ch.notifyDataSetChanged();
//                    }

                Log.e("TAG_CON private",TAG_CONVERSATION);
                    if(TAG_CONVERSATION.length()>0){
                        mSocket.emit("join",TAG_CONVERSATION);
                        Load_ListChat();
                    }

                } catch (Exception e) {

                } catch (Throwable t) {

                }

            }

        }
        new Get_Conversations().execute();
    }
    private void Load_ListChat()throws Exception{

        class Load_ListChat extends AsyncTask<String, String, String> {
            ProgressDialog progressDialog;
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
                    JSONObject json = new JSONObject();
                    HttpGet post = new HttpGet(HTTP_API.CHAT_LOADLIST + TAG_CONVERSATION);
                    post.addHeader("X-User-Id", TAG_USERID);
                    post.addHeader("X-Auth-Token", TAG_TOKEN);
                    HttpResponse response;
                    response = client.execute(post);
                    if (response != null) {
                        HttpEntity resEntity = response.getEntity();
                        if (resEntity != null) {
                            String msg = EntityUtils.toString(resEntity);
                          // Log.e("list chat-- cate", msg);
                            JSONObject jsonObject = new JSONObject(msg);
                            TAG_STATUS = jsonObject.getString("status");
                            TAG_MESSAGE = jsonObject.getString("message");
                            mSocket.emit("join",TAG_CONVERSATION);
                            JSONObject jdata = jsonObject.getJSONObject("data");
                            JSONArray message = jdata.getJSONArray("messages");

                            for (int i = 0; i < message.length(); i++) {
                                String _id = "";
                                if(!message.getJSONObject(i).isNull("_id")){
                                    _id=message.getJSONObject(i).getString("_id");
                                }
                                String conversationId ="";
                                if(!message.getJSONObject(i).isNull("_id")){
                                    conversationId=message.getJSONObject(i).getString("conversationId");
                                }
                                JSONObject fromUser = message.getJSONObject(i).getJSONObject("fromUser");
                                String id_user="";
                                if(!fromUser.isNull("_id")){
                                     id_user = fromUser.getString("_id");
                                }
                                String firstName="";
                                if(!fromUser.isNull("firstName")){
                                    firstName = fromUser.getString("firstName");
                                }
                                String gender ="";
                                if(!fromUser.isNull("gender")){
                                    gender = fromUser.getString("gender");
                                }
                                String lastName = "";
                                if(!fromUser.isNull("lastName")){
                                    lastName = fromUser.getString("lastName");
                                }
                                String imageUrl="";
                                if(!fromUser.isNull("imageUrl")){
                                    imageUrl = fromUser.getString("imageUrl");
                                }
                                String content = "";
                                if(!message.getJSONObject(i).isNull("content")){
                                    content=message.getJSONObject(i).getString("content");
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

                    Log.e("arr_chat size = ",arr_chat.size()+"");
                    if (arr_chat.size() > 0) {

                        adapter_ch.notifyDataSetChanged();
                      //  Scroll_Listview();

                    } else {
//                        Toast.makeText(activity, TAG_MESSAGE, Toast.LENGTH_SHORT).show();
                    }


                } catch (Exception e) {
                } catch (Throwable t) {
                }

            }

        }
        new Load_ListChat().execute();
    }
    private void send_msg(final String content){
        //Log.e("content-------",content);
        class Chat_Private extends AsyncTask<String, String, String> {
            String id_sender="";
            String sender="";
            String content_="";
            String  imageUrl="";

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(String... args) {
                try {
                    HttpClient client = new DefaultHttpClient();
                    JSONObject json = new JSONObject();
                    HttpPost post = new HttpPost(HTTP_API.CHAT_PRIVATE);
                    post.addHeader("X-User-Id", TAG_USERID);
                    post.addHeader("X-Auth-Token", TAG_TOKEN);
                    json.put("toUser", TAG_ID_RECEIVER);
                    json.put("toGroup", TAG_ID_GROUP);
                    json.put("content", content);

                    StringEntity se = new StringEntity(json.toString());
                    se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                    post.setEntity(se);
                    HttpResponse response;
                    response = client.execute(post);
                    if (response != null) {
                        HttpEntity resEntity = response.getEntity();
                        if (resEntity != null) {
                            String msg = EntityUtils.toString(resEntity);
                         Log.e("send-- CHAT", msg);
                            JSONObject jsonObject = new JSONObject(msg);
                            TAG_STATUS = jsonObject.getString("status");
                            TAG_MESSAGE = jsonObject.getString("message");
                            JSONObject jdata = jsonObject.getJSONObject("data");
                            JSONObject jlastmsg = jdata.getJSONObject("lastMessage");
                            TAG_CONVERSATION =jlastmsg.getString("conversationId");
                            JSONObject jformU=jlastmsg.getJSONObject("fromUser");

                            if(!jformU.isNull("_id")){
                                id_sender=jformU.getString("_id");
                            }
                            if(!jformU.isNull("firstName")){
                                sender=jformU.getString("firstName");
                            }
                            if(!jformU.isNull("imageUrl")){
                                imageUrl=jformU.getString("imageUrl");
                            }
                            if(!jlastmsg.isNull("content")){
                                content_=jlastmsg.getString("content");
                            }

                        }

                        if (resEntity != null) {
                            resEntity.consumeContent();
                        }

                        client.getConnectionManager().shutdown();

                    }
                } catch (Exception e) {
                    Log.e("Error----", "Error");
                  //  progressDialog.dismiss();

                } catch (Throwable t) {
                  //  progressDialog.dismiss();

                }
                return null;
            }

            // g cwivdXFuABKuhWcmS
            // c  dCZG2ov9NHMBrz5Kp
            @Override
            protected void onPostExecute(String result) {
               // progressDialog.dismiss();
                try {
                    if(TAG_CONVERSATION.length()>0){
                      //  Log.e("TAG_CONVERSATION",TAG_CONVERSATION);
                        mSocket.emit("join",TAG_CONVERSATION);
                        if(arr_chat.size()==0){
                            arr_chat.add(new item_chat("",TAG_CONVERSATION,id_sender,sender,"gender",imageUrl,"lastName",content_));
                         adapter_ch.notifyDataSetChanged();
                        }
                    }

               } catch (Exception e) {

                } catch (Throwable t) {

                }

            }

        }
        new Chat_Private().execute();
    }
    private Emitter.Listener onMessage = new Emitter.Listener() {

        @Override
        public void call(final Object... args) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String message = args[0].toString();
                   // Log.e("onMessage-----", message);
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
                        if(!data.isNull("_id")){
                            _id =data.getString("_id");
                        }
                        if(!data.isNull("_id")){
                            conversationId =data.getString("conversationId");
                        }
                        if(!data.isNull("content")){
                            content =data.getString("content");
                        }
                        JSONObject from = data.getJSONObject("fromUser");
                        if(from!=null){

                            if(!from.isNull("_id")){
                                id_user =from.getString("_id");
                            }
                            if(!from.isNull("firstName")){
                                firstName=from.getString("firstName");
                            }
                            if(!from.isNull("gender")){
                                gender=from.getString("gender");
                            }
                            if(!from.isNull("lastName")){
                                lastName=from.getString("lastName");
                            }
                            if(!from.isNull("imageUrl")){
                                imageUrl=from.getString("imageUrl");
                            }
                        }

                    }catch (JSONException e){
                        e.getStackTrace();
                    }
                    Log.e("TAG_CONVERSATION",TAG_CONVERSATION);
                    Log.e("conversationId",conversationId);
                    if(TAG_CONVERSATION.equals(conversationId)){
                        item_chat item = new item_chat(_id,conversationId,id_user,firstName,gender,lastName,imageUrl,content);
                        arr_chat.add(item);
                        adapter_ch.notifyDataSetChanged();
                    }

                }
            });
        }

    };
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

    }
}
