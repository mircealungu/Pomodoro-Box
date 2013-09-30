package lu.mir.android.pomodorobox;

import java.io.IOException;
import java.util.Locale;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.TextView;

import com.dropbox.sync.android.DbxPath.InvalidPathException;

/**
 * The TextToSpeech code is from:
 * http://android-developers.blogspot.ch/2009/09/introduction
 * -to-text-to-speech-in.html
 * 
 * @author mircea
 * 
 */

public class TimerActivity extends Activity implements OnInitListener {

	// Used to save the state between Activity restarts (which can happen even
	// when orientation is changed)
	static final String STATE_MILLIS = "millisRemaining";

	private TextToSpeech tts;
	private String pomodoroName;
	

	/*
	 * The broadcast receiver is registered when the app gets in the foreground
	 * and de-registerd when it is in the background. This means that we handle
	 * no callbacks when not in the foreground The callback that gets called is
	 * updateTheTimerComponent
	 */
	private SecondElapsedReceiver broadcastReceiver = null;
	Boolean myReceiverIsRegistered = false;

	private Intent xintent;

	private long pomodoroDuration;

	private long pomodoroBreakDuration;

	public final static String EXTRA_POMODORO_DURATION = "lu.mir.android.pomodorobox.TIME";
	public final static String EXTRA_POMODORO_NAME = "lu.mir.android.pomodorobox.MESSAGE";
	public final static String EXTRA_POMODORO_BREAK_DURATION = "lu.mir.android.pomodorobox.BREAK_DURATION";
	
	public final static long SECOND = 1000;
	public final static long POMODORO_DURATION = 25 * 60 * SECOND;
	public final static long POMODORO_BREAK_DURATION = 5 * 60 * SECOND;
	public final static long BLITZ_DURATION = 10 * SECOND;
	public final static long BLITZ_BREAK_DURATION = 10 * SECOND;
	

	/*
	 * This is the callback of the timer service
	 */
	class SecondElapsedReceiver extends BroadcastReceiver {

		private int alreadyShowingBreak;

		@Override
		public void onReceive(Context context, Intent intent) {
			long time = intent.getLongExtra(TimerService.TIMER_BROADCAST_MESSAGE_PAYLOAD, 0);
			int state = intent.getIntExtra(TimerService.TIMER_BROADCAST_MESSAGE_PAYLOAD_STATE, 0);
			
			if (state == TimerService.STATE_DONE)
				{
					finish();
					return;
				}
			
			if (state == TimerService.STATE_BREAK)
			{
				if (alreadyShowingBreak != TimerService.STATE_BREAK)
					inflateBreakLayout();
				alreadyShowingBreak = TimerService.STATE_BREAK;
			}
			
			updateTheTimerComponent(time);
			
		}
	}

	/**
	 * Update the timer component Gets called every second
	 * 
	 * @param millisUntilFinished
	 */
	protected void updateTheTimerComponent(long millisUntilFinished) {

		long minsToFinish = millisUntilFinished / 1000 / 60;
		long secs = millisUntilFinished / 1000 % 60;

		String minuteString = ((minsToFinish < 10) ? "0" : "") + minsToFinish;
		String secondsString = (secs < 10 ? "0" : "") + secs;

		TextView counterView = (TextView) findViewById(R.id.counter);

		counterView.setText(minuteString + ":" + secondsString);
	}

	public void inflateBreakLayout() {
		setContentView(R.layout.activity_break_countdown);
		TextView activityView = (TextView) findViewById(R.id.activity);
		activityView.setText(pomodoroName);
	}


	@Override
	public void finish() {
		super.finish();
	}
	


	protected void speak(String text) {
		tts.setLanguage(Locale.US);
		tts.speak(text, TextToSpeech.QUEUE_ADD, null);
	}

	protected void logPomodoroToDropbox() throws InvalidPathException,
			IOException {
		DropBoxConnection.logPomodoroToDropbox(pomodoroName);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Initialize
		broadcastReceiver = new SecondElapsedReceiver();
		tts = new TextToSpeech(this, this);

		startTheTimerService(savedInstanceState);

		// Set the text view as the activity layout
		setContentView(R.layout.activity_countdown);
		TextView activityView = (TextView) findViewById(R.id.activity);
		activityView.setText(pomodoroName);
	}

	/*
	 * The Bundle instance comes from OnCreate()
	 */
	
	private void startTheTimerService(Bundle savedInstanceState) {
		Intent intent = getIntent();
		pomodoroName = intent.getStringExtra(TimerActivity.EXTRA_POMODORO_NAME);
		pomodoroDuration = intent.getLongExtra(TimerActivity.EXTRA_POMODORO_DURATION, 10);
		pomodoroBreakDuration = intent.getLongExtra(TimerActivity.EXTRA_POMODORO_BREAK_DURATION, 10);

		if (savedInstanceState == null) {
			xintent = new Intent(this, TimerService.class);
			xintent.putExtra(TimerActivity.EXTRA_POMODORO_DURATION, pomodoroDuration);
			xintent.putExtra(TimerActivity.EXTRA_POMODORO_BREAK_DURATION, pomodoroBreakDuration);
			xintent.putExtra(TimerActivity.EXTRA_POMODORO_NAME, pomodoroName);
			startService(xintent);
		}
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onInit(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onResume() {
		if (!myReceiverIsRegistered) {
			registerReceiver(broadcastReceiver, new IntentFilter(
					TimerService.TIMER_BROADCAST_MESSAGE));
			myReceiverIsRegistered = true;
			if (!isTimerServiceRunning()){
				finish();
			}
		} else {
			// resuming.
			// i am registered to an event
		}
		super.onResume();
	}

	@Override
	protected void onPause() {
		if (myReceiverIsRegistered) {
			unregisterReceiver(broadcastReceiver);
			myReceiverIsRegistered = false;
		}
		super.onPause();
	}

	@Override
	public void onBackPressed() {
		new AlertDialog.Builder(this)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle("Stopping the counter")
				.setMessage(R.string.warning_reset)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								finishAndStopTheService();
								
							}

						}).setNegativeButton("No", null).show();
	}
	
	/*
	 * Utility functions
	 */
	private boolean isTimerServiceRunning() {
	    ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
	    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
	        if (TimerService.class.getName().equals(service.service.getClassName())) {
	            return true;
	        }
	    }
	    return false;
	}
	
	private void finishAndStopTheService() {
		xintent = new Intent(this, TimerService.class);
		stopService(xintent);
		finish();
	}	
}
