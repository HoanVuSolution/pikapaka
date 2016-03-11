package image;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.ImageView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;

public class lib_image_async_kong {
	
	private Context context = null;
	
	public String url = "";
	public ImageView imageview = null;
	
	public lib_image_async_kong(Context context, String file_url, ImageView imageview)
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
				try {
					f_file_save(url);
					f_size_check(url);
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
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
	
	private void f_file_save(String file_url) throws URISyntaxException
	{
		int count;
        try {
        	
        	HttpGet httpRequest = null;
        	httpRequest = new HttpGet(file_url);
        	HttpClient httpclient = new DefaultHttpClient();
        	HttpResponse response = (HttpResponse) httpclient.execute(httpRequest);
        	HttpEntity entity = response.getEntity();
        	BufferedHttpEntity bufHttpEntity = new BufferedHttpEntity(entity);
        	InputStream instream = bufHttpEntity.getContent();

        	BitmapFactory.Options options=new BitmapFactory.Options();
        	options.inSampleSize = 2;
        	options.inJustDecodeBounds = false;
            options.inDither = false;
            options.inScaled = false;
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            options.inPurgeable = true;
        	Bitmap bmp = BitmapFactory.decodeStream(instream, null, options);
        	
        	ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
			bmp.compress(lib_util_image.compressformat, lib_util_image.quality, bos);
        	byte[] bitmapdata = bos.toByteArray();
        	ByteArrayInputStream bs = new ByteArrayInputStream(bitmapdata);
        	
            // Output stream
            OutputStream output = new FileOutputStream(f_get_filepath(file_url));

            byte data[] = new byte[1024];

            while ((count = bs.read(data)) != -1) {
                output.write(data, 0, count);
            }

            // flushing output
            output.flush();

            // closing streams
            output.close();
            bs.close();
            
            bmp.recycle();
            bmp = null;

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
	    		int rotate = lib_get_rotate.f_get_rotate(imageUri);
	    		
	    		if(rotate > 0)
	    		{
		    		AssetFileDescriptor afd = context.getContentResolver().openAssetFileDescriptor(imageUri, "r");
		    		
			    	BitmapFactory.Options options = new BitmapFactory.Options();
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
