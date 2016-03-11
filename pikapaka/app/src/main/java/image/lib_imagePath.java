package image;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;

import java.io.File;

public class lib_imagePath {
		
	public String f_get_imagepath_tmp(Activity activity)
	{
		String path = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/tmp";
		
		f_mkdir(path);
		
		return path;
	}
	
	public String f_get_imagepath_cache_thumb(Context context)
	{
		String path = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/thumb";
		
		f_mkdir(path);
		
		return path;
	}
	
	public String f_get_imagepath_cache_picture(Context context)
	{
		String path = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/picture";
		
		f_mkdir(path);
		
		return path;
	}
	
	public String f_get_imagepath_down(Activity activity)
	{
		String path = Environment.getExternalStorageDirectory().getPath() + "/Download";
		
		f_mkdir(path);
		
		return path;
	}
		
	private void f_mkdir(String path)
    {
    	try 
		{    		
        	File file = new File(path);
        	
        	if(! file.isDirectory()) {
        		file.mkdirs();
        	}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
    }
	
	
}
