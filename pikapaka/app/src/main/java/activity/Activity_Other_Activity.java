package activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import api.HTTP_API;
import hoanvusolution.pikapaka.R;
import image.lib_image_save_original;

/**
 * Created by MrThanhPhong on 4/2/2016.
 */
public class Activity_Other_Activity extends AppCompatActivity{
    private AppCompatActivity activity;
    private ImageView img_avatar;
    private View ll_back;
    private TextView tv_ac_name,tv_fullname,tv_age,tv_gender,tv_rank;
    private RelativeLayout bg_toolbar;
//    private String   TAG_USERID ="";
//    private String   TAG_TOKEN ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_activity);
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void init()throws Exception{
        get_resource();
        get_Intent();

    }
    private void get_resource()throws Exception{
        activity=this;
         bg_toolbar=(RelativeLayout)findViewById(R.id.bg_toolbar);
        ll_back=(LinearLayout)findViewById(R.id.ll_back);
        img_avatar=(ImageView)findViewById(R.id.img_avatar);
        tv_fullname=(TextView)findViewById(R.id.tv_fullname);
        tv_ac_name=(TextView)findViewById(R.id.tv_ac_name);
        tv_age=(TextView)findViewById(R.id.tv_age);
        tv_gender=(TextView)findViewById(R.id.tv_gender);
        tv_rank=(TextView)findViewById(R.id.tv_rank);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    private void get_Intent(){
        Bundle b =getIntent().getExtras();

        if(b!=null){
            String ac_name = b.getString("ac_name");
            String color = b.getString("color");
            String fullname = b.getString("name");
            String imageUrl= b.getString("imageUrl");
            String age = b.getString("age");
            String gender = b.getString("gender");
            String rank = b.getString("rank");
            bg_toolbar.setBackgroundColor(Color.parseColor(color));
            tv_ac_name.setText(ac_name.toUpperCase());
            tv_fullname.setText(fullname);
            tv_age.setText(age);
            tv_gender.setText(gender);
            tv_rank.setText(rank);
            // Toast.makeText(activity,TAG_IMAGE,Toast.LENGTH_SHORT).show();
            if(imageUrl.length()>4){
                String check = imageUrl.substring(0,3);
                if(check.equals("http")){
                    new lib_image_save_original(activity,imageUrl,img_avatar);
                }
                else{
                    imageUrl= HTTP_API.url_image+imageUrl;
                    new lib_image_save_original(activity,imageUrl,img_avatar);

                }

            }
        }
    }
}
