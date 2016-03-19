package adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
        Log.e("size pager", arItem.size()+"");
       try{
//
//           final TextView tv_activity =(TextView)news.findViewById(R.id.tv_activity);
//           final TextView tv_activity1 =(TextView)news.findViewById(R.id.tv_activity1);
//           final TextView tv_activity2 =(TextView)news.findViewById(R.id.tv_activity2);
//           final TextView tv_activity3 =(TextView)news.findViewById(R.id.tv_activity3);
//           final TextView tv_activity4 =(TextView)news.findViewById(R.id.tv_activity4);
//           final TextView tv_activity5 =(TextView)news.findViewById(R.id.tv_activity5);
//           final TextView tv_activity6 =(TextView)news.findViewById(R.id.tv_activity6);
//           final TextView tv_activity7 =(TextView)news.findViewById(R.id.tv_activity7);
//
//           final LinearLayout ll_1=(LinearLayout)news.findViewById(R.id.ll_1);
//           final LinearLayout ll_2=(LinearLayout)news.findViewById(R.id.ll_2);
//           final LinearLayout ll_3=(LinearLayout)news.findViewById(R.id.ll_3);
//           final LinearLayout ll_4=(LinearLayout)news.findViewById(R.id.ll_4);
//           final LinearLayout ll_5=(LinearLayout)news.findViewById(R.id.ll_5);
//           final LinearLayout ll_6=(LinearLayout)news.findViewById(R.id.ll_6);
//           final LinearLayout ll_7=(LinearLayout)news.findViewById(R.id.ll_7);
//           final LinearLayout ll_8=(LinearLayout)news.findViewById(R.id.ll_8);
//           if(arItem.size()==1){
//               tv_activity.setText(arItem.get(0).name);
//               tv_activity.setBackgroundColor(Color.parseColor(arItem.get(0).color));
//               ll_1.setVisibility(View.VISIBLE);
//               ll_2.setVisibility(View.GONE);
//               ll_3.setVisibility(View.GONE);
//               ll_4.setVisibility(View.GONE);
//               ll_5.setVisibility(View.GONE);
//               ll_6.setVisibility(View.GONE);
//               ll_7.setVisibility(View.GONE);
//               ll_8.setVisibility(View.GONE);
//
//           }
//           if(arItem.size()==2){
//               tv_activity.setText(arItem.get(0).name);
//               tv_activity.setBackgroundColor(Color.parseColor(arItem.get(0).color));
//               tv_activity1.setText(arItem.get(1).name);
//               tv_activity1.setBackgroundColor(Color.parseColor(arItem.get(1).color));
//
//               ll_1.setVisibility(View.VISIBLE);
//               ll_2.setVisibility(View.VISIBLE);
//               ll_3.setVisibility(View.GONE);
//               ll_4.setVisibility(View.GONE);
//               ll_5.setVisibility(View.GONE);
//               ll_6.setVisibility(View.GONE);
//               ll_7.setVisibility(View.GONE);
//               ll_8.setVisibility(View.GONE);
//
//           }
//           if(arItem.size()==3){
//               tv_activity.setText(arItem.get(0).name);
//               tv_activity.setBackgroundColor(Color.parseColor(arItem.get(0).color));
//
//               tv_activity1.setText(arItem.get(1).name);
//               tv_activity1.setBackgroundColor(Color.parseColor(arItem.get(1).color));
//
//               tv_activity2.setText(arItem.get(2).name);
//               tv_activity2.setBackgroundColor(Color.parseColor(arItem.get(2).color));
//
//               ll_1.setVisibility(View.VISIBLE);
//               ll_2.setVisibility(View.VISIBLE);
//               ll_3.setVisibility(View.VISIBLE);
//               ll_4.setVisibility(View.GONE);
//               ll_5.setVisibility(View.GONE);
//               ll_6.setVisibility(View.GONE);
//               ll_7.setVisibility(View.GONE);
//               ll_8.setVisibility(View.GONE);
//
//           }
//           if(arItem.size()==4){
//               tv_activity.setText(arItem.get(0).name);
//               tv_activity.setBackgroundColor(Color.parseColor(arItem.get(0).color));
//
//               tv_activity1.setText(arItem.get(1).name);
//               tv_activity1.setBackgroundColor(Color.parseColor(arItem.get(1).color));
//
//
//               tv_activity2.setText(arItem.get(2).name);
//               tv_activity2.setBackgroundColor(Color.parseColor(arItem.get(2).color));
//
//
//               tv_activity3.setText(arItem.get(3).name);
//               tv_activity3.setBackgroundColor(Color.parseColor(arItem.get(3).color));
//
//               ll_1.setVisibility(View.VISIBLE);
//               ll_2.setVisibility(View.VISIBLE);
//               ll_3.setVisibility(View.VISIBLE);
//               ll_4.setVisibility(View.VISIBLE);
//               ll_5.setVisibility(View.GONE);
//               ll_6.setVisibility(View.GONE);
//               ll_7.setVisibility(View.GONE);
//               ll_8.setVisibility(View.GONE);
//
//           }
//
//           if(arItem.size()==5){
//               tv_activity.setText(arItem.get(0).name);
//               tv_activity.setBackgroundColor(Color.parseColor(arItem.get(0).color));
//
//               tv_activity1.setText(arItem.get(1).name);
//               tv_activity1.setBackgroundColor(Color.parseColor(arItem.get(1).color));
//
//               tv_activity2.setText(arItem.get(2).name);
//               tv_activity2.setBackgroundColor(Color.parseColor(arItem.get(2).color));
//
//
//               tv_activity3.setText(arItem.get(3).name);
//               tv_activity3.setBackgroundColor(Color.parseColor(arItem.get(3).color));
//
//
//               tv_activity4.setText(arItem.get(4).name);
//               tv_activity4.setBackgroundColor(Color.parseColor(arItem.get(4).color));
//               ll_1.setVisibility(View.VISIBLE);
//               ll_2.setVisibility(View.VISIBLE);
//               ll_3.setVisibility(View.VISIBLE);
//               ll_4.setVisibility(View.VISIBLE);
//               ll_5.setVisibility(View.VISIBLE);
//               ll_6.setVisibility(View.GONE);
//               ll_7.setVisibility(View.GONE);
//               ll_8.setVisibility(View.GONE);
//
//           }
//
//           if(arItem.size()==6){
//               tv_activity.setText(arItem.get(0).name);
//               tv_activity.setBackgroundColor(Color.parseColor(arItem.get(0).color));
//
//               tv_activity1.setText(arItem.get(1).name);
//               tv_activity1.setBackgroundColor(Color.parseColor(arItem.get(1).color));
//
//               tv_activity2.setText(arItem.get(2).name);
//               tv_activity2.setBackgroundColor(Color.parseColor(arItem.get(2).color));
//
//
//               tv_activity3.setText(arItem.get(3).name);
//               tv_activity3.setBackgroundColor(Color.parseColor(arItem.get(3).color));
//
//               tv_activity4.setText(arItem.get(4).name);
//               tv_activity4.setBackgroundColor(Color.parseColor(arItem.get(4).color));
//
//
//               tv_activity5.setText(arItem.get(5).name);
//               tv_activity5.setBackgroundColor(Color.parseColor(arItem.get(5).color));
//               ll_1.setVisibility(View.VISIBLE);
//               ll_2.setVisibility(View.VISIBLE);
//               ll_3.setVisibility(View.VISIBLE);
//               ll_4.setVisibility(View.VISIBLE);
//               ll_5.setVisibility(View.VISIBLE);
//               ll_6.setVisibility(View.VISIBLE);
//               ll_7.setVisibility(View.GONE);
//               ll_8.setVisibility(View.GONE);
//
//
//           }
//
//           if(arItem.size()==7){
//               tv_activity.setText(arItem.get(0).name);
//               tv_activity.setBackgroundColor(Color.parseColor(arItem.get(0).color));
//
//               tv_activity1.setText(arItem.get(1).name);
//               tv_activity1.setBackgroundColor(Color.parseColor(arItem.get(1).color));
//
//               tv_activity2.setText(arItem.get(2).name);
//               tv_activity2.setBackgroundColor(Color.parseColor(arItem.get(2).color));
//
//
//               tv_activity3.setText(arItem.get(3).name);
//               tv_activity3.setBackgroundColor(Color.parseColor(arItem.get(3).color));
//
//
//               tv_activity4.setText(arItem.get(4).name);
//               tv_activity4.setBackgroundColor(Color.parseColor(arItem.get(4).color));
//
//
//               tv_activity5.setText(arItem.get(5).name);
//               tv_activity5.setBackgroundColor(Color.parseColor(arItem.get(5).color));
//
//               tv_activity6.setText(arItem.get(6).name);
//               tv_activity6.setBackgroundColor(Color.parseColor(arItem.get(6).color));
//               ll_1.setVisibility(View.VISIBLE);
//               ll_2.setVisibility(View.VISIBLE);
//               ll_3.setVisibility(View.VISIBLE);
//               ll_4.setVisibility(View.VISIBLE);
//               ll_5.setVisibility(View.VISIBLE);
//               ll_6.setVisibility(View.VISIBLE);
//               ll_7.setVisibility(View.VISIBLE);
//               ll_8.setVisibility(View.GONE);
//
//
//           }
//           if(arItem.size()>=8){
//               tv_activity.setText(arItem.get(0).name);
//               tv_activity.setBackgroundColor(Color.parseColor(arItem.get(0).color));
//
//               tv_activity1.setText(arItem.get(1).name);
//               tv_activity1.setBackgroundColor(Color.parseColor(arItem.get(1).color));
//
//
//               tv_activity2.setText(arItem.get(2).name);
//               tv_activity2.setBackgroundColor(Color.parseColor(arItem.get(2).color));
//
//
//               tv_activity3.setText(arItem.get(3).name);
//               tv_activity3.setBackgroundColor(Color.parseColor(arItem.get(3).color));
//
//               tv_activity4.setText(arItem.get(4).name);
//               tv_activity4.setBackgroundColor(Color.parseColor(arItem.get(4).color));
//
//               tv_activity5.setText(arItem.get(5).name);
//               tv_activity5.setBackgroundColor(Color.parseColor(arItem.get(5).color));
//
//
//               tv_activity6.setText(arItem.get(6).name);
//               tv_activity6.setBackgroundColor(Color.parseColor(arItem.get(6).color));
//
//               tv_activity7.setText(arItem.get(7).name);
//               tv_activity7.setBackgroundColor(Color.parseColor(arItem.get(7).color));
//
//               ll_1.setVisibility(View.VISIBLE);
//               ll_2.setVisibility(View.VISIBLE);
//               ll_3.setVisibility(View.VISIBLE);
//               ll_4.setVisibility(View.VISIBLE);
//               ll_5.setVisibility(View.VISIBLE);
//               ll_6.setVisibility(View.VISIBLE);
//               ll_7.setVisibility(View.VISIBLE);
//               ll_8.setVisibility(View.VISIBLE);
//
//           }
//
//


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
