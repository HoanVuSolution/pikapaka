package image;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

public class lib_image_get_scale {
	
	public static int f_get_scale_picture_quality(Activity activity, Uri mImageCaptureUri)
	{
		int REQUIRED_SIZE = 1920;
        int scale=1;
		try 
		{			
			String photo_path = mImageCaptureUri.getPath();	
			
			int width = getBitmapOfWidth(photo_path);
			int height = getBitmapOfHeight(photo_path);
	        
			while(true){
	            if(width <= REQUIRED_SIZE && height <= REQUIRED_SIZE)
	            {
	                break;
	            }
	            else
	            {
	                width /= 2;
	                height /= 2;
	                scale *= 2;
	            }
	        }
		} 
		catch (Exception e) 
		{
		}
		catch (Throwable t)
		{
		}
		
		return scale;
	}
	
	public static int f_get_scale(Context context, Uri mImageCaptureUri, int required_size)
	{
        int scale=1;
		try 
		{	
			String photo_path = mImageCaptureUri.getPath();	
			
			int width = getBitmapOfWidth(photo_path);
			int height = getBitmapOfHeight(photo_path);
	        
			while(true){
	            if(width <= required_size && height <= required_size)
	            {
	                break;
	            }
	            else
	            {
	                width /= 2;
	                height /= 2;
	                scale *= 2;
	            }
	        }
		} 
		catch (Exception e) 
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return scale;
	}
	
	@SuppressWarnings("deprecation")
	public static String getRealPathFromURI(Activity activity, Uri contentUri) {
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor cursor = activity.managedQuery(contentUri, proj, null, null, null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	/** Get Bitmap's Width **/
	public static int getBitmapOfWidth(String fileName) {
		try {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(fileName, options);
			return options.outWidth;
		} catch (Exception e) {
			return 0;
		}
	}

	/** Get Bitmap's height **/
	public static int getBitmapOfHeight(String fileName) {
		try {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(fileName, options);

			return options.outHeight;
		} catch (Exception e) {
			return 0;
		}
	}
}
