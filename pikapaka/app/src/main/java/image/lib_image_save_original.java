package image;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class lib_image_save_original {
	
	private Context context = null;

	public String url = "";
	public ImageView imageview = null;
	
	public lib_image_save_original(Context context, String file_url, ImageView imageview)
	{
		try 
		{
			this.context = context;
			this.url = file_url;
			this.imageview = imageview;
			
			if(!file_url.equals(""))
			{
				f_file_check(file_url, imageview);
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
	
	private String f_get_filepath(String file_url)
	{
		String save_path = "";
		try 
		{  
			String arr[] = file_url.split("/");
			String save_filename = arr[arr.length-1];
			save_path = (new lib_imagePath()).f_get_imagepath_cache_picture(context) + "/" + save_filename;  	
		}
		catch (Exception e)
		{
		}
		catch (Throwable t)
		{
		}
		
		return save_path;
	}
	
	private void f_file_check(String file_url, ImageView imageview)
	{
		try 
		{    	
			File file = new File(f_get_filepath(file_url));
	    	
	    	if(file.isFile() && file.exists()) 
	    	{
    			imageview.setImageURI(Uri.parse(f_get_filepath(file_url)));
    			imageview.setVisibility(View.VISIBLE);
	    	}
	    	else
	    	{
	    		MyAsyncTask myAsyncTask = new MyAsyncTask();
	            myAsyncTask.execute(file_url);
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
	
	public class MyAsyncTask extends AsyncTask<String, Void, String> {

    	@Override
    	protected void onPreExecute() {
    		super.onPreExecute();
    	}
    	
		@Override
		protected String doInBackground(String... params) {
			
			String value = "";
			
			File file = new File(f_get_filepath(url));
	    	
	    	if(file.isFile() && file.exists())
	    	{    	
	    	}
	    	else
	    	{
				f_file_save(url);
				f_size_check(url);
	    	}
			
			return value;
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			
			if(result != null){
			}
			
			try 
			{    	
				File file = new File(f_get_filepath(url));
		    	
		    	if(file.isFile() && file.exists() && imageview != null) 
		    	{   
		    		imageview.setImageURI(Uri.parse(f_get_filepath(url)));		
	    			imageview.setVisibility(View.VISIBLE);	
		    	}	
			}
			catch (Exception e)
			{
			}
			catch (Throwable t)
			{
			}			
		}
		
		@Override
		protected void onCancelled() {
			super.onCancelled();
		}		
    }
	
	private void f_file_save(String file_url)
	{
		int count;
        try {
            URL url = new URL(file_url);
            URLConnection conection = url.openConnection();
            conection.connect();

            // download the file
            BufferedInputStream input = new BufferedInputStream(url.openStream(), 8192);

            // Output stream
            OutputStream output = new FileOutputStream(f_get_filepath(file_url));

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
        	f_delete(file_url);
        	e.printStackTrace();
        }
		catch (Throwable t)
		{
        	f_delete(file_url);
			t.printStackTrace();
		}
	}
	
	private void f_delete(String file_url)
	{
		try 
		{
			File file = new File(f_get_filepath(file_url));
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
	
	private void f_size_check(String file_url)
	{
		try 
		{    	
			File file = new File(f_get_filepath(file_url));
	    	
	    	if(file.isFile() && file.exists()) 
	    	{    	
				Uri imageUri = Uri.fromFile(file);
	    		int scale = lib_image_get_scale.f_get_scale(context, imageUri, lib_util_image.required_size_original);
	    		int rotate = lib_get_rotate.f_get_rotate(imageUri);
	    		
	    		if(scale > 1 || rotate > 0)
	    		{
		    		AssetFileDescriptor afd = context.getContentResolver().openAssetFileDescriptor(imageUri, "r");
		    		
			    	BitmapFactory.Options options = new BitmapFactory.Options();
			    	options.inSampleSize = scale;
			    	Bitmap bitmap = BitmapFactory.decodeFileDescriptor(afd.getFileDescriptor(), null, options);
			    	
			    	if (rotate > 0) {
			            Matrix matrix = new Matrix();
			            matrix.postRotate(rotate);
			            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
			        }

					OutputStream outstream = new FileOutputStream(file);
					bitmap.compress(lib_util_image.compressformat, lib_util_image.quality, outstream);
					bitmap.recycle();
					bitmap = null;
	    		}
	    		else
	    		{
	    		}
	    	}
	    	else
	    	{
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
