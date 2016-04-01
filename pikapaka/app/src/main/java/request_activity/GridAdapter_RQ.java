package request_activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import hoanvusolution.pikapaka.R;
import item.item_search_activity;

public class GridAdapter_RQ extends BaseAdapter{
	Context context;
	public class ViewHolder {
		public ImageView imageView;
		public TextView textTitle;
	}
	
	private GridItem_RQ[] items;
	private LayoutInflater mInflater;

	public GridAdapter_RQ(Context context, GridItem_RQ[] locations) {
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.context = context;
		items = locations;
	}

	public GridItem_RQ[] getItems() {
		return items;
	}

	public void setItems(GridItem_RQ[] items) {
		this.items = items;
	}

	@Override
	public int getCount() {
		if(items != null) {
			return items.length;
		}
		return 0;
	}

	@Override
	public Object getItem(int pos) {
		if(items != null && pos >= 0 && pos < getCount()) {
			return items[pos];
		}
		return null;
	}

	public long getItemId(int position) {
		return position;
	}
	@Override
	public View getView(int pos, View convertView, ViewGroup parent) {

		View view = convertView;
		ViewHolder viewHolder;

		if(view == null) {

			view = mInflater.inflate(R.layout.listview_widget_activity_type, parent, false);
			viewHolder = new ViewHolder();
		//	viewHolder.imageView = (ImageView) view.findViewById(R.id.grid_item_image);
			viewHolder.textTitle = (TextView) view.findViewById(R.id.tv_activity);
			view.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) view.getTag();
		}

		GridItem_RQ gridItems = items[pos];
		setCatImage(pos, viewHolder, gridItems.ac_type);
		
		return view;
	}
	private void setCatImage(int pos, ViewHolder viewHolder, item_search_activity ac_type) {
		//viewHolder.imageView.setImageResource(images[pos]);
		viewHolder.textTitle.setText(ac_type.firstName);
		//viewHolder.textTitle.setBackgroundColor(Color.parseColor(ac_type.color));
	}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}
}
