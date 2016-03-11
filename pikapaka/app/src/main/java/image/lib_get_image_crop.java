package image;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

public class lib_get_image_crop {
	private Activity activity = null;

	private final int FROM_CAMERA = 700;
	private final int FROM_ALBUM = 701;
	private final int FROM_CROP = 702;

	public Uri imageUri_copy_from_original = null;
	public Uri imageUri_crop = null;
	public ImageView imageview = null;
	
	public lib_get_image_crop(Activity activity, ImageView imageview)
	{		
		try 
		{
			this.activity = activity;
			this.imageview = imageview;

			imageUri_copy_from_original = createSaveCropFile();
			imageUri_crop = createSaveCropFile();
		}
		catch (Exception e)
		{
		}
		catch (Throwable t)
		{
		}
	}
	
	public void f_image_select_dialog() throws Exception, Throwable
	{
		try 
		{
			String[] item = new String[] {"���������� ����", "ī�޶� ���"};					
			new AlertDialog.Builder(activity)
			.setItems(item, new DialogInterface.OnClickListener() {		
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
			        try 
					{
						switch(which)
						{
						case 0:
							f_doTakeAlbumAction();
							break;
						case 1:
							f_doTakePhotoAction();
							break;
						}
						dialog.dismiss();
					} 
					catch (Exception e)
					{
					}
					catch (Throwable t)
					{
					}
				}
			})
			.show();
		}
		catch (Exception e) 
		{
		}
		catch (Throwable t)
		{
		}
	}
	
	private void f_doTakeAlbumAction() throws Exception, Throwable
	{
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
		activity.startActivityForResult(intent, FROM_ALBUM);
	}
	
	private void f_doTakePhotoAction() throws Exception, Throwable
	{
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri_copy_from_original);
		activity.startActivityForResult(intent, FROM_CAMERA);
	}
	
	private void f_from_gallery(Intent data) throws Exception, Throwable
	{
		Uri original_imageUri = data.getData();
		File original_file = getImageFile(original_imageUri);
		
		File new_file = new File(imageUri_copy_from_original.getPath()); 

		copyFile(original_file , new_file);
	}
	
	private void f_to_crop() throws Exception, Throwable
	{
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(imageUri_copy_from_original, "image/*"); 
		
		intent.putExtra("output", imageUri_crop);
		intent.putExtra("scale", true);
		intent.putExtra("max-width", 960);
	    intent.putExtra("max-height", 960);
	    activity.startActivityForResult(intent, FROM_CROP);
	}
	
	private void f_file_scale_change_save(Uri imageUri) throws Exception, Throwable
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
		selPhoto.compress(Bitmap.CompressFormat.JPEG, 80, outstream);
		selPhoto.recycle();
	}

	private Uri createSaveCropFile() throws Exception, Throwable
	{
		Uri uri;
		Random generator = new Random();
		String filename = String.valueOf(generator.nextInt((int) 100)) + String.valueOf(System.currentTimeMillis()) + ".jpg";;

		uri = Uri.fromFile(new File((new lib_imagePath()).f_get_imagepath_tmp(activity), filename));
		return uri;
	}
	
	private File getImageFile(Uri uri) throws Exception, Throwable
	{
		String[] projection = { MediaStore.Images.Media.DATA };
		if (uri == null) {
			uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
		}

		Cursor mCursor = activity.getContentResolver().query(uri, projection, null, null, 
				MediaStore.Images.Media.DATE_MODIFIED + " desc");
		if(mCursor == null || mCursor.getCount() < 1) {
			return null; // no cursor or no record
		}
		int column_index = mCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		mCursor.moveToFirst();

		String path = mCursor.getString(column_index);

		if (mCursor !=null ) {
			mCursor.close();
			mCursor = null;
		}

		return new File(path);
	}

	private boolean copyFile(File srcFile, File destFile)
	{
		boolean result = false;
		try {
			InputStream in = new FileInputStream(srcFile);
			try {
				result = copyToFile(in, destFile);
			} finally  {
				in.close();
			}
		} catch (IOException e) {
			result = false;
		}
		return result;
	}
	
	private boolean copyToFile(InputStream inputStream, File destFile)
	{
		try {
			OutputStream out = new FileOutputStream(destFile);
			try {
				byte[] buffer = new byte[4096];
				int bytesRead;
				while ((bytesRead = inputStream.read(buffer)) >= 0) {
					out.write(buffer, 0, bytesRead);
				}
			} finally {
				out.close();
			}
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public void f_ActivityResult(int requestCode, Intent data)
	{
		try
		{
			switch(requestCode)
			{		
				case FROM_ALBUM:
				{
					f_from_gallery(data);
//					imageview.setImageURI(imageUri_original);
					f_to_crop();		
					break;
				}
				case FROM_CAMERA:
				{
					f_to_crop();
					break;
				}				
				case FROM_CROP:
				{
					f_file_scale_change_save(imageUri_crop);
					imageview.setImageURI(imageUri_crop);
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
