package adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import hoanvusolution.pikapaka.R;
import item.item_activity_types;


public class adapter_activity_type extends BaseAdapter {
    private LayoutInflater mInflater;
    private ArrayList<item_activity_types> arItem;
    private Activity activity;

    public adapter_activity_type(Activity activity, ArrayList<item_activity_types> arItem) {
        mInflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.arItem = arItem;
        this.activity = activity;
    }

    public int getCount() {
        return arItem.size();

    }

    public item_activity_types getItem(int position) {
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
        View vi = convertView;
        try {
            if (convertView == null)

                vi = mInflater.inflate(R.layout.listview_widget_activity_type, null);


            TextView tv_activity = (TextView) vi.findViewById(R.id.tv_activity);

            tv_activity.setText(arItem.get(pos).name);
            tv_activity.setBackgroundColor(Color.parseColor(arItem.get(pos).color.toString()));

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            System.gc();
        } catch (Throwable t) {

        }

        if (convertView == null) {
            convertView = new View(activity);
        }
        return vi;
    }

}