package lu.mir.android.pomodorobox;

import java.io.IOException;
import java.util.Locale;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.widget.Toast;

import com.dropbox.sync.android.DbxPath.InvalidPathException;

public class TimerService extends Service implements OnInitListener {

	  public static final int MSG_INCREMENT = 1;
	  public static final int MSG_COUNTER = 2;

		private static long SECOND = 1000;
		private long initial_count;
		private CountDownTimer timer;
		private TextToSpeech tts;
		private String message;
	  
		private int ONGOING_NOTIFICATION_ID = 101;
		private Builder mNotifyBuilder;
	  



	@Override
		public void onDestroy() {
			timer.cancel();
			stopSelf();
			super.onDestroy();
		}


	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		

		mNotifyBuilder = new NotificationCompat.Builder(this)
	    .setContentTitle("Pomodoro in progress...")
	    .setContentText("Pomodoro in progress...")
	    .setSmallIcon(R.drawable.ic_launcher)
	    ;
		Notification notification = mNotifyBuilder.build();

		
		Intent notificationIntent = new Intent(this, TimerActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
		notification.setLatestEventInfo(this, "Pomodoro in progress",
		        message, pendingIntent);
		startForeground(ONGOING_NOTIFICATION_ID, notification);
		
		
		 Toast.makeText(this, "started", Toast.LENGTH_SHORT).show();
		 initial_count = intent.getLongExtra(MainActivity.EXTRA_TIME_IN_SECONDS, 7);
		 message = intent.getStringExtra("MESSAGE");
		 
			timer = new CountDownTimer(initial_count, SECOND) {
				private NotificationManager mNotificationManager;

				public void onTick(long millisUntilFinished) {
					Intent i = new Intent("lu.mir.android.pomodorobox.SECOND_ELAPSED");
					i.putExtra("SECONDS", millisUntilFinished);
					sendBroadcast(i);
					
					// update the notification bar
					long minsToFinish = millisUntilFinished / 1000 / 60;
					String minuteString = ((minsToFinish < 10)?"0":"") + minsToFinish;
					 mNotifyBuilder.setContentText(message)
					 .setContentTitle("Pomodoro: " + " " + minuteString + " more min");
					 
					// update the notification
					 mNotificationManager =
						        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

				    mNotificationManager.notify(
				    		ONGOING_NOTIFICATION_ID,
				            mNotifyBuilder.build());
				}

				public void onFinish() {
					//updateTimer(1);
					speak("Well done man!");
					stopSelf();
					try {
						DropBoxConnection.logPomodoroToDropbox(message);
					} catch (InvalidPathException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}.start();
		
		 return super.onStartCommand(intent, flags, startId);
	}
	  
	
	@Override
	public void onCreate() {
		super.onCreate();
		tts = new TextToSpeech(this, this);
	}



	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected void speak(String text) {
	
		tts.setLanguage(Locale.US);
		tts.speak(text, TextToSpeech.QUEUE_ADD, null);
	}


	@Override
	public void onInit(int status) {
		// TODO Auto-generated method stub
		
	}



}
