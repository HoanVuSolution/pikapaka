package image;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Window;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

public class Activity_Get_Image extends Activity {	
	private Activity activity = null;	

	private final int FROM_CAMERA = 700;
	private final int FROM_ALBUM = 701;

	public Uri imageUri_copy_from_original = null;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try 
		{
	        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	        	        
	        activity = this;
	        init();
		} 
		catch (Exception e)
		{
		}
		catch (Throwable t)
		{
		}
	}	
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	    super.onConfigurationChanged(newConfig);

	    // Checks the orientation of the screen
	    if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//	        Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
	    } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
//	        Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
	    }
	}
	
	@Override
    public void onActivityResult( int requestCode, int resultCode, Intent data ) {
		if (resultCode != RESULT_OK) {
			finish();
		}
		else
		{
			f_ActivityResult(requestCode, data);
		}
	}
	
	private void f_ActivityResult(int requestCode, Intent data)
	{
		try
		{
			switch(requestCode)
			{		
				case FROM_ALBUM:
				{
					f_from_gallery(data);
					f_file_scale_change_save(imageUri_copy_from_original);
					
					Intent mIntent = getIntent();
					mIntent.putExtra("act", "image");
					mIntent.putExtra("position", position);
					mIntent.putExtra("path", imageUri_copy_from_original.getPath().toString());
			        setResult(RESULT_OK, mIntent);
			        finish();
					break;
				}
				case FROM_CAMERA:
				{
					
					f_file_scale_change_save(imageUri_copy_from_original);

					Intent mIntent = getIntent();
					mIntent.putExtra("act", "image");
					mIntent.putExtra("position", position);
					mIntent.putExtra("path", imageUri_copy_from_original.getPath().toString());
			        setResult(RESULT_OK, mIntent);
			        finish();
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
	
	private void init() throws Exception, Throwable
	{
		f_get_parm();
	}
	
	private String act = "";
	private int position = 0;
	private void f_get_parm() throws Exception, Throwable
	{
		Intent intent = getIntent();
        act = intent.getStringExtra("act");
        position = intent.getIntExtra("position", 0);
        
        imageUri_copy_from_original = createSaveCropFile();
        if(act.equals("album"))
        {
        	f_doTakeAlbumAction();
        }
        else
        {
        	f_doTakePhotoAction();
        }
	}
	
	private void f_doTakeAlbumAction()
	{
		try 
		{
			Intent intent = new Intent(Intent.ACTION_PICK);
			intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
			activity.startActivityForResult(intent, FROM_ALBUM);
		}
		catch (Exception e) 
		{
			// TODO: handle exception
		}
		catch (Throwable t)
		{
		}
	}
	
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
	
	private void f_from_gallery(Intent data)
	{
		Uri original_imageUri = data.getData();
		File original_file = getImageFile(original_imageUri);
		
		File new_file = new File(imageUri_copy_from_original.getPath()); 

		copyFile(original_file , new_file);
	}
	
	private void f_file_scale_change_save(Uri imageUri)
	{
		try 
		{
			int scale = lib_image_get_scale.f_get_scale_picture_quality(activity, imageUri);
			int rotate = lib_get_rotate.f_get_rotate(imageUri);
			
			AssetFileDescriptor afd = activity.getContentResolver().openAssetFileDescriptor(imageUri, "r");
			 
	    	BitmapFactory.Options options = new BitmapFactory.Options();
	    	options.inSampleSize = scale;
	    	Bitmap bitmap = BitmapFactory.decodeFileDescriptor(afd.getFileDescriptor(), null, options);
	    	
	    	if (rotate > 0) {
	            Matrix matrix = new Matrix();
	            matrix.postRotate(rotate);
	            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
	        }
			
			String photo_path = imageUri.getPath();
			
			File copyFile = new File(photo_path);
	   	   	//copyFile.createNewFile();
			OutputStream outstream = new FileOutputStream(copyFile);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outstream);
			bitmap.recycle();
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			e.printStackTrace();
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
	
	private File getImageFile(Uri uri) {
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

	private boolean copyFile(File srcFile, File destFile) {
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
	
	private boolean copyToFile(InputStream inputStream, File destFile) {
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
}
