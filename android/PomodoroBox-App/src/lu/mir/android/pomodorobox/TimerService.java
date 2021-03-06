package lu.mir.android.pomodorobox;

import static lu.mir.android.pomodorobox.util.Duration.SECOND;
import lu.mir.android.pomodorobox.activities.MainActivity;
import lu.mir.android.pomodorobox.activities.TimerActivity;
import lu.mir.android.pomodorobox.persistence.PomodoroDatabase;
import lu.mir.android.pomodorobox.util.EnglishSpeechEngine;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.widget.Toast;

import com.dropbox.sync.android.DbxPath.InvalidPathException;

public class TimerService extends Service implements OnInitListener {

	private int ONGOING_NOTIFICATION_ID = 101;
	
	public static final String TIMER_BROADCAST_MESSAGE = "lu.mir.android.pomodorobox.SECOND_ELAPSED";
	public static final String TIMER_BROADCAST_MESSAGE_PAYLOAD = "SECOND";
	public static final String TIMER_BROADCAST_MESSAGE_PAYLOAD_STATE = "TIMER_STATE";
	
	public static final int STATE_POMODORO = 1;
	public static final int STATE_BREAK = 2;
	public static final int STATE_DONE = 3;	

	private Pomodoro pomodoro;
	PomodoroDatabase db;
	private CountDownTimer timer;
	private EnglishSpeechEngine speechEngine;
	private Builder mNotifyBuilder;
	

	@Override
	public void onDestroy() {
		timer.cancel();
		stopSelf();
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		// Get the payload and save it locally
		pomodoro = (Pomodoro) intent.getSerializableExtra(TimerActivity.EXTRA_POMODORO);
		db = (PomodoroDatabase) intent.getSerializableExtra(MainActivity.DB);

		// Start the timer
		timer = new CountDownTimer(pomodoro.getDuration(), SECOND) {
			public void onTick(long millisUntilFinished) {
				broadcastSecondElapsed(millisUntilFinished, STATE_POMODORO);
				updateTaskbar(millisUntilFinished, getString(R.string.notification_progress_state_pomodoro));
			}
			public void onFinish() {
				anounceAndLogPomodoroFinished();
				startBreakTimer();
			}
		}.start();
		
		taskbarNotify(getString(R.string.pomodoro_in_progress));
		
		return super.onStartCommand(intent, flags, startId);
	}
	
	public void startBreakTimer() {
		timer = new CountDownTimer(pomodoro.getPomodoroBreakDuration(), SECOND) {
			@Override
			public void onTick(long millisUntilFinished) {
				broadcastSecondElapsed(millisUntilFinished, STATE_BREAK);
				updateTaskbar(millisUntilFinished, getString(R.string.notification_progress_state_break));
			}
			
			@Override
			public void onFinish() {
				speechEngine.speak(getString(R.string.ready_for_next_one));
				broadcastSecondElapsed(0, STATE_DONE);
				stopSelf();
			}
		}.start();
	}

	private void broadcastSecondElapsed(long millisUntilFinished, int state) {
		Intent i = new Intent(TIMER_BROADCAST_MESSAGE);
		i.putExtra(TIMER_BROADCAST_MESSAGE_PAYLOAD, millisUntilFinished);
		i.putExtra(TIMER_BROADCAST_MESSAGE_PAYLOAD_STATE, state);
		sendBroadcast(i);
	}

	private void updateTaskbar(long millisUntilFinished, String state) {
		NotificationManager mNotificationManager;
		long minsToFinish = millisUntilFinished / 1000 / 60;

		String minuteString = ((minsToFinish < 10) ? "0" : "") + minsToFinish;
		mNotifyBuilder
				.setContentTitle(pomodoro.getNameAndTagRepresentation())
				.setContentText( 
						state + ":" +
						minuteString +	" " + 
						getString(R.string.notification_progress_text))
				.setProgress(25, 25 - (int) minsToFinish, false);

		// update the notification
		mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		mNotificationManager.notify(ONGOING_NOTIFICATION_ID,
				mNotifyBuilder.build());
	}

	@SuppressWarnings("deprecation")
	private void taskbarNotify(String notificationText) {
		mNotifyBuilder = new NotificationCompat.Builder(this)
				.setContentTitle(getString(R.string.welcome))
				.setContentText(notificationText)
				.setSmallIcon(R.drawable.ic_launcher);
		Notification notification = mNotifyBuilder.build();

		Intent notificationIntent = new Intent(
				"lu.mir.android.pomodorobox.TimerActivity");
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
				notificationIntent, 0);
		notification.setLatestEventInfo(this, notificationText, pomodoro.getNameAndTagRepresentation(),
				pendingIntent);

		startForeground(ONGOING_NOTIFICATION_ID, notification);
		Toast.makeText(this, "started", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onCreate() {
		super.onCreate();
		speechEngine = new EnglishSpeechEngine(this, this);
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onInit(int status) {
		// TODO Auto-generated method stub
	}

	private void anounceAndLogPomodoroFinished() {
		speechEngine.speak(getString(R.string.congratulation_message));
		try {
			db.logPomodoro(pomodoro.getNameAndTagRepresentation(), PomodoroBoxApplication.context());
		} catch (InvalidPathException e) {
			e.printStackTrace();
		}
	}

}
