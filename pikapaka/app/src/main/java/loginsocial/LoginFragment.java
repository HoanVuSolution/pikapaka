package loginsocial;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import hoanvusolution.pikapaka.R;

public class LoginFragment extends FragmentActivity {
    CallbackManager callbackManager;
    Button share, details;
    ShareDialog shareDialog;
    LoginButton login;
    ProfilePictureView profile;
    Dialog details_dialog;
    TextView details_txt;

    RelativeLayout rl_back;
    private FragmentActivity activity;

    private String status="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());

        try {
            init();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void init()throws Exception{
        get_reource();
        if (AccessToken.getCurrentAccessToken() != null) {
            RequestData();
            share.setVisibility(View.VISIBLE);
            details.setVisibility(View.VISIBLE);

            AccessToken token = AccessToken.getCurrentAccessToken();
//        if (token != null) {
//            Toast.makeText(activity.getApplication(), token, Toast.LENGTH_LONG).show();
//        }

            Log.i("token----------1", token.getToken().toString());
           // post_token();


        }
        onClick();
        Get_KeyHash();
    }

    private void get_reource()throws Exception{
        activity=this;
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_loginfacebook);
        login = (LoginButton) findViewById(R.id.login_button);
        profile = (ProfilePictureView) findViewById(R.id.picture);
        shareDialog = new ShareDialog(this);
        share = (Button) findViewById(R.id.share);
        details = (Button) findViewById(R.id.details);
//        login.setReadPermissions("public_profile");
//        login.setReadPermissions("email");
//        login.setReadPermissions("user_friends");
//        login.setReadPermissions("user_birthday");
        login.setReadPermissions(Arrays.asList("public_profile, email, user_birthday, user_friends"));

        share.setVisibility(View.INVISIBLE);
        details.setVisibility(View.INVISIBLE);
        details_dialog = new Dialog(this);
        details_dialog.setContentView(R.layout.dialog_details);
        details_dialog.setTitle("Details");
        details_txt = (TextView) details_dialog.findViewById(R.id.details);

        rl_back=(RelativeLayout)findViewById(R.id.rl_back);

       // login.registerCallback(new FacebookCallback<>());
    }
    private void onClick()throws Exception{
        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                details_dialog.show();
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AccessToken.getCurrentAccessToken() != null) {
                    share.setVisibility(View.INVISIBLE);
                    details.setVisibility(View.INVISIBLE);
                    profile.setProfileId(null);

//


                   // 52.26.102.232:3000/api/auth/login/local


                }
            }
        });



        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareLinkContent content = new ShareLinkContent.Builder()
                        .build();
                shareDialog.show(content);

            }
        });
        login.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        if (AccessToken.getCurrentAccessToken() != null) {
                            RequestData();
                            share.setVisibility(View.VISIBLE);
                            details.setVisibility(View.VISIBLE);
                        }


                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(FacebookException exception) {
                    }
                });

        rl_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }
    public void RequestData() {
        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {


                    @Override
                    public void onCompleted(JSONObject object,
                                            GraphResponse response) {
                        JSONObject json = response.getJSONObject();
                        Log.i("json---",json.toString());
                        try {
                            if (json != null) {
                                String text = "<b>Name :</b> "
                                        + json.getString("name")
                                        + "<br><br><b>Email :</b> "
                                        + json.getString("email")
                                        + "<br><br><b>Profile link :</b> "
                                        + json.getString("link");
                                details_txt.setText(Html.fromHtml(text));
                                profile.setProfileId(json.getString("id"));
                                Log.i("id", json.getString("id"));
                                Log.i("name", json.getString("name"));
                                Log.i("email", json.getString("email"));
                                Log.i("link", json.getString("link"));




                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,email,picture");
        request.setParameters(parameters);
        request.executeAsync();

        AccessToken token = AccessToken.getCurrentAccessToken();
        String _token=token.getToken().toString();

        if(_token!=null||!_token.equals("")){
            new http.Request_Token().send_token_social(getApplication(), _token);
        }


    }
    private  void Get_KeyHash(){


        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "hoanvusolution.pikapaka",// my package
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash: ---- Tú", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


}