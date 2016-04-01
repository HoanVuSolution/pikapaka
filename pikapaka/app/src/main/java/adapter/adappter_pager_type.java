package adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import hoanvusolution.pikapaka.R;
import item.item_activity_types;

public class adappter_pager_type extends PagerAdapter {

    private AppCompatActivity activity;
    private ArrayList<item_activity_types> arItem = new ArrayList<item_activity_types>();

    @SuppressWarnings("deprecation")
    private RelativeLayout.LayoutParams lparam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);

    public adappter_pager_type(AppCompatActivity context, ArrayList<item_activity_types> arItem) {
        this.activity = context;
        this.arItem = arItem;
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
//		((ViewPager) container).removeView((View) object);
//		((ImageView) object).setImageBitmap(null);
//		  object = null;
    }

    @Override
    public void finishUpdate(View container) {
    }

    @Override
    public int getCount() {
        return arItem.size();
    }

    @Override
    public Object instantiateItem(View convertView, int position) {
        View v =convertView;
        final int pos = position;
        LayoutInflater layoutinflater = (LayoutInflater)
                activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View news =layoutinflater.inflate(R.layout.layout_type_activity, null);
       news.setLayoutParams(lparam);
      //  Log.e("size pager", arItem.size()+"");
       try{



		} catch (Exception e) {
			e.printStackTrace();
		}
        ((ViewPager) convertView).addView(news, 0);
        return news;

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void startUpdate(View container) {
    }

//    @Override
//    public float getPageWidth(int position) {
//        return 0.7f;
//    }
}
