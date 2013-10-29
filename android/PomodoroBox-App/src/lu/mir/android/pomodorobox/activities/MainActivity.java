package lu.mir.android.pomodorobox.activities;

import static lu.mir.android.pomodorobox.util.Duration.BLITZ_BREAK_DURATION;
import static lu.mir.android.pomodorobox.util.Duration.BLITZ_DURATION;
import static lu.mir.android.pomodorobox.util.Duration.POMODORO_BREAK_DURATION;
import static lu.mir.android.pomodorobox.util.Duration.POMODORO_DURATION;
import lu.mir.android.pomodorobox.Pomodoro;
import lu.mir.android.pomodorobox.PomodoroBoxApplication;
import lu.mir.android.pomodorobox.R;
import lu.mir.android.pomodorobox.persistence.PomodoroDatabase;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	final String welcomeScreenShownPref = "welcomeScreenShown";
	PomodoroDatabase db;
	public static final String DB = "lu.mir.android.pomodorobox.DB";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		showKeyboardWhenStartingActivity();
		
		if (savedInstanceState == null) {
			  Bundle b = getIntent().getExtras();
			  db = (PomodoroDatabase)b.getSerializable(MainActivity.DB);
			  showTotalLoggedPomodoros();
		 }
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		showTotalLoggedPomodoros();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void showTotalLoggedPomodoros() {
		TextView totalLoggedPomodoros = (TextView) findViewById(R.id.totalLoggedPomodoros);
		totalLoggedPomodoros.setText("Until now:" + db.countPomodoros(PomodoroBoxApplication.context()) );
	}

	private void showKeyboardWhenStartingActivity() {
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
	}


	/** Called when the user clicks the Send button */
	public void startCounter(View view) {
		Intent intent = new Intent(this, TimerActivity.class);
		EditText editText = (EditText) findViewById(R.id.edit_message);
		String message = editText.getText().toString();
		
		Pomodoro newPomodoro = new Pomodoro(message, POMODORO_DURATION, POMODORO_BREAK_DURATION);
		intent.putExtra(TimerActivity.EXTRA_POMODORO, newPomodoro);
		intent.putExtra(DB, db);
		startActivity(intent);
	}
	
	/** Called when the user clicks the Send button */
	public void startBlitzCounter(View view) {
		Intent intent = new Intent(this, TimerActivity.class);
		EditText editText = (EditText) findViewById(R.id.edit_message);
		String message = editText.getText().toString();
		
		Pomodoro newPomodoro = new Pomodoro(message, BLITZ_DURATION, BLITZ_BREAK_DURATION);
		intent.putExtra(TimerActivity.EXTRA_POMODORO, newPomodoro);
		intent.putExtra(DB, db);
		startActivity(intent);
	}	
	
	public void showWelcomeScreen() {
		Intent intent = new Intent(this, WelcomeActivity.class);
		startActivity(intent);
	}

}
