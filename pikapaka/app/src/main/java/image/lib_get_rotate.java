package image;

import android.media.ExifInterface;
import android.net.Uri;

import java.io.File;

public class lib_get_rotate {
	public static int f_get_rotate(Uri imageUri)
	{
        int rotate = 0;
		try
		{
			File imageFile = new File(imageUri.getPath());
			ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
	        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
	        
	        switch (orientation) {
	        case ExifInterface.ORIENTATION_ROTATE_270:
	        	rotate = 270;
	        	break;
	        case ExifInterface.ORIENTATION_ROTATE_180:
	        	rotate = 180;
	        	break;
	        case ExifInterface.ORIENTATION_ROTATE_90:
	        	rotate = 90;
	        	break;
	        }
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		catch (Throwable t)
		{
		}
		
		return rotate;
	}
}
