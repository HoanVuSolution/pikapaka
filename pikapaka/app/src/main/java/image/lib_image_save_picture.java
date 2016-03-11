package image;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class lib_image_save_picture {
	
	private Activity activity = null;
	private ImageView imageview = null;
	private String file_url = "";
	private String save_path = "";
	private String save_filename = "";
	private int required_size = 5000;
	
	public lib_image_save_picture(Activity activity, String file_url, ImageView imageview)
	{
		try 
		{    	
			this.activity = activity;
			this.imageview = imageview;
			this.file_url = file_url;
			
			f_get_filename();
			f_file_check();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		catch (Throwable t)
		{
			t.printStackTrace();
		}
	}
	
	private void f_get_filename()
	{
		String arr[] = file_url.split("/");
		save_filename = arr[arr.length-1];
		save_path = (new lib_imagePath()).f_get_imagepath_cache_picture(activity) + "/" + save_filename;
	}

	private void f_file_check()
	{
		try 
		{    	
			File file = new File(save_path);
	    	
	    	if(file.isFile() && file.exists()) 
	    	{    	
				Uri imageUri = Uri.fromFile(file);
	    		int scale = lib_image_get_scale.f_get_scale(activity, imageUri, required_size);
	    		
	    		if(scale > 1)
	    		{	    		
		    		AssetFileDescriptor afd = activity.getContentResolver().openAssetFileDescriptor(imageUri, "r");
		    		
			    	BitmapFactory.Options options = new BitmapFactory.Options();
			    	options.inSampleSize = scale;
			    	Bitmap selPhoto = BitmapFactory.decodeFileDescriptor(afd.getFileDescriptor(), null, options);
			    	
			    	imageview.setImageBitmap(selPhoto);
	    		}
	    		else
	    		{
	    			imageview.setImageURI(Uri.parse(save_path));
	    		}
	    		
	    		double img_width = lib_image_get_scale.getBitmapOfWidth(save_path);
	    		double img_height = lib_image_get_scale.getBitmapOfHeight(save_path);
				
				@SuppressWarnings("deprecation")
				double view_width= activity.getWindowManager().getDefaultDisplay().getWidth();

				double d = view_width / img_width;
				double view_height = img_height * d;
				
	    		imageview.setScaleType(ScaleType.CENTER_CROP);
//	    		imageview.getLayoutParams().width = LayoutParams.FILL_PARENT;  
	    		imageview.getLayoutParams().height = (int) view_height;  
	    	}
	    	else
	    	{
	    		init_thread();
	    	}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		catch (Throwable t)
		{
			t.printStackTrace();
		}
	}
	
	@SuppressLint({ "HandlerLeak" })
	private void init_thread()
	{
		final Handler mHandler = new Handler() {
			public void handleMessage(Message msg)
			{
				try 
				{    	
					f_file_check();
				} 
				catch (Exception e)
				{
					e.printStackTrace();
				}
				catch (Throwable t)
				{
					t.printStackTrace();
				}
			}
		};
		
		Thread thread = new Thread(new Runnable() {
			public void run() {
				f_file_save();
				mHandler.sendEmptyMessage(0);
			}
		});
		thread.start();
	}
	
	private void f_file_save()
	{
		int count;
        try {
            URL url = new URL(file_url);
            URLConnection conection = url.openConnection();
            conection.connect();

            // download the file
            BufferedInputStream input = new BufferedInputStream(url.openStream(), 8192);

            // Output stream
            OutputStream output = new FileOutputStream(save_path);

            byte data[] = new byte[1024];

            while ((count = input.read(data)) != -1) {
                output.write(data, 0, count);
            }

            // flushing output
            output.flush();

            // closing streams
            output.close();
            input.close();

        } 
        catch (Exception e) 
        {
        	f_delete(save_path);
        	e.printStackTrace();
        }
		catch (Throwable t)
		{
        	f_delete(save_path);
			t.printStackTrace();
		}
	}
	
	private void f_delete(String save_path)
	{
		try 
		{
			File file = new File(save_path);
        	if(file.isFile())
        	{
        		file.delete();
        	}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		catch (Throwable t)
		{
			t.printStackTrace();
		}
	}
}
