package adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import hoanvusolution.pikapaka.R;
import item.item_chat;

public class adapater_chat extends BaseAdapter{
	private LayoutInflater mInflater;
	private ArrayList<item_chat> arItem;
	private Activity activity;

	public adapater_chat(Activity activity,
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

	//@SuppressLint("ResourceAsColor")
	public View getView(int position, View convertView, ViewGroup parent) {
		final int pos = position;

		try {
			if (convertView == null) {
				int res = 0;
				res = R.layout.listview_widget_chat;//
				convertView = mInflater.inflate(res, parent, false);
			}

			TextView tv_name_sender,tv_msg;
			tv_name_sender =(TextView)convertView.findViewById(R.id.tv_name_sender);
			tv_msg =(TextView)convertView.findViewById(R.id.tv_msg);
			
		
			tv_name_sender.setText(arItem.get(pos).firstName+":");
			tv_msg.setText(arItem.get(pos).content);
		
			
		
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
		return convertView;
	}
	
	


}
