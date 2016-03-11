package image;

import android.widget.ImageView;

public class item_img_download {
	public item_img_download(
			String url, 
			ImageView imageview
			) {

		this.url = url;
		this.imageview = imageview;
	}
	
	public String url = "";
	public ImageView imageview = null;
}
