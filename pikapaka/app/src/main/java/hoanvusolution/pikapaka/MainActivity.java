package hoanvusolution.pikapaka;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import activity.Activity_Login;
import activity.Activity_MyActivity;
import activity.Activity_Profile;
import api.HTTP_API;
import fragment.Home_Fragment;
import image.lib_image_save_original;
import internet.CheckWifi3G;
import shared_prefs.Commit_Sha;
import util.Activity_Result;
import util.dataString;

public class MainActivity extends AppCompatActivity {
    private String TAG_STATUS;
    private String TAG_IMAGE_URL="";

    //    private String TAG_USERID = "";
//    private String TAG_TOKEN = "";
    //Defining Variables
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    private ActionBarDrawerToggle actionBarDrawerToggle;

    // profile
    public static boolean get_profile = false;
    View header;
    ImageView profile_image;
    TextView first_name;
    TextView email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing Toolbar and setting it as the actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                //Checking if the item is in checked state or not, if not make it in checked state
                if(menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()){


                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.inbox:
                       // Toast.makeText(getApplicationContext(),"Inbox Selected",Toast.LENGTH_SHORT).show();
                        Home_Fragment fragment = new Home_Fragment();
                        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frame,fragment);
                        fragmentTransaction.commit();
                        return true;

                    // For rest of the options we just show a toast on click

                    case R.id.my_activity:
                        Intent in = new Intent(MainActivity.this, Activity_MyActivity.class);
                        startActivity(in);
                        return true;

                    case R.id.logout:
                        Commit_Sha.Clear_Token(MainActivity.this);
                        Intent in1 = new Intent(MainActivity.this, Activity_Login.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                                | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(in1);
                        finish();
                        return true;
                    default:

                        return true;

                }
            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

      //  actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
       // actionBarDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_menuoption);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.openDrawer, R.string.closeDrawer){

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();

        actionBarDrawerToggle.setDrawerIndicatorEnabled(false);

        // mDrawerToggle.setHomeAsUpIndicator(R.drawable.menu_icon);
        toolbar.setNavigationIcon(R.drawable.ic_menuoption);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        ////
        view_profile();
        try {
            get_shapreference();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Notyfication();
        Home_Fragment fragment = new Home_Fragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();



    }


