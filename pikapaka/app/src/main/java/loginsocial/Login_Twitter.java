package loginsocial;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;

import hoanvusolution.pikapaka.R;
import io.fabric.sdk.android.Fabric;

/**
 * Created by MrThanhPhong on 3/21/2016.
 */
public class Login_Twitter extends AppCompatActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "PEEvUqSOs9323hMWLferosQAC";
    private static final String TWITTER_SECRET = "rmcrPav15t3n9qEKzU6d3U0IR5EVrcTrkNSpZRnV1IG5MAkPdh";

    Long userid;
    TwitterSession session;

    private TwitterLoginButton loginButton;
    TextView textView;
    private RelativeLayout rl_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.login_twitter);

        textView = (TextView) findViewById(R.id.tv_username);

        loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // The TwitterSession is also available through:
                // Twitter.getInstance().core.getSessionManager().getActiveSession()

                Log.e("Twitter ", "Login sucessfull");
                session = result.data;

                String username = session.getUserName();
                userid = session.getUserId();


                textView.setText("Hi " + username);
                TwitterAuthToken authToken = session.getAuthToken();
                String token = authToken.token;
                String secret = authToken.secret;
                // TODO: Remove toast and use the TwitterSession's userID
                // with your app's user model
                String msg = "@" + session.getUserName() + " logged in! (#" + session.getUserId() + ")";
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

                /*TweetComposer.Builder builder = new TweetComposer.Builder(MainActivity.this).text("Just setting up my Fabric!");
                builder.show();*/
                getUserData();
            }

            @Override
            public void failure(TwitterException exception) {
                Log.e("TwitterKit", "Login with Twitter failure", exception);
            }
        });

        rl_back =(RelativeLayout)findViewById(R.id.rl_back);
        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginButton.onActivityResult(requestCode, resultCode, data);


    }

    void getUserData() {
        Twitter.getApiClient(session).getAccountService()
                .verifyCredentials(true, false, new Callback<User>() {

                    @Override
                    public void failure(TwitterException e) {

                    }

                    @Override
                    public void success(Result<User> userResult) {

                        User user = userResult.data;
                        String twitterImage = user.profileImageUrl;

                        try {
                            Log.e("imageurl", user.profileImageUrl);
                            Log.e("name", user.name);
                            Log.e("des", user.description);
                            Log.e("followers ", String.valueOf(user.followersCount));
                            Log.e("createdAt", user.createdAt);
                            Log.d("email",user.email);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }

                });
// Can also use Twitter directly: Twitter.getApiClient()

    }
}

