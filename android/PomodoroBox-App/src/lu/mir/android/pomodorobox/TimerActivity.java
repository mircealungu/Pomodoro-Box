package lu.mir.android.pomodorobox;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.TextView;

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
	public final static String EXTRA_POMODORO = "lu.mir.android.pomodorobox.POMODORO";

	/*
	 * The broadcast receiver is registered when the app gets in the foreground
	 * and de-registerd when it is in the background. This means that we handle
	 * no callbacks when not in the foreground The callback that gets called is
	 * updateTheTimerComponent
	 */
	Boolean myReceiverIsRegistered = false;
	private SecondElapsedReceiver broadcastReceiver = null;
	private Intent timerServiceIntent;
	private Pomodoro pomodoro;
	PomodoroDatabase db;

	/*
	 * This is the callback of the timer service
	 */
	class SecondElapsedReceiver extends BroadcastReceiver {

		private int alreadyShowingBreak;

		@Override
		public void onReceive(Context context, Intent intent) {
			long time = intent.getLongExtra(
					TimerService.TIMER_BROADCAST_MESSAGE_PAYLOAD, 0);
			int state = intent.getIntExtra(
					TimerService.TIMER_BROADCAST_MESSAGE_PAYLOAD_STATE, 0);

			if (state == TimerService.STATE_DONE) {
				finish();
				return;
			}

			if (state == TimerService.STATE_BREAK) {
				if (alreadyShowingBreak != TimerService.STATE_BREAK)
					inflateBreakLayout();
				alreadyShowingBreak = TimerService.STATE_BREAK;
			}

			updateTheTimerComponent(time);

		}
	}

	@Override
	public void finish() {
		super.finish();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Initialize
		broadcastReceiver = new SecondElapsedReceiver();

		startTheTimerService(savedInstanceState);

		// Set the text view as the activity layout
		setContentView(R.layout.activity_countdown);
		TextView activityView = (TextView) findViewById(R.id.activity);
		activityView.setText(pomodoro.getPomodoroName());
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
	}

	@Override
	protected void onResume() {
		if (!myReceiverIsRegistered) {
			registerReceiver(broadcastReceiver, new IntentFilter(
					TimerService.TIMER_BROADCAST_MESSAGE));
			myReceiverIsRegistered = true;
			if (!isTimerServiceRunning()) {
				finish();
			}
		} else {
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

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	/**
	 * Update the timer component Gets called every second
	 * 
	 * @param millisUntilFinished
	 */
	private void updateTheTimerComponent(long millisUntilFinished) {

		long minsToFinish = millisUntilFinished / 1000 / 60;
		long secs = millisUntilFinished / 1000 % 60;

		String minuteString = ((minsToFinish < 10) ? "0" : "") + minsToFinish;
		String secondsString = (secs < 10 ? "0" : "") + secs;

		TextView counterView = (TextView) findViewById(R.id.counter);

		counterView.setText(minuteString + ":" + secondsString);
	}
	
	/*
	 * The Bundle instance comes from OnCreate()
	 */

	private void startTheTimerService(Bundle savedInstanceState) {
		Intent intent = getIntent();
		pomodoro = (Pomodoro) intent
				.getSerializableExtra(TimerActivity.EXTRA_POMODORO);
		db = (PomodoroDatabase) intent
				.getSerializableExtra(MainActivity.DB);

		if (savedInstanceState == null) {
			timerServiceIntent = new Intent(this, TimerService.class);
			timerServiceIntent.putExtra(TimerActivity.EXTRA_POMODORO, pomodoro);
			timerServiceIntent.putExtra(MainActivity.DB, db);
			startService(timerServiceIntent);
		}
	}

	/*
	 * Utility functions
	 */
	private boolean isTimerServiceRunning() {
		ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		for (RunningServiceInfo service : manager
				.getRunningServices(Integer.MAX_VALUE)) {
			if (TimerService.class.getName().equals(
					service.service.getClassName())) {
				return true;
			}
		}
		return false;
	}

	private void finishAndStopTheService() {
		timerServiceIntent = new Intent(this, TimerService.class);
		stopService(timerServiceIntent);
		finish();
	}
	
	private void inflateBreakLayout() {
		setContentView(R.layout.activity_break_countdown);
		TextView activityView = (TextView) findViewById(R.id.activity);
		activityView.setText(pomodoro.getPomodoroName());
	}

}
