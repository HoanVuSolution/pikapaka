package activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import adapter.adapter_custom_fields;
import api.HTTP_API;
import hoanvusolution.pikapaka.R;
import internet.CheckWifi3G;
import item.item_custom_fields;
import item.item_selection_custom;
import loading.lib_loading;
import location_gps.GPSTracker;
import util.dataString;

/**
 * Created by MrThanhPhong on 2/18/2016.
 */
public class Activity_YourActivity extends AppCompatActivity {
    private String TAG_USERID = "";
    private String TAG_TOKEN = "";
    public static String TAG_COLOR = "";
    private String TAG_STATUS = "";
    private String TAG_MESSAGE = "";


    public static String TAG_ID = "";

    public static String TAG_MINNUMOF_ = "2";

    public static String TAG_MAXNUMOF_ = "4";
    public static String TAG_AGEFROM = "25";
    public static String TAG_AGETO = "37";
    public static String TAG_PARTNERGEGER = "Anything goes";
    public static String TAG_DISTANCE = "10";
    public static String TAG_EXPIREDHOURS = "2";
    public String TAG_PLAN = "";
    private String  TAG_LATITUDE="0";
    private static String TAG_LONGITUDE="0";

    private AppCompatActivity activity;
    private TextView tv_name, tv_desciption;
    private TextView tv_change;
    private LinearLayout  ll_3, ll_show;
    private EditText ed_plan;

    private ListView list;

    private boolean change = true;

    private LinearLayout ll_1, ll_back, ll_findparnter;
    private View view_line;
    double hours = 2;

    //-----------

    private LinearLayout ll_max;
    private TextView tv_max;
    private LinearLayout ll_min;
    private TextView tv_min;
    private LinearLayout ll_par_gender;
    private TextView tv_part_gender;
    private LinearLayout ll_distance;
    private TextView tv_km;
    private LinearLayout ll_type_distance;

    private TextView tv_type_dis;
    private LinearLayout ll_experss;
    private TextView tv_hours;

    private LinearLayout age1;
    private TextView tv_minage;
    private LinearLayout age2;
    private TextView tv_maxage;
    private LinearLayout ll_bg;

    private int Age_User;

    private List<String> list_age = new ArrayList<String>();
    private int select = 1;

    // private ImageView img;
    private boolean check_participants = true;

