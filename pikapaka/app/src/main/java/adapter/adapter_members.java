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

import hoanvusolution.pikapaka.R;
import image.lib_image_save_original;
import item.item_user_group;

/**
 * Created by MrThanhPhong on 3/7/2016.
 */
public class adapter_members extends BaseAdapter {
    private LayoutInflater mInflater;
    private ArrayList<item_user_group> arItem;

    private AppCompatActivity activity;


    private TextView tv_text2;
    public adapter_members(AppCompatActivity activity,
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

                tv_name.setText(arItem.get(pos).displayName);
                tv_gender.setText(arItem.get(pos).gender);
                tv_old.setText(arItem.get(pos).age+" year old");

            String url_image = arItem.get(pos).imageUrl;
            if(url_image.length()>0){
                new lib_image_save_original(activity,url_image,img_avatar);
            }

        }


        return convertView;
    }


}
