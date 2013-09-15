package lu.mir.android.pomodorobox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
	public final static String EXTRA_TIME_IN_SECONDS = "com.example.myfirstapp.TIME";
	
	final String welcomeScreenShownPref = "welcomeScreenShown";
	
	private static final int POMODORO_DURATION = 25;
	
	 SimpleCursorAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		showLinkToDropboxScreenIfNecesary();
		setContentView(R.layout.activity_main);
		showTotalLoggedPomodoros();
		showLastLoggedPomodoros();
		showKeyboardWhenStartingActivity();
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		showTotalLoggedPomodoros();
		showLastLoggedPomodoros();
		Log.d(EXTRA_MESSAGE, "resuming...");
	}

	private void showTotalLoggedPomodoros() {
		TextView totalLoggedPomodoros = (TextView) findViewById(R.id.totalLoggedPomodoros);
		totalLoggedPomodoros.setText("Until now:" + DropBoxConnection.countPomodoros());
	}
	
	private void showLastLoggedPomodoros() {
		ListView lastPomodoros = (ListView) findViewById(R.id.lastPomodoros);
		
		ArrayAdapter<String> arrayAdapter =      
			         new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, DropBoxConnection.last3Pomodoros());
		 lastPomodoros.setAdapter(arrayAdapter);  
	}
	


	private void showLinkToDropboxScreenIfNecesary() {
		if (!DropBoxConnection.getAccountManager().hasLinkedAccount()) {
			showWelcomeScreen();
		}
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
		intent.putExtra(EXTRA_MESSAGE, message);
		intent.putExtra(EXTRA_TIME_IN_SECONDS, (long)60 * POMODORO_DURATION + 1);
		startActivity(intent);
	}
	
	/** Called when the user clicks the Send button */
	public void startBlitzCounter(View view) {
		Intent intent = new Intent(this, TimerActivity.class);
		EditText editText = (EditText) findViewById(R.id.edit_message);
		String message = editText.getText().toString();
		intent.putExtra(EXTRA_MESSAGE, message);
		intent.putExtra(EXTRA_TIME_IN_SECONDS, (long)10);
		startActivity(intent);
	}	
	

	public void showWelcomeScreen() {
		Intent intent = new Intent(this, WelcomeActivity.class);
		startActivity(intent);
	}

}