    public ArrayList<item_custom_fields> arr_Icustom = new ArrayList<item_custom_fields>();
    public ArrayList<item_selection_custom> arr_I_seelctiom = new ArrayList<item_selection_custom>();
    GPSTracker gps;
    private TextView  tv_count_friend,tv_count_msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youractivity);

        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() throws Exception {

        get_resource();

        get_shapreference();
        //mSocket.connect();
        Get_Info();
        Onclick();

    }

    private void get_resource() throws Exception {

        activity = this;
        Log.e("_id", TAG_ID);

        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_name.setText("");
        tv_desciption.setText("");
        tv_count_friend = (TextView) findViewById(R.id.tv_count_friend);
        tv_count_msg = (TextView) findViewById(R.id.tv_count_msg);
        tv_count_friend.setVisibility(View.GONE);
        tv_count_msg.setVisibility(View.GONE);


        tv_desciption = (TextView) findViewById(R.id.tv_desciption);
        ll_bg = (LinearLayout) findViewById(R.id.ll_bg);
        ll_1 = (LinearLayout) findViewById(R.id.ll_1);
        if (TAG_COLOR.length() > 0) {
            ll_bg.setBackgroundColor(Color.parseColor(TAG_COLOR.toString()));
        }

        list=(ListView)findViewById(R.id.list);


        ll_3 = (LinearLayout) findViewById(R.id.ll_3);
        ll_show = (LinearLayout) findViewById(R.id.ll_show);
        ll_show.setVisibility(View.GONE);

        ed_plan = (EditText) findViewById(R.id.ed_plan);
        tv_change = (TextView) findViewById(R.id.tv_change);


        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        ll_findparnter = (LinearLayout) findViewById(R.id.ll_findparnter);
        view_line = (View) findViewById(R.id.view_line);
        view_line.setVisibility(View.GONE);

        //--------
        ll_max = (LinearLayout) findViewById(R.id.ll_max);
        tv_max = (TextView) findViewById(R.id.tv_max);
        ll_min = (LinearLayout) findViewById(R.id.ll_min);
        tv_min = (TextView) findViewById(R.id.tv_min);
        ll_par_gender = (LinearLayout) findViewById(R.id.ll_par_gender);
        tv_part_gender = (TextView) findViewById(R.id.tv_part_gender);
        ll_distance = (LinearLayout) findViewById(R.id.ll_distance);
        tv_km = (TextView) findViewById(R.id.tv_km);
        ll_type_distance = (LinearLayout) findViewById(R.id.ll_type_distance);

        tv_type_dis = (TextView) findViewById(R.id.tv_type_dis);
        ll_experss = (LinearLayout) findViewById(R.id.ll_experss);
        tv_hours = (TextView) findViewById(R.id.tv_hours);


        age1 = (LinearLayout) findViewById(R.id.age1);
        tv_minage = (TextView) findViewById(R.id.tv_minage);
        age2 = (LinearLayout) findViewById(R.id.age2);
        tv_maxage = (TextView) findViewById(R.id.tv_maxage);

        //---

    }

    private void Onclick() throws Exception {
//        img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent in = new Intent(activity,Activity_MyActivity.class);
//              //  startActivityForResult(in, Activity_Result.REQUEST_CODE_ACT);
//                startActivity(in);
//            }
//        });

        ed_plan.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        tv_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (change) {
                    ed_plan.setBackgroundColor(Color.parseColor("#efefef"));

//                    ll_2.setBackgroundResource(R.drawable.bacground_hine);
//                    ll_3.setBackgroundResource(R.drawable.bacground_hine);
//                    tv_text1.setTextColor(Color.parseColor("#868787"));
//                    tv_text2.setTextColor(Color.parseColor("#868787"));
//                    tv_text3.setTextColor(Color.parseColor("#868787"));
//                    tv_text4.setTextColor(Color.parseColor("#868787"));

                    ll_show.setVisibility(View.VISIBLE);
                    view_line.setVisibility(View.VISIBLE);
                    change = false;
                } else {
                    ll_show.setVisibility(View.GONE);
                    view_line.setVisibility(View.GONE);
                    change = true;

                    ed_plan.setBackgroundColor(Color.parseColor("#ffffff"));

                  //  ll_3.setBackgroundResource(R.drawable.bacground_while_border);

                }
            }
        });

        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ll_findparnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    check_input();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        //------------------
        ll_max.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    String[] item = new String[]{"2", "3", "4", "5", "10", "20"};
                    // String[] item = new String[] { getString(R.string.male), getString(R.string.female) };

                    new AlertDialog.Builder(activity).setTitle("Maximal")
                            .setItems(item, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    switch (which) {
                                        case 0:
                                            tv_max.setText("2");
                                            ///gend_ = 1;
                                            break;
                                        case 1:
                                            tv_max.setText("3");
                                            // gend_ = 1;
                                            break;
                                        case 2:
                                            tv_max.setText("4");
                                            // gend_ = 1;
                                            break;
                                        case 3:
                                            tv_max.setText("5");
                                            // gend_ = 1;
                                            break;
                                        case 4:
                                            tv_max.setText("10");
                                            // gend_ = 1;
                                            break;
                                        case 5:
                                            tv_max.setText("20");
                                            // gend_ = 1;
                                            break;

                                    }

                                    int max = Integer.parseInt(tv_max.getText().toString());
                                    int min = Integer.parseInt(tv_min.getText().toString());

                                    if (max < min) {
                                        Toast.makeText(Activity_YourActivity.this, "Max participants > Min participants", Toast.LENGTH_SHORT).show();
                                        check_participants = false;
                                    } else {
                                        check_participants = true;
                                    }
                                    dialog.dismiss();
                                }
                            }).show();
                }
            }
        });
        ll_min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    String[] item = new String[]{"2", "3", "4", "5", "10", "20"};
                    // String[] item = new String[] { getString(R.string.male), getString(R.string.female) };

                    new AlertDialog.Builder(activity).setTitle("Minimal")
                            .setItems(item, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    switch (which) {
                                        case 0:
                                            tv_min.setText("2");
                                            ///gend_ = 1;
                                            break;
                                        case 1:
                                            tv_min.setText("3");
                                            // gend_ = 1;
                                            break;
                                        case 2:
                                            tv_min.setText("4");
                                            // gend_ = 1;
                                            break;
                                        case 3:
                                            tv_min.setText("5");
                                            // gend_ = 1;
                                            break;
                                        case 4:
                                            tv_min.setText("10");
                                            // gend_ = 1;
                                            break;
                                        case 5:
                                            tv_min.setText("20");
                                            // gend_ = 1;
                                            break;

                                    }

                                    int max = Integer.parseInt(tv_max.getText().toString());
                                    int min = Integer.parseInt(tv_min.getText().toString());

                                    if (max < min) {
                                        Toast.makeText(Activity_YourActivity.this, "Max participants > Min participants", Toast.LENGTH_SHORT).show();
                                        check_participants = false;
                                    } else {
                                        check_participants = true;
                                    }

                                    dialog.dismiss();
                                }
                            }).show();
                }
            }
        });

        age1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select = 1;

                int a1 = Integer.parseInt(tv_minage.getText().toString());
                int a2 = Integer.parseInt(tv_maxage.getText().toString());
                list_age.clear();
                for (int i = 12; i <= a2; i++) {
                    list_age.add(i + "");
                }
                try {
                    dialog();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        age2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select = 2;
                int a1 = Integer.parseInt(tv_minage.getText().toString());
                int a2 = Integer.parseInt(tv_maxage.getText().toString());
                list_age.clear();
                for (int i = a1; i <= 120; i++) {
                    list_age.add(i + "");
                }
                try {
                    dialog();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        ll_par_gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] item = new String[]{"Man", "Woman", "Anything goes"};
                // String[] item = new String[] { getString(R.string.male), getString(R.string.female) };

                new AlertDialog.Builder(activity).setTitle("Partnet gender")
                        .setItems(item, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                switch (which) {
                                    case 0:
                                        tv_part_gender.setText("Man");
                                        ///gend_ = 1;
                                        break;
                                    case 1:
                                        tv_part_gender.setText("Woman");
                                        // gend_ = 1;
                                        break;
                                    case 2:
                                        tv_part_gender.setText("Anything goes");
                                        // gend_ = 1;
                                        break;

                                }
                                dialog.dismiss();
                            }
                        }).show();

            }
        });

        ll_distance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] item = new String[]{"10", "20", "30", "40", "50", "60", "70", "80", "90", "100"};
                // String[] item = new String[] { getString(R.string.male), getString(R.string.female) };

                new AlertDialog.Builder(activity).setTitle("Distance")
                        .setItems(item, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                switch (which) {
                                    case 0:
                                        tv_km.setText("10");
                                        ///gend_ = 1;
                                        break;
                                    case 1:
                                        tv_km.setText("20");
                                        // gend_ = 1;
                                        break;
                                    case 2:
                                        tv_km.setText("30");
                                        // gend_ = 1;
                                        break;
                                    case 3:
                                        tv_km.setText("40");
                                        // gend_ = 1;
                                        break;
                                    case 4:
                                        tv_km.setText("50");
                                        // gend_ = 1;
                                        break;
                                    case 5:
                                        tv_km.setText("60");
                                        // gend_ = 1;
                                        break;
                                    case 6:
                                        tv_km.setText("70");
                                        // gend_ = 1;
                                        break;
                                    case 7:
                                        tv_km.setText("80");
                                        // gend_ = 1;
                                        break;
                                    case 8:
                                        tv_km.setText("90");
                                        // gend_ = 1;
                                        break;
                                    case 9:
                                        tv_km.setText("100");
                                        // gend_ = 1;
                                        break;

                                }
                                dialog.dismiss();
                            }
                        }).show();

            }
        });

        ll_type_distance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] item = new String[]{"kilometers", "miles"};
                // String[] item = new String[] { getString(R.string.male), getString(R.string.female) };

                new AlertDialog.Builder(activity).setTitle("Unit")
                        .setItems(item, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                switch (which) {
                                    case 0:
                                        tv_type_dis.setText("kilometers");
                                        ///gend_ = 1;
                                        break;
                                    case 1:
                                        tv_type_dis.setText("miles");
                                        // gend_ = 1;
                                        break;


                                }

                                dialog.dismiss();
                            }
                        }).show();
            }

        });


        ll_experss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    String[] item = new String[]{"30 Minutes", "1 Hour", "2 Hours", "4 Hours", "12 Hours", "1 Week", "2 Weeks"};

                    new AlertDialog.Builder(activity).setTitle("Activity expires?")
                            .setItems(item, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    switch (which) {
                                        case 0:
                                            tv_hours.setText("30 Minutes");
                                            hours = 0.5;
                                            break;
                                        case 1:
                                            tv_hours.setText("1 Hour");
                                            hours = 1;
                                            break;
                                        case 2:
                                            tv_hours.setText("2 Hours");
                                            hours = 2;
                                            break;
                                        case 3:
                                            tv_hours.setText("4 Hours");
                                            hours = 4;
                                            break;
                                        case 4:
                                            tv_hours.setText("12 Hours");
                                            hours = 12;
                                            break;
                                        case 5:
                                            tv_hours.setText("1 Week");
                                            hours = 168;
                                            break;
                                        case 6:
                                            tv_hours.setText("2 Weeks");
                                            hours = 336;
                                            break;

                                    }
                                    dialog.dismiss();
                                }
                            }).show();
                }
            }
        });
    }


    private void dialog() throws Exception {
        final AlertDialog.Builder Select = new AlertDialog.Builder(
                activity);
        Select.setTitle("Age");

        final ListView List_age = new ListView(activity);
        ViewGroup.LayoutParams dialogTxt_idLayoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        List_age.setLayoutParams(dialogTxt_idLayoutParams);

        ArrayAdapter adapter_region = adapter_region = new ArrayAdapter(
                activity, R.layout.item_list, list_age);
        adapter_region
                .setDropDownViewResource(R.layout.item_list);
        List_age.setAdapter(adapter_region);

        LinearLayout layout = new LinearLayout(activity);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(List_age);

        Select.setView(layout);

        final AlertDialog alert = Select.create();

        alert.show();

        List_age.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (select == 1) {
                    tv_minage.setText(list_age.get(position).toString());
                }
                if (select == 2) {
                    tv_maxage.setText(list_age.get(position).toString());
                }
                alert.dismiss();
            }
        });
    }

    private void check_input() throws Exception {
        TAG_PLAN = ed_plan.getText().toString();
        TAG_PARTNERGEGER = tv_part_gender.getText().toString();
        if (TAG_PLAN.length() == 0) {
            ed_plan.setError("Input....");
        } else if (check_participants == false) {
            Toast.makeText(Activity_YourActivity.this, "Max participants > Min participants", Toast.LENGTH_SHORT).show();

        } else {
            create();
        }

    }

    private void get_shapreference() throws Exception {

        SharedPreferences sha_IDuser;
        SharedPreferences sha_Token;

        sha_IDuser = activity.getSharedPreferences("ID_user", 0);
        sha_Token = activity.getSharedPreferences("auth_token", 0);

        TAG_USERID = sha_IDuser.getString(
                "ID_user", "");
        TAG_TOKEN = sha_Token.getString(
                "auth_token", "");

    }

    private void Get_Info() {
        class Get_Single extends AsyncTask<String, String, String> {
            ProgressDialog progressDialog1;

            String name;
            String description;
            String categoryId;
            String minNumOfParticipants;
            String ageTo;
            String maxNumOfParticipants;
            String distance;
            String ageFrom;
            String expiredHours;
            String gender;
            String color;
            String updatedAt;
            String createdAt;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog1 = lib_loading.f_init(activity);

            }

            @Override
            protected String doInBackground(String... args) {
                try {
                    // Looper.prepare(); //For Preparing Message Pool for the child Thread
                    HttpClient client = new DefaultHttpClient();

                    //JSONObject json = new JSONObject();

                    HttpGet post = new HttpGet(HTTP_API.GET_CUSTOM_FIELDS + TAG_ID);
                    post.addHeader("X-User-Id", TAG_USERID);
                    post.addHeader("X-Auth-Token", TAG_TOKEN);

                    HttpResponse response;
                    response = client.execute(post);

                    if (response != null) {
                        HttpEntity resEntity = response.getEntity();
                        if (resEntity != null) {
                            String msg = EntityUtils.toString(resEntity);
                            Log.v("msg-- cate", msg);
                            JSONObject jsonObject = new JSONObject(msg);
                            TAG_STATUS = jsonObject.getString("status");
                            TAG_MESSAGE = jsonObject.getString("message");
                            JSONArray jarr=null;
                            if (TAG_STATUS.equals("success")) {

                                JSONObject data = jsonObject.getJSONObject("data");
                                TAG_ID = data.getString("_id");
                                name = data.getString("name");
                                description = data.getString("description");
                                categoryId = data.getString("categoryId");

                                JSONObject defaultValue = data.getJSONObject("defaultValue");
                                color = data.getString("color");
                                //updatedAt = data.getString("updatedAt");
                                createdAt = data.getString("createdAt");
                                if(defaultValue.length()>0){
                                    minNumOfParticipants = defaultValue.getString("minNumOfParticipants");
                                    maxNumOfParticipants = defaultValue.getString("maxNumOfParticipants");
                                    ageFrom = defaultValue.getString("ageFrom");
                                    ageTo = defaultValue.getString("ageTo");
                                    gender = defaultValue.getString("gender");
                                    distance = defaultValue.getString("distance");
                                    expiredHours = defaultValue.getString("expiredHours");

                                }
                                else{
                                    minNumOfParticipants="2";
                                    maxNumOfParticipants="4";
                                    ageFrom="12";
                                    ageTo="32";
                                    distance="30";
                                    expiredHours="12";
                                    gender="Man";
                                }


                                Log.e("jarr", "11");

                                 jarr = data.getJSONArray("customFields");
                                Log.e("jarr", jarr.length()+"");
                                if (jarr.length() > 0) {
                                    for (int i = 0; i < jarr.length(); i++) {
                                        String isRequired = jarr.getJSONObject(i).getString("isRequired");
                                        String type = jarr.getJSONObject(i).getString("type");
                                        String key = jarr.getJSONObject(i).getString("key");
                                        String name = jarr.getJSONObject(i).getString("name");
                                        String isMultiple;
                                        if (type.equals("selection")) {
                                            isMultiple = jarr.getJSONObject(i).getString("isMultiple");

                                            JSONArray jvalues = jarr.getJSONObject(i).getJSONArray("values");
                                            if (jvalues.length() > 0) {
                                                for (int j = 0; j < jvalues.length(); j++) {
                                                    String value = jvalues.getJSONObject(j).getString("value");
                                                    String name_sub = jvalues.getJSONObject(j).getString("name");
                                                    String isDefault = jvalues.getJSONObject(j).getString("isDefault");

                                                    item_selection_custom item_se = new item_selection_custom(
                                                            key, name, value, name_sub, isDefault
                                                    );
                                                    arr_I_seelctiom.add(item_se);
                                                }
                                            }
                                        } else {
                                            isMultiple = "false";
                                        }
                                        item_custom_fields item = new item_custom_fields(
                                                isRequired, type, key, name, isMultiple
                                        );
                                        arr_Icustom.add(item);


                                    }

                                } else {
                                    Log.e("jarr", "gbfgbfgfgfgn");
                                }
                            }
                        }

                        if (resEntity != null) {
                            resEntity.consumeContent();
                        }

                        client.getConnectionManager().shutdown();

                    }
                } catch (Exception e) {
                    progressDialog1.dismiss();

                } catch (Throwable t) {
                    progressDialog1.dismiss();

                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                progressDialog1.dismiss();
                try {
                    Log.e("arr_Icustom-----", arr_Icustom.size() + "");
                    Log.e("arr_I_seelctiom-----", arr_I_seelctiom.size() + "");
                    if (TAG_STATUS.equals("success")) {

                        if(maxNumOfParticipants.length()==0){
                            maxNumOfParticipants="4";
                        }
                        if(minNumOfParticipants.length()==0){
                            maxNumOfParticipants="2";
                        }
//                        if(ageFrom.length()==0){
//                            ageFrom="23";
//                        }
//                        if(ageTo.length()==0){
//                            ageTo="32";
//                        }
//                        if(gender.length()==0){
//                            gender="Man";
//                        }
                        if(distance.length()==0){
                            distance="10";
                        }
                        if(expiredHours.length()==0){
                            expiredHours="12";
                        }

                        int age = Integer.parseInt(dataString.TAG_AGE);
                        int age_form=0;
                        int age_to=0;
                        if(age>0){
                             age_form =age-7;
                            age_to=age+7;
                        }
                        tv_name.setText(name);
                        tv_desciption.setText(description);
                        tv_max.setText(maxNumOfParticipants);
                        tv_min.setText(minNumOfParticipants);
                        tv_minage.setText(age_form+"");
                        tv_maxage.setText(age_to+"");
                        //tv_part_gender.setText(gender);
                        tv_part_gender.setText("Anything gose");
                        tv_km.setText(distance);
                        tv_hours.setText(expiredHours + " Hours");

                        if(arr_Icustom.size()>0){
                            adapter_custom_fields adapter = new adapter_custom_fields(Activity_YourActivity.this,arr_Icustom,arr_I_seelctiom);

                            list.setAdapter(adapter);
                        }

                    } else {
                        Toast.makeText(activity, TAG_MESSAGE, Toast.LENGTH_SHORT).show();
                    }


                } catch (Exception e) {

                } catch (Throwable t) {

                }

            }

        }

        if (CheckWifi3G.isConnected(activity)) {
            new Get_Single().execute();
        } else {
            Toast.makeText(Activity_YourActivity.this, "Error : Connect Internet!", Toast.LENGTH_SHORT).show();
        }

    }

    private void create() throws Exception {
        int min_num = Integer.parseInt(tv_min.getText().toString());
        int max_num = Integer.parseInt(tv_max.getText().toString());
        int age_form = Integer.parseInt(tv_minage.getText().toString());
        int age_to = Integer.parseInt(tv_maxage.getText().toString());

        boolean meetConfirm = false;
        boolean publishToSocial = false;
        boolean test = false;
        float distance = Float.parseFloat(tv_km.getText().toString());

        Log.e("TAG_ID",TAG_ID);
        if(Get_GPS()){
            new http.create_activities().SEND(
                    activity, TAG_USERID, TAG_TOKEN, TAG_ID, TAG_PLAN, min_num, max_num, age_form, age_to, TAG_PARTNERGEGER
                    , distance, hours, meetConfirm, publishToSocial, test,TAG_LATITUDE,TAG_LONGITUDE
            );
        }

    }


    private boolean Get_GPS()throws Exception{
        boolean check = false;

             gps = new GPSTracker(activity);

            if (gps.canGetLocation())
            {
                double latitude_ = gps.getLatitude();
                double longitude_ = gps.getLongitude();

                TAG_LATITUDE = String.valueOf(latitude_);
                TAG_LONGITUDE = String.valueOf(longitude_);
                Log.e("location----","lat:"+ TAG_LATITUDE +" - lng:"+ TAG_LONGITUDE);

                check=  true;
            }
            else{
                check=   false;
                gps.showSettingsAlert();
            }

        return  check;
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
