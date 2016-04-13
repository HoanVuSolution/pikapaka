package fragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import adapter.adapter_activity_type;
import api.HTTP_API;
import hoanvusolution.pikapaka.R;
import image.lib_image_save_original;
import internet.CheckWifi3G;
import item.item_activity_types;
import item.item_categories;
import lib_wheelmenu.WheelMenu;
import loading.lib_loading;
import util.dataString;

/**
 * Created by Admin on 04-06-2015.
 */
public class Home_Fragment extends Fragment {
    private String TAG_USERID = "";
    private String TAG_TOKEN = "";
    //  private AppCompatActivity activity;

    private ImageView img_profile;
    private ProgressDialog progressDialog;

    private WheelMenu wheelMenu;
    private ArrayList<item_categories> arr_cate = new ArrayList<item_categories>();
    public ArrayList<item_activity_types> arr_activty = new ArrayList<item_activity_types>();
    public ArrayList<item_activity_types> arr_activty_search = new ArrayList<item_activity_types>();


    private GridView listview;
private adapter_activity_type adapter;
    private String _idCate = "";
    private ImageView ic_favorites, ic_popular, ic_surprise;
    private int check_favorites = 0;
    View v;
    TextView tv_noty_users;
    private String d_id,d_name;

    private ViewPager awesomePager;
    private PagerAdapter pm;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home, container, false);

        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        if(dataString.TAG_IMAGE_URL.length()>0){

           // new lib_image_save_original(getActivity(),dataString.TAG_IMAGE_URL,img_profile);

            String check = dataString.TAG_IMAGE_URL.substring(0,3);
            if(check.equals("http")){
                new lib_image_save_original(getActivity(),dataString.TAG_IMAGE_URL,img_profile);

            }
            else{
                dataString.TAG_IMAGE_URL=HTTP_API.url_image+dataString.TAG_IMAGE_URL;
                new lib_image_save_original(getActivity(),dataString.TAG_IMAGE_URL,img_profile);

            }
        }
    }



    private void init() throws Exception {
        get_resource();
        get_shapreference();
        get_cate();
        OnClick();
        Item_Onlick();


    }

    private void get_resource() throws Exception {
        tv_noty_users =(TextView)v.findViewById(R.id.tv_noty_users);
        tv_noty_users.setVisibility(View.GONE);
        awesomePager = (ViewPager)v.findViewById(R.id.pager);
        //listview = (GridView) v.findViewById(R.id.listview);
       // adapter = new adapter_activity_type(getActivity(), arr_activty_search);
        //listview.setAdapter(adapter);
        wheelMenu = (WheelMenu) v.findViewById(R.id.wheelMenu);


        img_profile = (ImageView) v.findViewById(R.id.img_profile);
        ic_favorites = (ImageView) v.findViewById(R.id.ic_favorites);
        ic_popular = (ImageView) v.findViewById(R.id.ic_popular);
        ic_surprise = (ImageView) v.findViewById(R.id.ic_surprise);


    }


    private void OnClick() throws Exception {

        wheelMenu.setDivCount(8);
        wheelMenu.setWheelImage(R.drawable.bg_menu1);
        wheelMenu.setWheelChangeListener(new WheelMenu.WheelChangeListener() {
            @Override
            public void onSelectionChange(int selectedPosition) {
                Log.v("wheelMenu change", selectedPosition + 1 + "");
                selectedPosition += 1;
                if (selectedPosition == 1) {
                    _idCate = "F7Xiz3BF566ubpRuQ";
                } else if (selectedPosition == 2) {
                    _idCate = "fHPd2gfPv8deuN6fd";
                } else if (selectedPosition == 3) {
                    _idCate = "o6aNH4udeStDNpm87";
                } else if (selectedPosition == 4) {
                    _idCate = "oNB8v22uGxBPxvAET";
                } else if (selectedPosition == 5) {
                    _idCate = "WcKQuXyjDeM8jvmJy";
                } else if (selectedPosition == 6) {////---------
                    _idCate = "QfELWdkNM4GCaL6Zp";
                } else if (selectedPosition == 7) {
                    _idCate = "LzPtaATXQ5WXnvq2b";
                } else if (selectedPosition == 8) {
                    _idCate = "4toWfbpiEnngkNjBz";
                }

                if (arr_cate.size() == 0) {
                    try {
                        get_cate();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                Search(_idCate);
            }

        });


//        img_profile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent in = new Intent(getActivity(), Activity_Profile.class);
//                startActivityForResult(in, Activity_Result.REQUEST_CODE_ACT);
//            }
//        });

        ic_favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_favorites = 1;
                Get_Favorites();
            }
        });
        ic_popular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_favorites = 2;
                Get_Favorites();
            }
        });
        ic_surprise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_favorites = 3;
                Get_Favorites();
            }
        });

    }

    private void get_shapreference() throws Exception {
        SharedPreferences sha_IDuser;
        SharedPreferences sha_Token;
        sha_IDuser = getActivity().getSharedPreferences("ID_user", 0);
        sha_Token = getActivity().getSharedPreferences("auth_token", 0);
        TAG_USERID = sha_IDuser.getString(
                "ID_user", "");
        TAG_TOKEN = sha_Token.getString(
                "auth_token", "");
//        Log.e("TAG_USERID",TAG_USERID);
//        Log.e("TAG_TOKEN",TAG_TOKEN);

    }

    private void get_cate() throws Exception {

        class Loading extends AsyncTask<String, String, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = lib_loading.f_init(getActivity());
            }

            @Override
            protected String doInBackground(String... args) {
                try {
                    //Looper.prepare(); //For Preparing Message Pool for the child Thread
                    HttpClient client = new DefaultHttpClient();
                    // HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
                    HttpResponse response;

                    HttpGet post = new HttpGet(HTTP_API.GET_ALL_CATEGORIES);
                    post.addHeader("X-User-Id", TAG_USERID);
                    post.addHeader("X-Auth-Token", TAG_TOKEN);

                    response = client.execute(post);
                    if (response != null) {
                        HttpEntity resEntity = response.getEntity();
                        if (resEntity != null) {
                            String msg = EntityUtils.toString(resEntity);
                            Log.i("msg-- cate", msg);

                            JSONObject jsonObject = new JSONObject(msg);
                            String status = jsonObject.getString("status");

                            Log.e("status--", status);
                            if (status.equals("success")) {
                                String _id = "";
                                String name = "";
                                String description = "";
                                String color = "";
                                String activityTypes = "";

                                JSONArray jsonaTypes = null;
                                // JSONObject object=null;
                                //JSONObject defaultValue = null;
                                JSONArray jsonarray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonarray.length(); i++) {
                                    _id = jsonarray.getJSONObject(i).getString("_id");
                                    name = jsonarray.getJSONObject(i).getString("name");
                                    description = jsonarray.getJSONObject(i).getString("description");
                                    color = jsonarray.getJSONObject(i).getString("color");
                                    activityTypes = jsonarray.getJSONObject(i).getString("activityTypes");

                                    item_categories item = new item_categories(_id, name, description, color, activityTypes);
                                    arr_cate.add(item);

                                    jsonaTypes = jsonarray.getJSONObject(i).getJSONArray("activityTypes");
                                    if (jsonaTypes.length() > 0) {
                                        for (int j = 0; j < jsonaTypes.length(); j++) {

                                            String _id1 = jsonaTypes.getJSONObject(j).getString("_id");
                                            String nane = jsonaTypes.getJSONObject(j).getString("name");
                                            String description1 = jsonaTypes.getJSONObject(j).getString("description");
                                            String categoryId = jsonaTypes.getJSONObject(j).getString("categoryId");
                                            String defaultValue = jsonaTypes.getJSONObject(j).getString("defaultValue");
                                            String customField = jsonaTypes.getJSONObject(j).getString("customFields");
                                            String color_ = jsonaTypes.getJSONObject(j).getString("color");
                                          //  String updatedAt = jsonaTypes.getJSONObject(j).getString("updatedAt");
                                            String updatedAt="";

                                            //
                                            item_activity_types item1 = new item_activity_types(_id1, nane, description1, categoryId,
                                                    defaultValue, customField, color_, updatedAt);
                                            arr_activty.add(item1);
                                        }
                                    }
                                }
                            }


                        }

                        if (resEntity != null) {
                            resEntity.consumeContent();
                        }

                        client.getConnectionManager().shutdown();

                    }
                } catch (Exception e) {
                    progressDialog.dismiss();
                    Log.e("ERROR ", "ERROR1");

                } catch (Throwable t) {
                    progressDialog.dismiss();

                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                progressDialog.dismiss();
                try {
                    if (arr_cate.size() > 0) {
//                        adapter_activity_type adapter = new adapter_activity_type(activity,arr_activty);
//                        listview.setAdapter(adapter);
                        Search("F7Xiz3BF566ubpRuQ");// default
                        for (int i = 0; i < arr_cate.size(); i++) {
                            Log.i("_id:" + arr_cate.get(i)._id, "name:" + arr_cate.get(i).name);
                        }

                    } else {
                      //  Log.v("No data", "Nodata");

                    }
                } catch (Exception e) {

                } catch (Throwable t) {

                }

            }

        }

        if (CheckWifi3G.isConnected(getActivity())) {
            new Loading().execute();
        }
    }


    private void Search(String _idCate) {
        arr_activty_search.clear();
        Log.e("_idCate-", _idCate);
        if (arr_activty.size() > 0) {
            for (int i = 0; i < arr_activty.size(); i++) {
               // Log.i("name-" + arr_activty.get(i).name, "categoryId-" + arr_activty.get(i).categoryId);
                if (arr_activty.get(i).categoryId.equals(_idCate)) {
                    arr_activty_search.add(new item_activity_types(
                            arr_activty.get(i)._id,
                            arr_activty.get(i).name,
                            arr_activty.get(i).description,
                            arr_activty.get(i).categoryId,
                            arr_activty.get(i).defaultValue,
                            arr_activty.get(i).customField,
                            arr_activty.get(i).color,
                            arr_activty.get(i).updatedAt

                    ));
                }
            }

        }

       // if(arr_activty_search.size()>0){
            load_slider();

        //}
    }

    private void load_slider(){

        ArrayList<item_activity_types> a = new ArrayList<item_activity_types>();
        for(int i = 0; i < arr_activty_search.size(); i++) {
            a.add(arr_activty_search.get(i));

        }
        Iterator<item_activity_types> it = a.iterator();

        List<GridFragment> gridFragments = new ArrayList<GridFragment>();
        it = a.iterator();

        int i = 0;
        while(it.hasNext()) {
            ArrayList<GridItems> imLst = new ArrayList<GridItems>();

            GridItems itm = new GridItems(0,it.next());
            imLst.add(itm);
            i = i + 1;

            if(it.hasNext()) {
                GridItems itm1 = new GridItems(1, it.next());
                imLst.add(itm1);
                i = i + 1;
            }

            if(it.hasNext()) {
                GridItems itm2 =new GridItems(2, it.next());
                imLst.add(itm2);
                i = i + 1;
            }

            if(it.hasNext()) {
                GridItems itm3 =new GridItems(3, it.next());
                imLst.add(itm3);
                i = i + 1;
            }

            if(it.hasNext()) {
                GridItems itm4 = new GridItems(4, it.next());
                imLst.add(itm4);
                i = i + 1;
            }

            if(it.hasNext()) {
                GridItems itm5 = new GridItems(5,  it.next());
                imLst.add(itm5);
                i = i + 1;
            }

            if(it.hasNext()) {
                GridItems itm6 = new GridItems(6,  it.next());
                imLst.add(itm6);
                i = i + 1;
            }

            if(it.hasNext()) {
                GridItems itm7 = new GridItems(7, it.next());
                imLst.add(itm7);
                i = i + 1;
            }

//            if(it.hasNext()) {
//                GridItems itm8 = new GridItems(8, it.next());
//                imLst.add(itm8);
//                i = i + 1;
//            }

            GridItems[] gp = {};
            GridItems[] gridPage = imLst.toArray(gp);
            gridFragments.add(new GridFragment(gridPage, getActivity()));
        }

        pm = new PagerAdapter( getActivity().getSupportFragmentManager(), gridFragments);
        awesomePager.setAdapter(pm);
    }

    private void Item_Onlick() throws Exception {

//        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//
//                Activity_YourActivity.TAG_ID = arr_activty_search.get(position)._id;
//
//                Activity_YourActivity.TAG_COLOR = arr_activty_search.get(position).color;
//
//
//                Intent in = new Intent(getActivity(), Activity_YourActivity.class);
//                startActivityForResult(in, 100);
//
//            }
//        });
    }
    private void Get_Favorites() {
        arr_activty_search.clear();
        class Loading extends AsyncTask<String, String, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = lib_loading.f_init(getActivity());
            }

            @Override
            protected String doInBackground(String... args) {
                try {
                    HttpClient client = new DefaultHttpClient();
                    HttpResponse response;
                    HttpGet post = null;
                    if (check_favorites == 1) {
                        post = new HttpGet(HTTP_API.GET_FAVORITE);

                    } else if (check_favorites == 2) {
                        post = new HttpGet(HTTP_API.GET_POPULAR);

                    } else if (check_favorites == 3) {
                        post = new HttpGet(HTTP_API.GET_SURPRISE);
                    }

                    post.addHeader("X-User-Id", TAG_USERID);
                    post.addHeader("X-Auth-Token", TAG_TOKEN);

                    response = client.execute(post);
                    if (response != null) {
                        HttpEntity resEntity = response.getEntity();
                        if (resEntity != null) {
                            String msg = EntityUtils.toString(resEntity);
                            //Log.i("msg-- cate", msg);

                            JSONObject jsonObject = new JSONObject(msg);
                            String status = jsonObject.getString("status");

                           // Log.e("status--", status);

                            if (status.equals("success")) {
                                JSONArray jsonarray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonarray.length(); i++) {

                                    String _id1 = jsonarray.getJSONObject(i).getString("_id");
                                    String name = jsonarray.getJSONObject(i).getString("name");
                                    String description1 = jsonarray.getJSONObject(i).getString("description");
                                    String categoryId = jsonarray.getJSONObject(i).getString("categoryId");
                                    String defaultValue = jsonarray.getJSONObject(i).getString("defaultValue");
                                    String customField = jsonarray.getJSONObject(i).getString("customField");
                                    String color_ = jsonarray.getJSONObject(i).getString("color");
                                    String updatedAt = jsonarray.getJSONObject(i).getString("updatedAt");

                                    arr_activty_search.add(new item_activity_types(
                                            _id1,
                                            name,
                                            description1,
                                            categoryId,
                                            defaultValue,
                                            customField,
                                            color_,
                                            updatedAt

                                    ));


                                }
                            }


                        }

                        if (resEntity != null) {
                            resEntity.consumeContent();
                        }

                        client.getConnectionManager().shutdown();

                    }
                } catch (Exception e) {
                    progressDialog.dismiss();
                    Log.e("ERROR ", "ERROR1");

                } catch (Throwable t) {
                    progressDialog.dismiss();


                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                progressDialog.dismiss();
                try {
                    //adapter.notifyDataSetChanged();
                    //adapter1.notifyDataSetChanged();
                    //if(arr_activty_search.size()>0){
                        load_slider();
                    //}
                } catch (Exception e) {

                } catch (Throwable t) {

                }

            }

        }

        if (CheckWifi3G.isConnected(getActivity())) {
            new Loading().execute();
        }
    }

    private class PagerAdapter extends FragmentStatePagerAdapter {

        private List<GridFragment> fragments;

        public PagerAdapter(FragmentManager fm, List<GridFragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int pos) {
            return this.fragments.get(pos);
        }

        @Override
        public int getCount() {
            return this.fragments.size();
        }
    }


}
