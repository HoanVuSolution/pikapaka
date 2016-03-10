package loading;



import android.app.ProgressDialog;
import android.content.Context;
import android.widget.ProgressBar;

public class lib_loading {
	
	public static ProgressDialog f_init(Context context)
	{
		ProgressDialog progressDialog = new ProgressDialog(context);
		
		try 
		{
			progressDialog = ProgressDialog.show(context, "", "Loading...", true, true);
			progressDialog.setCanceledOnTouchOutside(false);
			
			ProgressBar progressbar = new ProgressBar(context);
			progressDialog.setContentView(progressbar);
			
		} 
		catch (Exception e) 
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		catch (Throwable t)
		{
		}
		
		return progressDialog;
	}
}
