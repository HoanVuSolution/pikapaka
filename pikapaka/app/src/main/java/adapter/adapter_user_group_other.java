package adapter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import activity.Activity_MyActivity;
import hoanvusolution.pikapaka.R;
import image.lib_image_save_original;
import item.item_user_group;

/**
 * Created by MrThanhPhong on 3/7/2016.
 */
public class adapter_user_group_other extends BaseAdapter {
    private LayoutInflater mInflater;
    private ArrayList<item_user_group> arItem;

    private AppCompatActivity activity;


    private TextView tv_text2;
    public adapter_user_group_other(AppCompatActivity activity,
                           ArrayList<item_user_group> arItem) {
        mInflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.arItem = arItem;

        this.activity = activity;
    }

    public int getCount() {
        return arItem.size();
    }

    public item_user_group getItem(int position) {
        return arItem.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public int getItemViewType(int position) {
        return 1;
    }

    public int getViewTypeCount() {
        return 1;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.listview_widget_members, null);

            final TextView tv_name =(TextView)convertView.findViewById(R.id.tv_name);
            final TextView tv_gender =(TextView)convertView.findViewById(R.id.tv_gender);
            final TextView tv_old =(TextView)convertView.findViewById(R.id.tv_old);
            final ImageView img_avatar=(ImageView)convertView.findViewById(R.id.img_avatar);
            final ImageView ic_chat=(ImageView)convertView.findViewById(R.id.ic_chat);
            ic_chat.setVisibility(View.GONE);
            tv_name.setText(arItem.get(pos).displayName);
            tv_gender.setText(arItem.get(pos).gender);
            tv_old.setText(arItem.get(pos).age+" year old");
            String my_id = arItem.get(pos)._id;
            if(my_id.equals(Activity_MyActivity.TAG_USERID)){
                ic_chat.setVisibility(View.GONE);
            }

            String url_image = arItem.get(pos).imageUrl;
            if(url_image.length()>0){
                new lib_image_save_original(activity,url_image,img_avatar);
            }
//            ic_chat.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent in = new Intent(activity, Activity_Chat_Private.class);
//                    in.putExtra("TAG_ID_GROUP", Activity_Members.TAG_ID);
//                    in.putExtra("TAG_ID_RECEIVER", arItem.get(pos)._id);
//                    in.putExtra("TAG_NAME_USER",arItem.get(pos).displayName);
//                    activity.startActivityForResult(in, Activity_Result.REQUEST_CODE_ACT);
//                }
//            });

        }


        return convertView;
    }


}
