package adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import activity.Activity_Chat_Private;
import api.HTTP_API;
import hoanvusolution.pikapaka.R;
import image.lib_image_save_original;
import item.item_chat;

public class adapter_chat_pri extends BaseAdapter {
    private LayoutInflater mInflater;
    private ArrayList<item_chat> arItem;
    private Activity activity;

    public adapter_chat_pri(Activity activity,
                            ArrayList<item_chat> arItem2) {
        mInflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.arItem = arItem2;
        this.activity = activity;
    }

    public int getCount() {
        return arItem.size();
    }

    public item_chat getItem(int position) {
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

    @Override
    public View getView(int pos, View v, ViewGroup parent) {

        item_chat c = getItem(pos);
        String id_send = c.id_user;

        if (id_send.equals(Activity_Chat_Private.TAG_USERID)) {
            v = mInflater.inflate(
                    R.layout.layout_sender, null);
        } else {
            v = mInflater.inflate(
                    R.layout.layout_receiver, null);
        }
            ImageView img_profile = (ImageView) v.findViewById(R.id.img_profile);

        TextView tv_msg = (TextView) v.findViewById(R.id.tv_msg);

            tv_msg.setText(arItem.get(pos).content);
            String imageUrl = arItem.get(pos).imageUrl;
        try {
            if (imageUrl.length() > 0) {
                String check = imageUrl.substring(0,3);
                if(check.equals("http")){
                    new lib_image_save_original(activity,imageUrl,img_profile);

                }
                else{
                    imageUrl= HTTP_API.url_image+imageUrl;
                    new lib_image_save_original(activity,imageUrl,img_profile);

                }


            }
        }catch (Exception e){
            e.getStackTrace();
        }


        return v;
    }
}