    @Override
    protected void onStart() {
        super.onStart();
        if(get_profile == true){
            try {
                get_shapreference();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
       // get_profile = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private void get_shapreference() throws Exception {

        SharedPreferences sha_IDuser;
        SharedPreferences sha_Token;

        sha_IDuser = MainActivity.this.getSharedPreferences("ID_user", 0);
        sha_Token = MainActivity.this.getSharedPreferences("auth_token", 0);

      String  TAG_USERID = sha_IDuser.getString(
                "ID_user", "");
      String  TAG_TOKEN = sha_Token.getString(
                "auth_token", "");

        Get_Profile(MainActivity.this,TAG_USERID,TAG_TOKEN);

    }
    private void Notyfication(){
        TextView tv_noty_mail=(TextView)findViewById(R.id.tv_noty_mail);
        tv_noty_mail.setVisibility(View.GONE);
    }
//    private void Profile(){
//       View header =navigationView.inflateHeaderView(R.layout.header);
//        ImageView profile_image =(ImageView)header.findViewById(R.id.profile_image);
//        TextView first_name=(TextView)header.findViewById(R.id.first_name);
//        TextView email=(TextView)header.findViewById(R.id.email);
//        first_name.setText(dataString.TAG_FIRSTNAME);
//        email.setText(dataString.TAG_EMAIL);
//        profile_image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent in = new Intent(MainActivity.this, Activity_Profile.class);
//                startActivityForResult(in, Activity_Result.REQUEST_CODE_ACT);
//            }
//        });
//    }

    public void Get_Profile(final Context context, final String TAG_USERID, final String TAG_AUTH_TOKEN)throws Exception {
        class Request extends AsyncTask<String, String, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected String doInBackground(String... args) {
                HttpClient client = new DefaultHttpClient();

                HttpResponse response;
                try {
                    HttpGet post = new HttpGet(HTTP_API.Request_Token);
                    post.addHeader("X-User-Id", TAG_USERID);
                    post.addHeader("X-Auth-Token", TAG_AUTH_TOKEN);

                    Log.i("URL--------", post.getAllHeaders().toString());
                    response = client.execute(post);
                    if (response != null) {


                        HttpEntity resEntity = response.getEntity();
                        if (resEntity != null) {
                            String msg = EntityUtils.toString(resEntity);
                            Log.e("msg--", msg);

                            JSONObject jsonObject = new JSONObject(msg);
                            TAG_STATUS = jsonObject.getString("status");

                            if (TAG_STATUS.equals("success")) {
                                JSONObject data = jsonObject.getJSONObject("data");

                                if(!data.isNull("firstName")){
                                    dataString.TAG_FIRSTNAME = data.getString("firstName");

                                }
                                if(!data.isNull("lastName")){
                                    dataString.TAG_LASTNAME = data.getString("lastName");

                                }

                                if(!data.isNull("displayName")){
                                    dataString.TAG_DISPLAYNAME = data.getString("displayName");

                                }
                                if(!data.isNull("gender")){
                                    dataString.TAG_GENDER = data.getString("gender");
                                }
                                if(!data.isNull("email")){
                                    dataString.TAG_EMAIL = data.getString("email");
                                }
                                if(!data.isNull("age")){
                                    dataString.TAG_AGE = data.getString("age");
                                }
                                if(!data.isNull("imageUrl")){
                                    TAG_IMAGE_URL =data.getString("imageUrl");
                                    dataString.TAG_IMAGE_URL =TAG_IMAGE_URL;
                                }



                            }

                        }
                        if (resEntity != null) {
                            resEntity.consumeContent();
                        }

                        client.getConnectionManager().shutdown();

                    }


                } catch (Exception e) {

                    Log.e("Error--", "Erro----r");

                } catch (Throwable t) {

                    Log.e("Error1", "Error1");

                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {

                try {

                    if (TAG_STATUS.equals("success")) {
                        Log.e("get_profile","success");
                        first_name.setText(dataString.TAG_FIRSTNAME);
                        email.setText(dataString.TAG_EMAIL);
                        Log.e("TAG_IMAGE_URL",dataString.TAG_IMAGE_URL);

                        if(dataString.TAG_IMAGE_URL.length()>0){
                            String check = dataString.TAG_IMAGE_URL.substring(0,3);
                            String url;
                            if(check.equals("http")){
                                new lib_image_save_original(MainActivity.this,dataString.TAG_IMAGE_URL,profile_image);

                            }
                            else{
                                url=HTTP_API.url_image+dataString.TAG_IMAGE_URL;
                                new lib_image_save_original(MainActivity.this,url,profile_image);

                            }
                        }
//
                    } else {
                        Log.e("get_profile","fail");
                    }


                } catch (Exception e) {

                } catch (Throwable t) {

                }

            }

        }

        if (CheckWifi3G.isConnected(MainActivity.this)) {
            new Request().execute();
        }
    }

    private void get_notify(){
        class Request extends AsyncTask<String, String, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected String doInBackground(String... args) {
                HttpClient client = new DefaultHttpClient();
                HttpResponse response;
                try {
                    HttpGet post = new HttpGet(HTTP_API.GET_ALL_NOTIFICATION);
//                    post.addHeader("X-User-Id", TAG_USERID);
//                    post.addHeader("X-Auth-Token", TAG_TOKEN);

                    response = client.execute(post);
                    if (response != null) {


                        HttpEntity resEntity = response.getEntity();
                        if (resEntity != null) {
                            String msg = EntityUtils.toString(resEntity);
                            Log.e("msg--", msg);

                            JSONObject jsonObject = new JSONObject(msg);
                            TAG_STATUS = jsonObject.getString("status");

                            if (TAG_STATUS.equals("success")) {
                                JSONObject data = jsonObject.getJSONObject("data");
                                if(data.length()>0){

                                }


                            }

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

                    if (TAG_STATUS.equals("success")) {
                        Log.e("get_profile","success");


                        Log.e("get_profile","success");
                    } else {
                        Log.e("get_profile","fail");
                    }


                } catch (Exception e) {

                } catch (Throwable t) {

                }

            }

        }

        if (CheckWifi3G.isConnected(MainActivity.this)) {
            new Request().execute();
        }
    }

    private void view_profile(){
         header =navigationView.inflateHeaderView(R.layout.header);
         profile_image =(ImageView)header.findViewById(R.id.profile_image);
         first_name=(TextView)header.findViewById(R.id.first_name);
         email=(TextView)header.findViewById(R.id.email);

        first_name.setText(dataString.TAG_FIRSTNAME);
        email.setText(dataString.TAG_EMAIL);

                profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, Activity_Profile.class);
                startActivityForResult(in, Activity_Result.REQUEST_CODE_ACT);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}

