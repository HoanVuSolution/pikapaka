package loading;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;

public class lib_dialog{
	
	public void f_dialog_msg(Context context, String msg)
	{
		try 
        {
			if(context != null)
			{
				new AlertDialog.Builder(context)
		    	.setMessage(msg)
		    	.setPositiveButton(android.R.string.ok, null)
		    	.show();
			}
		} 
        catch (Exception e) 
        {
		}
		catch (Throwable t)
		{
		}
	}
	
	public void f_dialog_msg(Context context, int rid)
	{
		try 
        {
			if(context != null)
			{
				new AlertDialog.Builder(context)
		    	.setMessage(rid)
		    	.setPositiveButton(android.R.string.ok, null)
		    	.show();
			}
		} 
        catch (Exception e) 
        {
		}
		catch (Throwable t)
		{
		}
	}
	
	void f_alert(Context context)
	{
		new AlertDialog.Builder(context)
    	.setMessage("message")
    	.setPositiveButton(android.R.string.ok, new AlertDialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) 
            {
            }
        })
    	.setNegativeButton(android.R.string.cancel, null)
    	.show();
	}
	
	void f_alert_2(Activity activity)
	{
		new AlertDialog.Builder(activity)
    	.setMessage("message")
    	.setPositiveButton(android.R.string.ok, new AlertDialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) 
            {
            }
        })
    	.setNegativeButton(android.R.string.cancel, null)
        .setOnCancelListener(new OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
            	
            }
        })
    	.show();
	}
}
