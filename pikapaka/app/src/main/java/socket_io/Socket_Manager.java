package socket_io;

import android.content.Context;
import android.util.Log;

import com.github.nkzawa.socketio.client.IO;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import api.HTTP_API;

/**
 * Created by MrThanhPhong on 3/12/2016.
 */
public class Socket_Manager {

    private Context context;
    public Socket_Manager (Context c){
        this.context =c;
    }
    public com.github.nkzawa.socketio.client.Socket Connect;
    {
        try {
            Connect =IO.socket(HTTP_API.baseUrl);
        } catch (URISyntaxException e) {
            Log.e("SOCKET CONNNECT----",e.toString());
        }
    }
    public void addMessage(String message){
        JSONObject sendText = new JSONObject();
        try{
            sendText.put("text",message);
            Connect.emit("message", sendText);
        }catch(JSONException e){

        }
    }

//    private Emitter.Listener handleIncomingMessages = new Emitter.Listener(){
//        @Override
//        public void call(final Object... args){
//            this.runOnUiThread(new Runnable() {
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
//                    try {
//                        imageText = data.getString("image");
//                        addImage(decodeImage(imageText));
//                    } catch (JSONException e) {
//                        //retur
//                    }
//
//                }
//            });
//        }
//    };
}
