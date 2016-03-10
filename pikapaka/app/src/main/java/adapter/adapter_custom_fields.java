package adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import activity.Activity_YourActivity;
import hoanvusolution.pikapaka.R;
import item.item_custom_fields;
import item.item_selection_custom;

/**
 * Created by MrThanhPhong on 3/7/2016.
 */
public class adapter_custom_fields extends BaseAdapter {
    private LayoutInflater mInflater;
    private ArrayList<item_custom_fields> arItem;
    private ArrayList<item_selection_custom> arItem_se;
    private Activity_YourActivity activity;


    private TextView tv_text2;
    public adapter_custom_fields(Activity_YourActivity activity,
                                 ArrayList<item_custom_fields> arItem, ArrayList<item_selection_custom> arItem_se) {
        mInflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.arItem = arItem;
        this.arItem_se = arItem_se;
        this.activity = activity;
    }

    public int getCount() {
        return arItem.size();
    }

    public item_custom_fields getItem(int position) {
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
       // View vi = convertView;

        final	String type =arItem.get(pos).type;



            if (convertView == null) {
				if(type.equals("text")){
					convertView = mInflater.inflate(R.layout.custum_text, null);

                    final TextView tv_name =(TextView)convertView.findViewById(R.id.tv_name);
                    tv_name.setText(arItem.get(pos).name);
                }
				else{
                    convertView = mInflater.inflate(R.layout.custom_selection, null);
                    final TextView tv_name1 =(TextView)convertView.findViewById(R.id.tv_name1);
                    tv_name1.setText(arItem.get(pos).name);
                    tv_text2 =(TextView)convertView.findViewById(R.id.tv_text2);
                    final LinearLayout ll_select =(LinearLayout)convertView.findViewById(R.id.ll_select);
                    if(arItem_se.size()>0){
                        tv_text2.setText(arItem_se.get(0).name_sub.toString());


                    }
                    ll_select.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popup();
                        }
                    });
				}
                //


            }


            return convertView;
        }

    private void popup(){
        final AlertDialog.Builder Update = new AlertDialog.Builder(
                activity);
         ListView list=  new ListView(activity);
        ArrayAdapter adapter_categoty = new ArrayAdapter(
                activity, R.layout.listview_item1,
                arItem_se);
        adapter_categoty
                .setDropDownViewResource(R.layout.listview_item1);
        list.setAdapter(adapter_categoty);
        LinearLayout layout = new LinearLayout(activity);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(list);

        Update.setView(layout);

        // ..............Xu ly UpDate..................

        // Update.show();
        final AlertDialog alert = Update.create(); 

        alert.show();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tv_text2.setText(arItem_se.get(position).name_sub);
                alert.dismiss();
            }
        });
    }
}
