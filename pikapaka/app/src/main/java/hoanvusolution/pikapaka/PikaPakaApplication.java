package hoanvusolution.pikapaka;

import android.app.Application;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

import api.HTTP_API;

/**
 * Created by MrThanhPhong on 3/18/2016.
 */
public class PikaPakaApplication extends Application{

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket(HTTP_API.SOCKET);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Socket getSocket() {
        return mSocket;
    }
}
