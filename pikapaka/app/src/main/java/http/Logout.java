package http;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import api.HTTP_API;

/**
 * Created by MrThanhPhong on 3/15/2016.
 */
public class Logout {

    public void Send_server(final Context context, final String TAG_USERID, final String TAG_AUTH_TOKEN,final String TAG_USER,final String TAG_PASSWORD)throws Exception {
        class Request extends AsyncTask<String, String, String> {
 String message = "";
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected String doInBackground(String... args) {
                HttpClient client = new DefaultHttpClient();

                HttpResponse response;
                JSONObject json = new JSONObject();
                try {
                    HttpPost post = new HttpPost(HTTP_API.LOGOUT);
                    post.addHeader("X-User-Id", TAG_USERID);
                    post.addHeader("X-Auth-Token", TAG_AUTH_TOKEN);

                    json.put("user",TAG_USER);
                    json.put("password",TAG_PASSWORD);
                    StringEntity se = new StringEntity(json.toString());
                    se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                    post.setEntity(se);
                    response = client.execute(post);
                    if (response != null) {


                        HttpEntity resEntity = response.getEntity();
                        if (resEntity != null) {
                            String msg = EntityUtils.toString(resEntity);
                            Log.e("msg--", msg);

                            JSONObject jsonObject = new JSONObject(msg);
                           // TAG_STATUS = jsonObject.getString("status");
                            message=jsonObject.getString("message");

                        }
                        if (resEntity != null) {
                            resEntity.consumeContent();
                        }

                        client.getConnectionManager().shutdown();

                    }


                } catch (Exception e) {

                    Log.e("Error", "Error");

                } catch (Throwable t) {

                    Log.e("Error1", "Error1");

                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {

                try {
                     Log.e("logout---",message);

                } catch (Exception e) {

                } catch (Throwable t) {

                }

            }

        }


            new Request().execute();

    }
}
