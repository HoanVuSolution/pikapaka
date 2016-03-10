package gcm;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import hoanvusolution.pikapaka.MainActivity;
import hoanvusolution.pikapaka.R;

public class GcmIntentService extends IntentService {
	Context context;
	public static final int NOTIFICATION_ID = 1;
	private NotificationManager mNotificationManager;
	NotificationCompat.Builder builder;
	public static final String TAG = "GCM PIKAPAKA";


	public GcmIntentService() {
		super("GcmIntentService");
	
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		//Log.d(TAG,intent.getDataString());
		Bundle extras = intent.getExtras();
//		String msg = intent.getStringExtra("message");
//		String title = intent.getStringExtra("title");
		Log.e("extras--------",extras.toString());
		Log.e("extras--------",extras.get("message").toString());
//		Log.e("title-------",title);
        String msg = "";
        String title = "";
        //Log.e("msg--------",msg);

       // Log.e("title-------",title);
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
		String messageType = gcm.getMessageType(intent);
        Log.e("messageType",messageType);

		if (!extras.isEmpty()) {

			if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR
					.equals(messageType)) {
				sendNotification("Send error: " + extras.toString());
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED
					.equals(messageType)) {
				sendNotification("Deleted messages on server: "
						+ extras.toString());
				// If it's a regular GCM message, do some work.
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE
					.equals(messageType)) {
				// This loop represents the service doing some work.
				for (int i = 0; i < 5; i++) {
					Log.e(TAG,
							"Working... " + (i + 1) + "/5 @ "
									+ SystemClock.elapsedRealtime());
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
					}
				}
				Log.e(TAG, "Completed work @ " + SystemClock.elapsedRealtime());

				//f_add_notification_statusbar(msg);

				sendNotification(msg);
				Log.e(TAG, "Received: " + extras.toString());
			}
		}
		GcmBroadcastReceiver.completeWakefulIntent(intent);
	}

	@SuppressWarnings("deprecation")
	private void sendNotification(String message) {
		mNotificationManager = (NotificationManager) this
				.getSystemService(Context.NOTIFICATION_SERVICE);

		Uri uri = RingtoneManager
				.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

		Intent myintent = new Intent(this,MainActivity.class)
				.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);

		myintent.putExtra("message", message);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				myintent, PendingIntent.FLAG_UPDATE_CURRENT);

		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				this).setSmallIcon(R.mipmap.ic_launcher)
				.setContentTitle("Pika Paka")
				.setStyle(new NotificationCompat.BigTextStyle().bigText(message))
				.setContentText(message).setSound(uri)
				.setAutoCancel(true)
				;


		mBuilder.setContentIntent(contentIntent);
		mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
		// ----------
		

		
	}

	


}