package lu.mir.android.pomodorobox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.dropbox.sync.android.DbxAccountManager;

public class MainActivity extends Activity {
	public final static String DBX_APP_KEY = "91kr6dmol3ta60l";
	public final static String DBX_APP_SECRET = "4wkjx70xpfl0yqu";

	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
	public final static String EXTRA_TIME_IN_SECONDS = "com.example.myfirstapp.TIME";
	
	final String welcomeScreenShownPref = "welcomeScreenShown";
	
	private static final int POMODORO_DURATION = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		DbxAccountManager mDbxAcctMgr = DbxAccountManager.getInstance(
				getApplicationContext(), DBX_APP_KEY, DBX_APP_SECRET);

		if (!mDbxAcctMgr.hasLinkedAccount()) {
			showWelcomeScreen();
		}

		setContentView(R.layout.activity_main);

		showKeyboardWhenStartingActivity();
	}

	private void showKeyboardWhenStartingActivity() {
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
	}

	public void unlinkFromDropbox(View view) {
		DbxAccountManager mDbxAcctMgr = DbxAccountManager.getInstance(
				getApplicationContext(), DBX_APP_KEY, DBX_APP_SECRET);
		mDbxAcctMgr.unlink();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/** Called when the user clicks the Send button */
	public void startCounter(View view) {
		Intent intent = new Intent(this, CountdownActivity.class);
		EditText editText = (EditText) findViewById(R.id.edit_message);
		String message = editText.getText().toString();
		intent.putExtra(EXTRA_MESSAGE, message);
		intent.putExtra(EXTRA_TIME_IN_SECONDS, (long)60 * POMODORO_DURATION + 1);
		startActivity(intent);
	}
	
	/** Called when the user clicks the Send button */
	public void startBlitzCounter(View view) {
		Intent intent = new Intent(this, CountdownActivity.class);
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
