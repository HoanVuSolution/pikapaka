package image;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Random;

public class lib_get_image_from_camera {
	private Activity activity = null;

	private final int FROM_CAMERA = 700;
	private final int FROM_CROP = 702;

	public Uri imageUri_copy_from_original = null;
	public Uri imageUri_crop = null;
	
	public lib_get_image_from_camera(Activity activity)
	{		
		try 
		{
			this.activity = activity;

			imageUri_copy_from_original = createSaveCropFile();
			imageUri_crop = createSaveCropFile();
			
		}
		catch (Exception e) 
		{
			// TODO: handle exception
		}
	}
		
	@SuppressWarnings("unused")
	private void f_doTakePhotoAction()
	{
		try 
		{
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri_copy_from_original);
			activity.startActivityForResult(intent, FROM_CAMERA);
		}
		catch (Exception e) 
		{
			// TODO: handle exception
		}
		catch (Throwable t)
		{
		}	
	}
	
	private void f_to_crop()
	{
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(imageUri_copy_from_original, "image/*"); 
		
		intent.putExtra("output", imageUri_crop);
		intent.putExtra("scale", true);
		intent.putExtra("max-width", 960);
	    intent.putExtra("max-height", 960);
	    activity.startActivityForResult(intent, FROM_CROP);
	}
	
	private void f_file_scale_change_save(Uri imageUri)
	{
		try 
		{
			int scale = lib_image_get_scale.f_get_scale_picture_quality(activity, imageUri);
			
			AssetFileDescriptor afd = activity.getContentResolver().openAssetFileDescriptor(imageUri, "r");
			 
	    	BitmapFactory.Options options = new BitmapFactory.Options();
	    	options.inSampleSize = scale;
	    	Bitmap selPhoto = BitmapFactory.decodeFileDescriptor(afd.getFileDescriptor(), null, options);
			
			String photo_path = imageUri.getPath();
			
			File copyFile = new File(photo_path);
	   	   	//copyFile.createNewFile();
			OutputStream outstream = new FileOutputStream(copyFile);
			selPhoto.compress(Bitmap.CompressFormat.PNG, 75, outstream);
		}
		catch (Exception e) 
		{
			// TODO: handle exception
		}
		catch (Throwable t)
		{
		}	
	}

	private Uri createSaveCropFile(){
		Uri uri;
		Random generator = new Random();
		String filename = String.valueOf(generator.nextInt((int) 100)) + String.valueOf(System.currentTimeMillis()) + ".jpg";;

		uri = Uri.fromFile(new File((new lib_imagePath()).f_get_imagepath_tmp(activity), filename));
		return uri;
	}
	
	public void f_ActivityResult(int requestCode, Intent data)
	{
		try
		{
			switch(requestCode)
			{		
				case FROM_CAMERA:
				{
					f_to_crop();
					break;
				}				
				case FROM_CROP:
				{
					f_file_scale_change_save(imageUri_crop);
					break;
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		catch (Throwable t)
		{
		}
	}
}
