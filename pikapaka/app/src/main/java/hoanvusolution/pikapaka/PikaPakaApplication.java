package hoanvusolution.pikapaka;

import android.app.Application;

import java.net.URISyntaxException;

import api.HTTP_API;
import io.socket.client.IO;
import io.socket.client.Socket;

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
