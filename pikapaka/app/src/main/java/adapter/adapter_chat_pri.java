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
import hoanvusolution.pikapaka.R;
import image.lib_image_save_original;
import item.item_chat;

/**
 * Created by MrThanhPhong on 3/20/2016.
 */
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

//    //@SuppressLint("ResourceAsColor")
//    public View getView(int position, View convertView, ViewGroup parent) {
//        final int pos = position;
//        try {
//            String id_sender = arItem.get(pos).id_user;
//            if (convertView == null) {
//                int res = 0;
//
//
//                if(id_sender.equals(Activity_Chat_Private.TAG_USERID)){
//                   convertView = mInflater.inflate(R.layout.layout_sender,
//                           null);
////                    res = R.layout.layout_sender;//
////                    convertView = mInflater.inflate(res, parent, false);
//                }
//                else{
////                    res = R.layout.layout_receiver;//
////                    convertView = mInflater.inflate(res, parent, false);
//                    convertView = mInflater.inflate(R.layout.layout_receiver,
//                      null);
//                }
//            }
//
//            ImageView img_profile = (ImageView) convertView.findViewById(R.id.img_profile);
//            TextView tv_msg;
//            tv_msg = (TextView) convertView.findViewById(R.id.tv_msg);
//
//            tv_msg.setText(arItem.get(pos).content);
//            String imageUrl = arItem.get(pos).imageUrl;
//            if (imageUrl.length() > 0) {
//                new lib_image_save_original(activity, imageUrl, img_profile);
//
//            }
//
//            // }
//
//        } catch (Exception e) {
//            // TODO: handle exception
//            e.printStackTrace();
//        } catch (OutOfMemoryError e) {
//            System.gc();
//        } catch (Throwable t) {
//
//        }
//
//        if (convertView == null) {
//            convertView = new View(activity);
//        }
//
//
//        return convertView;
//    }

    @Override
    public View getView(int pos, View v, ViewGroup parent) {

        item_chat c = getItem(pos);
        String id_send = c.id_user;
        //
        // if (id_send.equals(id_user))
        // v = getLayoutInflater().inflate(
        // R.layout.listview_widget_chat_sender, null);
        // else
        //
        // v = getLayoutInflater().inflate(
        // R.layout.listview_widget_chat_receiver, null);

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
            if (imageUrl.length() > 0) {
                new lib_image_save_original(activity, imageUrl, img_profile);

            }

        return v;
    }
}
