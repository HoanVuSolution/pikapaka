package request_activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import activity.Activity_YourActivity;
import hoanvusolution.pikapaka.R;

public class GridFragment_RQ extends Fragment{
	private GridView mGridView;
	private GridAdapter_RQ mGridAdapter;
	GridItem_RQ[] gridItems = {};
	private Activity activity;
	public GridFragment_RQ(GridItem_RQ[] gridItems, Activity activity) {
		this.gridItems = gridItems;
		this.activity = activity;
	}
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		
		View view;
		
		view = inflater.inflate(R.layout.grid, container, false);
		
		mGridView = (GridView) view.findViewById(R.id.grid_view);

		return view;
	}
	
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		if(activity != null) {
			
			mGridAdapter = new GridAdapter_RQ(activity, gridItems);
			
			if(mGridView != null){
				mGridView.setAdapter(mGridAdapter);
			}
			
			mGridView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int pos, long id) {
					
					onGridItemClick((GridView) parent, view, pos, id);
				}
			});
		}
	}
	
	public void onGridItemClick(GridView g, View v, int pos, long id) {
		Activity_YourActivity.TAG_ID = gridItems[pos].ac_type._id;
		Activity_YourActivity.TAG_COLOR = gridItems[pos].ac_type.activityTypeColor;
		Intent in = new Intent(getActivity(), Activity_YourActivity.class);
		startActivityForResult(in, 100);

	}
}
