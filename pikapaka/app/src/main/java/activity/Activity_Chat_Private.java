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
        Log.e("TAG_ID_GROUP-",TAG_ID_GROUP);
        Log.e("TAG_ID_RECEIVER-",TAG_ID_RECEIVER);
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
                        //Log.e("conversation by user",msg);
                            JSONObject jsonObject = new JSONObject(msg);
                            TAG_STATUS = jsonObject.getString("status");
                            TAG_MESSAGE = jsonObject.getString("message");

                            JSONObject jarr = jsonObject.getJSONObject("data");

                            for (int i = 0; i < jarr.length(); i++) {

                                JSONArray jmessage = jarr.getJSONArray("messages");

                                String _id = jmessage.getJSONObject(i).getString("_id");
                                String conversationId = jmessage.getJSONObject(i).getString("conversationId");
                                JSONObject fromUser = jmessage.getJSONObject(i).getJSONObject("fromUser");
                                String id_user = fromUser.getString("_id");
                                String firstName = fromUser.getString("firstName");
                                String gender = fromUser.getString("gender");
                                String lastName = fromUser.getString("lastName");
                                String imageUrl="";
                                try {
                                    imageUrl = fromUser.getString("imageUrl");
                                }catch (JSONException e){
                                    imageUrl="";
                                }
                                mSocket.emit("join",conversationId);
                                TAG_CONVERSATION=conversationId;
//                                String content = jmessage.getJSONObject(i).getString("content");
//                                item_chat item = new item_chat(_id, conversationId,id_user, firstName, gender, lastName,imageUrl, content);
//                                arr_chat.add(item);
                            }

                        }

                        if (resEntity != null) {
                            resEntity.consumeContent();
                        }

                        client.getConnectionManager().shutdown();

                    }
                } catch (Exception e) {
                    Log.e("Error:","Error");
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
//                    if(arr_chat.size()>0){
//                        adapter_ch.notifyDataSetChanged();
//                    }
                    if(TAG_CONVERSATION.length()>0){
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
                            //Log.e("msg-- cate", msg);
                            JSONObject jsonObject = new JSONObject(msg);
                            TAG_STATUS = jsonObject.getString("status");
                            TAG_MESSAGE = jsonObject.getString("message");
                            mSocket.emit("join",TAG_CONVERSATION);
                            JSONObject jdata = jsonObject.getJSONObject("data");
                            JSONArray message = jdata.getJSONArray("messages");
                            arr_chat.clear();
                            for (int i = 0; i < message.length(); i++) {
                                String _id = message.getJSONObject(i).getString("_id");
                                String conversationId = message.getJSONObject(i).getString("conversationId");
                                JSONObject fromUser = message.getJSONObject(i).getJSONObject("fromUser");
                                String id_user = fromUser.getString("_id");
                                //JSONObject profile = fromUser.getJSONObject("profile");
                                String firstName = fromUser.getString("firstName");
                                String gender = fromUser.getString("gender");
                                String lastName = fromUser.getString("lastName");
                                String imageUrl="";
                                try {
                                     imageUrl = fromUser.getString("imageUrl");
                                }catch (JSONException e){
                                    imageUrl="";
                                }

                                String content = message.getJSONObject(i).getString("content");
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
        class Chat_Private extends AsyncTask<String, String, String> {            @Override
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
                          //  Log.e("send-- CHAT", msg);
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
                    Log.e("Error----", "Error");
                  //  progressDialog.dismiss();

                } catch (Throwable t) {
                  //  progressDialog.dismiss();

                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
               // progressDialog.dismiss();
                try {
//                    Toast.makeText(activity, TAG_MESSAGE, Toast.LENGTH_SHORT).show();
                    if(TAG_CONVERSATION.length()>0){

                        if(arr_chat.size()==0){
                            mSocket.emit("join",TAG_CONVERSATION);
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
                  //  adapter_myactivity.add_message(item);
                    arr_chat.add(item);
                    adapter_ch.notifyDataSetChanged();
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
