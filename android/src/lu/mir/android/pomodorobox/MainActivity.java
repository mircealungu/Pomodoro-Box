package lu.mir.android.pomodorobox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	final String welcomeScreenShownPref = "welcomeScreenShown";
	
	 SimpleCursorAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		showTotalLoggedPomodoros();
		showKeyboardWhenStartingActivity();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		showTotalLoggedPomodoros();
		//showLastLoggedPomodoros();
	}

	private void showTotalLoggedPomodoros() {
		TextView totalLoggedPomodoros = (TextView) findViewById(R.id.totalLoggedPomodoros);
		totalLoggedPomodoros.setText("Until now:" + DropBoxConnection.countPomodoros());
	}

	private void showKeyboardWhenStartingActivity() {
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/** Called when the user clicks the Send button */
	public void startCounter(View view) {
		Intent intent = new Intent(this, TimerActivity.class);
		EditText editText = (EditText) findViewById(R.id.edit_message);
		String message = editText.getText().toString();
		intent.putExtra(TimerActivity.EXTRA_POMODORO_NAME, message);
		intent.putExtra(TimerActivity.EXTRA_POMODORO_DURATION, TimerActivity.POMODORO_DURATION);
		intent.putExtra(TimerActivity.EXTRA_POMODORO_BREAK_DURATION, TimerActivity.POMODORO_BREAK_DURATION);
		startActivity(intent);
	}
	
	/** Called when the user clicks the Send button */
	public void startBlitzCounter(View view) {
		Intent intent = new Intent(this, TimerActivity.class);
		EditText editText = (EditText) findViewById(R.id.edit_message);
		String message = editText.getText().toString();
		intent.putExtra(TimerActivity.EXTRA_POMODORO_NAME, message);
		intent.putExtra(TimerActivity.EXTRA_POMODORO_DURATION, TimerActivity.BLITZ_DURATION);
		intent.putExtra(TimerActivity.EXTRA_POMODORO_BREAK_DURATION, TimerActivity.BLITZ_BREAK_DURATION);
		startActivity(intent);
	}	
	

	public void showWelcomeScreen() {
		Intent intent = new Intent(this, WelcomeActivity.class);
		startActivity(intent);
	}

}
